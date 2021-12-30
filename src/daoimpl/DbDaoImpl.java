package daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import config.Database;
import dao.DbDao;
import entities.Db;

public class DbDaoImpl implements DbDao{

	@Override
	public Db getActivated() {
		Db data = new Db();
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_database where aktif = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "Y");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				data.setDbName(rs.getString("db_name"));
				data.setHost(rs.getString("host"));
				data.setPassword(rs.getString("password"));
				data.setUser(rs.getString("user"));
				data.setDbUser(rs.getString("db_username"));
				data.setDbPassword(rs.getString("db_password"));
				data.setPort(rs.getInt("port"));
				data.setId(rs.getString("id"));
				data.setJenis(rs.getString("jenis"));
				data.setAktif(rs.getString("aktif"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List<Db> get() {
		Database db = new Database();
		List<Db> d = new ArrayList<>();
		db.con();
		try {
			String query = "select * from tb_database";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Db data = new Db();
				data.setDbName(rs.getString("db_name"));
				data.setHost(rs.getString("host"));
				data.setPassword(rs.getString("password"));
				data.setUser(rs.getString("user"));
				data.setDbUser(rs.getString("db_username"));
				data.setDbPassword(rs.getString("db_password"));
				data.setPort(rs.getInt("port"));
				data.setId(rs.getString("id"));
				data.setJenis(rs.getString("jenis"));
				data.setAktif(rs.getString("aktif"));

				d.add(data);
			}

			rs.close();
			st.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	@Override
	public void update(Db d, String id) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_database set host = ?, "
					+ "user = ?, password = ?, "
					+ "db_name = ?, db_username = ? , "
					+ "db_password = ?, port = ? where id = ?" ;
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, d.getHost());
			ps.setString(2, d.getUser());
			ps.setString(3, d.getPassword());
			ps.setString(4, d.getDbName());
			ps.setString(5, d.getDbUser());
			ps.setString(6, d.getPassword());
			ps.setString(7, "" + d.getPort());
			ps.setString(8, id);
			ps.execute();
			ps.close();
			db.con.close();

			JOptionPane.showMessageDialog(null, "Data berhasil diubah...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void activate(String id) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_database set aktif = ? ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "T");
			ps.execute();

			query = "update tb_database set aktif = ? where id = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, "Y");
			ps.setString(2, id);
			ps.execute();

			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Database server berhasil diaktifkan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Db find(String id) {
		Database db = new Database();
		Db data = new Db();
		db.con();
		try {
			String query = "select * from tb_database where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				data.setDbName(rs.getString("db_name"));
				data.setHost(rs.getString("host"));
				data.setPassword(rs.getString("password"));
				data.setUser(rs.getString("user"));
				data.setDbUser(rs.getString("db_username"));
				data.setDbPassword(rs.getString("db_password"));
				data.setPort(rs.getInt("port"));
				data.setId(rs.getString("id"));
				data.setJenis(rs.getString("jenis"));
				data.setAktif(rs.getString("aktif"));
			}

			rs.close();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Connection TestConnection(Db d) {
		Connection con = null;
		if (d.getJenis().equals("Local")) {
			try {
				String url="jdbc:mysql://" + d.getHost() + "/" + d.getDbName() + "?autoReconnect=true&useSSL=false";
				Class.forName("com.mysql.jdbc.Driver");
				con =  DriverManager.getConnection(url, d.getUser(), d.getPassword());
				JOptionPane.showMessageDialog(null, "Connected to local DB...");
			} catch (Exception e){
				JOptionPane.showMessageDialog(null, "Koneksi Database Gagal " + e);
			}

		} else if (d.getJenis().equals("Server")) {
			//SSL Tunnerl settings
	        String host= d.getHost();  //Remote host to connect to
	        String user= d.getUser();					//Remote shell username
	        String password= d.getPassword();	//Remote shell password
	        int lport = 56560; 						//Local port to create
	        int rport = 3306; 						//Destination port
	        String rhost="127.0.0.1";				//Destination address

	        //MySQL Connection settings
	        String dbuserName = d.getDbUser();			//mysql username
	        String dbpassword = d.getDbPassword();			//mysql password
	        String url = "jdbc:mysql://127.0.0.1:"+lport+"/" + d.getDbName(); //connect to local end of SSL tunnel
	        String driverName="com.mysql.jdbc.Driver";

	        Connection conn = null;
	        Session session= null;
	        try{
	            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
	            java.util.Properties config = new java.util.Properties();
	            config.put("StrictHostKeyChecking", "no");
	            JSch jsch = new JSch();
	            session=jsch.getSession(user, host, d.getPort());
	            session.setPassword(password);
	            session.setConfig(config);
	            session.connect();
	            System.out.println("-- SSH connection successful");
	            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
	            System.out.println("-- localhost:"+assinged_port+" tunneled to "+host+":"+rport);

	            //mysql database connectivity
	            Class.forName(driverName).newInstance();
	            System.out.println ("-- Mysql connect to " +url +" " +dbuserName +" " +dbpassword);
	            conn = DriverManager.getConnection (url, dbuserName, dbpassword);
	            JOptionPane.showMessageDialog(null, "Connected to server...");

	            conn.close();
	            session.disconnect();
	        }catch(Exception e){
	            JOptionPane.showMessageDialog(null, e);
	        }
		}
		return con;
	}
}
