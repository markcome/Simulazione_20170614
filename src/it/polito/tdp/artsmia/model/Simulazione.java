package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.jgrapht.Graphs;

public class Simulazione {
	
	private Model model;
	private List<Studente> studenti;
	
	public Simulazione(Model model) {
		this.model = model;
	}
	
	public void simula(int numeroStudenti, int year) {
	
		//AGGIUNGO AL MODELLO I DATI MANCANTI PER LA SIMULAZIONE
		this.model.loadArtObjectForExhibition();
		
		List<Exhibition> exhibitionsForYear = this.model.getExhibitionForYear(year);;
				
		
		//PREPARO LA SIMULAZIONE
		Random rand = new Random();
		int randInt = rand.nextInt(exhibitionsForYear.size());
		Exhibition nextExhibition = exhibitionsForYear.get(randInt);
		
		//STRUTTURE NECESSARIE ALLA SIMULAZIONE
		Queue<Event> queue = new LinkedList<Event>();
		studenti = new LinkedList<Studente>();
		
		//IMPOSTO I PRIMI EVENTI
		for(int i = 0; i < numeroStudenti; i++) {
			Studente studente = new Studente(i+1);
			studenti.add(studente);
			
			queue.add(new Event(studente, nextExhibition));
		}
		
		
		//SIMULO
		while(!queue.isEmpty()) {
			
			//Prendo evento
			Event evento = queue.poll();
			
			//Aggiungo le opere viste alla mostra
			evento.getStudente().getArtObjectSet().addAll(evento.getExhibition().getArtObjectId());
			System.out.println("[(" + evento.getStudente() +
					") --- Mostra("+ this.model.outDegreeOf(evento.getExhibition()) +"): " + evento.getExhibition().getId() +
					" --- N.Opere: " + evento.getExhibition().getArtObjectId().size() + "]");

			//Controllo se la simuazione deve andare avanti
			if(this.model.outDegreeOf(evento.getExhibition()) > 0) {
				randInt = rand.nextInt(this.model.outDegreeOf(evento.getExhibition()));
				
				nextExhibition = this.model.successorOf(evento.getExhibition(), randInt);
				queue.add(new Event(evento.getStudente(), nextExhibition));
			}
		}
	}
	
	public List<Studente> getResult() {
		Collections.sort(this.studenti);
		return this.studenti;
	}

}
