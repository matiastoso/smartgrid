package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class Monitoreo {
	private LocalDateTime fechaHora;
	private double energiaGenerada;
	private int incidencias;
	
	public Monitoreo(LocalDateTime fechaHora, double energiaGenerada, int incidencias) {
		this.fechaHora = fechaHora;
		this.energiaGenerada = energiaGenerada;
		this.incidencias = incidencias;
	}
	
	public LocalDateTime getFechaHora() { 
		return fechaHora; 
	}
	
    public double getEnergiaGenerada() { 
    	return energiaGenerada; 
    }
    
    public int getIncidencias() { 
    	return incidencias;
    }
}
