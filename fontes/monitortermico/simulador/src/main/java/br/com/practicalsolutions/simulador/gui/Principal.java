package br.com.practicalsolutions.simulador.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Principal {
	
	private static JLabel ipLabel = new JLabel("IP:");
	private static JTextField ipField = new JTextField();
	private static JLabel portLabel = new JLabel("Porta:");
	private static JTextField portField = new JTextField();
	private static JButton submitButton = new JButton("Ativar");
	private static ServerSocket server;
	
	public static void main(String[] args) {
		JFrame janela = new JFrame("Simulador");
		janela.setSize(300, 300);
		Container content = janela.getContentPane();
//		content.setBackground(Color.WHITE);
		content.setLayout(new GridLayout());
		ipField.setSize(100, 20);
		portField.setSize(100, 20);
		content.add(ipLabel);
		content.add(ipField);
		content.add(portLabel);
		content.add(portField);
		content.add(submitButton);
		janela.setVisible(true);
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(server == null){
						server = new ServerSocket();
						InetAddress addres = InetAddress.getByName(ipField.getText());
					}
					server.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
	}

}
