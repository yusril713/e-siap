package entities;

import java.util.Date;

import config.Database;

public class User {
	private String nip, username, password,
		nama, noTelp, alamat, jenisKelamin,
		tempatLahir, kecamatan;
	public String getKecamatan() {
		return kecamatan;
	}


	public void setKecamatan(String kecamatan) {
		this.kecamatan = kecamatan;
	}
	private Date tglLahir;
	private int level;

	public User() {}


	public User(String nip, String username, String password, String nama, String noTelp, String alamat,
			String jenisKelamin, String tempatLahir, Date tglLahir, int level, Database db) {
		super();
		this.nip = nip;
		this.username = username;
		this.password = password;
		this.nama = nama;
		this.noTelp = noTelp;
		this.alamat = alamat;
		this.jenisKelamin = jenisKelamin;
		this.tempatLahir = tempatLahir;
		this.tglLahir = tglLahir;
		this.level = level;
	}


	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getNoTelp() {
		return noTelp;
	}
	public void setNoTelp(String noTelp) {
		this.noTelp = noTelp;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public String getJenisKelamin() {
		return jenisKelamin;
	}
	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}
	public String getTempatLahir() {
		return tempatLahir;
	}
	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}
	public Date getTglLahir() {
		return tglLahir;
	}
	public void setTglLahir(Date tglLahir) {
		this.tglLahir = tglLahir;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
