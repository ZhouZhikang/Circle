package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class ModifyPwdExecuter extends Executer {
	private String oldPwd;
	private String newPwd;
	private String userid;

	public ModifyPwdExecuter(String oldPwd, String newPwd, String userid) {
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "ModifyPwdMethod";
		Object[] paraList = new Object[3];
		paraList[0] = this.oldPwd;
		paraList[1] = this.newPwd;
		paraList[2] = this.userid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("M")) {
			return new Object[] { args[1] };
		} else {
			return null;
		}
	}

}
