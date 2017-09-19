/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import it.polito.tdp.artsmia.model.Simulazione;
import it.polito.tdp.artsmia.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ChoiceBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldStudenti"
    private TextField txtFieldStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;
	private Simulazione simulazione;
	private int yearSelected;

    @FXML
    void handleCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	
    	if(this.boxAnno.getValue() == null) {
    		this.txtResult.appendText("Selezionare un anno");
    		return;
    	}
    	
    	int year = this.boxAnno.getValue();
    	this.yearSelected = year;
    	this.model.creaGrafo(year);
    	
    	this.txtResult.appendText("Il grafo è fortemente connesso? ");
    	if(this.model.isStronglyConnected()) {
    		this.txtResult.appendText("Si!\n");
    	} else {
    		this.txtResult.appendText("No..\n");
    	}
    	
    	this.txtResult.appendText("\nLa mostra con il maggior numero di oggetti è: \n");
    	this.txtResult.appendText(this.model.biggestExhibition(year).toString());
    }

    @FXML
    void handleSimula(ActionEvent event) {
    	this.txtResult.clear();
    	
    	//Controllo se aveva costruito il grafo e quindi acquisito l'anno in precedenza
    	if(this.yearSelected == -1) {
    		this.txtResult.appendText("Utilizzare prima la funzione \"crea grafo\"");
    		return;
    	}
    	
    	//Controllo se ha inserito un valore
    	if(this.txtFieldStudenti.getText() == null || this.txtFieldStudenti.getText().compareTo("") == 0) {
    		this.txtResult.appendText("Inserire un numero di studenti");
    		return;
    	}
    	
    	int numeroStudenti = -1;
    	//Controllo se il valore inserito è un intero
    	try {
    		numeroStudenti = Integer.parseInt(this.txtFieldStudenti.getText());
    	} catch (NumberFormatException nfe) {
    		this.txtFieldStudenti.appendText("Inserire un numero intero");
    		return;
    	}
    	
    	//Controllo se ha inserito un intero maggiore di uno e contemporaneamente se
    	//il valore è stato assegnato correttamente
    	if(numeroStudenti < 1) {
    		this.txtFieldStudenti.appendText("Inserire almeno uno studente");
    		return;
    	}
	
    	this.simulazione = new Simulazione(this.model);
    	this.simulazione.simula(numeroStudenti, this.yearSelected);
    	System.out.println("__________________Nuova simulazione__________________");

    	this.txtResult.appendText("Classifica studenti:\n");
    	for(Studente s: this.simulazione.getResult()) {
    		this.txtResult.appendText(s.toString() + "\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtFieldStudenti != null : "fx:id=\"txtFieldStudenti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getListYearsOfExhitions());
		this.yearSelected = -1;
	}
}
