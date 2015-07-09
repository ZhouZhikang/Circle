package com.test.zhikangzhou.client.util;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class SearchGroupExecuter extends Executer {
	private String keyword;
	private String userid;

	public SearchGroupExecuter(String keyword, String userid) {
		this.keyword = keyword;
		this.userid = userid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "SearchGroupMethod";
		Object[] paraList = new Object[2];
		paraList[0] = this.keyword;
		paraList[1] = this.userid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D") && args.length > 1) {
			int length = (args.length - 1) / 5;
			Object[] array = new Object[length];
			int i = 0;
			for (int j = 1; j < args.length; j += 5) {
				ViewCircle vg = new ViewCircle();
				vg.setGroupid(Integer.parseInt(args[j]));
				vg.setGroupname(args[j + 1]);
				vg.setInstraction(args[j + 2]);
				vg.setOwnername(args[j + 3]);
				if (args[j + 4].equals("F")) {
					vg.setMyFocus(false);
				}
				array[i++] = vg;
			}
			return array;
		} else {
			return null;
		}
	}

}
