package views;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import permissions.PermissoesSingleton;


public class FramePrincipal extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JMenuItem menuItemJournal;
	private JMenuItem menuItemPaper;
	private JMenuItem menuItemSair;
	private JDesktopPane desktopPane;
	private JFrame framePrincipal;
	
	JPanel panel;
	JLabel lblNewLabel;
	
	
	public FramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 345);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		framePrincipal = this;
		framePrincipal.setTitle("Elsevier da Quebrada");
		
		menuItemJournal = new JMenuItem("Journal");
		menuItemJournal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemJournal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogJournal dialogJournal = new DialogJournal(framePrincipal);
				dialogJournal.novo();
				dialogJournal.setVisible(true);
			}
		});
		mnMenu.add(menuItemJournal);
		
		menuItemPaper = new JMenuItem("Paper");
		menuItemPaper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemPaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogPaper dialogPaper = new DialogPaper(framePrincipal);
				dialogPaper.novo();
				dialogPaper.setVisible(true);
			}
		});
		mnMenu.add(menuItemPaper);
		
		menuItemSair = new JMenuItem("Sair");
		menuItemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnMenu.add(menuItemSair);
			
		getContentPane().setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setBounds(0, 0, 598, 286);
		getContentPane().add(desktopPane);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(244, 24, 215, 262);
		desktopPane.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("/home/bbspock/Git-Projects/TCC00312-PS/journal-paper-final/outros/elsevier-da-quebrada-icon.png"));
		
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		panel = new JPanel();
		panel.setBounds(0, 0, 614, 325);
		getContentPane().add(panel);
		panel.setLayout(null);
	}
}
