package dao;

import java.sql.Connection;
import java.util.List;

import entities.Db;

public interface DbDao {
	List<Db> get();
	void update(Db d, String id);
	Connection TestConnection(Db d);
	void activate(String id);
	Db find(String id);
	Db getActivated();
}
