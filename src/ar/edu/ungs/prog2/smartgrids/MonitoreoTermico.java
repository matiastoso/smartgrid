package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class MonitoreoTermico extends Monitoreo {
	private double co2Emitido;
    private double combustibleUtilizado;

	public MonitoreoTermico(LocalDateTime fechaHora, double energiaGenerada, int incidencias, double co2Emitido, double combustibleUtilizado) {
		super(fechaHora, energiaGenerada, incidencias);
		this.co2Emitido = co2Emitido;
		this.combustibleUtilizado = combustibleUtilizado;
	}
	
	public double getCo2Emitido() { return co2Emitido; }
    public double getCombustibleUtilizado() { return combustibleUtilizado; }
}
