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
import model.entities.Studio;

public class StudioDAO implements GenericDao<Studio>{
	
	private Connection conn;
	
	public StudioDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Studio studio) {
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
	public List<Studio> getListComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getListName() {
		List<String> listStudio = new ArrayList<>();
		try{
			PreparedStatement st = conn.prepareStatement(
					"SELECT studio.studio_name FROM studio;"
					);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				listStudio.add(rs.getString("studio_name"));
			}
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		return listStudio;
	}

	@Override
	public ObservableList<Studio> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isExists(String obj) {
		String studio_exists = obj.toUpperCase();
		try {
			PreparedStatement st = conn.prepareStatement(
					"SELECT studio_id FROM studio WHERE studio_name = '"+studio_exists+"';"
					);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getInt("studio_id");
			}
			return -1;
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

}
