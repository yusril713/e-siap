package views.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import config.ImagePanel;
import daoimpl.DbDaoImpl;
import entities.Db;

public class PSettingDb extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txtHost;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JTextField txtPort;
	private JTextField txtDbUser;
	private JPasswordField txtDbPassword;
	private JTextField txtDbName;
	private JLabel lblHost;
	private JLabel lblPassword;
	private JLabel lblUsername;
	private JLabel lblPort;
	private JLabel lblDbUser;
	private JLabel lblDbPassword;
	private JLabel lblDbName;
	private JLabel lblNewLabel;
	private JSeparator separator;
	private String id;

	/**
	 * Create the panel.
	 */
	public PSettingDb() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				GetData();
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 30, 0, 127, 0, 1, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		lblNewLabel = new JLabel("Database Server");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 7;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 7;
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);

		lblHost = new JLabel("Host");
		lblHost.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblHost = new GridBagConstraints();
		gbc_lblHost.anchor = GridBagConstraints.WEST;
		gbc_lblHost.insets = new Insets(0, 0, 5, 5);
		gbc_lblHost.gridx = 1;
		gbc_lblHost.gridy = 3;
		add(lblHost, gbc_lblHost);

		txtHost = new JTextField();
		txtHost.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtHost = new GridBagConstraints();
		gbc_txtHost.gridwidth = 2;
		gbc_txtHost.insets = new Insets(0, 0, 5, 5);
		gbc_txtHost.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHost.gridx = 3;
		gbc_txtHost.gridy = 3;
		add(txtHost, gbc_txtHost);
		txtHost.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 9;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 6;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0)
					GetDetail();
			}
		});
		scrollPane.setViewportView(table);

		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 4;
		add(lblUsername, gbc_lblUsername);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.gridwidth = 2;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 3;
		gbc_txtUsername.gridy = 4;
		add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 5;
		add(lblPassword, gbc_lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.gridwidth = 2;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 3;
		gbc_txtPassword.gridy = 5;
		add(txtPassword, gbc_txtPassword);

		lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 6;
		add(lblPort, gbc_lblPort);

		txtPort = new JTextField();
		txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.gridwidth = 2;
		gbc_txtPort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPort.gridx = 3;
		gbc_txtPort.gridy = 6;
		add(txtPort, gbc_txtPort);
		txtPort.setColumns(10);

		lblDbUser = new JLabel("Db User");
		lblDbUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbUser = new GridBagConstraints();
		gbc_lblDbUser.anchor = GridBagConstraints.WEST;
		gbc_lblDbUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbUser.gridx = 1;
		gbc_lblDbUser.gridy = 7;
		add(lblDbUser, gbc_lblDbUser);

		txtDbUser = new JTextField();
		txtDbUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtDbUser = new GridBagConstraints();
		gbc_txtDbUser.gridwidth = 2;
		gbc_txtDbUser.insets = new Insets(0, 0, 5, 5);
		gbc_txtDbUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbUser.gridx = 3;
		gbc_txtDbUser.gridy = 7;
		add(txtDbUser, gbc_txtDbUser);
		txtDbUser.setColumns(10);

		lblDbPassword = new JLabel("Db Password");
		lblDbPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbPassword = new GridBagConstraints();
		gbc_lblDbPassword.anchor = GridBagConstraints.WEST;
		gbc_lblDbPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbPassword.gridx = 1;
		gbc_lblDbPassword.gridy = 8;
		add(lblDbPassword, gbc_lblDbPassword);

		txtDbPassword = new JPasswordField();
		txtDbPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtDbPassword = new GridBagConstraints();
		gbc_txtDbPassword.gridwidth = 2;
		gbc_txtDbPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtDbPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbPassword.gridx = 3;
		gbc_txtDbPassword.gridy = 8;
		add(txtDbPassword, gbc_txtDbPassword);

		lblDbName = new JLabel("Db Name");
		lblDbName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbName = new GridBagConstraints();
		gbc_lblDbName.anchor = GridBagConstraints.WEST;
		gbc_lblDbName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbName.gridx = 1;
		gbc_lblDbName.gridy = 9;
		add(lblDbName, gbc_lblDbName);

		txtDbName = new JTextField();
		txtDbName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtDbName = new GridBagConstraints();
		gbc_txtDbName.gridwidth = 2;
		gbc_txtDbName.insets = new Insets(0, 0, 5, 5);
		gbc_txtDbName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbName.gridx = 3;
		gbc_txtDbName.gridy = 9;
		add(txtDbName, gbc_txtDbName);
		txtDbName.setColumns(10);

		JButton btnActivate = new JButton("Activate");
		btnActivate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					DbDaoImpl d = new DbDaoImpl();
					d.activate(id);
					GetData();
				} else
					JOptionPane.showMessageDialog(null, "Pilih database yang akan diaktifkan!");
			}
		});

		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengubah data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (table.getSelectedRow() >= 0) {
						Update(id);
						GetData();
					}
					else
						JOptionPane.showMessageDialog(null, "Pilih data yang akan diubah!");
				}
			}
		});

		JButton btnTesConnection = new JButton("Tes Koneksi");
		btnTesConnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					DbDaoImpl d = new DbDaoImpl();
					Db data = d.find(id);
					d.TestConnection(data);
				} else
					JOptionPane.showMessageDialog(null, "Pilih data!");
			}
		});
		btnTesConnection.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnTesConnection = new GridBagConstraints();
		gbc_btnTesConnection.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTesConnection.insets = new Insets(0, 0, 5, 5);
		gbc_btnTesConnection.gridx = 3;
		gbc_btnTesConnection.gridy = 10;
		add(btnTesConnection, gbc_btnTesConnection);
		btnSimpan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSimpan.insets = new Insets(0, 0, 5, 5);
		gbc_btnSimpan.gridx = 4;
		gbc_btnSimpan.gridy = 10;
		add(btnSimpan, gbc_btnSimpan);
		btnActivate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnActivate = new GridBagConstraints();
		gbc_btnActivate.insets = new Insets(0, 0, 5, 5);
		gbc_btnActivate.gridx = 7;
		gbc_btnActivate.gridy = 12;
		add(btnActivate, gbc_btnActivate);

		ImagePanel lblNewLabel = new ImagePanel(".\\icon\\BG menu.png");
		GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
		gbc_lblNewLabel1.gridx = 0;
		gbc_lblNewLabel1.gridy = 0;
		gbc_lblNewLabel1.gridheight = 14;
		gbc_lblNewLabel1.gridwidth = 9;
		add(lblNewLabel, gbc_lblNewLabel1);

	}

	private void GetData() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("id");
		model.addColumn("Jenis");
		model.addColumn("Host");
		model.addColumn("Password");
		model.addColumn("Port");
		model.addColumn("Db User");
		model.addColumn("Db Password");
		model.addColumn("Db Name");
		model.addColumn("Aktif");
		model.addColumn("Username");

		DbDaoImpl dbDaoImpl = new DbDaoImpl();
		List<Db> data  = dbDaoImpl.get();
		for (Db d : data) {
			model.addRow(new Object[] {
					d.getId(),
					d.getJenis(),
					d.getHost(),
					d.getPassword(),
					d.getPort(),
					d.getDbUser(),
					d.getPassword(),
					d.getDbName(),
					d.getAktif(),
					d.getUser(),
			});
		}
		table.setModel(model);
	}

	private void GetDetail() {
		id = table.getValueAt(table.getSelectedRow(), 0).toString();
		txtUsername.setText(table.getValueAt(table.getSelectedRow(), 9).toString());
		txtHost.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
		txtPassword.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
		txtPort.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
		txtDbUser.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
		txtDbPassword.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
		txtDbName.setText(table.getValueAt(table.getSelectedRow(), 7).toString());
	}

	@SuppressWarnings("deprecation")
	private void Update(String id) {
		DbDaoImpl dbDaoImpl = new DbDaoImpl();
		Db d = new Db();
		d.setDbName(txtDbName.getText());
		d.setDbPassword(txtDbPassword.getText());
		d.setDbUser(txtDbUser.getText());
		d.setHost(txtHost.getText());
		d.setPassword(txtPassword.getText());
		d.setPort(Integer.parseInt(txtPort.getText()));
		d.setUser(txtUsername.getText());
		dbDaoImpl.update(d, id);
	}
}
