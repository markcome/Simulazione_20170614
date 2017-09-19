package it.polito.tdp.artsmia.model;

public class Event {
	
	private Studente studente;
	private Exhibition exhibition;
	
	
	public Event(Studente studente, Exhibition exhibition) {
		super();
		this.studente = studente;
		this.exhibition = exhibition;
	}


	public Studente getStudente() {
		return studente;
	}


	public void setStudente(Studente studente) {
		this.studente = studente;
	}


	public Exhibition getExhibition() {
		return exhibition;
	}


	public void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
	}
	
	

}
