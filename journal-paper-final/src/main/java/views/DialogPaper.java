package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import errors.JournalNaoEncontradoException;
import errors.PaperNaoEncontradoException;
import errors.PermissaoException;
import errors.ViolacaoDeConstraintDesconhecidaException;
import models.Journal;
import models.Paper;
import service.JournalService;
import service.PaperService;

public class DialogPaper extends JDialog implements ActionListener 
{
	private static final long serialVersionUID = 1L;

	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;
	private JButton buscarJournalButton;
	
	private JTextField nomeTextField;
	private JTextField valorTextField;
	private JTextField journalTextField;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel nomeMensagem;
	private JLabel valorMensagem;
	private JLabel journalMensagem;

	private Paper umPaper;
	private Journal umJournal;
	private String resp;
	
	private static PaperService paperService;
	private static JournalService journalService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	paperService = (PaperService)fabrica.getBean ("PaperService");
    	journalService = (JournalService)fabrica.getBean ("JournalService");
    }
	
	public DialogPaper(JFrame frame)
	{
		super(frame);
		
		setBounds(107, 151, 600, 287);
		setTitle("Cadastro de Papers");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 544, 224);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastro de Papers");
		cadastrarLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		cadastrarLabel.setBounds(144, 21, 190, 26);
		panel.add(cadastrarLabel);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(42, 61, 46, 14);
		panel.add(nomeLabel);
		
		nomeTextField = new JTextField();
		nomeLabel.setLabelFor(nomeTextField);
		nomeTextField.setBounds(116, 59, 278, 20);
		panel.add(nomeTextField);
		nomeTextField.setColumns(50);
		
		JLabel valorLabel = new JLabel("Valor");
		valorLabel.setBounds(42, 108, 56, 14);
		panel.add(valorLabel);
		
		valorTextField = new JTextField();
		valorTextField.setBounds(116, 105, 113, 21);
		panel.add(valorTextField);
		valorTextField.setColumns(10);
		
		nomeMensagem = new JLabel("");
		nomeMensagem.setForeground(Color.RED);
		nomeMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		nomeMensagem.setBounds(118, 77, 241, 14);
		panel.add(nomeMensagem);
		
		valorMensagem = new JLabel("");
		valorMensagem.setForeground(Color.RED);
		valorMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		valorMensagem.setBounds(118, 125, 241, 14);
		panel.add(valorMensagem);
		
		journalMensagem = new JLabel("");
		journalMensagem.setForeground(Color.RED);
		journalMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		journalMensagem.setBounds(118, 165, 241, 14);
		panel.add(journalMensagem);
		
		novoButton = new JButton("Novo");
		novoButton.setBounds(425, 23, 96, 23);
		panel.add(novoButton);
		novoButton.addActionListener(this);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(425, 50, 96, 23);
		panel.add(cadastrarButton);
		cadastrarButton.addActionListener(this);
		
		editarButton = new JButton("Editar");
		editarButton.setBounds(425, 77, 96, 23);
		panel.add(editarButton);
		editarButton.addActionListener(this);
		
		alterarButton = new JButton("Alterar");
		alterarButton.setBounds(425, 104, 96, 23);
		panel.add(alterarButton);
		alterarButton.addActionListener(this);

		removerButton = new JButton("Remover");
		removerButton.setBounds(425, 131, 96, 23);
		panel.add(removerButton);
		removerButton.addActionListener(this);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(425, 158, 96, 23);
		panel.add(cancelarButton);
		cancelarButton.addActionListener(this);

		buscarButton = new JButton("Buscar");
		buscarButton.setBounds(425, 185, 96, 23);
		panel.add(buscarButton);
		buscarButton.addActionListener(this);
		
		journalTextField = new JTextField();
		journalTextField.setColumns(50);
		journalTextField.setBackground(Color.LIGHT_GRAY);
		journalTextField.setBounds(112, 146, 137, 20);
		journalTextField.setEnabled(false);
		panel.add(journalTextField);
		
		JLabel journalLabel = new JLabel("Journal");
		journalLabel.setBounds(42, 147, 74, 14);
		panel.add(journalLabel);
		
		buscarJournalButton = new JButton("Buscar");
		buscarJournalButton.setBounds(261, 146, 82, 23);
		panel.add(buscarJournalButton);
		buscarJournalButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		if (obj == novoButton)
		{	novo();
		}
		else if (obj == cadastrarButton)
		{
			boolean deuErro = validaJournal();
			
			if(!deuErro)
			{	
				umPaper = new Paper();
				umPaper.setNome(nomeTextField.getText());
				umPaper.setValor(Float.parseFloat(valorTextField.getText()));
				umPaper.setJournal(umJournal);
				try {
					paperService.inclui(umPaper);
					salvo();
					
					JOptionPane.showMessageDialog(this, "Paper cadastrado com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				} catch (JournalNaoEncontradoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	// inclui o paper
				catch (PermissaoException e1) {
					JOptionPane.showMessageDialog(this, "Você não tem permissão para isso, amiguinho", "", 
				JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(obj == editarButton)
		{
			editavel();
		}
		else if(obj == alterarButton)
		{
			boolean deuErro = validaJournal();
			
			if(!deuErro)
			{	
				umPaper.setNome(nomeTextField.getText());
				umPaper.setValor(Float.parseFloat(valorTextField.getText()));

				try 
				{
					paperService.altera(umPaper);     	// altera o paper
					
					salvo();
					
					JOptionPane.showMessageDialog(this, "Paper atualizado com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (JournalNaoEncontradoException e1) 
				{
					removido();
					
					JOptionPane.showMessageDialog(this, "Paper não encontrado", "", 
						JOptionPane.ERROR_MESSAGE);
				} catch (PermissaoException e1) {
					JOptionPane.showMessageDialog(this, "Você não tem permissão para isso, amiguinho", "", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(obj == removerButton)
		{
			try 
			{
				paperService.exclui(umPaper);

				removido();
				
				JOptionPane.showMessageDialog(this, "Paper removido com sucesso", "", 
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (PaperNaoEncontradoException e1) 
			{
				removido();
				
				JOptionPane.showMessageDialog(this, "Paper não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			} catch (PermissaoException e1) {
				JOptionPane.showMessageDialog(this, "Você não tem permissão para isso, amiguinho", "", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == cancelarButton)
		{
			try 
			{
				umPaper = paperService.recuperaUmPaper(umPaper.getId());	// recupera todas as informações do journal no banco de dados

				nomeTextField.setText(umPaper.getNome());
				valorTextField.setText(new Float(umPaper.getValor()).toString());
				
				salvo();
			} 
			catch (PaperNaoEncontradoException e1) 
			{
				removido();
				
				JOptionPane.showMessageDialog(this, "Paper não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
		}

		else if(obj == buscarJournalButton)
		{	
			DialogBuscarJournal dialog = new DialogBuscarJournal(this);
			dialog.setVisible(true);
		}
		else if(obj == buscarButton)
		{	
			DialogBuscarPaper dialog = new DialogBuscarPaper(this);
			dialog.setVisible(true);
		}
	}
	
	public void setPaperBuscado(long id) throws JournalNaoEncontradoException, PaperNaoEncontradoException {
		this.umPaper = paperService.recuperaUmPaper(id);
		this.nomeTextField.setText(this.umPaper.getNome());
		Float valor = this.umPaper.getValor();
		this.valorTextField.setText(valor.toString());
		this.umJournal = journalService.recuperaUmJournal(
										this.umPaper.getJournal().getId());
		this.journalTextField.setText(umJournal.getNome());
		salvo();
	}
	
	public void setJournalBuscado(long id) throws JournalNaoEncontradoException {
		this.umJournal = journalService.recuperaUmJournal(id);
		this.journalTextField.setText(umJournal.getNome());
		salvo();
	}
	
	private boolean eNumero(String numero)
	{
		boolean resposta = true;
		try
		{	
			if (numero == null) return true;
			
			Long.parseLong(numero);
		}
		catch(NumberFormatException e)
		{	resposta = false;
		}
		return resposta;
	}
	
	private boolean validaJournal()
	{
		boolean deuErro = false;
		if (nomeTextField.getText().trim().length() == 0)
		{	deuErro = true;
			nomeMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	nomeMensagem.setText("");
		}
		
		if (valorTextField.getText().trim().length() == 0)
		{	deuErro = true;
			valorMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	valorMensagem.setText("");
		}
		
		if (journalTextField.getText().trim().length() == 0)
		{	deuErro = true;
			journalMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	journalMensagem.setText("");
		}
		
		return deuErro;
	}
	
	public void novo()
	{
		nomeTextField.setEnabled(true);
		valorTextField.setEnabled(true);
		
		nomeTextField.setText("");
		buttonGroup.clearSelection();

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void salvo()
	{
		nomeTextField.setEnabled(false);
		valorTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void editavel()
	{
		nomeTextField.setEnabled(true);
		valorTextField.setEnabled(true);

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(false);
	}

	public void removido()
	{
		nomeTextField.setEnabled(false);
		valorTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);   // novo
	}
}
