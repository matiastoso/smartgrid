package ar.edu.ungs.prog2.smartgrids;

public class PlantaTermica extends Planta {
	private String tipoCombustible;
	private double objetivoEmisionesCo2PorHora;
    private double capacidadAlmacenamientoCombustible;

	public PlantaTermica(String nombre, String direccion, double capacidadMaxima, boolean estaActiva, String fechaHoraPuestaEnFuncionamiento, String tipoCombustible, double objetivoEmisionesCo2PorHora, double capacidadAlmacenamientoCombustible) {
		super(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento);

		this.tipoCombustible = tipoCombustible;
		this.objetivoEmisionesCo2PorHora = objetivoEmisionesCo2PorHora;
		this.capacidadAlmacenamientoCombustible = capacidadAlmacenamientoCombustible;
	}

	@Override
	public String toString() {
		return super.getNombre();
	}

	@Override
	public double calcularCapacidadMinimaCuota() {
		return super.getCapacidadMaxima() / 2.0;
	}
}
