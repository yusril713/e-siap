package dao;

import java.util.List;

import entities.Tahun;

public interface TahunDao {
	List<Tahun> get();
	void insert(Tahun t);
	void update(Tahun t, String tahun);
	void activate(String tahun);
	void destroy(String tahun);
	boolean isAvailable(String tahun);
	String getTahunAktif();
}
