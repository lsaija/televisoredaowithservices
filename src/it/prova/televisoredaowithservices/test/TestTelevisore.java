package it.prova.televisoredaowithservices.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

			testCercaProdottiNelIntervallo(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testCercaIlPiuGrande(televisoreService);
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");

			testCercaProdottiNegliUltimiSeiMesi(televisoreService);
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

	private static void testCercaProdottiNelIntervallo(TelevisoreService televisoreService) throws Exception {

		System.out.println(".......testCercaProdottiNelIntervallo inizio.............");

		televisoreService.inserisciNuovo(
				new Televisore("LG", "slim4", 55, new SimpleDateFormat("dd-MM-yyyy").parse("03-06-2022")));
		televisoreService.inserisciNuovo(
				new Televisore("philips", "V6", 65, new SimpleDateFormat("dd-MM-yyyy").parse("03-05-2022")));

		Date dataInizio = new SimpleDateFormat("dd-MM-yyyy").parse("31-03-2022");
		Date dataFine = new SimpleDateFormat("dd-MM-yyyy").parse("21-10-2022");

		List<Televisore> result = televisoreService.cercaProdottiNelIntervallo(dataInizio, dataFine);

		if (result.size() != 2)
			throw new RuntimeException("testCercaProdottiNelIntervallo FAILED ");

		for (Televisore televisoreItem : result) {
			televisoreService.rimuovi(televisoreItem);
		}

		System.out.println(".......testCercaProdottiNelIntervallo PASSED.............");

	}

	private static void testCercaIlPiuGrande(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testCercaIlPiuGrande inizio.............");

		Televisore grande = new Televisore("philips", "V6", 1000,
				new SimpleDateFormat("dd-MM-yyyy").parse("03-05-2022"));
		televisoreService.inserisciNuovo(grande);

		Televisore result = televisoreService.cercaIlPiuGrande();

		if (result.getPollici() != grande.getPollici())
			throw new RuntimeException("testCercaProdottiNelIntervallo FAILED ");

		televisoreService.rimuovi(televisoreService.listAll().get(0));

		System.out.println(".......testCercaIlPiuGrande PASSED.............");

	}

	private static void testCercaProdottiNegliUltimiSeiMesi(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testCercaProdottiNegliUltimiSeiMesi inizio.............");

		televisoreService.inserisciNuovo(
				new Televisore("LG", "slim4", 55, new SimpleDateFormat("dd-MM-yyyy").parse("03-06-2022")));
		televisoreService.inserisciNuovo(
				new Televisore("philips", "V6", 65, new SimpleDateFormat("dd-MM-yyyy").parse("03-05-2022")));

		List<String> result = televisoreService.cercaProdottiNegliUltimiSeiMesi();

		if (result.size() < 2)
			throw new RuntimeException("testCercaProdottiNegliUltimiSeiMesi FAILED");
		televisoreService.rimuovi(televisoreService.listAll().get(0));
		televisoreService.rimuovi(televisoreService.listAll().get(0));

		System.out.println(".......testCercaProdottiNegliUltimiSeiMesi PASSED.............");
	}

}
