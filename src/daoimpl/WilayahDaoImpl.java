package daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.Database;
import dao.WilayahDao;
import entities.Wilayah;

public class WilayahDaoImpl implements WilayahDao {

	@Override
	public List<Wilayah> get(String keyword) {
		Database db = new Database();
		db.con();

		List<Wilayah> regions = new ArrayList<>();

		try {
			String query = "select tb_user.*, tb_wilayah.wilayah, "
					+ "tb_wilayah.hari, tb_wilayah.keterangan "
					+ "from tb_user "
					+ "join tb_wilayah on tb_wilayah.nip = tb_user.nip "
					+ "where tb_user.nip like ? or tb_user.nama like ? or wilayah like ? "
					+ "or hari like ? order by tb_user.nip asc";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Wilayah region = new Wilayah();
				region.setNip(rs.getString("nip"));
				region.setNama(rs.getString("nama"));
				region.setWilayah(rs.getString("wilayah"));
				region.setHari(rs.getString("hari"));
				region.setKeterangan(rs.getString("keterangan"));

				regions.add(region);
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return regions;
	}

	public List<Wilayah> all() {
		Database db = new Database();
		db.con();

		List<Wilayah> regions = new ArrayList<>();

		try {
			String query = "select nama from tb_kecamatan order by nama";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Wilayah region = new Wilayah();
				region.setWilayah(rs.getString("nama"));
				regions.add(region);
			}
			st.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return regions;
	}

	@Override
	public void delete(String nip, String wilayah) {
		Database db = new Database();
		db.con();
		try {
			String query = "delete from tb_wilayah where nip = ? and wilayah = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, nip);
			ps.setString(2, wilayah);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Wilayah find(String nip, String wilayah) {
		Wilayah reg = null;
		Database db = new Database();
		db.con();
		try {
			String query = "select tb_user.*, tb_wilayah.wilayah, "
					+ "tb_wilayah.hari, tb_wilayah.keterangan "
					+ "from tb_user "
					+ "join tb_wilayah on tb_wilayah.nip = tb_user.nip "
					+ "where tb_wilayah.nip = ? and wilayah = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, nip);
			ps.setString(2, wilayah);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				reg = new Wilayah();
				reg.setKeterangan(rs.getString("keterangan"));
				reg.setWilayah(rs.getString("wilayah"));
				reg.setNip(rs.getString("nip"));
				reg.setNama(rs.getString("nama"));
				reg.setWilayah(rs.getString("wilayah"));
				reg.setHari(rs.getString("hari"));
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return reg;
	}

	@Override
	public void insert(Wilayah region, String[] w) {
		Database db = new Database();
		db.con();
		try {
			String query = "insert into tb_wilayah (nip, wilayah, hari, keterangan) "
					+ "values(?,?,?,?)";
			PreparedStatement ps = null;
			for (String element : w) {
				ps = db.con.prepareStatement(query);
				ps.setString(1, region.getNip());
				ps.setString(2, element);
				ps.setString(3, region.getHari());
				ps.setString(4, region.getKeterangan().equals("Masukkan keterangan...") ? "-" : region.getKeterangan());
				ps.execute();
			}
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Wilayah region) {
		Database db = new Database();
		db.con();
		try {
			String query = "insert into tb_wilayah (nip, wilayah, hari, keterangan) "
					+ "values(?,?,?,?)";
			PreparedStatement ps = null;
			ps = db.con.prepareStatement(query);
			ps.setString(1, region.getNip());
			ps.setString(2, region.getWilayah());
			ps.setString(3, region.getHari());
			ps.setString(4, region.getKeterangan().equals("Masukkan keterangan...") ? "-" : region.getKeterangan());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Wilayah region, String nip, String wilayah) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_wilayah set nip = ?, wilayah = ?, hari = ?, "
					+ "keterangan = ? where nip = ? and wilayah = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, region.getNip());
			ps.setString(2, region.getWilayah());
			ps.setString(3, region.getHari());
			ps.setString(4, region.getKeterangan());
			ps.setString(5, nip);
			ps.setString(6, wilayah);
			ps.execute();
			ps.close();
			db.con.close();

			JOptionPane.showMessageDialog(null, "Data berhasil diperbaharui...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
