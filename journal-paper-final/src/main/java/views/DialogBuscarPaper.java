package views;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import errors.JournalNaoEncontradoException;
import errors.PaperNaoEncontradoException;
import models.Journal;
import util.JournalsModel;
import util.PapersModel;


public class DialogBuscarPaper extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTextField nomeTextField;
	private JTable table;
	private PapersModel paperModel;
	private JScrollPane scrollPane;
	
	private TableColumnModel columnModel;
	
	DialogPaper dialogPaper;
	
	public DialogBuscarPaper(DialogPaper dialogPaper)
	{
		super(dialogPaper);
		this.dialogPaper = dialogPaper;
		
		setTitle("Buscar Paper");
		setBounds(110, 171, 608, 301);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 588, 111);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblPesquisaPorNome = new JLabel("Buscar Paper");
		lblPesquisaPorNome.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPesquisaPorNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblPesquisaPorNome.setBounds(203, 11, 195, 22);
		panel.add(lblPesquisaPorNome);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNome.setBounds(92, 42, 55, 22);
		panel.add(lblNome);
		
		nomeTextField = new JTextField();
		nomeTextField.setBackground(UIManager.getColor("Button.light"));
		nomeTextField.setForeground(SystemColor.desktop);
		nomeTextField.setBounds(142, 44, 324, 20);
		panel.add(nomeTextField);
		nomeTextField.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(this);
		btnPesquisar.setBounds(249, 75, 102, 23);
		panel.add(btnPesquisar);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(0, 112, 588, 144);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);

		columnModel = table.getColumnModel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String nome = nomeTextField.getText();
		if(nome.equals(""))
			nome = " ";
		paperModel = new PapersModel(nome);	
		//paperModel.setNomePaper(nomeTextField.getText());
		table.setModel(paperModel);
		
		columnModel.getColumn(0).setPreferredWidth(50);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(90);
		
		scrollPane.setVisible(true);
		
		if(dialogPaper != null) {
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			        public void valueChanged(ListSelectionEvent event) {
			        	try {
			        		try {
								dialogPaper.setPaperBuscado(new Long(table.getValueAt(table.getSelectedRow(), 0).toString()));
								dispose();
							} catch (PaperNaoEncontradoException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (NumberFormatException | JournalNaoEncontradoException e) {
							e.printStackTrace();
						}
			            System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
			        }
			    });
		}
	}
}
