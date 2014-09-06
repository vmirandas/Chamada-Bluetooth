/****************************************************************************************************
 * 
 ****************************************************************************************************/
package br.edu.fatec.chamada;

import android.bluetooth.BluetoothDevice;

/****************************************************************************************************
 * @author jade e veronica
 *
 ****************************************************************************************************/
public class Aluno {
	
	private String bluetoothMacAddress;
	private String nome;
	private int id;
	
	/****************************************************************************************************
	 * @return the bluetoothMacAddress
	 ****************************************************************************************************/
	public String getBluetoothMacAddress() {
		return bluetoothMacAddress;
	}
	
	/****************************************************************************************************
	 * @param bluetoothMacAddress the bluetoothMacAddress to set
	 ****************************************************************************************************/
	public void setBluetoothMacAddress(String bluetoothMacAddress) {
		this.bluetoothMacAddress = bluetoothMacAddress;
	}
	
	/****************************************************************************************************
	 * @return the nome
	 ****************************************************************************************************/
	public String getNome() {
		return nome;
	}
	
	/****************************************************************************************************
	 * @param nome the nome to set
	 ****************************************************************************************************/
	public void setNome(String nome) {
		this.nome = nome;
	}
	/****************************************************************************************************
	 * @return the id
	 ****************************************************************************************************/
	public int getId() {
		return id;
	}
	
	
	
	
}
