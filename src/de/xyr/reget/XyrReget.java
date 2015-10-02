package de.xyr.reget;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

/**
 * XyrReget.java
 *
 * Download manager
 *
 * Created: Mon Nov 12 17:24:53 2001
 *
 * @author arnaud.denagbe@free.fr
 * @version 1.0
 */
public class XyrReget extends JFrame {
	public final static int DEFAULT_MAX_THREADS = 5;
	private XyrRegetMenuBar menuBar;
	private XyrRegetDownloadManager downloadManager;

	public XyrReget() throws Exception {
		super("Download Manager -- Arnaud Denagbe @2001");
		final XyrReget parent = this;
		JPanel mainPanel = ((JPanel) getContentPane());

		menuBar = new XyrRegetMenuBar();
		setJMenuBar(menuBar);

		downloadManager = new XyrRegetDownloadManager(parent, DEFAULT_MAX_THREADS);

		menuBar.openMItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileFilter(new XyrRegetFileFilter());
				int returnVal = chooser.showOpenDialog(parent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						downloadManager.add(new DownloadChooseDialog(parent, chooser.getSelectedFiles()).getUrls());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		menuBar.quitMItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		final JTextField maxThreadsTextField = new JTextField(String.valueOf(DEFAULT_MAX_THREADS), 15);
		maxThreadsTextField.setHorizontalAlignment(JTextField.RIGHT);
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int maxThreads = Integer.parseInt(maxThreadsTextField.getText());
					downloadManager.setMaxThreads(maxThreads);
				} catch (NumberFormatException ex) {
					maxThreadsTextField.setText(String.valueOf(downloadManager.getMaxThreads()));
				}
			}
		});
		JPanel threadsPanel = new JPanel(new FlowLayout());
		threadsPanel.add(new JLabel("Concurrent downloads:"));
		threadsPanel.add(maxThreadsTextField);
		threadsPanel.add(applyButton);

		final JTextField dstDirTextField = new JTextField("C:\\Temp\\ChevaliersDuZodiaque\\", 50);
		JButton applyButton2 = new JButton("OK");
		applyButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downloadManager.setDstDir(dstDirTextField.getText());
			}
		});
		JPanel dstDirPanel = new JPanel(new FlowLayout());
		dstDirPanel.add(new JLabel("Destination directory:"));
		dstDirPanel.add(dstDirTextField);
		dstDirPanel.add(applyButton2);

		JPanel optionsPanel = new JPanel(new GridLayout(2, 1));
		optionsPanel.add(threadsPanel);
		optionsPanel.add(dstDirPanel);
		mainPanel.add(optionsPanel);

		mainPanel.setPreferredSize(new Dimension(800, 70));
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		try {
			new XyrReget();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
} // XyrReget
