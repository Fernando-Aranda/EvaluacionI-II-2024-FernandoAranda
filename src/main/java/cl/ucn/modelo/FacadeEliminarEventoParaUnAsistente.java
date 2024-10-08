package cl.ucn.modelo;

import java.util.List;

import com.jcabi.log.Logger;

import cl.ucn.main.Main;
import jakarta.persistence.EntityManager;

public class FacadeEliminarEventoParaUnAsistente implements FacadeInterfaz {

	private EntityManager em;
	private Asistente asistente;

	public FacadeEliminarEventoParaUnAsistente(Asistente asistente, EntityManager em) {
		super();
		this.asistente = asistente;
		this.em = em;
	}

	private void EliminarEvento() {
		Logger.info(Main.class, "El asistente " + asistente.getNombre() + " con email " + asistente.getEmail());
		Logger.info(Main.class, "Asiste a los siguientes eventos:");
		List<Evento> eventoAsiste = asistente.getEventos();
		for (Evento evento : eventoAsiste) {
			Logger.info(Main.class, evento.getNombre());
		}
		Logger.info(Main.class, "Actualiza eliminando el festival de jazz del asistente");
		Evento eventoRemueve = em.find(Evento.class, 3);
		asistente.getEventos().remove(eventoRemueve);
		eventoRemueve.getAsistentes().remove(asistente);
		em.merge(asistente);
		Logger.info(Main.class, "Ahora solo asistirá a: ");
		for (Evento evento : eventoAsiste) {
			Logger.info(Main.class, evento.getNombre());
		}
		em.getTransaction().commit();

	}

	@Override
	public void ejecutar() {
		EliminarEvento();

	}

}
