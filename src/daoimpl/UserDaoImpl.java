package daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.Database;
import dao.UserDao;
import entities.User;

public class UserDaoImpl implements UserDao {
	private Database db;

	public UserDaoImpl() {
		db = new Database();
	}

	@Override
	public void insert(User user) {
		db.con();
		try {
			String query = "insert into tb_user values(?,?,md5(?),?,?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, user.getNip());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getNama());
			ps.setString(5, user.getNoTelp());
			ps.setString(6, user.getAlamat());
			ps.setString(7, user.getJenisKelamin().equals("Laki-laki") ? "L" : "P");
			ps.setString(8, user.getTempatLahir());
			ps.setString(9, new SimpleDateFormat("yyyy-MM-dd").format(user.getTglLahir()));
			ps.setString(10, user.getLevel() == 1 ? "1" :
				(user.getLevel() == 2 ? "2" : (user.getLevel() == 3) ? "3" : "4"));
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(User user, String nip) {
		db.con();
		try {
			String query = "update tb_user set nip = ?, nama = ?, no_telp = ?, "
					+ "alamat = ?, jenis_kelamin = ?, tempat_lahir = ?, tgl_lahir = ?, "
					+ "level = ? where nip = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, user.getNip());
			ps.setString(2, user.getNama());
			ps.setString(3, user.getNoTelp());
			ps.setString(4, user.getAlamat());
			ps.setString(5, user.getJenisKelamin().equals("Laki-laki") ? "L" : "P");
			ps.setString(6, user.getTempatLahir());
			ps.setString(7, new SimpleDateFormat("yyyy-MM-dd").format(user.getTglLahir()));
			ps.setString(8, user.getLevel() == 1 ? "1" :
				(user.getLevel() == 2 ? "2" : (user.getLevel() == 3) ? "3" : "4"));
			ps.setString(9, nip);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil diperbaharui...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findMyAccount(String username) {
		User user = new User();
		db.con();
		try {
			String query = "select *, date_format(tgl_lahir, '%Y') as tahun, "
					+ "date_format(tgl_lahir, '%m') as bulan, "
					+ "date_format(tgl_lahir, '%d') as tanggal "
					+ "from tb_user where username = ? ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setAlamat(rs.getString("alamat"));
				user.setNama(rs.getString("nama"));
				user.setNip(rs.getString("nip"));
				user.setNoTelp(rs.getString("no_telp"));
				user.setTempatLahir(rs.getString("tempat_lahir"));
				user.setJenisKelamin(rs.getString("jenis_kelamin").equals("L") ? "Laki-laki" : "Perempuan");
				user.setTglLahir(rs.getDate("tgl_lahir"));
			}
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void updateMyAccount(User user, String username) {
		db.con();
		try {
			String query = "update tb_user set nip = ?, nama = ?, no_telp = ?, "
					+ "alamat = ?, jenis_kelamin = ?, tempat_lahir = ?, tgl_lahir = ? "
					+ "where username = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, user.getNip());
			ps.setString(2, user.getNama());
			ps.setString(3, user.getNoTelp());
			ps.setString(4, user.getAlamat());
			ps.setString(5, user.getJenisKelamin().equals("Laki-laki") ? "L" : "P");
			ps.setString(6, user.getTempatLahir());
			ps.setString(7, new SimpleDateFormat("yyyy-MM-dd").format(user.getTglLahir()));
			ps.setString(8, username);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil diperbaharui...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String nip) {
		db.con();
		try {
			String query = "delete from tb_user where nip = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, nip);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> get(String key) {
		List<User> users = new ArrayList<>();
		db.con();
		try {
			String query = "select * from tb_user where nip like ? or nama like ? "
					+ "or username like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ps.setString(3, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setNip(rs.getString("nip"));
				user.setNama(rs.getString("nama"));
				user.setJenisKelamin(rs.getString("jenis_kelamin"));
				user.setTempatLahir(rs.getString("tempat_lahir"));
				user.setTglLahir(rs.getDate("tgl_lahir"));
				user.setNoTelp("no_telp");
				user.setAlamat(rs.getString("alamat"));
				user.setUsername(rs.getString("username"));
				user.setLevel(rs.getInt("level"));
				users.add(user);
			}
			rs.close();
			ps.close();
			db.con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public boolean IsAvalilable(String nip, String username) {
		db.con();
		try {
			String query = "select * from tb_user where nip = ? or username = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, nip);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public User find(String nip) {
		User user = new User();
		db.con();
		try {
			String query = "select *, date_format(tgl_lahir, '%Y') as tahun, "
					+ "date_format(tgl_lahir, '%m') as bulan, "
					+ "date_format(tgl_lahir, '%d') as tanggal "
					+ "from tb_user where nip = ? ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, nip);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setAlamat(rs.getString("alamat"));
				user.setJenisKelamin(rs.getString("jenis_kelamin").equals("L") ? "Laki-laki" : "Perempuan");
				user.setLevel(rs.getInt("level"));
				user.setNama(rs.getString("nama"));
				user.setNip(rs.getString("nip"));
				user.setNoTelp(rs.getString("no_telp"));
				user.setTempatLahir(rs.getString("tempat_lahir"));
				user.setTglLahir(rs.getDate("tgl_lahir"));
				user.setUsername(rs.getString("username"));
			}
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
