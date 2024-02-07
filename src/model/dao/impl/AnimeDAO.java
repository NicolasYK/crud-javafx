package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DataBase;
import db.DataBaseException;
import gui.utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import model.dao.GenericDao;
import model.entities.Anime;
import model.entities.Demographics;
import model.entities.Genres;
import model.entities.Studio;
import model.entities.Themes;

public class AnimeDAO implements GenericDao<Anime> {

	private Connection conn;

	public AnimeDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Anime anime) {
		PreparedStatement st = null;
		try {
		    int verifyInsert = isExists(anime.getTitle());
		    if (verifyInsert > 0) {
		        Alerts.showAlert("ATENÇÃO!",
		                "Este formulário foi cadastrado!",
		                null, AlertType.INFORMATION);
		    } else {
		        conn.setAutoCommit(false); // Inicia uma transação

		        // Insere na tabela animes
		        st = conn.prepareStatement(
		                "INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) "
		                        + "VALUES (?, ?, ?, ?, ?);",
		                Statement.RETURN_GENERATED_KEYS
		        );
		        st.setString(1, anime.getTitle());
		        st.setInt(2, anime.getGenres().getGenresId());
		        st.setInt(3, anime.getThemes().getThemesId());
		        st.setInt(4, anime.getDemographics().getDemographicId());
		        st.setInt(5, anime.getStudio().getStudioId());

		        int rowsAffected = st.executeUpdate();
		        if (rowsAffected > 0) {
		            ResultSet rs = st.getGeneratedKeys();
		            int animeId;
		            if (rs.next()) {
		                animeId = rs.getInt(1);

		                PreparedStatement stGenres = conn.prepareStatement(
		                        "INSERT INTO anime_genres (anime_id, genres_id) VALUES (?, ?);"
		                );
		                stGenres.setInt(1, animeId);
		                stGenres.setInt(2, anime.getGenres().getGenresId());
		                stGenres.executeUpdate();

		                PreparedStatement stThemes = conn.prepareStatement(
		                        "INSERT INTO anime_themes (anime_id, themes_id) VALUES (?, ?);"
		                );
		                stThemes.setInt(1, animeId);
		                stThemes.setInt(2, anime.getThemes().getThemesId());
		                stThemes.executeUpdate();

		                PreparedStatement stDemographics = conn.prepareStatement(
		                        "INSERT INTO anime_demographics (anime_id, demographics_id) VALUES (?, ?);"
		                );
		                stDemographics.setInt(1, animeId);
		                stDemographics.setInt(2, anime.getDemographics().getDemographicId());
		                stDemographics.executeUpdate();


		                PreparedStatement stStudio = conn.prepareStatement(
		                        "INSERT INTO anime_studio (anime_id, studio_id) VALUES (?, ?);"
		                );
		                stStudio.setInt(1, animeId);
		                stStudio.setInt(2, anime.getStudio().getStudioId());
		                stStudio.executeUpdate();

		                conn.commit();
		                Alerts.showAlert("CONCLUIDO!",
		                        "O BANCO FOI ATUALIZADO!",
		                        "UM NOVO FORMULÁRIO FOI INCLUIDO, ATUALIZE O BANCO PARA VER AS MODIFICAÇÕES...",
		                        AlertType.CONFIRMATION);
		            }
		            DataBase.closeResultSet(rs);
		        } else {
		            throw new DataBaseException("Unexpected error! No rows affected!");
		        }
		    }
		} catch (SQLException e) {
		    try {
		        conn.rollback();
		    } catch (SQLException rollbackException) {
		        rollbackException.printStackTrace();
		    }
		    throw new DataBaseException(e.getMessage());
		} finally {
		    DataBase.closeStatement(st);
		    try {
		        conn.setAutoCommit(true);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int isExists(String obj) {
		String title_exists = obj.toUpperCase();
		try {
			PreparedStatement st = conn.prepareStatement(
					"SELECT anime_id FROM animes WHERE title = '"+title_exists+"';"
					);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getInt("anime_id");
			}
			return -1;
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	private Genres instanceGenres(ResultSet rs) throws SQLException {
		Genres genres = new Genres(rs.getInt("genres_id"), rs.getString("genres"));
		return genres;
	}

	private Themes instanceThemes(ResultSet rs) throws SQLException {
		Themes themes = new Themes(rs.getInt("themes_id"), rs.getString("themes"));
		return themes;
	}

	private Demographics instanceDemo(ResultSet rs) throws SQLException {
		Demographics demo = new Demographics(rs.getInt("demographics_id"),
				rs.getString("demographic"));
		return demo;
	}

	private Studio instanceStudio(ResultSet rs) throws SQLException {
		Studio studio = new Studio(rs.getInt("studio_id"), rs.getString("studio_name"));
		return studio;
	}

	private Anime instanceAnime(ResultSet rs, Genres genres, Themes themes, Demographics demo, Studio studio)
			throws SQLException {
		Anime anime = new Anime();
		anime.setAnimeId(rs.getInt("anime_id"));
		anime.setTitle(rs.getString("title"));
		anime.setGenres(genres);
		anime.setThemes(themes);
		anime.setDemographics(demo);
		anime.setStudio(studio);
		return anime;
	}
	
	@Override
	public List<Anime> getListComplete() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT animes.anime_id, animes.title, genres.genres_id, genres.genres, themes.themes_id, themes.themes, demographics.demographics_id, demographics.demographic, studio.studio_id, studio.studio_name\r\n"
							+ "FROM animes\r\n" + "JOIN anime_genres ON animes.anime_id = anime_genres.anime_id\r\n"
							+ "JOIN genres ON anime_genres.genres_id = genres.genres_id\r\n"
							+ "JOIN anime_themes ON animes.anime_id = anime_themes.anime_id\r\n"
							+ "JOIN themes ON anime_themes.themes_id = themes.themes_id\r\n"
							+ "JOIN anime_demographics ON animes.anime_id = anime_demographics.anime_id\r\n"
							+ "JOIN demographics ON anime_demographics.demographics_id = demographics.demographics_id\r\n"
							+ "JOIN anime_studio ON animes.anime_id = anime_studio.anime_id\r\n"
							+ "JOIN studio ON anime_studio.studio_id = studio.studio_id;"
					);
			rs = st.executeQuery();
			List<Anime> list = new ArrayList<>();
			Map<Integer, Genres> map_genres = new HashMap<>();
			Map<Integer, Themes> map_themes = new HashMap<>();
			Map<Integer, Demographics> map_demo = new HashMap<>();
			Map<Integer, Studio> map_studio = new HashMap<>();
			while (rs.next()) {

				Genres genres = map_genres.get(rs.getInt("genres_id"));
				if (genres == null) {
					genres = instanceGenres(rs);
					map_genres.put(rs.getInt("genres_id"), genres);
				}
				Themes themes = map_themes.get(rs.getInt("themes_id"));
				if (themes == null) {
					themes = instanceThemes(rs);
					map_themes.put(rs.getInt("themes_id"), themes);
				}
				Demographics demo = map_demo.get(rs.getInt("demographics_id"));
				if (demo == null) {
					demo = instanceDemo(rs);
					map_demo.put(rs.getInt("demographics_id"), demo);
				}
				Studio studio = map_studio.get(rs.getInt("studio_id"));
				if (studio == null) {
					studio = instanceStudio(rs);
					map_studio.put(rs.getInt("studio_id"), studio);
				}
				Anime anime = instanceAnime(rs, genres, themes, demo, studio);
				list.add(anime);
			}
			return list;
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		finally {
			DataBase.closeStatement(st);
			DataBase.closeResultSet(rs);
		}
	}

	@Override
	public List<String> getListName() {
		return null;
	}

	@Override
	public ObservableList<Anime> findByName(String name) {
		ObservableList<Anime> list = FXCollections.observableArrayList();
		Map<Integer, Genres> map_genres = new HashMap<>();
		Map<Integer, Themes> map_themes = new HashMap<>();
		Map<Integer, Demographics> map_demo = new HashMap<>();
		Map<Integer, Studio> map_studio = new HashMap<>();
		Map<Integer, Object> map = new HashMap<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT animes.anime_id, animes.title, genres.genres_id, genres.genres, "
					+ "themes.themes_id, themes.themes, demographics.demographics_id, "
					+ "demographics.demographic, studio.studio_id, studio.studio_name\r\n"
					+ "FROM animes\r\n"
					+ "JOIN anime_genres ON animes.anime_id = anime_genres.anime_id\r\n"
					+ "JOIN genres ON anime_genres.genres_id = genres.genres_id\r\n"
					+ "JOIN anime_themes ON animes.anime_id = anime_themes.anime_id\r\n"
					+ "JOIN themes ON anime_themes.themes_id = themes.themes_id\r\n"
					+ "JOIN anime_demographics ON animes.anime_id = anime_demographics.anime_id\r\n"
					+ "JOIN demographics ON anime_demographics.demographics_id = demographics.demographics_id\r\n"
					+ "JOIN anime_studio ON animes.anime_id = anime_studio.anime_id\r\n"
					+ "JOIN studio ON anime_studio.studio_id = studio.studio_id\r\n"
					+ "WHERE animes.title = UPPER('"+name+"');\r\n"
					);
			rs = st.executeQuery();
			while(rs.next()) {
				Genres genres = (Genres) map.get(rs.getInt("genres_id"));
				if (genres == null) {
					genres = instanceGenres(rs);
					map_genres.put(rs.getInt("genres_id"), genres);
				}
				Themes themes = (Themes) map.get(rs.getInt("themes_id"));
				if (themes == null) {
					themes = instanceThemes(rs);
					map_themes.put(rs.getInt("themes_id"), themes);
				}
				Demographics demo = (Demographics) map.get(rs.getInt("demographics_id"));
				if (demo == null) {
					demo = instanceDemo(rs);
					map_demo.put(rs.getInt("demographics_id"), demo);
				}
				Studio studio = (Studio) map.get(rs.getInt("studio_id"));
				if (studio == null) {
					studio = instanceStudio(rs);
					map_studio.put(rs.getInt("studio_id"), studio);
				}
				Anime anime = instanceAnime(rs, genres, themes, demo, studio);
				list.add(anime);
				return list;
			}
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		finally {
			DataBase.closeStatement(st);
			DataBase.closeResultSet(rs);
		}
		return null;
	}
	
	
}
