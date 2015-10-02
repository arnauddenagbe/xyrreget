package de.xyr.reget;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * DownloadChooseDialog.java
 *
 *
 * Created: Mon Nov 12 18:47:11 2001
 *
 * @author arnaud.denagbe@free.fr
 * @version 1.0
 */
public class DownloadChooseDialog extends JDialog {
	private Vector urls;

	public DownloadChooseDialog(Frame parent, File[] files) throws Exception {
		super(parent, "Downloads selector", true);

		urls = new Vector();
		for (int i = 0; i < files.length; i++) {
			String filename = files[i].getAbsolutePath();
			BufferedReader in = new BufferedReader(new FileReader(filename));
			for (String line; (line = in.readLine()) != null;) {
				if (line.length() >= 4 && line.substring(0, 4).toLowerCase().equals("http")) {
					urls.add(line);
				}
				;
			}
			in.close();
		}

		/*
		 * for(int i = 0; i < urls.size(); i++) {
		 * System.out.println(urls.get(i)); }
		 */
	}

	public Vector getUrls() {
		return urls;
	}
}// DownloadChooseDialog
