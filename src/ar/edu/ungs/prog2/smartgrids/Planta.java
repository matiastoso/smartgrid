package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;
import java.util.HashMap;

public abstract class Planta {
	private String nombre;
	private String direccion;
	private double capacidadMaxima;
	private String fechaHoraPuestaEnFuncionamiento;
	private boolean estaActiva;
	private HashMap<LocalDateTime, Monitoreo> registroMonitoreos;
	
	public Planta(String nombre, String direccion, double capacidadMaxima,  String fechaHoraPuestaEnFuncionamiento) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxima = capacidadMaxima;
		this.estaActiva = true;
		this.fechaHoraPuestaEnFuncionamiento = fechaHoraPuestaEnFuncionamiento;
		registroMonitoreos = new HashMap<>();
	}

	public String getNombre() {
		return nombre;
	}
	
	public Boolean getEstaActiva() {
		return estaActiva;
	}
	
	public void setEstaActiva(boolean nuevoEstado) {
		this.estaActiva = nuevoEstado;
	}
	
	public String getFechaHoraPuestaEnFuncionamiento() {
		return fechaHoraPuestaEnFuncionamiento;
	}
	
	public double getCapacidadMaxima() {
		return capacidadMaxima;
	}
	
	public abstract double calcularCapacidadMinimaCuota ();
	
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
}
