package com.test.zhikangzhou.client.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class JoinActExecuter extends Executer {
	private int activityid;
	private String userid;

	public JoinActExecuter(int activityid, String userid) {
		this.activityid = activityid;
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "JoinActMethod";
		Object[] paraList = new Object[2];
		paraList[0] = this.activityid;
		paraList[1] = this.userid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D") && args.length > 1) {
			Map<Date, Integer> map = new HashMap<Date, Integer>();
			for (int j = 1; j < args.length; j += 2) {
				int id = Integer.parseInt(args[j]);
				Date date = new Date(Long.parseLong(args[j + 1]));
				map.put(date, id);
			}
			return new Object[] { map };
		} else if (args[0].equals("M")) {
			return new Object[] { args[1] };
		} else {
			return null;
		}
	}

}
