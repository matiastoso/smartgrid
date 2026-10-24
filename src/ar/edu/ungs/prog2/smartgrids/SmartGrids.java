package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.TreeMap;

public class SmartGrids implements ISmartGrids {
    private LinkedHashMap<String, Planta> plantas;
    
    public SmartGrids () {
    	plantas = new LinkedHashMap<>();
    }
    
    @Override
    public void registrarPlantaSolar(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, double superficieCubierta, int cantidadPaneles, double horasRadiacionDiaria) {
        validarDatosPlanta(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento);
        PlantaSolar plantaNueva = new PlantaSolar(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio, superficieCubierta, cantidadPaneles, horasRadiacionDiaria);
        plantas.put(nombre, plantaNueva);
    }
    
    @Override
    public void registrarPlantaEolica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadAerogeneradores, double alturaTorres, double velocidadMinimaViento) {
        validarDatosPlanta(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento);
        PlantaEolica plantaNueva = new PlantaEolica(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio, velocidadMinimaViento, cantidadAerogeneradores, velocidadMinimaViento);
        plantas.put(nombre, plantaNueva);
    }

    @Override
    public void registrarPlantaHidraulica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas, double capacidadEmbalse) {
        validarDatosPlanta(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento);
        PlantaHidraulica plantaNueva = new PlantaHidraulica(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio, cantidadTurbinas, capacidadEmbalse);
        plantas.put(nombre, plantaNueva);
    }

    @Override
    public void registrarPlantaTermica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String tipoCombustible, double objetivoEmisionesCo2PorHora, double capacidadAlmacenamientoCombustible) {
        validarDatosPlanta(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento);
        PlantaTermica plantaNueva = new PlantaTermica(nombre, direccion, capacidadMaxima, true, fechaHoraPuestaEnFuncionamiento,tipoCombustible, objetivoEmisionesCo2PorHora, capacidadAlmacenamientoCombustible);
        plantas.put(nombre, plantaNueva);
    }
    
    @Override
    public List<String> consultarPlantas() {
        List<String> plantasExistentes = new ArrayList<>();
        for (String nombrePlanta : plantas.keySet()) {
            Planta planta = plantas.get(nombrePlanta);
            String estadoActual = (planta.getEstaActiva()) ? "ACTIVA" : "INACTIVA";
            String itemPlanta = String.format("%s (%s): %s", planta.getNombre(), estadoActual, planta.getFechaHoraPuestaEnFuncionamiento());
            plantasExistentes.add(itemPlanta);
        }
        
        return plantasExistentes;
    }
    
    public void validarDatosPlanta(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento) {
        if (plantas.containsKey(nombre) || nombre == null || nombre.equals("") ||
            direccion == null || direccion.equals("") || capacidadMaxima <= 0 ||
            fechaHoraPuestaEnFuncionamiento == null || fechaHoraPuestaEnFuncionamiento.equals("")) {
                throw new RuntimeException("Error al registrar Planta");
        }
    }

    @Override
    public double capacidadMinimaCuota(String nombre) {
        Planta planta = plantas.get(nombre);
        if (planta == null) {
            throw new RuntimeException("No existe la planta");
        }
        return planta.calcularCapacidadMinimaCuota();
    }

    @Override
    public void activarPlanta(String nombre, String fechaHora) {
        if (!plantas.containsKey(nombre)) {
            throw new RuntimeException("ERROR: no existe la planta");
        }
        validarFecha(fechaHora);
        plantas.get(nombre).activar(LocalDateTime.parse(fechaHora));
    }

 
    @Override
    public void desactivarPlanta(String nombre, String fechaHora) {
        if (!plantas.containsKey(nombre)) {
            throw new RuntimeException("ERROR: no existe la planta");
        }
        validarFecha(fechaHora);
        plantas.get(nombre).desactivar(LocalDateTime.parse(fechaHora));
    }

    private void validarFecha(String fechaHora) {
        if (fechaHora == null || fechaHora.equals("")) {
            throw new RuntimeException("La fecha no debe estar vacia");
        }
        try {
            LocalDateTime.parse(fechaHora);
        } catch(DateTimeParseException e) {
            throw new RuntimeException("ERROR :" + e.getMessage());
        }
    }

    @Override
    public boolean estaActiva(String nombre) {
        if (!plantas.containsKey(nombre)) {
            throw new RuntimeException("ERROR: no existe la planta");
        }
        return plantas.get(nombre).getEstaActiva();
    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {
        validarFecha(fechaHora);
        if (!plantas.containsKey(nombre)) {
            throw new RuntimeException("ERROR: no existe la planta");
        }       
        Planta plantaSeleccionada = plantas.get(nombre);
        if (!plantaSeleccionada.getEstaActiva()) {
            throw new RuntimeException("ERROR: la planta debe estar activa para registrar un monitoreo");
        }
        Monitoreo monitoreo = new Monitoreo(LocalDateTime.parse(fechaHora), energiaGenerada, incidencias);
        plantaSeleccionada.agregarMonitoreo(monitoreo);
    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double velocidadViento) {
        validarFecha(fechaHora);
        if (!plantas.containsKey(nombre)) {
            throw new RuntimeException("ERROR: no existe la planta");
        }		
        
        Planta plantaSeleccionada = plantas.get(nombre);
        
        if (!(plantaSeleccionada instanceof PlantaEolica)) {
            throw new RuntimeException("ERROR: La planta no es eólica");
        }
        
        if (!plantaSeleccionada.getEstaActiva()) {
            throw new RuntimeException("ERROR: la planta debe estar activa para registrar un monitoreo");
        }
        
        if (velocidadViento < 0) {
            throw new RuntimeException("La velocidad del viento no puede ser negativa");
        }
        
        MonitoreoEolico monitoreo = new MonitoreoEolico(LocalDateTime.parse(fechaHora), energiaGenerada, incidencias, velocidadViento);
        plantaSeleccionada.agregarMonitoreo(monitoreo);
    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double co2Emitido, double combustibleUtilizado) {
        validarFecha(fechaHora);
        if (!plantas.containsKey(nombre)) {
            throw new RuntimeException("ERROR: no existe la planta");
        }		
        
        Planta plantaSeleccionada = plantas.get(nombre);
        
        if (!(plantaSeleccionada instanceof PlantaTermica)) {
            throw new RuntimeException("ERROR: La planta no es térmica");
        }
        
        if (!plantaSeleccionada.getEstaActiva()) {
            throw new RuntimeException("ERROR: la planta debe estar activa para registrar un monitoreo");
        }
        
        if (co2Emitido < 0 || combustibleUtilizado < 0) {
            throw new RuntimeException("El CO2 o el combustible no pueden ser negativos.");
        }
        
        MonitoreoTermico monitoreo = new MonitoreoTermico(LocalDateTime.parse(fechaHora), energiaGenerada, incidencias, co2Emitido, combustibleUtilizado);
        plantaSeleccionada.agregarMonitoreo(monitoreo);
    }

    @Override
    public double totalGenerado(String nombre) {
        Planta planta = plantas.get(nombre);
        if (planta == null) {
            throw new RuntimeException("No existe la planta");
        }
        return planta.calcularTotalGenerado();
    }

    @Override
    public int[] incidenciasPorHora(String nombre) {
        Planta planta = plantas.get(nombre);
        if (planta == null) {
            throw new RuntimeException("No existe la planta");
        }
        return planta.calcularIncidenciasPorHora();
    }
   
    @Override
    public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
        Planta planta = plantas.get(nombre);
        if (planta == null) {
            throw new RuntimeException("No existe la planta");
        }
        LocalDate desde = LocalDate.parse(fechaDesde);
        LocalDate hasta = LocalDate.parse(fechaHasta);
        if (desde.isAfter(hasta)) {
            throw new RuntimeException("La fecha inicial es posterior a la final");
        }
        return planta.calcularHistograma(desde, hasta);
    }

    @Override
    public Map<String, Integer> plantasRenovablesPorFuente() {
        Map<String, Integer> resultado = new TreeMap<>();
        for (Planta planta : plantas.values()) {
            String fuente = planta.fuenteRenovable();
            if (fuente != null) {
                if (resultado.containsKey(fuente)) {
                    resultado.put(fuente, resultado.get(fuente) + 1);
                } else {
                    resultado.put(fuente, 1);
                }
            }
        }
        return resultado;
    }

    @Override
    public List<String> plantasQueNuncaCumplieronCuota() {
        List<String> resultado = new ArrayList<>();
        for (String nombrePlanta : plantas.keySet()) {
            Planta planta = plantas.get(nombrePlanta);
            if (planta.nuncaCumplioCuota()) {
                resultado.add(planta.getNombre());
            }
        }
        
        return resultado;
    }

    @Override
    public String horasSinVelocidadMinimaPorPlantaEolica() {
        StringBuilder reporte = new StringBuilder();
        
        for (String nombrePlanta : plantas.keySet()) {
            Planta planta = plantas.get(nombrePlanta);
            
            if (planta instanceof PlantaEolica) {
                PlantaEolica eolica = (PlantaEolica) planta;
                java.util.List<String> horas = eolica.obtenerHorasSinVelocidadMinima(eolica.getVelocidadMinimaViento());
                
                if (!horas.isEmpty()) {
                    reporte.append(eolica.toString())
                           .append(" - Horas: ")
                           .append(String.join(", ", horas))
                           .append("\n");
                }
            }
        }
        
        if (reporte.length() == 0) {
            return "No hay registros de plantas eólicas con horas sin velocidad mínima.";
        }
        return reporte.toString();
    }

    @Override
    public int limpiarMonitoreosSinDatos() {
        int totalBorrados = 0;
        for (Planta planta : plantas.values()) {
            totalBorrados += planta.limpiarMonitoreosSinDatos();
        }
        return totalBorrados;
    }

    @Override
    public List<String> plantasOrdenadasPorEnergiaGenerada() {
        List<Planta> listaPlantas = new ArrayList<>();
        for (Planta planta : plantas.values()) {
            listaPlantas.add(planta);
        }
        
        for (int i = 0; i < listaPlantas.size() - 1; i++) {
            for (int j = 0; j < listaPlantas.size() - 1 - i; j++) {
                double energiaActual = listaPlantas.get(j).calcularTotalGenerado();
                double energiaSiguiente = listaPlantas.get(j + 1).calcularTotalGenerado();
                if (energiaActual < energiaSiguiente) {
                    Planta auxiliar = listaPlantas.get(j);
                    listaPlantas.set(j, listaPlantas.get(j + 1));
                    listaPlantas.set(j + 1, auxiliar);
                }
            }
        }
        List<String> resultado = new ArrayList<>();
        for (Planta planta : listaPlantas) {
            resultado.add(planta.toString() + ": " + planta.calcularTotalGenerado());
        }       
        return resultado;
    }
    
    @Override
    public List<String> plantasInactivasEn(String fechaHora) {
        validarFecha(fechaHora);
        LocalDateTime consulta = LocalDateTime.parse(fechaHora);
        List<String> resultado = new ArrayList<>();
        for (Planta planta : plantas.values()) {
            if (planta.estaInactivaEn(consulta)) {
                resultado.add(planta.toString());
            }
        }
        return resultado;
    }
    
    @Override
    public String toString() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Sistema SmartGrids - Estado actual:\n");
        for (Planta planta : plantas.values()) {
            String estado = planta.getEstaActiva() ? "ACTIVA" : "INACTIVA";
            mensaje.append(" - ").append(planta.toString())
                   .append(" | Estado: ").append(estado)
                   .append("\n");
        }
        return mensaje.toString();
    }
}
