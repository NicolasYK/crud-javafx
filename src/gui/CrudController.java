package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import db.DataBase;
import db.DataBaseException;
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
import javafx.scene.control.cell.PropertyValueFactory;
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

	@FXML
	private void onActionInsert() {
		
	}

	@FXML
	private void onActionUpdate() {
		
	}

	@FXML
	private void onActionDelete() {

	}

	private static ObservableList<Anime> getAllAnimeList() {
		ObservableList<Anime> animelist = FXCollections.observableArrayList();
		Connection conn = DataBase.getConnection();
		Anime animes;
		try (Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(
					"select animes.anime_id as id ,animes.title, genres.genres, themes.themes, demographics.demographic, studio.studio_name as studio \r\n"
							+ "from animes\r\n" + "join anime_genres on anime_genres.anime_id = animes.anime_id\r\n"
							+ "join genres on anime_genres.genres_id = genres.genres_id\r\n"
							+ "join anime_themes on anime_themes.anime_id = animes.anime_id\r\n"
							+ "join themes on anime_themes.themes_id = themes.themes_id\r\n"
							+ "join anime_demographics on anime_demographics.anime_id = animes.anime_id\r\n"
							+ "join demographics on anime_demographics.demographics_id = demographics.demographics_id\r\n"
							+ "join anime_studio on anime_studio.anime_id = animes.anime_id\r\n"
							+ "join studio on anime_studio.studio_id = studio.studio_id\r\n"
							+ "order by animes.anime_id;");
			while (rs.next()) {
				animes = new Anime(rs.getInt("id"), rs.getString("title"), rs.getString("genres"),
						rs.getString("themes"), rs.getString("demographic"), rs.getString("studio"));
				animelist.add(animes);
			}
			return animelist;
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	private static ObservableList<String> getAllGenericList(String query, String columName){
		ObservableList<String> genericlist = FXCollections.observableArrayList();
		Connection conn = DataBase.getConnection();
		try(Statement st = conn.createStatement()){
			
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				genericlist.add(rs.getString(columName));
			}
			return genericlist;
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
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
		CbGenres.setItems(getAllGenericList("SELECT * FROM genres;", "genres"));
		CbThemes.setItems(getAllGenericList("SELECT * FROM themes;", "themes"));
		CbDemographics.setItems(getAllGenericList("SELECT * FROM demographics;", "demographic"));
		CbStudio.setItems(getAllGenericList("SELECT * FROM studio;", "studio_name"));
		showAnimeList(getAllAnimeList());
	}
}
