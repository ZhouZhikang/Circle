package com.test.zhikangzhou.client.model;

import java.util.Date;
import java.util.List;

public class ViewActivity {

	private int activityid;
	private String activityname;
	private String activitydetail;
	private Date time;
	private String username;
	private String groupname;
	private int size;
	private boolean myParticipation = true;
	private boolean done = false;

	public int getActivityid() {
		return activityid;
	}

	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public String getActivitydetail() {
		return activitydetail;
	}

	public void setActivitydetail(String activitydetail) {
		this.activitydetail = activitydetail;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isMyParticipation() {
		return myParticipation;
	}

	public void setMyParticipation(boolean myParticipation) {
		this.myParticipation = myParticipation;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
