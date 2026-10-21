package ar.edu.ungs.prog2.smartgrids;

import java.util.HashMap;

public abstract class Planta {
	private String nombre;
	private String direccion;
	private double capacidadMaxima;
	private String fechaHoraPuestaEnFuncionamiento;
	private boolean estaActiva;
	
	public Planta(String nombre, String direccion, double capacidadMaxima,  String fechaHoraPuestaEnFuncionamiento) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxima = capacidadMaxima;
		this.estaActiva = true;
		this.fechaHoraPuestaEnFuncionamiento = fechaHoraPuestaEnFuncionamiento;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Boolean getEstaActiva() {
		return estaActiva;
	}
	
	public String getFechaHoraPuestaEnFuncionamiento() {
		return fechaHoraPuestaEnFuncionamiento;
	}
	
	public double getCapacidadMaxima() {
		return capacidadMaxima;
	}
	
	public abstract double calcularCapacidadMinimaCuota ();
}
