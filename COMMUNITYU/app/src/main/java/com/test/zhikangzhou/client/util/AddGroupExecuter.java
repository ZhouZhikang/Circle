package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou. client.model.ViewUser;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class AddGroupExecuter extends Executer {
	private String groupname;
	private String instruction;
	private String userid;

	public AddGroupExecuter(String groupname, String instruction, String userid) {
		this.groupname = groupname;
		this.instruction = instruction;
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "AddGroupMethod";
		Object[] paraList = new Object[3];
		paraList[0] = this.groupname;
		paraList[1] = this.instruction;
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
