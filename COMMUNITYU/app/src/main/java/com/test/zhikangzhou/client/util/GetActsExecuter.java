package com.test.zhikangzhou.client.util;

import java.util.Date;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class GetActsExecuter extends Executer {
	private int groupid;
	private String userid;

	public GetActsExecuter(int groupid, String userid) {
		this.groupid = groupid;
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "GetActsMethod";
		Object[] paraList = new Object[2];
		paraList[0] = this.groupid;
		paraList[1] = this.userid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D") && args.length > 1) {
			int length = (args.length - 1) / 8;
			Object[] array = new Object[length];
			int i = 0;
			for (int j = 1; j < args.length; j += 8) {
				ViewActivity va = new ViewActivity();
				va.setActivityid(Integer.parseInt(args[j]));
				va.setActivityname(args[j + 1]);
				va.setActivitydetail(args[j + 2]);
				if (!args[j + 3].equals("")) {
					va.setTime(new Date(Long.parseLong((String) args[j + 3])));
				} else {
					va.setTime(null);
				}
				va.setUsername(args[j + 4]);
				if (args[j + 5].equals("F")) {
					va.setMyParticipation(false);
				}
				va.setGroupname(args[j + 6]);
				va.setSize(Integer.parseInt(args[j + 7]));
				// ��Ƿ��Ѿ�����
				if (va.getTime() != null && va.getTime().before(new Date())) {
					va.setDone(true);
				}
				array[i++] = va;
			}
			return array;
		} else {
			return null;
		}
	}

}
