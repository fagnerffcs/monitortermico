package br.com.practicalsolutions.monitortermico.service;

import javax.ejb.Stateless;

@Stateless
public class EquipamentoEJB {
	
	public byte[] sendData(String val){
		
		byte[] byteArray = new byte[10];			
		if(val.equals("a")){
			byteArray[1] = (byte) ('+');
			
			byteArray[2] = (byte) (Math.random() * 10);
			byteArray[3] = (byte) (Math.random() * 10);
			byteArray[4] = (byte) (Math.random() * 10);
			
			byteArray[6] = (byte) (Math.random() * 10);
			byteArray[7] = (byte) (Math.random() * 10);
			byteArray[8] = (byte) (Math.random() * 10);
		}
		
		return byteArray;
	}	

}