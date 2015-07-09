package com.test.zhikangzhou.client.tools;

// ������Ϣ����Э��
public abstract class TransProtocol {
	// �ָ���
	private static String SEPARATOR = "@=@";

	// ���ָ��
	public static String createCommand(String cmd, Object[] paraList) {
		StringBuffer result = new StringBuffer();
		// ָ����
		result.append("C");
		result.append(SEPARATOR);
		// �ָ�������ָ�����
		result.append(cmd);
		// �����б��еĸ�������׷������м��÷ָ���ָ�
		for (Object para : paraList) {
			result.append(SEPARATOR);
			if (para != null)
				result.append(para.toString());
			else
				result.append("");
		}
		return result.toString();
	}

	// �����ݼ�
	public static String createDataSet(Object[] paraList) {
		StringBuffer result = new StringBuffer();
		// ��ݱ��
		result.append("D");
		// �б��еĸ������׷������м��÷ָ���ָ�
		for (Object para : paraList) {
			result.append(SEPARATOR);
			if (para != null)
				result.append(para.toString());
			else
				result.append("");
		}
		return result.toString();
	}

	// �����Ϣ
	public static String createMessage(String message) {
		StringBuffer result = new StringBuffer();
		// �쳣���
		result.append("M");
		result.append(SEPARATOR);
		// �ָ��������쳣��Ϣ
		result.append(message);
		return result.toString();
	}

	// ������Ϣ��ʽ
	public static String[] decode(String information) {
		// ���ָ���ֿ�
		return information.split(SEPARATOR);
	}
}
