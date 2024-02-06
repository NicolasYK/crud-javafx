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
	public void insert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	private Genres instanceGenres(ResultSet rs) throws SQLException {
		Genres genres = new Genres(rs.getInt("genres.genres_id"), rs.getString("genres.genres"));
		return genres;
	}

	private Themes instanceThemes(ResultSet rs) throws SQLException {
		Themes themes = new Themes(rs.getInt("themes.themes_id"), rs.getString("themes.themes"));
		return themes;
	}

	private Demographics instanceDemo(ResultSet rs) throws SQLException {
		Demographics demo = new Demographics(rs.getInt("demographics.demographics_id"),
				rs.getString("demographics.demographic"));
		return demo;
	}

	private Studio instanceStudio(ResultSet rs) throws SQLException {
		Studio studio = new Studio(rs.getInt("studio.studio_id"), rs.getString("studio.studio_name"));
		return studio;
	}

	private Anime instanceAnime(ResultSet rs, Genres genres, Themes themes, Demographics demo, Studio studio)
			throws SQLException {
		Anime anime = new Anime(rs.getInt("animes.anime_id"), rs.getString("animes.title"), genres.getGenres(),
				themes.getThemes(), demo.getDemographic(), studio.getStudio());
		return anime;
	}

	@Override
	public List<Anime> getListComplete() {

		try (Statement st = conn.createStatement()) {
			List<Anime> list = new ArrayList<>();
			Map<Integer, Genres> map_genres = new HashMap<>();
			Map<Integer, Themes> map_themes = new HashMap<>();
			Map<Integer, Demographics> map_demo = new HashMap<>();
			Map<Integer, Studio> map_studio = new HashMap<>();
			ResultSet rs = st.executeQuery(
					"SELECT animes.anime_id, animes.title, genres.genres_id, genres.genres, themes.themes_id, themes.themes, demographics.demographics_id, demographics.demographic, studio.studio_id, studio.studio_name\r\n"
							+ "FROM animes\r\n" + "JOIN anime_genres ON animes.anime_id = anime_genres.anime_id\r\n"
							+ "JOIN genres ON anime_genres.genres_id = genres.genres_id\r\n"
							+ "JOIN anime_themes ON animes.anime_id = anime_themes.anime_id\r\n"
							+ "JOIN themes ON anime_themes.themes_id = themes.themes_id\r\n"
							+ "JOIN anime_demographics ON animes.anime_id = anime_demographics.anime_id\r\n"
							+ "JOIN demographics ON anime_demographics.demographics_id = demographics.demographics_id\r\n"
							+ "JOIN anime_studio ON animes.anime_id = anime_studio.anime_id\r\n"
							+ "JOIN studio ON anime_studio.studio_id = studio.studio_id;");
			while (rs.next()) {

				Genres genres = map_genres.get(rs.getInt("genres.genres_id"));
				if (genres == null) {
					genres = instanceGenres(rs);
					map_genres.put(rs.getInt("genres.genres_id"), genres);
				}
				Themes themes = map_themes.get(rs.getInt("themes.themes_id"));
				if (themes == null) {
					themes = instanceThemes(rs);
					map_themes.put(rs.getInt("themes.themes_id"), themes);
				}
				Demographics demo = map_demo.get(rs.getInt("demographics.demographics_id"));
				if (demo == null) {
					demo = instanceDemo(rs);
					map_demo.put(rs.getInt("demographics.demographics_id"), demo);
				}
				Studio studio = map_studio.get(rs.getInt("studio.studio_id"));
				if (studio == null) {
					studio = instanceStudio(rs);
					map_studio.put(rs.getInt("studio.studio_id"), studio);
				}
				Anime anime = instanceAnime(rs, genres, themes, demo, studio);
				list.add(anime);
			}
			return list;
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	@Override
	public List<String> getListName() {
		return null;
	}

	@Override
	public ObservableList<Anime> findByName(String name) {
		ObservableList<Anime> list = FXCollections.observableArrayList();
		try {
			PreparedStatement st = conn.prepareStatement(
					"SELECT animes.anime_id, animes.title, genres.genres, themes.themes, demographics.demographic, studio.studio_name\r\n"
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
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				list.add(new Anime(rs.getInt("anime_id"), rs.getString("title"), 
						rs.getString("genres"), rs.getString("themes"), 
						rs.getString("demographic"), rs.getString("studio_name")));
				return list;
			}
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		return null;
	}

}
