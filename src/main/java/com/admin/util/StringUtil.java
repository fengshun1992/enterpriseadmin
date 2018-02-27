package com.admin.util;


import java.io.Reader;
import java.sql.Clob;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 */
public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0) {
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

			i++;
		}
		return returnStr;
	}
	public static boolean isMobile(String str) {
		String[] strs = StrList(str);
		boolean b = false;
		for (int i = 0; i < strs.length; i++) {
			String s = strs[i];
			Pattern p = null;
			Matcher m = null;
			p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
			m = p.matcher(s);
			b = m.matches();
		}
		return b;
	}

	public static String getClobtoStr(Clob clob) throws Exception {
		Reader inStream = clob.getCharacterStream();
		String project_des = null;
		char[] c = new char[(int) clob.length()];
		inStream.read(c);
		project_des = new String(c);
		inStream.close();
		return project_des;
	}

	/**
	 * 判断是否为数字
	 *
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			if (str.matches("^(-)?\\d+$")) {
				return true;
			}
		}
		if (obj instanceof Integer) {
			return true;
		}
		return false;
	}
}
