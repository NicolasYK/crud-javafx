package model.dao;

import java.util.List;

import javafx.collections.ObservableList;

public interface GenericDao<Type> {
	
	void insert(Type obj);
	void update();
	void delete();
	int isExists(String obj);
	List<Type> getListComplete();
	List<String> getListName();
	ObservableList<Type> findByName(String name);

}
