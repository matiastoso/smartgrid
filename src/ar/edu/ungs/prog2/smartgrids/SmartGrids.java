package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartGrids implements ISmartGrids {
	private HashMap<String, Planta> plantas;
	
	public SmartGrids () {
		plantas = new HashMap<>();
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

		PlantaTermica plantaNueva = new PlantaTermica(nombre, direccion, capacidadMaxima, true, fechaHoraPuestaEnFuncionamiento, tipoCombustible, capacidadAlmacenamientoCombustible, capacidadAlmacenamientoCombustible);
		plantas.put(nombre, plantaNueva);

	}
	
	@Override
	public List<String> consultarPlantas() {
		List<String> plantasExistentes = new ArrayList<>();

		for (Planta planta : plantas.values()) {
			String estadoActual = (planta.getEstaActiva()) ? "ACTIVA" : "INACTIVA";
			String itemPlanta = String.format("%s (%s): %s", planta.getNombre(),  estadoActual, planta.getFechaHoraPuestaEnFuncionamiento());
			plantasExistentes.add(itemPlanta);
		}
		
		return plantasExistentes;
	}
	
	public void validarDatosPlanta(String nombre, String direccion, double capacidadMaxima,  String fechaHoraPuestaEnFuncionamiento) {
		if (plantas.containsKey(nombre) || nombre == null || nombre == "" ||
			direccion == null || direccion == "" || capacidadMaxima <= 0 ||
			fechaHoraPuestaEnFuncionamiento == null ||
			fechaHoraPuestaEnFuncionamiento == "") {
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
		
		plantas.get(nombre).setEstaActiva(true);
	}

	@Override
	public void desactivarPlanta(String nombre, String fechaHora) {
		if (!plantas.containsKey(nombre)) {
			throw new RuntimeException("ERROR: no existe la planta");
		}
		validarFecha(fechaHora);
		
		plantas.get(nombre).setEstaActiva(false);
	}

	private void validarFecha(String fechaHora) {
		if (fechaHora == null || fechaHora == "") {
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
		
		if (plantaSeleccionada.getEstaActiva() == false) {
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
		
		if (plantaSeleccionada.getEstaActiva() == false) {
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
		
		if (plantaSeleccionada.getEstaActiva() == false) {
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] incidenciasPorHora(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> plantasRenovablesPorFuente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> plantasQueNuncaCumplieronCuota() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String horasSinVelocidadMinimaPorPlantaEolica() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int limpiarMonitoreosSinDatos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> plantasOrdenadasPorEnergiaGenerada() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> plantasInactivasEn(String fechaHora) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuilder mensaje = new StringBuilder();
		
		ArrayList<Planta> listaPlantas = new ArrayList<>();
		
		listaPlantas.addAll(plantas.values());	
		
		for (Planta planta : plantas.values()) {
			listaPlantas.add(planta);
		}
		
		System.out.println(plantas.values());
		
		return mensaje.toString();
	}

}
