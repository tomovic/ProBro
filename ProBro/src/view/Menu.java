package view;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import main.Commands;
import main.Main;
import main.Messages;
import main.OS;
import main.Utils;

/**
 * Menu for the application
 * 
 * @author tweber
 *
 */
public class Menu extends JMenuBar implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;

	/**
	 * Main frame reference
	 */
	private MainFrame frame;
	
	// File menu
	private JMenuItem open;
	private JMenuItem locate;
	private JMenuItem delete;
	private JMenuItem zip;
	private JMenuItem quit;
	
	// View menu
	private JRadioButtonMenuItem showFilebrowser;
	private JRadioButtonMenuItem showProjects;
	private JRadioButtonMenuItem showProjectLeftovers;
	private JMenuItem refresh;
	private JMenuItem resetWindows;
	
	// Project menu 
	private JMenuItem openDefinition;
	private JMenuItem openDefaultDefinition;
	public JRadioButtonMenuItem openedDefinition;
	private JMenuItem showStats;
	
	public Menu(MainFrame frame) {
		this.frame = frame;
	}
	
	/**
	 * Initialize the menu bar
	 * 
	 */
	public void init() throws Throwable {
		// File menu
		JMenu file = new JMenu(Messages.getString("Menu.File")); //$NON-NLS-1$
		add(file);
		
		open = new JMenuItem(Messages.getString("Menu.Open")); //$NON-NLS-1$
		open.addActionListener(this);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		file.add(open);
		open.setEnabled(Utils.getDesktop().isSupported(Desktop.Action.OPEN));  // Check the actions are supported on this platform!

		locate = new JMenuItem(Messages.getString("Menu.Locate")); //$NON-NLS-1$
		locate.addActionListener(this);
		locate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		file.add(locate);

		file.add(new JSeparator());
		
		zip = new JMenuItem(Messages.getString("Menu.ZIP")); //$NON-NLS-1$
		zip.addActionListener(this);
		zip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		file.add(zip);
		
		if (!OS.isMac()) {
			file.add(new JSeparator());
			
			// For non-Macs, add Quit command on Alt+F4
			quit = new JMenuItem(Messages.getString("Menu.Quit"));  //$NON-NLS-1$
			quit.addActionListener(this);
			quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
			file.add(quit);			
		}
		
		delete = new JMenuItem(Messages.getString("Menu.Delete"));  //$NON-NLS-1$
		delete.addActionListener(this);
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		//file.add(delete); // Deactivated for security reasons (does not support trash folder)

		// View menu
		JMenu view = new JMenu(Messages.getString("Menu.View"));  //$NON-NLS-1$
		add(view);
		
		showFilebrowser = new JRadioButtonMenuItem(Messages.getString("Menu.FileManager")); //$NON-NLS-1$
		showFilebrowser.addActionListener(this);
		showFilebrowser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		view.add(showFilebrowser);

		showProjects = new JRadioButtonMenuItem(Messages.getString("Menu.ProjectsManager"));   //$NON-NLS-1$
		showProjects.addActionListener(this);
		showProjects.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		view.add(showProjects);

		showProjectLeftovers = new JRadioButtonMenuItem(Messages.getString("Menu.ProjectsLeftovers"));   //$NON-NLS-1$
		showProjectLeftovers.addActionListener(this);
		showProjectLeftovers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		view.add(showProjectLeftovers);
		
		view.add(new JSeparator());
		
		refresh = new JMenuItem(Messages.getString("Menu.Refresh"));  //$NON-NLS-1$
		refresh.addActionListener(this);
		refresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		view.add(refresh);
		
		view.add(new JSeparator());

		resetWindows = new JMenuItem(Messages.getString("Menu.ResetWindows"));  //$NON-NLS-1$
		resetWindows.addActionListener(this);
		view.add(resetWindows);

		// View menu
		JMenu projects = new JMenu(Messages.getString("Menu.Projects"));  //$NON-NLS-1$
		add(projects);
		
		openDefinition = new JMenuItem(Messages.getString("Menu.OpenDefinition")); //$NON-NLS-1$
		openDefinition.addActionListener(this);
		openDefinition.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		projects.add(openDefinition);
		
		openDefaultDefinition = new JMenuItem(Messages.getString("Menu.OpenDefaultDefinition")); //$NON-NLS-1$
		openDefaultDefinition.addActionListener(this);
		projects.add(openDefaultDefinition);

		projects.add(new JSeparator());
		
		openedDefinition = new JRadioButtonMenuItem(""); //$NON-NLS-1$
		openedDefinition.addActionListener(this);
		openedDefinition.setEnabled(false);
		projects.add(openedDefinition);

		projects.add(new JSeparator());

		showStats = new JMenuItem(Messages.getString("Menu.showStats")); //$NON-NLS-1$
		showStats.addActionListener(this);
		projects.add(showStats);

		setProjectsOptionsState();
		
		frame.setJMenuBar(this);
	}

	/**
	 * Set the state of the projects menu items, determined by the current state of the project definition
	 * @throws Throwable 
	 * 
	 */
	public void setProjectsOptionsState() throws Throwable {
		if (Main.getProjectDefinition() == null) {
			showProjects.setEnabled(false);
			showProjectLeftovers.setEnabled(false);
			openedDefinition.setSelected(false);
			openedDefinition.setText(Messages.getString("NoPDloaded"));
		} else {
			showProjects.setEnabled(true);
			showProjectLeftovers.setEnabled(true);
			openedDefinition.setSelected(true);
			openedDefinition.setText(Main.getProjectDefinition().getFile().getName());
		}
	}

	/**
	 * Action handler for all menu items
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JMenuItem source = (JMenuItem) (e.getSource());
	
			Commands com = new Commands(frame.mainPanel, source);

			/*if (source == delete) {
				com.deleteFile(e);
			}*/
			if (source == open) {
				com.openFile(e);
			}
			if (source == locate) {
				com.locateFile(e);
			}
			if (source == zip) {
				com.zipFile(e);
			}
			if (source == quit) {
				System.exit(0);
			}
			if (source == refresh) {
				frame.mainPanel.refresh();
			}
			if (source == resetWindows) {
				com.resetWindows();;
			}
			if (source == showFilebrowser) {
				com.showFilebrowser();
			}
			if (source == showProjects) {
				com.showProjects();
			}
			if (source == showProjectLeftovers) {
				com.showProjectLeftovers();
			}
			if (source == openDefinition) {
				com.openDefinition();
			}
			if (source == openDefaultDefinition) {
				com.openDefaultDefinition();
			}
			if (source == showStats) {
				com.showStats();
			}

		} catch (Throwable e1) {
			Main.handleThrowable(e1);
		}
	}

	/**
	 * Set the view menu items 
	 * 
	 * @param projects
	 */
	public void setView(int view) throws Throwable {
		switch(view) {
		case 0: 
			showFilebrowser.setSelected(true); 
			showProjects.setSelected(false);
			showProjectLeftovers.setSelected(false);
			break;
		case 1: 
			showFilebrowser.setSelected(false); 
			showProjects.setSelected(true);
			showProjectLeftovers.setSelected(false);
			break; 
		case 2: 
			showFilebrowser.setSelected(false); 
			showProjects.setSelected(false);
			showProjectLeftovers.setSelected(true);
			break; 
		}
	}
	
	/**
	 * Unused
	 * 
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {}
}
