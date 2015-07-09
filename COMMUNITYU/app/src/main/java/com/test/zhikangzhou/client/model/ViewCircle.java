package com.test.zhikangzhou.client.model;

public class ViewCircle {
	
	private int groupid;
	private String groupname;
	private String instraction;
	private String ownername;
	private boolean myFocus = true;

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getInstraction() {
		return instraction;
	}

	public void setInstraction(String instraction) {
		this.instraction = instraction;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public boolean isMyFocus() {
		return myFocus;
	}

	public void setMyFocus(boolean myFocus) {
		this.myFocus = myFocus;
	}

}
