package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class AddMessageExecuter extends Executer {
	private String content;
	private String userid;
	private int activityid;

	public AddMessageExecuter(String content, String userid, int activityid) {
		this.content = content;
		this.userid = userid;
		this.activityid = activityid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "AddMessageMethod";
		Object[] paraList = new Object[3];
		paraList[0] = this.content;
		paraList[1] = this.userid;
		paraList[2] = this.activityid;
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
