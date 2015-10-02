import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * XyrRegetMenuBar.java
 *
 * Menu bar for the download manager
 *
 * Created: Mon Nov 12 17:31:11 2001
 *
 * @author <a href="mailto: "Arnaud Denagbe</a>
 * @version
 */
public class XyrRegetMenuBar extends JMenuBar
{
    JMenu fileMenu;
    JMenuItem openMItem;
    JMenuItem startMItem;
    JMenuItem quitMItem;

    public XyrRegetMenuBar()
    {
	// File menu
	fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

	openMItem = new JMenuItem("Open", KeyEvent.VK_O);
        openMItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        fileMenu.add(openMItem);

	startMItem = new JMenuItem("Start", KeyEvent.VK_S);
        startMItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        fileMenu.add(startMItem);

	quitMItem = new JMenuItem("Quit", KeyEvent.VK_Q);
        quitMItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        fileMenu.add(quitMItem);

	add(fileMenu);
    }
}// XyrRegetMenuBar
