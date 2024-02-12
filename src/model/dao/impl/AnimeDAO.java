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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
			st = conn.prepareStatement(
					"INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) "
					+ "VALUE (?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, anime.getTitle());
			st.setInt(2, anime.getGenres().getGenresId());
			st.setInt(3, anime.getThemes().getThemesId());
			st.setInt(4, anime.getDemographics().getDemographicId());
			st.setInt(5, anime.getStudio().getStudioId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					anime.setAnimeId(id);
				}
				DataBase.closeResultSet(rs);
			}
			else {
				throw new SQLException();
			}
		}
		catch(SQLException e){
			throw new DataBaseException(e.getMessage());
		}
		finally {
			DataBase.closeStatement(st);
		}
	}

	@Override
	public void update(Anime anime) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE animes SET title = ?, genres_id = ?, themes_id = ?, "
					+ "demographics_id = ?, studio_id = ? "
					+ "WHERE anime_id = ?"
					);
			st.setString(1, anime.getTitle().toUpperCase());
			st.setInt(2, anime.getGenres().getGenresId());
			st.setInt(3, anime.getThemes().getThemesId());
			st.setInt(4, anime.getDemographics().getDemographicId());
			st.setInt(5, anime.getStudio().getStudioId());
			st.setInt(6, anime.getAnimeId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		finally {
			DataBase.closeStatement(st);
		}
	}

	@Override
	public void delete(Anime animes) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM animes WHERE anime_id = ?"
					);
			st.setInt(1, animes.getAnimeId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected == 0) {
				throw new DataBaseException("Anime Id not found!.");
			}
			
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}

	}

	@Override
	public int isExists(String obj) {
		String title_exists = obj.toUpperCase();
		try {
			PreparedStatement st = conn
					.prepareStatement("SELECT anime_id FROM animes WHERE title = '" + title_exists + "';");
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt("anime_id");
			}
			return -1;
		} catch (SQLException e) {
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
		Demographics demo = new Demographics(rs.getInt("demographics_id"), rs.getString("demographic"));
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
					"SELECT a.anime_id, a.title, g.genres_id, g.genres, t.themes_id, t.themes, d.demographics_id, d.demographic, s.studio_id, s.studio_name\r\n"
					+ "FROM animes a \r\n"
					+ "JOIN genres g ON a.genres_id = g.genres_id \r\n"
					+ "JOIN themes t ON a.themes_id = t.themes_id\r\n"
					+ "JOIN demographics d ON a.demographics_id = d.demographics_id\r\n"
					+ "JOIN studio s ON a.studio_id = s.studio_id\r\n"
					+ "ORDER BY a.anime_id;");
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
		} finally {
			DataBase.closeStatement(st);
			DataBase.closeResultSet(rs);
		}
	}

	@Override
	public List<String> getListName() {
		return null;
	}

	public int getId(String title) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT animes.anime_id FROM animes WHERE title = ?;");
			st.setString(1, title);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt("anime_id");
			}
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		return -1;
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
					"SELECT a.anime_id, a.title, g.genres_id, g.genres, t.themes_id, t.themes, d.demographics_id, d.demographic, s.studio_id, s.studio_name\r\n"
					+ "FROM animes a \r\n"
					+ "JOIN genres g ON a.genres_id = g.genres_id \r\n"
					+ "JOIN themes t ON a.themes_id = t.themes_id\r\n"
					+ "JOIN demographics d ON a.demographics_id = d.demographics_id\r\n"
					+ "JOIN studio s ON a.studio_id = s.studio_id\r\n"
					+ "WHERE a.title = '"+name+"';"
					);
			rs = st.executeQuery();
			while (rs.next()) {
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
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		} finally {
			DataBase.closeStatement(st);
			DataBase.closeResultSet(rs);
		}
		return null;
	}

}
