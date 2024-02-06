package model.dao;

import db.DataBase;
import model.dao.impl.AnimeDAO;
import model.dao.impl.DemographicsDAO;
import model.dao.impl.GenresDAO;
import model.dao.impl.StudioDAO;
import model.dao.impl.ThemesDAO;

public class DAOFactory {
	
	public static AnimeDAO createAnimeDao() {
		return new AnimeDAO(DataBase.getConnection());
	}
	
	public static GenresDAO createGenresDao() {
		return new GenresDAO(DataBase.getConnection());
	}
	
	public static ThemesDAO createThemesDao() {
		return new ThemesDAO(DataBase.getConnection());
	}
	
	public static DemographicsDAO createDemoDao() {
		return new DemographicsDAO(DataBase.getConnection());
	}
	
	public static StudioDAO createStudioDAO() {
		return new StudioDAO(DataBase.getConnection());
	}

}
