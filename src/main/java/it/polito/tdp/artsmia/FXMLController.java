/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxLUN"
    private ChoiceBox<?> boxLUN; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaComponenteConnessa"
    private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaOggetti"
    private Button btnCercaOggetti; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizzaOggetti"
    private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

    @FXML // fx:id="txtObjectId"
    private TextField txtObjectId; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    
    @FXML
    void doAnalizzaOggetti(ActionEvent event) {

    	this.txtResult.clear();
    	this.txtResult.appendText("Creazione grafo.... \n");
    	model.creaGrafo();
    	this.txtResult.appendText("Grafo creato! "+model.nVertex()+" vertici e "+model.nArchi()+" archi \n");
    	
    	//a a questo punto posso anche fare altre cose 
    	this.btnCalcolaComponenteConnessa.setDisable(false);
    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {

    	txtResult.clear();
    	if (this.txtObjectId.getLength()==0) {
    		txtResult.appendText("ERRORE : inserire un id \n");
    		return; 
    	}
    	
    	int objectId=0; 
    	try {
    		objectId= Integer.parseInt(this.txtObjectId.getText()); 
    	}
    	catch(NumberFormatException nfe) {
    		txtResult.appendText("ERRORE : inserire un id numerico\n");
    		return; 
    	}
    	
    	boolean esiste =model.esisteArtObject(objectId);
    	if(esiste==false) {
    		txtResult.appendText("ERRORE : ArtObject inesistente \n");
    		return;
    	}
    	txtResult.appendText("Vertici connessi "+model.visita(objectId).size()+"\n");
    	for (ArtObject ao : model.visita(objectId)) {
    		txtResult.appendText(ao.toString()+"\n");
    	}
    	
    	
    }

    @FXML
    void doCercaOggetti(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnCalcolaComponenteConnessa.setDisable(true); //inizialmente non posso cliccare 
    }
}
