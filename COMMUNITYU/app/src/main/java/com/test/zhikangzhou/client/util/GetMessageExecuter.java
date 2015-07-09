package com.test.zhikangzhou.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewMessage;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class GetMessageExecuter extends Executer {
	
	private int activityid;
	
	public GetMessageExecuter(int activityid) {
		this.activityid = activityid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "GetMessageMethod";
		Object[] paraList = new Object[1];
		paraList[0] = this.activityid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D") && args.length > 1) {
			int length = (args.length - 1) / 3;
			Object[] array = new Object[length];
			int i = 0;
			for (int j = 1; j < args.length; j += 3) {
				ViewMessage msg = new ViewMessage();
				msg.setUsername(args[j]);
				msg.setSendtime(args[j + 1]);
				msg.setContent(args[j + 2]);
				array[i++] = msg;
			}
			return array;
		} else {
			return null;
		}
	}

}
