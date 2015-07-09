package com.test.zhikangzhou.client.util;

import java.util.Date;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class ModifyActExecuter extends Executer {
	private String detail;
	private Date date;
	private int activityid;

	public ModifyActExecuter(String detail, Date date, int activityid) {
		this.detail = detail;
		this.date = date;
		this.activityid = activityid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "ModifyActMethod";
		Object[] paraList = new Object[3];
		paraList[0] = this.activityid;
		paraList[1] = this.date.getTime();
		paraList[2] = this.detail;
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
