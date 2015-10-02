package de.xyr.reget;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * XyrRegetFileFilter.java
 *
 *
 * Created: Mon Nov 12 18:07:19 2001
 *
 * @author arnaud.denagbe@free.fr
 * @version 1.0
 */
public class XyrRegetFileFilter extends FileFilter {
	public final static String TEXT = "txt";
	public final static String JPEG = "jpeg";
	public final static String JPG = "jpg";
	public final static String GIF = "gif";
	public final static String TIFF = "tiff";
	public final static String TIF = "tif";

	// Accept all directories and all gif, jpg, or tiff files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(TEXT) || extension.equals(TIFF) || extension.equals(TIF) || extension.equals(GIF)
					|| extension.equals(JPEG) || extension.equals(JPG)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	private static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	// The description of this filter
	public String getDescription() {
		return "Just Images";
	}
}// XyrRegetFileFilter
