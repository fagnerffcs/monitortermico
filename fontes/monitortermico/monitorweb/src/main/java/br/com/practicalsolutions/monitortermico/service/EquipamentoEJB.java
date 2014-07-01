package br.com.practicalsolutions.monitortermico.service;

import javax.ejb.Stateless;

@Stateless
public class EquipamentoEJB {
	
	public byte[] sendData(String val){
		
		byte[] byteArray = new byte[10];			
		if(val.equals("a")){
			byte signal = (byte) (Math.random() * 10);
			if(signal >= 1){
				signal = '+';
			} else {
				signal = '-';
			}
			//byte de sinal (+/-)
			byteArray[1] = (byte) signal;
			
			for (int i = 2; i <= 8; i++) {
				byteArray[i] = (byte) (Math.random() * 10);	
			}
		}
		
		return byteArray;
	}	

}
