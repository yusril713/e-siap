package dao;

import java.util.List;

import entities.Wilayah;

public interface WilayahDao {
	List<Wilayah> get(String keyword);
	void delete(String nip, String wilayah);
	Wilayah find(String nip, String wilayah);
	void insert(Wilayah region, String[] w);
	void insert(Wilayah region);
	void update(Wilayah region, String nip, String wilayah);
}
