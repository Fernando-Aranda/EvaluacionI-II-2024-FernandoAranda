package cl.ucn.modelo;

public class EventoObserver implements Observer {

	@Override
	public void notificar(String dato) {
		System.out.println(dato);

	}

}
