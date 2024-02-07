package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.DAOFactory;
import model.dao.impl.AnimeDAO;
import model.entities.Anime;
import model.entities.Demographics;
import model.entities.Genres;
import model.entities.Studio;
import model.entities.Themes;

public class CrudController implements Initializable {

	@FXML
	private TextField TfSearch;
	@FXML
	private TextField TfID;
	@FXML
	private TextField TfTitle;
	@FXML
	private ChoiceBox<String> CbGenres;
	@FXML
	private ChoiceBox<String> CbThemes;
	@FXML
	private ChoiceBox<String> CbDemographics;
	@FXML
	private ChoiceBox<String> CbStudio;
	@FXML
	private Button BtSearch;
	@FXML
	private Button BtInsert;
	@FXML
	private Button BtUpdate;
	@FXML
	private Button BtDelete;
	@FXML
	private Button BtClear;
	@FXML
	private TableView<Anime> TvAnime;
	@FXML
	private TableColumn<Anime, Integer> ColId;
	@FXML
	private TableColumn<Anime, String> ColTitle;
	@FXML
	private TableColumn<Anime, String> ColGenres;
	@FXML
	private TableColumn<Anime, String> ColThemes;
	@FXML
	private TableColumn<Anime, String> ColDemographics;
	@FXML
	private TableColumn<Anime, String> ColStudio;
	@FXML
	private ProgressBar PbSearch;

	@FXML
	private void onActionSearch() {
		try {
			String text_search = TfSearch.getText().toUpperCase();
			ObservableList<Anime> listSearch = DAOFactory.createAnimeDao().findByName(text_search);
			Anime firstAnime = listSearch.get(0);
			TfID.setText(String.valueOf(firstAnime.getAnimeId()));
			TfTitle.setText(firstAnime.getTitle());
			CbGenres.setValue(firstAnime.getGenres().getGenres());
			CbThemes.setValue(firstAnime.getThemes().getThemes());
			CbDemographics.setValue(firstAnime.getDemographics().getDemographic());
			CbStudio.setValue(firstAnime.getStudio().getStudio());			
		}
		catch(NullPointerException e) {
			Alerts.showAlert("Erro!", 
					"Falha ao encontrar o valor especificado", 
					"Por favor, verifique se preencheu os campos corretamente e tente novamente...", 
					AlertType.WARNING);
			PbSearch.setProgress(1.0);
		}
		finally {
			PbSearch.setProgress(1.0);
		}
	}
	
	@FXML
	private void onActionInsert() {
		int titleId = DAOFactory.createAnimeDao().isExists(TfTitle.getText().toUpperCase());
		int genresId = DAOFactory.createGenresDao().isExists(CbGenres.getValue().toUpperCase());
		int themeId = DAOFactory.createThemesDao().isExists(CbThemes.getValue().toUpperCase());
		int demoId = DAOFactory.createDemoDao().isExists(CbDemographics.getValue().toUpperCase());
		int studioId = DAOFactory.createStudioDAO().isExists(CbStudio.getValue().toUpperCase());
		if(genresId > 0 && themeId > 0 && demoId > 0 && studioId > 0) {
			if(titleId > 0) {
				Alerts.showAlert(
						"ATENÇÃO!",
						"Este formulário foi cadastrado!",
						null, AlertType.INFORMATION);
			}
			else {
				Anime anime = new Anime();
				anime.setTitle(TfTitle.getText().toUpperCase());
				anime.setGenres(new Genres(genresId, CbGenres.getValue().toUpperCase()));
				anime.setThemes(new Themes(themeId, CbThemes.getValue().toUpperCase()));
				anime.setDemographics(new  Demographics(demoId, CbDemographics.getValue().toUpperCase()));
				anime.setStudio(new Studio(studioId, CbStudio.getValue().toUpperCase()));
				DAOFactory.createAnimeDao().insert(anime);
				showAnimeList(getAllAnimeList());
			}
		}
		
	}

	@FXML
	private void onActionUpdate() {
		
	}

	@FXML
	private void onActionDelete() {
	}
	
	@FXML
	private void onActionClear() {
		TfID.setText("");
		TfTitle.setText("");
		TfSearch.setText("");
		CbGenres.setValue("");
		CbThemes.setValue("");
		CbDemographics.setValue("");
		CbStudio.setValue("");
		PbSearch.setProgress(0);
		showAnimeList(getAllAnimeList());
	}

	private static ObservableList<Anime> getAllAnimeList() {
		ObservableList<Anime> animelist;
		animelist = FXCollections.observableArrayList(DAOFactory.createAnimeDao().getListComplete());
		return animelist;
	}

	private static ObservableList<String> getCollectionNames(List<String> list){
		ObservableList<String> genericList;
		genericList = FXCollections.observableArrayList(list);
		return genericList;
	}
	
	
	public void showAnimeList(ObservableList<Anime> animeList) {

		ColId.setCellValueFactory(new PropertyValueFactory<Anime, Integer>("AnimeId"));
		ColTitle.setCellValueFactory(new PropertyValueFactory<Anime, String>("Title"));
		ColGenres.setCellValueFactory(new PropertyValueFactory<Anime, String>("Genres"));
		ColThemes.setCellValueFactory(new PropertyValueFactory<Anime, String>("Themes"));
		ColDemographics.setCellValueFactory(new PropertyValueFactory<Anime, String>("Demographics"));
		ColStudio.setCellValueFactory(new PropertyValueFactory<Anime, String>("Studio"));
		TvAnime.setItems(animeList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TfID.setDisable(true);
		CbGenres.setItems(getCollectionNames(DAOFactory.createGenresDao().getListName()));
		CbThemes.setItems(getCollectionNames(DAOFactory.createThemesDao().getListName()));
		CbDemographics.setItems(getCollectionNames(DAOFactory.createDemoDao().getListName()));
		CbStudio.setItems(getCollectionNames(DAOFactory.createStudioDAO().getListName()));
		showAnimeList(getAllAnimeList());
	}
}