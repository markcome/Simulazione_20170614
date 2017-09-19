package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private ArtsmiaDAO dao;
	
	private List<Exhibition> exhibitions;
	private Map<Integer, Exhibition> exhibitionsMap;
	
	private SimpleDirectedGraph<Exhibition, DefaultEdge> grafo;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
		this.exhibitionsMap = new HashMap<Integer, Exhibition>();
	}
	
	public List<Integer> getListYearsOfExhitions() {
		return this.dao.listYearsOfExhitions();
	}
	
	public void creaGrafo(int year) {
		
		this.grafo = new SimpleDirectedGraph<Exhibition, DefaultEdge>(DefaultEdge.class);
		this.exhibitions = new ArrayList<Exhibition>(this.dao.listExhitions(year));
		
		if(this.exhibitions == null || this.exhibitions.isEmpty()) {
			System.out.println("Non è stato possibile trovare mostre per l'anno selezionato");
		}

		//Aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.exhibitions);
		
		//Aggiungo gli archi
		for(Exhibition e: this.exhibitions) {
			this.exhibitionsMap.put(e.getId(), e); //Popolo l'identity map delle mostre
			for(Exhibition e2: this.exhibitions) {
				if((!e.equals(e2)) && ((e.getEnd() - e2.getBegin()) > 0)) {
					grafo.addEdge(e, e2);
				}
			}
		}
		
		System.out.println("Creato grafo con " + grafo.vertexSet().size() + " vertici e " + grafo.edgeSet().size() + " archi.");
	}
	
	public boolean isStronglyConnected() {
		KosarajuStrongConnectivityInspector<Exhibition, DefaultEdge> ksci = new KosarajuStrongConnectivityInspector<Exhibition, DefaultEdge>(grafo);
		return ksci.isStronglyConnected();
	}
	
	public BiggestExhibition biggestExhibition(int year) {
		return this.dao.getBiggestExhitions(exhibitionsMap, year);
	}

	public void loadArtObjectForExhibition(){
		for(Exhibition e: grafo.vertexSet()) {
			e.setArtObjectId(this.dao.idListObjectForExhibition(e));
		}
	}
	
	/**
	 * Restituisce una lista di vertici del grafo che corrispondono ad una mostra
	 * che ha inizio nell'anno passato come parametro
	 * @param year anno da cercare
	 * @return List<"Exhibition">
	 */
	public List<Exhibition> getExhibitionForYear(int year) {
		
		List<Exhibition> result = new ArrayList<Exhibition>();
		for(Exhibition e: this.grafo.vertexSet()) {
			if(e.getBegin() == year) {
				result.add(e);
			}
		}
		
		return result;
	}
	
	public int outDegreeOf(Exhibition e) {
		return this.grafo.outDegreeOf(e);
	}
	
	public Exhibition successorOf(Exhibition e, int index) {
		return Graphs.successorListOf(this.grafo, e).get(index);
	}
}
