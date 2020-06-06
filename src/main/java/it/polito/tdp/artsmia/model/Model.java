package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Map<Integer, ArtObject> idMapObject; 
	private ArtsmiaDAO dao; 
	private Graph<ArtObject, DefaultWeightedEdge> graph; 
	
	public Model() {
		this.idMapObject= new HashMap<>(); 
		this.dao= new ArtsmiaDAO(); 
		dao.listObjects(idMapObject);
	}
	
	public void creaGrafo() {
		this.graph= new SimpleWeightedGraph<ArtObject, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		
		//vertici
		Graphs.addAllVertices(this.graph, this.idMapObject.values()); 
		
		//archi 
		for (CoppiaArtObjects c : dao.getArtObjectsContemporanei(idMapObject)) {
			// il > nella query mi evita di avere coppie uguali 
			// -> non va controllato dunque se l'arco esiste gia' perche' non esiste sicuro
			//pero' aggiungo l'arco solo se il peso e' positivo
			if (c.getPeso()>0) {
				Graphs.addEdgeWithVertices(this.graph, c.getOb1(), c.getOb2(), c.getPeso()); 
			}
		}
	}
	
	public int nVertex() {
		return this.graph.vertexSet().size(); 
	}
	public int nArchi() {
		return this.graph.edgeSet().size(); 
	}

	/**
	 * id valido ovvero se e' presente nel grafo
	 * @param id
	 * @return true se esiste, false altrimenti 
	 */
	public boolean esisteArtObject(int id ) {
		return this.graph.containsVertex(this.idMapObject.get(id)); 
	}
	
	/**
	 * Componenti connesse a partire da un certo {@code ArtObject}
	 * @param id
	 * @return
	 */
	public List<ArtObject> visita(int id){
		List<ArtObject> connessi= new ArrayList<>();
		boolean valido = this.esisteArtObject(id); 
		if (valido==false) {
			return null; 
		}
		ArtObject obj= idMapObject.get(id); 
		
		BreadthFirstIterator<ArtObject, DefaultWeightedEdge> it= new BreadthFirstIterator<ArtObject, DefaultWeightedEdge>(this.graph, obj); 
		
		while(it.hasNext()) {
			connessi.add(it.next()); 
		}
		return connessi; 
	}

}
