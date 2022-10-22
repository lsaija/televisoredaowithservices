package it.prova.televisoredaowithservices.dao.televisore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.dao.AbstractMySQLDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public class TelevisoreDAOImpl extends AbstractMySQLDAO implements TelevisoreDAO {

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Televisore> list() throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from televisore;")) {

			while (rs.next()) {
				Televisore televisoreTemp = new Televisore();
				televisoreTemp.setMarca(rs.getString("marca"));
				televisoreTemp.setModello(rs.getString("MODELLO"));
				televisoreTemp.setPollici(rs.getInt("POLLICI"));
				televisoreTemp.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
				televisoreTemp.setId(rs.getLong("ID"));
				result.add(televisoreTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Televisore get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Televisore result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from televisore where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Televisore();
					result.setMarca(rs.getString("MARCA"));
					result.setModello(rs.getString("MODELLO"));
					result.setPollici(rs.getInt("POLLICI"));
					result.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Televisore utenteInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (utenteInput == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO televisore (marca, modello, pollici, dataproduzione) VALUES (?, ?, ?, ?);")) {
			ps.setString(1, utenteInput.getMarca());
			ps.setString(2, utenteInput.getModello());
			ps.setInt(3, utenteInput.getPollici());
			ps.setDate(4, new java.sql.Date(utenteInput.getDataProduzione().getTime()));

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Televisore utenteInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (utenteInput == null || utenteInput.getId() == null || utenteInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE televisore SET marca=?, modello=?, pollici=?, dataproduzione=? where id=?;")) {

			ps.setString(1, utenteInput.getMarca());
			ps.setString(2, utenteInput.getModello());
			ps.setInt(3, utenteInput.getPollici());
			ps.setDate(4, new java.sql.Date(utenteInput.getDataProduzione().getTime()));
			ps.setLong(5, utenteInput.getId());

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Televisore utenteInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (utenteInput == null || utenteInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM televisore WHERE ID=?")) {
			ps.setLong(1, utenteInput.getId());

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Televisore> findByExample(Televisore input) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();

		String query = "select * from televisore where 1=1 ";
		if (input.getMarca() != null && !input.getMarca().isEmpty()) {
			query += " and marca like '" + input.getMarca() + "%' ";
		}

		if (input.getModello() != null && !input.getModello().isEmpty()) {
			query += " and modello like '" + input.getModello() + "%' ";
		}

		if (input.getPollici() < 0) {
			query += " and pollici like '" + input.getPollici() + "%' ";
		}

		if (input.getDataProduzione() != null) {
			query += " and dataProduzione='" + new java.sql.Date(input.getDataProduzione().getTime()) + "' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				Televisore televisoreTemp = new Televisore();
				televisoreTemp.setMarca(rs.getString("MARCA"));
				televisoreTemp.setModello(rs.getString("MODELLO"));
				televisoreTemp.setPollici(rs.getInt("POLLICI"));
				televisoreTemp.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
				televisoreTemp.setId(rs.getLong("ID"));
				result.add(televisoreTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// ####################################################

	@Override
	public List<Televisore> findAllCreatedInRangeDates(Date inizio, Date fine) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (inizio == null || fine == null || inizio.after(fine))
			throw new Exception("Valore di input non ammesso.");

		List<Televisore> result = new ArrayList<Televisore>();
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT* FROM televisore WHERE dataproduzione BETWEEN ? AND ? ;")) {

			ps.setDate(1, new java.sql.Date(inizio.getTime()));
			ps.setDate(2, new java.sql.Date(fine.getTime()));

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Televisore televisoreTemp = new Televisore();
					televisoreTemp.setMarca(rs.getString("MARCA"));
					televisoreTemp.setModello(rs.getString("MODELLO"));
					televisoreTemp.setPollici(rs.getInt("POLLICI"));
					televisoreTemp.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
					televisoreTemp.setId(rs.getLong("ID"));
					result.add(televisoreTemp);
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public Televisore findByMaxInch() throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		Televisore result = new Televisore();
		try (Statement ps = connection.createStatement();
				ResultSet rs = ps.executeQuery("SELECT* FROM televisore ORDER BY pollici DESC LIMIT 1")) {

			if (rs.next()) {

				result.setMarca(rs.getString("marca"));
				result.setModello(rs.getString("MODELLO"));
				result.setPollici(rs.getInt("POLLICI"));
				result.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
				result.setId(rs.getLong("ID"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

	public List<String> findAllCreatedInLastSixMonths() throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<String> result = new ArrayList<String>();
		try (Statement ps = connection.createStatement();
				ResultSet rs = ps.executeQuery("SELECT marca FROM televisore WHERE dataproduzione> '2022-03-31';")) {

			while (rs.next()) {
				String temp = rs.getString("marca");
				result.add(temp);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

}
