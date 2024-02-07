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
import model.entities.Themes;

public class ThemesDAO implements GenericDao<Themes>{
	
	private Connection conn;
	
	public ThemesDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Themes themes) {
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

	@Override
	public List<Themes> getListComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getListName() {
		List<String> listThemes = new ArrayList<>();
		try{
			PreparedStatement st = conn.prepareStatement(
					"SELECT themes.themes FROM themes;"
					);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				listThemes.add(rs.getString("themes"));
			}
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		return listThemes;
	}

	@Override
	public ObservableList<Themes> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isExists(String obj) {
		String title_exists = obj.toUpperCase();
		try {
			PreparedStatement st = conn.prepareStatement(
					"SELECT themes_id FROM themes WHERE themes = '"+title_exists+"';"
					);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getInt("themes_id");
			}
			return -1;
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

}
