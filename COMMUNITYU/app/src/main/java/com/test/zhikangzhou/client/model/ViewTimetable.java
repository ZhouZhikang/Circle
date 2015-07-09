package com.test.zhikangzhou.client.model;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ViewTimetable {

	private int totalSum;
	private Map<Date, Integer> availableList;

	public int getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(int totalSum) {
		this.totalSum = totalSum;
	}

	public Map<Date, Integer> getAvailableList() {
		return availableList;
	}

	public void setAvailableList(Map<Date, Integer> availableList) {
		this.availableList = availableList;
	}

}
