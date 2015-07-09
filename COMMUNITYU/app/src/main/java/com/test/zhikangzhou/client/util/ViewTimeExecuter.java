package com.test.zhikangzhou.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewMessage;
import com.test.zhikangzhou.client.model.ViewTimetable;
import com.test.zhikangzhou.client.tools.TransProtocol;

public class ViewTimeExecuter extends Executer {
	
	private int activityid;
	
	public ViewTimeExecuter(int activityid) {
		this.activityid = activityid;
	}

	@Override
	public String createCommand() {
		// TODO Auto-generated method stub
		String cmd = "ViewTimeMethod";
		Object[] paraList = new Object[1];
		paraList[0] = this.activityid;
		return TransProtocol.createCommand(cmd, paraList);
	}

	@Override
	public Object[] response(String result) {
		// TODO Auto-generated method stub
		String[] args = TransProtocol.decode(result);
		if (args[0].equals("D")) {
			ViewTimetable timetable = new ViewTimetable();
			timetable.setTotalSum(Integer.parseInt(args[1]));
			Map<Date, Integer> map = new HashMap<Date, Integer>();
			for (int j = 2; j < args.length; j += 2) {
				Date date = new Date(Long.parseLong(args[j]));
				int sum = Integer.parseInt(args[j + 1]);
				map.put(date, sum);
			}
			timetable.setAvailableList(map);
			return new Object[] { timetable };
		} else {
			return null;
		}
	}

}
