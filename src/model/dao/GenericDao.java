package model.dao;

import java.util.List;

import javafx.collections.ObservableList;
import model.entities.Studio;

public interface GenericDao<Type> {
	
	void insert();
	void update();
	void delete();
	List<Type> getListComplete();
	List<String> getListName();
	ObservableList<Type> findByName(String name);

}
