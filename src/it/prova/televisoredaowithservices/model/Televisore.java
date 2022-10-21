package it.prova.televisoredaowithservices.model;

import java.util.Date;

public class Televisore {
	private Long id;
	private String marca;
	private String modello;
	private int pollici;
	private Date dataProduzione;

	public Televisore() {
	}

	public Televisore(Long id, String marca, String modello, int pollici, Date dataProduzione) {

		this.id = id;
		this.marca = marca;
		this.modello = modello;
		this.pollici = pollici;
		this.dataProduzione = dataProduzione;
	}

	public Televisore(String marca, String modello, int pollici, Date dataProduzione) {

		this.marca = marca;
		this.modello = modello;
		this.pollici = pollici;
		this.dataProduzione = dataProduzione;
	}

	public Televisore(String modello, String marca) {

		this.modello = modello;
		this.marca = marca;
	}

	public Televisore(String marca) {
		super();
		this.marca = marca;
	}

	public Televisore(int pollici) {
		super();
		this.pollici = pollici;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public int getPollici() {
		return pollici;
	}

	public void setPollici(int pollici) {
		this.pollici = pollici;
	}

	public Date getDataProduzione() {
		return dataProduzione;
	}

	public void setDataProduzione(Date dataProduzione) {
		this.dataProduzione = dataProduzione;
	}

	@Override
	public String toString() {
		return "Televisore [id=" + id + ", marca=" + marca + ", modello=" + modello + ", pollici=" + pollici
				+ ", dataProduzione=" + dataProduzione + "]";
	}

}
