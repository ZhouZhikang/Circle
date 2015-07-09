package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class LoginExecuter extends Executer {
	private String username;
	private String password;

	public LoginExecuter(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "LoginMethod";
		Object[] paraList = new Object[2];
		paraList[0] = this.username;
		paraList[1] = this.password;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D")) {
			ViewUser vu = new ViewUser();
			vu.setUserid(args[1]);
			vu.setUsername(args[2]);
			vu.setUserschool(args[3]);
			vu.setUserspecial(args[4]);
			vu.setUserclass(args[5]);
			vu.setDorm(args[6]);
			vu.setRoom(args[7]);
			return new Object[] { vu };
		} else if (args[0].equals("M")) {
			return new Object[] { args[1] };
		} else {
			return null;
		}
	}

}
