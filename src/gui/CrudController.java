package gui;

import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Anime;

public class CrudController implements Initializable{
	
	@FXML
	private TextField TfSearch;
	@FXML
	private TextField TfID;
	@FXML
	private TextField TfTitle;
	@FXML
	private TextField TfGenres;
	@FXML
	private TextField TfThemes;
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
	private TableColumn<Anime, String> ColThemes;
	@FXML
	private TableColumn<Anime, String> ColDemographics;
	@FXML
	private TableColumn<Anime, String> ColStudio;
	@FXML
	private ProgressBar PbSearch;
	
	
	@FXML
	private void onActionSearch() {
		System.out.println("Search in DataBase...");
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
	
	private static ObservableList<Anime> getAnimeList(){
		ObservableList<Anime> animelist = FXCollections.observableArrayList();
		Connection conn = DataBase.getConnection();
		Anime animes;
		try (Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(
					"select animes.anime_id as id ,animes.title, genres.genres, themes.themes, demographics.demographic, studio.studio_name as studio \r\n"
					+ "from animes\r\n"
					+ "join anime_genres on anime_genres.anime_id = animes.anime_id\r\n"
					+ "join genres on anime_genres.genres_id = genres.genres_id\r\n"
					+ "join anime_themes on anime_themes.anime_id = animes.anime_id\r\n"
					+ "join themes on anime_themes.themes_id = themes.themes_id\r\n"
					+ "join anime_demographics on anime_demographics.anime_id = animes.anime_id\r\n"
					+ "join demographics on anime_demographics.demographics_id = demographics.demographics_id\r\n"
					+ "join anime_studio on anime_studio.anime_id = animes.anime_id\r\n"
					+ "join studio on anime_studio.studio_id = studio.studio_id\r\n"
					+ "order by animes.anime_id;"
					);
			while(rs.next()) {
				animes = new Anime(rs.getInt("id"), rs.getString("title"),
						rs.getString("genres"), rs.getString("themes"), 
						rs.getString("demographic"), rs.getString("studio"));
				animelist.add(animes);
			}
			return animelist;
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	public void showAnimeList() {
		ObservableList<Anime> list = getAnimeList();
		
		ColId.setCellValueFactory(new PropertyValueFactory<Anime, Integer>("Id"));
		ColTitle.setCellValueFactory(new PropertyValueFactory<Anime, String>("Title"));
		ColGenres.setCellValueFactory(new PropertyValueFactory<Anime, String>("Genres"));
		ColThemes.setCellValueFactory(new PropertyValueFactory<Anime, String>("Themes"));
		ColDemographics.setCellValueFactory(new PropertyValueFactory<Anime, String>("Demographics"));
		ColStudio.setCellValueFactory(new PropertyValueFactory<Anime, String>("Studio"));
		TvAnime.setItems(list);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showAnimeList();
	}
}
