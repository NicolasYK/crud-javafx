package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DataBaseException;
import javafx.collections.ObservableList;
import model.dao.GenericDao;
import model.entities.Genres;

public class GenresDAO implements GenericDao<Genres>{
	
	private Connection conn;
	
	public GenresDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Genres genres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Genres genres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Genres genres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Genres> getListComplete() {
		List<Genres> genresList = new ArrayList<>();
		try{
			PreparedStatement st = conn.prepareStatement(
					"SELECT genres.genres_id, genres.genres FROM genres;"
					);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Genres genres = new Genres(rs.getInt("genres_id"), rs.getString("genres"));
				genresList.add(genres);
			}
		}
		catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
		return genresList;
	}

	@Override
	public List<String> getListName() {
		List<String> listGenres = new ArrayList<>();
		try{
			PreparedStatement st = conn.prepareStatement(
					"SELECT genres.genres FROM genres;"
					);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				listGenres.add(rs.getString("genres"));
			}
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		return listGenres;
	}

	@Override
	public ObservableList<Genres> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isExists(String obj) {
		String genres_exists = obj.toUpperCase();
		try {
			PreparedStatement st = conn.prepareStatement(
					"SELECT genres_id FROM genres WHERE genres = '"+genres_exists+"';"
					);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getInt("genres_id");
			}
			return -1;
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

}
