package handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClienteSocket {
	private Socket socket;
	private OutputStream ou;
	private Writer ouw;
	private BufferedWriter bfw;
	
	public void conectar(String txtIP, String txtPorta, String txtNome) throws IOException{
		socket = new Socket(txtIP,Integer.parseInt(txtPorta));
		ou = socket.getOutputStream();
		ouw = new OutputStreamWriter(ou);
		bfw = new BufferedWriter(ouw);
		bfw.write(txtNome+"\n\r");
		bfw.flush();
	}

	public void enviarMensagem(String msg, JTextArea texto, JTextField txtMsg) throws IOException{
		if(msg.equals("Sair")) {
			bfw.write("Desconectao \r\n");
			texto.append("Desconectado \r\n");
		}else {
			bfw.write(msg+"\r\n");
			texto.append("Voce ->"+ txtMsg.getText()+"\r\n");
		}
		bfw.flush();
		txtMsg.setText("");
	}

	public void escutar(JTextArea texto) throws IOException{
		InputStream in = socket.getInputStream();
		InputStreamReader inr = new InputStreamReader(in);
		BufferedReader bfr = new BufferedReader(inr);
		String msg = "";

		while(!"Sair".equalsIgnoreCase(msg)) {
			if(bfr.ready()) {
				msg = bfr.readLine();
				if(msg.contentEquals("Sair")) {
					texto.append("Servidor caiu\r\n ");
				}else {
					texto.append(msg+"\r\n");
				}
			}
		}
	}
	
	public void sair() throws IOException{
		
		enviarMensagem("Sair",null, null);
		bfw.close();
		ouw.close();
		ou.close();
		socket.close();
	}

}
