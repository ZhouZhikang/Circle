package com.test.zhikangzhou.client.util;

import java.util.Date;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class DecideTimeExecuter extends Executer {
	private Date date;
	private int activityid;

	public DecideTimeExecuter(Date date, int activityid) {
		this.date = date;
		this.activityid = activityid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "ModifyActMethod";
		Object[] paraList = new Object[2];
		paraList[0] = this.activityid;
		paraList[1] = this.date.getTime();
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
