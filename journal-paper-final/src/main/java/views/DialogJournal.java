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

import errors.JournalComPapersException;
import errors.JournalNaoEncontradoException;
import errors.PermissaoException;
import errors.ViolacaoDeConstraintDesconhecidaException;
import models.Journal;
import service.JournalService;
import views.DialogBuscarJournal;

public class DialogJournal extends JDialog implements ActionListener 
{
	private static final long serialVersionUID = 1L;

	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;
	
	private JTextField nomeTextField;
	private JTextField volumeTextField;
	private JTextField valorTextField;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel nomeMensagem;
	private JLabel volumeMensagem;
	private JLabel valorMensagem;

	private Journal umJournal;
	private String resp;
	
	private static JournalService journalService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	journalService = (JournalService)fabrica.getBean ("JournalService");
    }
	
	public DialogJournal(JFrame frame)
	{
		super(frame);
		
		setBounds(107, 151, 600, 287);
		setTitle("Cadastro de Journals");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 544, 224);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastro de Journals");
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
		
		JLabel volumeLabel = new JLabel("Volume");
		volumeLabel.setBounds(32, 99, 66, 18);
		panel.add(volumeLabel);
		
		volumeTextField = new JTextField();
		volumeTextField.setBounds(116, 96, 36, 21);
		panel.add(volumeTextField);
		volumeTextField.setColumns(10);

		JLabel valorLabel = new JLabel("Valor");
		valorLabel.setBounds(32, 132, 56, 14);
		panel.add(valorLabel);
		
		valorTextField = new JTextField();
		valorTextField.setBounds(116, 129, 113, 21);
		panel.add(valorTextField);
		valorTextField.setColumns(10);
		
		nomeMensagem = new JLabel("");
		nomeMensagem.setForeground(Color.RED);
		nomeMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		nomeMensagem.setBounds(118, 75, 241, 14);
		panel.add(nomeMensagem);
		
		volumeMensagem = new JLabel("");
		volumeMensagem.setForeground(Color.RED);
		volumeMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		volumeMensagem.setBounds(118, 112, 241, 14);
		panel.add(volumeMensagem);
		
		valorMensagem = new JLabel("");
		valorMensagem.setForeground(Color.RED);
		valorMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		valorMensagem.setBounds(118, 155, 241, 14);
		panel.add(valorMensagem);
		
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
				umJournal = new Journal();
				umJournal.setNome(nomeTextField.getText());
				umJournal.setVolume(Integer.parseInt(volumeTextField.getText()));
				umJournal.setValor(Float.parseFloat(valorTextField.getText()));

				try {
					journalService.inclui(umJournal);
					
					salvo();
					
					JOptionPane.showMessageDialog(this, "Journal cadastrado com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				} catch (PermissaoException e1) {
					JOptionPane.showMessageDialog(this, "Você não tem permissão para isso, amiguinho", "", 
							JOptionPane.ERROR_MESSAGE);
				}	// inclui o journal

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
				umJournal.setNome(nomeTextField.getText());
				umJournal.setVolume(Integer.parseInt(volumeTextField.getText()));
				umJournal.setValor(Float.parseFloat(valorTextField.getText()));

				try 
				{
					journalService.altera(umJournal);     	// altera o journal
					
					salvo();
					
					JOptionPane.showMessageDialog(this, "Journal atualizado com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (JournalNaoEncontradoException e1) 
				{
					removido();
					
					JOptionPane.showMessageDialog(this, "Journal não encontrado", "", 
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
				journalService.exclui(umJournal);

				removido();
				
				JOptionPane.showMessageDialog(this, "Journal removido com sucesso", "", 
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (JournalNaoEncontradoException | JournalComPapersException e1) 
			{
				removido();
				
				JOptionPane.showMessageDialog(this, "Journal possui Papers associados", "", 
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
				umJournal = journalService.recuperaUmJournal(umJournal.getId());	// recupera todas as informações do journal no banco de dados

				nomeTextField.setText(umJournal.getNome());
				volumeTextField.setText(new Integer(umJournal.getVolume()).toString());
				valorTextField.setText(new Float(umJournal.getValor()).toString());
				
				novo();
			} 
			catch (JournalNaoEncontradoException e1) 
			{
				removido();
				
				JOptionPane.showMessageDialog(this, "Journal não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == buscarButton)
		{	
			DialogBuscarJournal dialog = new DialogBuscarJournal(this);
			dialog.setVisible(true);
		}
	}
	
	public void setJournalBuscado(long id) throws JournalNaoEncontradoException {
		this.umJournal = journalService.recuperaUmJournal(id);
		this.nomeTextField.setText(this.umJournal.getNome());
		Integer volume = this.umJournal.getVolume();
		this.volumeTextField.setText(volume.toString());
		Float valor = this.umJournal.getValor();
		this.valorTextField.setText(valor.toString());
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
		
		if (volumeTextField.getText().trim().length() == 0)
		{	deuErro = true;
			volumeMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	volumeMensagem.setText("");
		}
		
		if (valorTextField.getText().trim().length() == 0)
		{	deuErro = true;
			valorMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	valorMensagem.setText("");
		}
		
		return deuErro;
	}
	
	public void novo()
	{
		nomeTextField.setEnabled(true);
		volumeTextField.setEnabled(true);
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
		volumeTextField.setEnabled(false);
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
		volumeTextField.setEnabled(true);
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
		volumeTextField.setEnabled(false);
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
