package entities;

import java.util.Date;

public class Panggilan extends User{
	private String id, perkaraNo, namaPp, jenis, kecamatan, kelurahan, nama, alamat, pglPbt, jurusita;
	public String getJurusita() {
		return jurusita;
	}
	public void setJurusita(String jurusita) {
		this.jurusita = jurusita;
	}
	public String getPglPbt() {
		return pglPbt;
	}
	public void setPglPbt(String pglPbt) {
		this.pglPbt = pglPbt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getNama() {
		return nama;
	}
	@Override
	public void setNama(String nama) {
		this.nama = nama;
	}
	@Override
	public String getAlamat() {
		return alamat;
	}
	@Override
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	private Date tglSidang, tglPutus;
	private int biaya;


	@Override
	public String getKecamatan() {
		return kecamatan;
	}
	@Override
	public void setKecamatan(String kecamatan) {
		this.kecamatan = kecamatan;
	}
	public String getKelurahan() {
		return kelurahan;
	}
	public void setKelurahan(String kelurahan) {
		this.kelurahan = kelurahan;
	}
	public String getPerkaraNo() {
		return perkaraNo;
	}
	public void setPerkaraNo(String perkaraNo) {
		this.perkaraNo = perkaraNo;
	}
	public String getNamaPp() {
		return namaPp;
	}
	public void setNamaPp(String namaPp) {
		this.namaPp = namaPp;
	}
	public String getJenis() {
		return jenis;
	}
	public void setJenis(String jenis) {
		this.jenis = jenis;
	}
	public Date getTglSidang() {
		return tglSidang;
	}
	public void setTglSidang(Date tglSidang) {
		this.tglSidang = tglSidang;
	}
	public Date getTglPutus() {
		return tglPutus;
	}
	public void setTglPutus(Date tglPutus) {
		this.tglPutus = tglPutus;
	}
	public int getBiaya() {
		return biaya;
	}
	public void setBiaya(int biaya) {
		this.biaya = biaya;
	}
}