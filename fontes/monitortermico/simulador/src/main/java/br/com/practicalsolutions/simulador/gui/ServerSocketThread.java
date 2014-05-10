package br.com.practicalsolutions.simulador.gui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSocketThread extends Thread {
	
    private static Logger logger = LoggerFactory.getLogger(ServerSocketThread.class);	
	
	private String ip;
	private int port;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@SuppressWarnings("static-access")
	public void run(){
		try {
			//socket de comunicação
			java.net.Socket s = new java.net.Socket();
			//leitor de entrada
			BufferedReader bf = null;
			//criacao do server socket na porta especificada
			ServerSocket server = new ServerSocket(getPort(), 0, InetAddress.getByName(null));
			//aguarda uma conexão com o cliente
			s = server.accept();
			
			bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			String val = bf.readLine();
			byte[] byteArray = new byte[10];			
			if(val.equals("a")){
				byteArray[2] = (byte) (Math.random() * 100);
				byteArray[3] = (byte) (Math.random() * 100);
				byteArray[4] = (byte) (Math.random() * 100);
				
				byteArray[6] = (byte) (Math.random() * 100);
				byteArray[7] = (byte) (Math.random() * 100);
				byteArray[8] = (byte) (Math.random() * 100);
			}
			
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.write(byteArray);
			dos.flush();
			
			s.close();
			server.close();
			
			this.yield();
			//dorme por x segundos
			this.sleep(1000);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		} 
	}

}
