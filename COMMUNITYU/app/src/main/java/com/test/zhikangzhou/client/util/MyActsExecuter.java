package com.test.zhikangzhou.client.util;

import java.util.Date;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class MyActsExecuter extends Executer {
	private String userid;

	public MyActsExecuter(String userid) {
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "MyActsMethod";
		Object[] paraList = new Object[1];
		paraList[0] = this.userid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D") && args.length > 1) {
			int length = (args.length - 1) / 7;
			Object[] array = new Object[length];
			int i = 0;
			for (int j = 1; j < args.length; j += 7) {
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
				va.setGroupname(args[j + 5]);
				va.setSize(Integer.parseInt(args[j + 6]));
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
