package com.test.zhikangzhou.client.util;

import java.util.Date;
import java.util.List;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class AddActExecuter extends Executer {
	private String activityname;
	private String activitydetail;
	private int groupid;
	private String userid;
	private List<Date> availableList;

	public AddActExecuter(String activityname, String activitydetail,
			int groupid, String userid, List<Date> availableList) {
		this.activityname = activityname;
		this.activitydetail = activitydetail;
		this.groupid = groupid;
		this.userid = userid;
		this.availableList = availableList;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "AddActMethod";
		Object[] paraList = new Object[4 + availableList.size()];
		paraList[0] = this.activityname;
		paraList[1] = this.activitydetail;
		paraList[2] = this.groupid;
		paraList[3] = this.userid;
		int i = 4;
		for (Date d : this.availableList) {
			paraList[i++] = d.getTime();
		}
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
