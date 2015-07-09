package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class ActMemberExecuter extends Executer {
	private int actid;

	public ActMemberExecuter(int actid) {
		this.actid = actid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "ActMemberMethod";
		Object[] paraList = new Object[1];
		paraList[0] = this.actid;
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
				ViewUser vu = new ViewUser();
				vu.setUserid(args[j]);
				vu.setUsername(args[j + 1]);
				vu.setUserschool(args[j + 2]);
				vu.setUserspecial(args[j + 3]);
				vu.setUserclass(args[j + 4]);
				vu.setDorm(args[j + 5]);
				vu.setRoom(args[j + 6]);
                array[i++]=vu;
			}
			return array;
		} else {
			return null;
		}
	}

}
