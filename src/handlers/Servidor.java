package handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Servidor  extends Thread{

	private static ArrayList<BufferedWriter>clientes;
	private static ServerSocket server;
	private String nome;
	private Socket conexaoCli;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;

	public Servidor(Socket conexaoCli) {
		this.conexaoCli = conexaoCli;
		try {
			in = conexaoCli.getInputStream();
			inr = new InputStreamReader(in);
			bfr = new BufferedReader(inr);
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		try {

			String msg;
			OutputStream ou = this.conexaoCli.getOutputStream();
			Writer ouw = new OutputStreamWriter(ou);
			BufferedWriter bfw = new BufferedWriter(ouw);
			clientes.add(bfw);
			nome = msg = bfr.readLine();

			while(!"Sair".equalsIgnoreCase(msg) && msg != null) {
				msg = bfr.readLine();
				sendToAll(bfw, msg);
				System.out.println(msg);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException{
		BufferedWriter bws;

		for(BufferedWriter bw : clientes) {
			bws = (BufferedWriter)bw;
			if(!(bwSaida == bws)) {
				bw.write(nome + " ->"+msg+"\r\n");
				bw.flush();
			}
		}
	}

	public static void main(String[] args) {
		try {
			JLabel lsMessage = new JLabel("Porta do Servidor:");
			JTextField txtPorta = new JTextField("10000");
			Object[] texts = {lsMessage, txtPorta };
			JOptionPane.showMessageDialog(null, texts);
			server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
			clientes = new ArrayList<BufferedWriter>();
			JOptionPane.showMessageDialog(null, "Porta do Servidor:"+txtPorta.getText());

			while(true) {
				System.out.println("Aguardando conexcao . . .");
				Socket con = server.accept();
				System.out.println("Usuario conectado. . . ");
				Thread t = new Servidor(con);
				t.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
