package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	                         //pesato non orientato
	private Graph<ArtObject, DefaultWeightedEdge> grafo; 
	private Map<Integer, ArtObject> idMap; 
	
	public Model() {
		//this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		this.idMap= new HashMap<>(); 
	}
	
	
	//Piu' corretto farlo qui così che ogni volta sia aggiornato
	public void creaGrafo() {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		
		ArtsmiaDAO dao= new ArtsmiaDAO(); 
		dao.listObjects(idMap); // così la mappa contiene gli oggetti 
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.idMap.values()); 
		
		//aggiungo gli archi 
		//Posso usare tre strategie
		/* Non posso usarlo qui perche' finisce in 67 gg ( tempo esecuzione una query * #vertici alla seconda)
		//APPROCCIO 1 -> doppio ciclo for sui vertici : dati due vertici controllo se sono collegati
		for(ArtObject a : this.grafo.vertexSet()) {
			for(ArtObject aa : this.grafo.vertexSet()) {
				// devo collegarli? -> lo so con un metodo del dao
				//prima controllo che non esista gia' quell'arco perche' le coppie possono
				// essere invertite essendo non orientato
				if(this.grafo.containsEdge(a, aa)) {
				int peso = dao.getPeso(a, aa); 
				if(peso>0) {
					//ok son connessi 
					Graphs.addEdge(this.grafo, a, aa, peso); 
				}
				}
			}
		}
		System.out.println(String.format("Grafo creato! Numero vertici %d , numero archi %d", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
	*/
		
		//APPROCCIO 2 -> un vertice alla volta, quali sono i suoi adiacenti?
		/*con questa query sql="SELECT ex2.object_id, COUNT(*) FROM exhibition_objects as ex1 ,exhibition_objects as ex2 WHERE 
		ex1.exhibition_id= ex2.exhibition_id AND ex1.object_id='8485' AND ex1.object_id != ex2.object_id
				GROUP BY ex2.object_id"
		impiega mezz'ora circa 
		*/
		
		//APPROCCIO 3 
		// chiedo direttamente al dao quali sono le connessioni 
		for (Adiacenti a : dao.getAdiacenti()) {
			// qui non si controlla che le coppie siano invertite 
			// lavoro gia' fatto nella query con >
			if(a.getPeso()>0) {
				Graphs.addEdge(this.grafo, idMap.get(a.getObj1()), idMap.get(a.getObj2()), a.getPeso()); 
			}
			
		}
		
		System.out.println(String.format("Grafo creato! Numero vertici %d , numero archi %d", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
		
		
	}
	
	public int nVertici(){
		return this.grafo.vertexSet().size(); 
	}
	

	public int nArchi(){
		return this.grafo.edgeSet().size(); 
	}
}
