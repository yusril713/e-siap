package views.panel;

import javax.swing.JPanel;

import config.ImagePanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class PMain extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PMain() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		ImagePanel lblNewLabel = new ImagePanel(".\\icon\\BG home.png");
		GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
		gbc_lblNewLabel1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel1.gridx = 0;
		gbc_lblNewLabel1.gridy = 0;
		gbc_lblNewLabel1.gridheight = 1;
		gbc_lblNewLabel1.gridwidth = 1;
		add(lblNewLabel, gbc_lblNewLabel1);
	}

}
