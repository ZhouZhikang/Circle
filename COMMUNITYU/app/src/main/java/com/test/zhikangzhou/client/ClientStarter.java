package com.test.zhikangzhou.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.test.zhikangzhou.client.comm.Executer;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.model.ViewMessage;
import com.test.zhikangzhou.client.model.ViewTimetable;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.util.AddActExecuter;
import com.test.zhikangzhou.client.util.AddGroupExecuter;
import com.test.zhikangzhou.client.util.AddMessageExecuter;
import com.test.zhikangzhou.client.util.GetActsExecuter;
import com.test.zhikangzhou.client.util.GetGroupsExecuter;
import com.test.zhikangzhou.client.util.GetMessageExecuter;
import com.test.zhikangzhou.client.util.JoinActExecuter;
import com.test.zhikangzhou.client.util.JoinGroupExecuter;
import com.test.zhikangzhou.client.util.LoginExecuter;
import com.test.zhikangzhou.client.util.ModifyActExecuter;
import com.test.zhikangzhou.client.util.ModifyPwdExecuter;
import com.test.zhikangzhou.client.util.MyActsExecuter;
import com.test.zhikangzhou.client.util.MyGroupsExecuter;
import com.test.zhikangzhou.client.util.PartActsExecuter;
import com.test.zhikangzhou.client.util.PickTimeExecuter;
import com.test.zhikangzhou.client.util.SearchGroupExecuter;
import com.test.zhikangzhou.client.util.ViewTimeExecuter;

public class ClientStarter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.add(401);
		Executer e = new ViewTimeExecuter(4);
		Object[] result = e.execute();
		if (result != null) {
			ViewTimetable tt = (ViewTimetable) result[0];
			System.out.println(tt.getTotalSum());
			for (Date d : tt.getAvailableList().keySet()) {
				System.out.println(d + " --- " + tt.getAvailableList().get(d));
			}
		}
	}

}
