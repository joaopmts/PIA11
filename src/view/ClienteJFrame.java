package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import handlers.ClienteSocket;

public class ClienteJFrame extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JTextArea texto;
	private JTextField txtMsg;
	private JButton btnSend;
	private JButton btnSair;
	private JLabel lblHistorico;
	private JLabel lblMsg;
	private JPanel pnlContent;
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTextField txtNome;
	ClienteSocket cliente;

	public ClienteJFrame() throws IOException{
		JLabel lblMessage = new JLabel("Verificar!");
		txtIP = new JTextField("127.0.0.1");
		txtPorta = new JTextField("Coloque a porta do Servidor");
		txtNome = new JTextField("Seu nome");
		Object[] texts = {lblMessage, txtIP, txtPorta, txtNome };
		JOptionPane.showMessageDialog(null, texts);
		pnlContent = new JPanel();
		texto = new JTextArea(10,20);
		texto.setEditable(false);
		texto.setBackground(new Color(240,240,240));
		txtMsg = new JTextField(20);
		lblHistorico = new JLabel("Historico");
		lblMsg = new JLabel("Mensagem");
		btnSend = new JButton("Enviar");
		btnSend.setToolTipText("Enviar Mensagem");
		btnSair = new JButton("Sair");
		btnSend.addKeyListener(this);
		txtMsg.addKeyListener(this);
		JScrollPane scroll = new JScrollPane(texto);
		texto.setLineWrap(true);
		pnlContent.add(lblHistorico);
		pnlContent.add(scroll);
		pnlContent.add(lblMsg);
		pnlContent.add(txtMsg);
		pnlContent.add(btnSair);
		pnlContent.add(btnSend);
		pnlContent.setBackground(Color.LIGHT_GRAY);
		texto.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
		txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
		setTitle(txtNome.getText());
		setContentPane(pnlContent);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(250,330);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		btnSend.addActionListener(this);
		btnSair.addActionListener(this);
		cliente = new ClienteSocket();

	}

	public void iniciar() throws IOException{
		cliente.conectar(this.txtIP.getText(), this.txtPorta.getText(), this.txtNome.getText());
		cliente.escutar(this.texto);
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getActionCommand().equals(btnSend.getActionCommand())) {
				cliente.enviarMensagem(txtMsg.getText(), this.texto, this.txtMsg);
			}else {
				if(e.getActionCommand().equals(btnSair.getActionCommand())) {
					cliente.sair();
				}
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				cliente.enviarMensagem(txtMsg.getText(), this.texto, this.txtMsg);
			}catch(IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws IOException{

		ClienteJFrame app = new ClienteJFrame();
		app.iniciar();
	}
}
