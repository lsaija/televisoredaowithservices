package it.prova.televisoredaowithservices.service.televisore;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.dao.televisore.TelevisoreDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public interface TelevisoreService {
	public void setTelevisoreDao(TelevisoreDAO televisoreDao);

	public List<Televisore> listAll() throws Exception;

	public Televisore findById(Long idInput) throws Exception;

	public int aggiorna(Televisore input) throws Exception;

	public int inserisciNuovo(Televisore input) throws Exception;

	public int rimuovi(Televisore input) throws Exception;

	public List<Televisore> findByExample(Televisore input) throws Exception;

	// ################################################

	public List<Televisore> cercaProdottiNelIntervallo(Date inizio, Date fine) throws Exception;

	public Televisore cercaIlPiuGrande() throws Exception;

	public List<String> cercaProdottiNegliUltimiSeiMesi() throws Exception;

}
