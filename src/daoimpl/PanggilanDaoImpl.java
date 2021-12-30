package daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.Database;
import dao.PanggilanDao;
import entities.Panggilan;

public class PanggilanDaoImpl implements PanggilanDao{

	@Override
	public List<Panggilan> getKecamatan() {
		List<Panggilan> kecamatan = new ArrayList<>();
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_kecamatan order by nama";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Panggilan p = new Panggilan();
				p.setKecamatan(rs.getString("nama"));
				kecamatan.add(p);
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return kecamatan;
	}

	@Override
	public List<Panggilan> getKelurahan(String kec) {
		List<Panggilan> kelurahan = new ArrayList<>();
		Database db = new Database();
		db.con();
		try {
			String query = "select *, tb_kelurahan.nama as kelurahan from tb_kecamatan "
					+ "join tb_kelurahan on tb_kelurahan.id_kec = tb_kecamatan.id "
					+ "where tb_kecamatan.nama = ?"
					+ "order by tb_kelurahan.nama";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, kec);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Panggilan p = new Panggilan();
				p.setKelurahan(rs.getString("kelurahan"));
				kelurahan.add(p);
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return kelurahan;
	}

	@Override
	public List<Panggilan> get(String key) {
		List<Panggilan> p = new ArrayList<>();
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_panggilan where no_perkara like ? "
					+ "or jenis like ? or nama_js like ? or nama_pp like ? or nama like ? "
					+ "order by jenis";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ps.setString(3, "%" + key + "%");
			ps.setString(4, "%" + key + "%");
			ps.setString(5, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Panggilan pgl = new Panggilan();
				pgl.setAlamat(rs.getString("alamat"));
				pgl.setBiaya(rs.getInt("biaya"));
				pgl.setJenis(rs.getString("jenis"));
				pgl.setKecamatan(rs.getString("kecamatan"));
				pgl.setKelurahan(rs.getString("kelurahan"));
				pgl.setNama(rs.getString("nama"));
				pgl.setPerkaraNo(rs.getString("no_perkara"));
				pgl.setId(rs.getString("id"));
				pgl.setNamaPp(rs.getString("nama_pp"));
				pgl.setPglPbt(rs.getString("pgl_pbt"));
				pgl.setJurusita(rs.getString("nama_js"));

				if (rs.getString("jenis").equals("Tunda")) {
					pgl.setTglPutus(null);
					pgl.setTglSidang(rs.getDate("tgl_sidang"));
				} else if (rs.getString("jenis").equals("Pbt")) {
					pgl.setTglPutus(rs.getDate("tgl_putus"));
					pgl.setTglSidang(null);
				} else {
					pgl.setTglPutus(rs.getDate("tgl_putus"));
					pgl.setTglSidang(rs.getDate("tgl_sidang"));
				}
				p.add(pgl);
			}

			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	@Override
	public List<Panggilan> get(String key, int level) {
		List<Panggilan> p = new ArrayList<>();
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_panggilan where no_perkara like ? "
					+ "or jenis like ? or nama_js like ? or nama_pp like ? or nama like ? "
					+ "order by jenis";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ps.setString(3, "%" + key + "%");
			ps.setString(4, "%" + key + "%");
			ps.setString(5, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Panggilan pgl = new Panggilan();
				pgl.setAlamat(rs.getString("alamat"));
				pgl.setBiaya(rs.getInt("biaya"));
				pgl.setJenis(rs.getString("jenis"));
				pgl.setKecamatan(rs.getString("kecamatan"));
				pgl.setKelurahan(rs.getString("kelurahan"));
				pgl.setNama(rs.getString("nama"));
				pgl.setPerkaraNo(rs.getString("no_perkara"));
				pgl.setId(rs.getString("id"));
				pgl.setNamaPp(rs.getString("nama_pp"));
				pgl.setPglPbt(rs.getString("pgl_pbt"));
				if (level == 3) {
					pgl.setJurusita("");
				} else
					pgl.setJurusita(rs.getString("nama_js"));

				if (rs.getString("jenis").equals("Tunda")) {
					pgl.setTglPutus(null);
					pgl.setTglSidang(rs.getDate("tgl_sidang"));
				} else if (rs.getString("jenis").equals("Pbt")) {
					pgl.setTglPutus(rs.getDate("tgl_putus"));
					pgl.setTglSidang(null);
				} else {
					pgl.setTglPutus(rs.getDate("tgl_putus"));
					pgl.setTglSidang(rs.getDate("tgl_sidang"));
				}
				p.add(pgl);
			}

			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	@Override
	public List<Panggilan> get(String key, int level, String[] kecamatan) {
		List<Panggilan> p = new ArrayList<>();
		Database db = new Database();
		db.con();
		try {
			StringBuilder query = new StringBuilder("select * from tb_panggilan where (no_perkara like ? "
					+ "or jenis like ? or nama_js like ? or nama_pp like ? or nama like ?) and kecamatan in (");
//					+ "order by jenis";

			for (int i = 0; i < kecamatan.length; i++) {
				query.append("?,");
			}
			query.delete(query.length() - 1, query.length());
			query.append(") order by jenis");

			PreparedStatement ps = db.con.prepareStatement(query.toString());
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ps.setString(3, "%" + key + "%");
			ps.setString(4, "%" + key + "%");
			ps.setString(5, "%" + key + "%");
			for (int i = 0; i < kecamatan.length; i++) {
				ps.setString(i + 6, kecamatan[i]);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Panggilan pgl = new Panggilan();
				pgl.setAlamat(rs.getString("alamat"));
				pgl.setBiaya(rs.getInt("biaya"));
				pgl.setJenis(rs.getString("jenis"));
				pgl.setKecamatan(rs.getString("kecamatan"));
				pgl.setKelurahan(rs.getString("kelurahan"));
				pgl.setNama(rs.getString("nama"));
				pgl.setPerkaraNo(rs.getString("no_perkara"));
				pgl.setId(rs.getString("id"));
				pgl.setNamaPp(rs.getString("nama_pp"));
				pgl.setPglPbt(rs.getString("pgl_pbt"));
				pgl.setJurusita(rs.getString("nama_js"));

				if (rs.getString("jenis").equals("Tunda")) {
					pgl.setTglPutus(null);
					pgl.setTglSidang(rs.getDate("tgl_sidang"));
				} else if (rs.getString("jenis").equals("Pbt")) {
					pgl.setTglPutus(rs.getDate("tgl_putus"));
					pgl.setTglSidang(null);
				} else {
					pgl.setTglPutus(rs.getDate("tgl_putus"));
					pgl.setTglSidang(rs.getDate("tgl_sidang"));
				}
				p.add(pgl);
			}

			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	@Override
	public void destroy(String id) {
		Database db = new Database();
		db.con();
		try {
			String query = "delete from tb_panggilan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.execute();
			ps.close();
			db.con.close();

			JOptionPane.showMessageDialog(null, "Data berhasil dihapus...");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
