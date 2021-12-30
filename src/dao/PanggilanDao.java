package dao;

import java.util.List;

import entities.Panggilan;

public interface PanggilanDao {
	List<Panggilan> getKecamatan();
	List<Panggilan> getKelurahan(String kecamatan);
	List<Panggilan> get(String key);
	List<Panggilan> get(String key, int level);
	List<Panggilan> get(String key, int level, String[] kecamatan);
	void destroy(String id);
}
