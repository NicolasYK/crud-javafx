package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Anime;

public class CrudController {
	
	@FXML
	private TextField TfSearch;
	@FXML
	private TextField TfID;
	@FXML
	private TextField TfTitle;
	@FXML
	private TextField TfGenres;
	@FXML
	private TextField TfDemographics;
	@FXML
	private TextField TfStudio;
	@FXML
	private Button BtSearch;
	@FXML
	private Button BtInsert;
	@FXML
	private Button BtUpdate;
	@FXML
	private Button BtDelete;
	@FXML
	private TableView<Anime> TvAnime;
	@FXML
	private TableColumn<Anime, Integer> ColId;
	@FXML
	private TableColumn<Anime, String> ColTitle;
	@FXML
	private TableColumn<Anime, String> ColGenres;
	@FXML
	private TableColumn<Anime, String> ColDemographics;
	@FXML
	private TableColumn<Anime, String> ColStudio;
	@FXML
	private ProgressBar PbSearch;
	
	
	@FXML
	private void OnActionSearch() {
		System.out.println("Search in DataBase...");
	}
	
	
}
