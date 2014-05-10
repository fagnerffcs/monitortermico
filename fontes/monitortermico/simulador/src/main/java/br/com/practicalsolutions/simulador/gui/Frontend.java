package br.com.practicalsolutions.simulador.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Frontend {
	private static Text txtIP;
	private static Text txtPorta;
	private static ServerSocketThread sst;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shlSimuladorTermohigrmetro = new Shell();
		shlSimuladorTermohigrmetro.setSize(450, 300);
		shlSimuladorTermohigrmetro.setText("Simulador Termo-Higrômetro");
		shlSimuladorTermohigrmetro.setLayout(new FormLayout());
		
		Label lblNewLabel = new Label(shlSimuladorTermohigrmetro, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 46);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("IP:");
		
		txtIP = new Text(shlSimuladorTermohigrmetro, SWT.BORDER);
		FormData fd_txtTxtip = new FormData();
		fd_txtTxtip.bottom = new FormAttachment(lblNewLabel, 0, SWT.BOTTOM);
		fd_txtTxtip.right = new FormAttachment(lblNewLabel, 163, SWT.RIGHT);
		txtIP.setLayoutData(fd_txtTxtip);
		
		Label lblPorta = new Label(shlSimuladorTermohigrmetro, SWT.NONE);
		lblPorta.setText("Porta:");
		FormData fd_lblPorta = new FormData();
		fd_lblPorta.top = new FormAttachment(lblNewLabel, 34);
		fd_lblPorta.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		lblPorta.setLayoutData(fd_lblPorta);
		
		txtPorta = new Text(shlSimuladorTermohigrmetro, SWT.BORDER);
		fd_txtTxtip.left = new FormAttachment(txtPorta, 0, SWT.LEFT);
		FormData fd_text_1 = new FormData();
		fd_text_1.bottom = new FormAttachment(lblPorta, 0, SWT.BOTTOM);
		fd_text_1.left = new FormAttachment(lblPorta, 6);
		txtPorta.setLayoutData(fd_text_1);
		
		Button btnNewButton = new Button(shlSimuladorTermohigrmetro, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(sst==null){
					sst = new ServerSocketThread();
				}
				sst.setPort(Integer.parseInt(txtPorta.getText()));
				sst.start();
			}
		});
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(txtIP, 92);
		fd_btnNewButton.left = new FormAttachment(0, 167);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Ligar");
		
		Button btnNewButton_1 = new Button(shlSimuladorTermohigrmetro, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(sst!=null){
					sst.yield();
					sst.interrupt();
				}
			}
		});
		FormData fd_btnNewButton_1 = new FormData();
		fd_btnNewButton_1.bottom = new FormAttachment(btnNewButton, 0, SWT.BOTTOM);
		fd_btnNewButton_1.left = new FormAttachment(btnNewButton, 17);
		btnNewButton_1.setLayoutData(fd_btnNewButton_1);
		btnNewButton_1.setText("Desligar");

		shlSimuladorTermohigrmetro.open();
		shlSimuladorTermohigrmetro.layout();
		while (!shlSimuladorTermohigrmetro.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
