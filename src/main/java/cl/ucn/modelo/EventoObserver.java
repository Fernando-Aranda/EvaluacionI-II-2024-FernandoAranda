package cl.ucn.modelo;

public class EventoObserver implements Observer {
	private Asistente asistente;
	public EventoObserver(Asistente asistente) {
		this.asistente = asistente;
	}

	@Override
	public void observar(String dato) {
		System.out.println(asistente.getNombre()+"A sido notificado con el siguiente cambio: "+dato);

	}

}
