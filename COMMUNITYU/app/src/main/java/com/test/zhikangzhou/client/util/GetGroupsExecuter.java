package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class GetGroupsExecuter extends Executer {
	private String userid;

	public GetGroupsExecuter(String userid) {
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "GetGroupsMethod";
		Object[] paraList = new Object[1];
		paraList[0] = this.userid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D") && args.length > 1) {
			int length = (args.length - 1) / 4;
			Object[] array = new Object[length];
			int i = 0;
			for (int j = 1; j < args.length; j += 4) {
				ViewCircle vg = new ViewCircle();
				vg.setGroupid(Integer.parseInt(args[j]));
				vg.setGroupname(args[j + 1]);
				vg.setInstraction(args[j + 2]);
				vg.setOwnername(args[j + 3]);
				array[i++] = vg;
			}
			return array;
		} else {
			return null;
		}
	}

}
