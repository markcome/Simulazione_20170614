package it.polito.tdp.artsmia.model;

import java.util.HashSet;
import java.util.Set;

public class Studente implements Comparable<Studente>{
	
	private int id;
	private Set<Integer> artObjectSet;
	
	public Studente(int id) {
		this.id = id;
		this.artObjectSet = new HashSet<Integer>();
	}

	public int getId() {
		return id;
	}

	public Set<Integer> getArtObjectSet() {
		return artObjectSet;
	}

	@Override
	public String toString() {
		return String.format("Studente: %s, opere viste: %s", id, artObjectSet.size());
	}

	@Override
	public int compareTo(Studente o) {
		return Integer.compare(o.getArtObjectSet().size(), this.artObjectSet.size());
	}

	
}
