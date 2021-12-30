package entities;

public class Wilayah extends User {
	private String wilayah, hari, keterangan;

	public Wilayah() {}

	public Wilayah(String wilayah, String hari, String keterangan) {
		super();
		this.wilayah = wilayah;
		this.hari = hari;
		this.keterangan = keterangan;
	}

	public String getWilayah() {
		return wilayah;
	}

	public void setWilayah(String wilayah) {
		this.wilayah = wilayah;
	}

	public String getHari() {
		return hari;
	}

	public void setHari(String hari) {
		this.hari = hari;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}


}
