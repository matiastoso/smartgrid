package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;

public abstract class Planta {
    private String nombre;
    private String direccion;
    private double capacidadMaxima;
    private String fechaHoraPuestaEnFuncionamiento;
    private boolean estaActiva;
    private HashMap<LocalDateTime, Monitoreo> registroMonitoreos;
    
    public Planta(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMaxima = capacidadMaxima;
        this.estaActiva = true;
        this.fechaHoraPuestaEnFuncionamiento = fechaHoraPuestaEnFuncionamiento;
        this.registroMonitoreos = new HashMap<>();
    }

    public String getNombre() { return nombre; }
    
    public Boolean getEstaActiva() { return estaActiva; }
    
    public void setEstaActiva(boolean nuevoEstado) { this.estaActiva = nuevoEstado; }
    
    public String getFechaHoraPuestaEnFuncionamiento() { return fechaHoraPuestaEnFuncionamiento; }
    
    public double getCapacidadMaxima() { return capacidadMaxima; }
    
    public abstract double calcularCapacidadMinimaCuota();
    
    public void agregarMonitoreo(Monitoreo monitoreo) {
        LocalDateTime fechaHora = monitoreo.getFechaHora();
        
        if (registroMonitoreos.containsKey(fechaHora)) {
            throw new RuntimeException("Ya existe un registro para esta hora.");
        }
        
        if (fechaHora.isBefore(LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento))) {
            throw new RuntimeException("La fecha es anterior a la puesta en marcha.");
        }
        
        if (monitoreo.getEnergiaGenerada() < 0 || monitoreo.getIncidencias() < 0) {
            throw new RuntimeException("La energía o las incidencias no pueden ser negativas.");
        }

        registroMonitoreos.put(fechaHora, monitoreo);
    }

    public double calcularTotalGenerado() {
        double total = 0;
        for (Monitoreo monitoreo : registroMonitoreos.values()) {
            total += monitoreo.getEnergiaGenerada();
        }
        return total;
    }

    public int[] calcularIncidenciasPorHora() {
        int[] incidenciasPorHora = new int[24];
        for (Monitoreo monitoreo : registroMonitoreos.values()) {
            int hora = monitoreo.getFechaHora().getHour();
            incidenciasPorHora[hora] += monitoreo.getIncidencias();
        }
        return incidenciasPorHora;
    }
  
    public int[] calcularHistograma(LocalDate desde, LocalDate hasta) {
        int[] histograma = new int[24];
        for (Monitoreo monitoreo : registroMonitoreos.values()) {
            LocalDate fecha = monitoreo.getFechaHora().toLocalDate();
            if (!fecha.isBefore(desde) && !fecha.isAfter(hasta)) {
                int hora = monitoreo.getFechaHora().getHour();
                if (cumpleCuota(monitoreo)) {
                    histograma[hora]++;
                }
            }
        }
        return histograma;
    }

    public boolean cumpleCuota(Monitoreo monitoreo) {
        return monitoreo.cumpleCuota(calcularCapacidadMinimaCuota());
    }
  
    public String fuenteRenovable() {
        return null;
    }

    public boolean nuncaCumplioCuota() {
        for (Monitoreo monitoreo : registroMonitoreos.values()) {
            if (cumpleCuota(monitoreo)) {
                return false; 
            }
        }
        return true; 
    }
        
    public int limpiarMonitoreosSinDatos() {
        int cantidadBorrados = 0;
        Iterator<Monitoreo> iterador = registroMonitoreos.values().iterator();
        while (iterador.hasNext()) { 
            Monitoreo monitoreo = iterador.next(); 
            if (monitoreo.getEnergiaGenerada() == 0 && monitoreo.getIncidencias() == 0) {
                iterador.remove(); 
                cantidadBorrados++;
            }
        }
        return cantidadBorrados;
    }

    public int contarHorasSinViento() {
        int horas = 0;
        for (Monitoreo monitoreo : registroMonitoreos.values()) {
            if (monitoreo instanceof MonitoreoEolico) {
                MonitoreoEolico monitoreoEolico = (MonitoreoEolico) monitoreo;
                if (monitoreoEolico.getVelocidadViento() <= 0) {
                    horas++;
                }
            }
        }
        return horas;
    }
    
    public java.util.List<String> obtenerHorasSinVelocidadMinima(double velocidadMinima) {
        java.util.List<String> horasAfectadas = new java.util.ArrayList<>();
        double[] maxVientoPorHora = new double[24];
        boolean[] horaRegistrada = new boolean[24];
        
        for (Monitoreo m : this.registroMonitoreos.values()) {
            if (m instanceof MonitoreoEolico) {
                MonitoreoEolico me = (MonitoreoEolico) m;
                int hora = me.getFechaHora().getHour();
                horaRegistrada[hora] = true;
                if (me.getVelocidadViento() > maxVientoPorHora[hora]) {
                    maxVientoPorHora[hora] = me.getVelocidadViento();
                }
            }
        }
        
        for (int i = 0; i < 24; i++) {
            if (horaRegistrada[i] && maxVientoPorHora[i] < velocidadMinima) {
                horasAfectadas.add(String.format("%02d", i));
            }
        }
        return horasAfectadas;
    }
}