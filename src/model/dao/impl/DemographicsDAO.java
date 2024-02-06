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
import model.entities.Demographics;

public class DemographicsDAO implements GenericDao<Demographics>{
	
	private Connection conn;
	

	public DemographicsDAO(Connection conn) {
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

	@Override
	public List<Demographics> getListComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getListName() {
		List<String> listDemo = new ArrayList<>();
		try{
			PreparedStatement st = conn.prepareStatement(
					"SELECT demographics.demographic FROM demographics;"
					);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				listDemo.add(rs.getString("demographic"));
			}
		}
		catch(SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		return listDemo;
	}

	@Override
	public ObservableList<Demographics> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
