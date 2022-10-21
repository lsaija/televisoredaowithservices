package it.prova.televisoredaowithservices.test;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.model.Televisore;
import it.prova.televisoredaowithservices.service.MyServiceFactory;
import it.prova.televisoredaowithservices.service.televisore.TelevisoreService;

public class TestTelevisore {

	public static void main(String[] args) {

		TelevisoreService televisoreService = MyServiceFactory.getTelevisoreServiceImpl();
		try {

			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testInserimentoNuovoTelevisore(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testRimozioneTelevisore(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testInserimentoNuovoTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testInserimentoNuovoTelevisore inizio.............");

		Televisore televisoreInstance = new Televisore("samsung", "uu3", 65, new Date());
		if (televisoreService.inserisciNuovo(televisoreInstance) != 1)
			throw new RuntimeException("testInserimentoNuovoTelevisore FAILED ");

		System.out.println(".......testInserimentoNuovoTelevisore PASSED.............");
	}

	private static void testRimozioneTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testRimozioneTelevisore inizio.............");

		List<Televisore> elencoTabella = televisoreService.listAll();
		if (elencoTabella.isEmpty() || elencoTabella.get(0) == null)
			throw new Exception("Nessun elemento da rimuovere");

		Long idPrimo = elencoTabella.get(0).getId();

		Televisore daRimuovere = televisoreService.findById(idPrimo);
		if (televisoreService.rimuovi(daRimuovere) != 1)
			throw new RuntimeException("testRimozioneTelevisore FAILED ");

		System.out.println(".......testRimozioneTelevisore PASSED.............");
	}

	private static void testFindByExample(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindByExample inizio.............");

		televisoreService.inserisciNuovo(new Televisore("LG", "slim4", 55, new Date()));
		televisoreService.inserisciNuovo(new Televisore("philips", "V6", 65, new Date()));

		List<Televisore> result = televisoreService.findByExample(new Televisore("ph"));
		if (result.size() != 2)
			throw new RuntimeException("testFindByExample FAILED ");

		for (Televisore televisoreItem : result) {
			televisoreService.rimuovi(televisoreItem);
		}

		System.out.println(".......testFindByExample PASSED.............");
	}

}
