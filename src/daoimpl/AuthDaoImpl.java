package daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.Database;
import dao.AuthDao;
import entities.User;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class AuthDaoImpl implements AuthDao {

	@Override
	public User auth(String username, String password) {
		Database db = new Database();
		User user = new User();
		db.con();
		try {
			String query = "select * from tb_user where username = ? and password = md5(?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setUsername(rs.getString("username"));
				user.setLevel(rs.getInt("level"));
				user.setNama(rs.getString("nama"));
			} else {
				JOptionPane.showMessageDialog(null, "Username password tidak valid...");
			}

			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error login " + e);
		}

		return user;
	}

	public List<String> getKecamatan(String username) {
		List<String> kec = new ArrayList<>();
		Database db = new Database();
		db.con();
		try {
			String query = "select tb_wilayah.wilayah from tb_user "
					+ "join tb_wilayah on tb_user.nip = tb_wilayah.nip "
					+ "where tb_user.username = ? "
					+ "order by tb_wilayah.wilayah ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				kec.add(rs.getString("wilayah"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return kec;
	}

	@Override
	public void changePassword(String username, String password) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_user set password = md5(?) where username = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, password);
			ps.setString(2, username);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Password berhasil diubah...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initReport() {
		JFrame jf = null;
		Database db = new Database();
		db.con();
		try {
			String reportName = ".\\reports\\Init.jasper";
			JasperPrint jp = JasperFillManager.fillReport(reportName, null, db.con);
			JRViewer jv= new JRViewer(jp);
			jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | 0x6);
			jf.setDefaultCloseOperation(2);
			db.con.close();
		} catch(Exception e) {
			System.out.println("error inisialisasi: " + e);
		}
		jf.dispose();
	}
}
