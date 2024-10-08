package cl.ucn.modelo;

import java.util.ArrayList;

import com.jcabi.log.Logger;

import cl.ucn.main.Main;
import jakarta.persistence.EntityManager;

public class FacadeAsociarEventoConAsistente implements FacadeInterfaz {

	private Asistente asistente;
	private EntityManager em;
	private Evento evento;

	public FacadeAsociarEventoConAsistente(EntityManager em, Asistente asistente, Evento evento) {
		super();
		this.em = em;
		this.asistente = asistente;
		this.evento = evento;
	}

	private void Asociar() {
		// Agregar Observers
		Observer observador = new EventoObserver(asistente);
		evento.agregarObserver(observador);

		asistente.getEventos().add(evento);
		evento.setAsistentes(new ArrayList<>());
		evento.getAsistentes().add(asistente);
		em.merge(asistente);
		em.getTransaction().commit();
		Logger.info(Main.class, "Se ha asociado el evento N° " + evento.getId() + " con " + asistente.getNombre());

	}

	@Override
	public void ejecutar() {
		Asociar();

	}

}
