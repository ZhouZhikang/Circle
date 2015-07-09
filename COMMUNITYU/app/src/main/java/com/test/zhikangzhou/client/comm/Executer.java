package com.test.zhikangzhou.client.comm;

public abstract class Executer {
	protected Sender sender;

	public Executer() {
		sender = new Sender();
	}

	public Object[] execute() {
		String msg = this.createCommand();
		String result = this.sender.communicate(msg);
		if (result == null) {
			return null;
		} else {
			return this.response(result);
		}
	}

	public abstract String createCommand();

	public abstract Object[] response(String result);
}
