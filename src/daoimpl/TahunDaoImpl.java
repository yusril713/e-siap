package daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.Database;
import dao.TahunDao;
import entities.Tahun;

public class TahunDaoImpl implements TahunDao{

	@Override
	public List<Tahun> get() {
		List<Tahun> tahun = new ArrayList<>();
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_tahun order by tahun desc";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Tahun t = new Tahun();
				t.setTahun(rs.getString("tahun"));
				t.setAktif(rs.getString("aktif"));
				tahun.add(t);
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return tahun;
	}

	@Override
	public void insert(Tahun t) {
		Database db = new Database();
		db.con();
		try {
			String query = "insert into tb_tahun values(?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, t.getTahun());
			ps.setString(2, t.getAktif());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Tahun t, String tahun) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_tahun set tahun = ? where tahun = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, t.getTahun());
			ps.setString(2, tahun);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil diubah...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void activate(String tahun) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_tahun set aktif = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "T");
			ps.execute();
			
			query = "update tb_tahun set aktif = ?  where tahun = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, "Y");
			ps.setString(2, tahun);
			ps.execute();
			
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil aktifkan...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy(String tahun) {
		Database db = new Database();
		db.con();
		try {
			String query = "delete from tb_tahun where tahun = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, tahun);
			ps.execute();
			
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isAvailable(String tahun) {
		boolean available = false;
		Database db = new Database();
		try {
			String query = "select * from tb_tahun where tahun = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, tahun);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				available = true;
			}
			
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return available;
	}

	@Override
	public String getTahunAktif() {
		String tahun = "";
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_tahun where aktif = 'Y'";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				tahun = rs.getString("tahun");
			}
			rs.close();
			st.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tahun;
	}

}
