// 10.64.72.147
package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class OutGroupExecuter extends Executer {
	private int groupid;
	private String userid;

	public OutGroupExecuter(int groupid, String userid) {
		this.groupid = groupid;
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "OutGroupMethod";
		Object[] paraList = new Object[2];
		paraList[0] = this.groupid;
		paraList[1] = this.userid;
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
