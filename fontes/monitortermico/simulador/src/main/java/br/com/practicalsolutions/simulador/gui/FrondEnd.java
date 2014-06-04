package br.com.practicalsolutions.simulador.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FrondEnd {

	private JFrame frmSimuladorMonitorTrmico;
	private JTextField txtIP;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrondEnd window = new FrondEnd();
					window.frmSimuladorMonitorTrmico.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrondEnd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSimuladorMonitorTrmico = new JFrame();
		frmSimuladorMonitorTrmico.setTitle("Simulador Monitor Térmico");
		frmSimuladorMonitorTrmico.setBounds(100, 100, 450, 300);
		frmSimuladorMonitorTrmico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSimuladorMonitorTrmico.getContentPane().setLayout(null);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(10, 11, 20, 14);
		frmSimuladorMonitorTrmico.getContentPane().add(lblIp);
		
		txtIP = new JTextField();
		txtIP.setBounds(55, 8, 177, 20);
		frmSimuladorMonitorTrmico.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(10, 49, 37, 14);
		frmSimuladorMonitorTrmico.getContentPane().add(lblPorta);
		
		textField = new JTextField();
		textField.setBounds(55, 39, 67, 20);
		frmSimuladorMonitorTrmico.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(33, 138, 89, 23);
		frmSimuladorMonitorTrmico.getContentPane().add(btnIniciar);
	}
}
