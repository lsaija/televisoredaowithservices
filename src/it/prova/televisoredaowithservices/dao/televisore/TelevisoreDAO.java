package it.prova.televisoredaowithservices.dao.televisore;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.dao.IBaseDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public interface TelevisoreDAO extends IBaseDAO<Televisore> {

	public List<Televisore> findAllCreatedInRangeDates(Date inizio, Date fine) throws Exception;

	public Televisore findByMaxInch() throws Exception;

	public List<String> findAllCreatedInLastSixMonths() throws Exception;

}
