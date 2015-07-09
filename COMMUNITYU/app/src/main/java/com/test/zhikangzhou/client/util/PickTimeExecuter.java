package com.test.zhikangzhou.client.util;

import java.util.List;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class PickTimeExecuter extends Executer {
	private int activityid;
	private String userid;
	private List<Integer> list;

	public PickTimeExecuter(int activityid, String userid, List<Integer> list) {
		this.activityid = activityid;
		this.userid = userid;
		this.list = list;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "PickTimeMethod";
		Object[] paraList = new Object[2 + list.size()];
		paraList[0] = this.activityid;
		paraList[1] = this.userid;
		int i = 2;
		for (int k : list) {
			paraList[i++] = k;
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
