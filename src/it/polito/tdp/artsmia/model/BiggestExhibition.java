package it.polito.tdp.artsmia.model;

public class BiggestExhibition {
	
	private Exhibition exhibition;
	private int counter;
	
	public BiggestExhibition(Exhibition exhibition, int counter) {
		super();
		this.exhibition = exhibition;
		this.counter = counter;
	}

	public Exhibition getExhibition() {
		return exhibition;
	}

	public int getCounter() {
		return counter;
	};
	
	@Override
	public String toString() {
		return this.exhibition.getTitle() + " (id: " + exhibition.getId() + ") ha il maggior numero di opere esposte: " + counter;
	}

}
