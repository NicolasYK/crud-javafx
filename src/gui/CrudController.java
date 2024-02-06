package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import db.DataBase;
import db.DataBaseException;
import gui.utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import model.dao.DAOFactory;
import model.entities.Anime;

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
			Anime title_search = listSearch.get(0);
			TfID.setText(String.valueOf(title_search.getAnimeId()));
			TfTitle.setText(String.valueOf(title_search.getTitle()));
			CbGenres.setValue(String.valueOf(title_search.getGenres()));
			CbThemes.setValue(String.valueOf(title_search.getThemes()));
			CbDemographics.setValue(String.valueOf(title_search.getDemographics()));
			CbStudio.setValue(String.valueOf(title_search.getStudio()));			
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

/*
	@FXML
	private void onActionSearch() {
		String text_search = TfSearch.getText().toUpperCase();
		Connection conn = DataBase.getConnection();
		ObservableList<Anime> list = getAllAnimeList();
		Double point_pbSearch = 0.0;
		try {
			PreparedStatement st = conn.prepareStatement(
					"select animes.anime_id as id ,animes.title, genres.genres, themes.themes, demographics.demographic, studio.studio_name as studio \r\n"
							+ "from animes\r\n" + "join anime_genres on anime_genres.anime_id = animes.anime_id\r\n"
							+ "join genres on anime_genres.genres_id = genres.genres_id\r\n"
							+ "join anime_themes on anime_themes.anime_id = animes.anime_id\r\n"
							+ "join themes on anime_themes.themes_id = themes.themes_id\r\n"
							+ "join anime_demographics on anime_demographics.anime_id = animes.anime_id\r\n"
							+ "join demographics on anime_demographics.demographics_id = demographics.demographics_id\r\n"
							+ "join anime_studio on anime_studio.anime_id = animes.anime_id\r\n"
							+ "join studio on anime_studio.studio_id = studio.studio_id\r\n"
							+ "where UPPER(animes.title) = '"+text_search+"';"
					);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				point_pbSearch = point_pbSearch + 0.1;
				PbSearch.setProgress(point_pbSearch);
				list.removeAll(list);
				list.add(new Anime(rs.getInt("id"), rs.getString("title"), rs.getString("genres"),
						rs.getString("themes"), rs.getString("demographic"), rs.getString("studio")));
				
				TfID.setText(String.valueOf(rs.getInt("id")));
				TfTitle.setText(String.valueOf(rs.getString("title")));
				CbGenres.setValue(String.valueOf(rs.getString("genres")));
				CbThemes.setValue(String.valueOf(rs.getString("themes")));
				CbDemographics.setValue(String.valueOf(rs.getString("demographic")));
				CbStudio.setValue(String.valueOf(rs.getString("studio")));
				
				
				if(rs.next() == false) {
					point_pbSearch = 1.0;
					PbSearch.setProgress(point_pbSearch);
				}
			}
			showAnimeList(list);

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
 */
