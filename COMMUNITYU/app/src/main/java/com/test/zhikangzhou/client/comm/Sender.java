package com.test.zhikangzhou.client.comm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sender {
	// Ŀ��IP
	private String ipDest;
	// Ŀ�Ķ˿�
	private int portDest;
	
	public Sender() {
		ipDest = "10.66.27.8";
		portDest = 8800;
	}
	
	public String communicate(String msg) {
		String result = null;
		try {
			Socket socket = new Socket(ipDest, portDest);
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
			outStream.writeUTF(msg);
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			result = inStream.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
