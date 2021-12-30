package views.panel;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import config.ImagePanel;
import daoimpl.TahunDaoImpl;
import entities.Tahun;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PPilihTahun extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txtTahun;

	/**
	 * Create the panel.
	 */
	public PPilihTahun() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				TampilData();
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 225, 0, 0, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Tahun Aktif");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 4;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tahun");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtTahun = new JTextField();
		txtTahun.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtTahun = new GridBagConstraints();
		gbc_txtTahun.insets = new Insets(0, 0, 5, 5);
		gbc_txtTahun.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTahun.gridx = 2;
		gbc_txtTahun.gridy = 2;
		add(txtTahun, gbc_txtTahun);
		txtTahun.setColumns(10);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Tahun tahun = new Tahun();
					TahunDaoImpl tDaoImpl = new TahunDaoImpl();
					tahun.setTahun(txtTahun.getText());
					tahun.setAktif("T");
					tDaoImpl.insert(tahun);
					TampilData();
				}
			}
		});
		btnSimpan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.insets = new Insets(0, 0, 5, 5);
		gbc_btnSimpan.gridx = 3;
		gbc_btnSimpan.gridy = 2;
		add(btnSimpan, gbc_btnSimpan);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0) {
					txtTahun.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
					if (konfirm == 0) {
						TahunDaoImpl tDaoImpl = new TahunDaoImpl();
						tDaoImpl.destroy(txtTahun.getText());
						TampilData();
					}
				}
			}
		});
		
		JButton btnNewButton = new JButton("Activate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengaktifkan tahun " + txtTahun.getText() + "?", "Konfirmasi", 0);
				if (konfirm == 0) {
					TahunDaoImpl tDaoImpl = new TahunDaoImpl();
					tDaoImpl.activate(txtTahun.getText());
					TampilData();
				}
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 4;
		add(btnNewButton, gbc_btnNewButton);
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 6;
		gbc_btnHapus.gridy = 4;
		add(btnHapus, gbc_btnHapus);
		
		ImagePanel lblNewLabel1 = new ImagePanel(".\\icon\\BG menu.png");
		GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
		gbc_lblNewLabel1.gridx = 0;
		gbc_lblNewLabel1.gridy = 0;
		gbc_lblNewLabel1.gridheight = 14;
		gbc_lblNewLabel1.gridwidth = 9;
		add(lblNewLabel1, gbc_lblNewLabel1);

	}

	private void TampilData() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Tahun");
		model.addColumn("Aktif");
		
		TahunDaoImpl tDaoImpl = new TahunDaoImpl();
		List<Tahun> tahun = tDaoImpl.get();
		for (Tahun t : tahun) {
			model.addRow(new Object[] {
					t.getTahun(),
					t.getAktif()
			});
		}
		table.setModel(model);
		table.setAutoResizeMode(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
	}
}
