/**
 * <p>
 * Copyright: Copyright (c) 2012 Company: ZTE Description: 瀛楃涓插伐鍏风被瀹炵幇鏂囦欢
 * </p>
 *
 * @Title StringUtil.java
 * @Package com.zte.iptvclient.android.common
 * @version 1.0
 * @author jamesqiao10065075
 * @date 2012-02-08
 */

/** IPTV瀹㈡埛绔疉ndroid閫氱敤鍔熻兘鍖? */

package com.my_movingbricks.utlis;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @ClassName: StringUtil
 * @Description: 汇集字符串处理相关的一些功能函数。
 * @author: jamesqiao10065075
 * @date: 2012-2-8
 *
 */
public final class StringUtil {

	/** 日志标签 */
	public static final String LOG_TAG = "StringUtil";

	/********************************** 删除空格类型 **********************************/
	/** 删除全部空格 */
	public static final int TYPE_REMOVE_SPACE_ALL = 0;

	/** 删除左边空格 */
	public static final int TYPE_REMOVE_SPACE_LEFT = 1;

	/** 删除右边空格 */
	public static final int TYPE_REMOVE_SPACE_RIGHT = 2;

	/** 删除两端空格 */
	public static final int TYPE_REMOVE_SPACE_BOTH = 3;

	/**
	 * 不使用默认构造函数
	 */
	private StringUtil() {

	}

	/**
	 * 判断某个字符串是否以指定子字符串开头
	 * <p>
	 * Description:
	 * 判断某个字符串是否已某个子字符串开头，当strTarget或strPrefix为null或空字符串的时候，返回false，其他根据实际情况返回。
	 * <p>
	 *
	 * @date 2012-4-16
	 * @param strTarget
	 *            待比较的目标字符串
	 * @param strPrefix
	 *            待搜索的前缀字符串
	 * @return
	 *         字符串是否已某个子字符串开头，当strTarget或strPrefix为null或空字符串的时候，返回false，其他根据实际情况返回
	 *         。
	 */
	public static boolean startsWith(String strTarget, String strPrefix) {
		if (isEmptyString(strTarget) || isEmptyString(strPrefix)) {
			return false;
		} else {
			return strTarget.startsWith(strPrefix);
		}
	}

	/**
	 * 判断字符串是否为空
	 * <p>
	 * Description: 判空的依据是null或者空字符串
	 * <p>
	 *
	 * @date 2012-2-8
	 * @param strTarget
	 *            待判断的目标字符串
	 * @return 如果是null或者是空字符串，则返回true，否则，返回false。
	 */
	public static boolean isEmptyString(String strTarget) {
		return ((null == strTarget) || (0 == strTarget.length()));
	}

	/**
	 *
	 * 获取非空字符串
	 * <p>
	 * Description: 如果不为空，直接返回；为空则用""代替。
	 * <p>
	 *
	 * @author jamesqiao10065075
	 * @date 2012-3-23
	 * @param strTarget
	 *            目标字符串
	 * @return 如果目标字符串为null，则返回""；否则返回目标字符串。
	 */
	public static String getStringNotNull(String strTarget) {
		if (null != strTarget) {
			return strTarget;
		} else {
			return "";
		}
	}

	/**
	 *
	 * 判断两个字符串是否相同
	 * <p>
	 * Description: 判断两个字符串是否相同，依据是否都为null或者内容是否相同。
	 * <p>
	 *
	 * @date 2012-6-7
	 * @author jamesqiao10065075
	 * @param strSource
	 *            字符串1
	 * @param strTarget
	 *            字符串2
	 * @return 如果都为null或者内容相同，则返回true；否则返回false。
	 */
	public static boolean isSameString(String strSource, String strTarget) {
		// 都不为null
		if (null != strSource && null != strTarget) {
			return strSource.equals(strTarget);
		}
		// 两个都为null
		else if (strSource == strTarget) {
			return true;
		} else
		// 有一个为null
		{
			return false;
		}
	}

	/**
	 *
	 * 获取拆分后指定位置的单个字符串
	 * <p>
	 * Description: 根据分隔符拆分字符串，并返回对应位置的字符串
	 * <p>
	 *
	 * @author jamesqiao10065075
	 * @date 2012-3-23
	 * @param strSrc
	 *            用分隔符分割的原始字符串
	 * @param strSeperator
	 *            分隔符
	 * @param iPos
	 *            指定位置
	 * @return 对应位置的字符串，如果入参非法，则返回null;如果分隔符之间无内容，返回空串。
	 */
	public static String getSplitedString(String strSrc, String strSeperator,
										  int iPos) {
		Log.d(LOG_TAG, "strSrc=" + strSrc + ",strSeperator=" + strSeperator
				+ ",iPos=" + iPos);

		if (null == strSrc) {
			return null;
		} else if ("".equals(strSrc) || isEmptyString(strSeperator)) {
			return strSrc;
		}

		String[] arStrings = splitString(strSrc, strSeperator);

		Log.d(LOG_TAG, "length=" + arStrings.length);
		if (iPos >= 0 && iPos < arStrings.length) {
			return getStringNotNull(arStrings[iPos]);
		} else {
			return null;
		}
	}

	/**
	 *
	 * 分隔字符串
	 * <p>
	 * Description: 使用指定分隔符分隔字符串，如果两个分隔符之间没有字符，则返回空串
	 * <p>
	 *
	 * @author jamesqiao10065075
	 * @date 2012-3-23
	 * @param strTarget
	 *            源字符串
	 * @param strSeperator
	 *            分隔符
	 * @return 分隔后的字符串数组
	 */
	public static String[] splitString(String strTarget, String strSeperator) {
		List<String> listRets = new ArrayList<String>();
		if (isEmptyString(strTarget)) {
			return (String[]) listRets.toArray(new String[1]);
		}

		// 下一个分隔符位置
		int iLastPos = 0;
		int iPos = strTarget.indexOf(strSeperator, iLastPos);
		if (iPos >= 0) {
			while (iPos >= 0) {
				if (iPos > iLastPos) {
					listRets.add(strTarget.substring(iLastPos, iPos));
				} else
				// 两个分隔符紧邻
				{
					listRets.add("");
				}

				iLastPos = iPos + 1;

				iPos = strTarget.indexOf(strSeperator, iLastPos);
			}

			// 最后一段
			if (iLastPos <= (strTarget.length() - 1)) {
				listRets.add(strTarget.substring(iLastPos));
			}
			// 最后一个
			else {
				listRets.add("");
			}
		} else
		// 整体作为一个单元项
		{
			listRets.add(strTarget);
		}

		return (String[]) listRets.toArray(new String[1]);
	}

	/**
	 *
	 * 去除字符串中的两端的空格
	 * <p>
	 * Description: 支持删除两端空格。
	 * <p>
	 *
	 * @author jamesqiao10065075
	 * @date 2012-3-27
	 * @param strSource
	 *            来源字符串
	 * @return 去除空格后的字符串
	 */
	public static String removeSpace(String strSource) {
		return removeSpace(strSource, TYPE_REMOVE_SPACE_BOTH);
	}

	/**
	 *
	 * 去除字符串中的空格
	 * <p>
	 * Description: 支持删除全部空格、只删除左边空格、只删除右边空格，删除两端空格
	 * <p>
	 *
	 * @author jamesqiao10065075
	 * @date 2012-3-27
	 * @param strSource
	 *            来源字符串
	 * @param iRemoveType
	 *            TYPE_REMOVE_SPACE_ALL等于全部，TYPE_REMOVE_SPACE_LEFT等于左边，
	 *            TYPE_REMOVE_SPACE_RIGHT等于右边，TYPE_REMOVE_SPACE_BOTH等于两端
	 * @return 去除空格后的字符串
	 */
	public static String removeSpace(String strSource, int iRemoveType) {
		if (isEmptyString(strSource)) {
			return "";
		}

		String strReturn = strSource;

		switch (iRemoveType) {
			case TYPE_REMOVE_SPACE_ALL: // 去除全部空格
			{
				strReturn = strReturn.replace(" ", "");
				break;
			}
			case TYPE_REMOVE_SPACE_LEFT: // 去除左边空格
			case TYPE_REMOVE_SPACE_RIGHT: // 去除右边空格
			case TYPE_REMOVE_SPACE_BOTH: // 去除两端空格
			{
				// 去除左边或者两端空格
				if (TYPE_REMOVE_SPACE_BOTH == iRemoveType
						|| TYPE_REMOVE_SPACE_LEFT == iRemoveType) {
					int i = 0;
					int n = strReturn.length();
					for (i = 0; i < n; i++) {
						if (strReturn.charAt(i) != ' ') {
							break;
						}
					}

					if (i < n) {
						strReturn = strReturn.substring(i);
					} else {
						strReturn = "";
					}
				}
				// 去除右边或者两端空格
				if (TYPE_REMOVE_SPACE_BOTH == iRemoveType
						|| TYPE_REMOVE_SPACE_RIGHT == iRemoveType) {
					int i = 0;
					int n = strReturn.length();
					for (i = n - 1; i >= 0; i--) {
						if (strReturn.charAt(i) != ' ') {
							break;
						}
					}

					if (i >= 0) {
						strReturn = strReturn.substring(0, i + 1);
					} else {
						strReturn = "";
					}
				}
				break;
			}
			default: //
			{
				break;
			}
		}

		return strReturn;
	}

	/**
	 * 替换目标位置的字符
	 *
	 * @param
	 *
	 * @param position 原字符串
	 *            替换的位置,从1 开始
	 * @param replaceChar
	 *            替换的字符
	 * @return
	 */
	public static String replaceChar(String targetString, int position,
									 String replaceChar) {
		String strReturn = "";
		int realPosition = 0;
		try {
			if ((null == targetString) || (0 == targetString.length())) {
				System.out
						.println("error (null == targetString) || (0 == targetString.length())");
				return targetString;
			}
			if (position < 1 || position > targetString.length()) {
				System.out
						.println("error position < 1 || position > targetString.length()");
				return targetString;
			}
			realPosition = position - 1;
			if ((null == replaceChar) || 0 == replaceChar.length()
					|| replaceChar.length() != 1) {
				System.out
						.println("error (null == targetString)|| (0 == targetString.length() || targetString.length() != 1)");
				return targetString;
			}
			if (realPosition == 0) {
				strReturn = replaceChar
						+ targetString.substring(1, targetString.length());
			} else if (realPosition > 0
					&& realPosition < targetString.length() - 1) {
				strReturn = targetString.substring(0, realPosition)
						+ replaceChar
						+ targetString.substring(realPosition + 1,
						targetString.length());

			} else if (realPosition == targetString.length() - 1) {

				strReturn = targetString.substring(0, realPosition)
						+ replaceChar;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return targetString;
		}

		return strReturn;
	}

	/**
	 * 验证电话号码的正确性
	 *
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNum(String phone) {
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(phone);
			return m.matches();
		} catch (Exception e) {
			return false;
		}
		// return
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")
		// .matcher(paramString).matches();
	}

	/**
	 * 验证Email地址的正确性
	 *
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static String shiftUp(String str) {
		int size = str.length();
		char[] chs = str.toCharArray();
		for (int i = 0; i < size; i++) {
			if (chs[i] <= 'z' && chs[i] >= 'a') {
				chs[i] = (char) (chs[i] - 32);
			}
		}
		return new String(chs);
	}

	public static String shiftLow(String str) {
		int size = str.length();
		char[] chs = str.toCharArray();
		for (int i = 0; i < size; i++) {
			if (chs[i] <= 'Z' && chs[i] >= 'A') {
				chs[i] = (char) (chs[i] + 32);
			}
		}
		return new String(chs);
	}

	public static String shift(String str) {
		int size = str.length();
		char[] chs = str.toCharArray();
		for (int i = 0; i < size; i++) {
			if (chs[i] <= 'Z' && chs[i] >= 'A') {
				chs[i] = (char) (chs[i] + 32);
			} else if (chs[i] <= 'z' && chs[i] >= 'a') {
				chs[i] = (char) (chs[i] - 32);
			}
		}
		return new String(chs);
	}

	/**
	 * 首字母大写
	 *
	 * @param st
	 * @return
	 */
	public static String toUpperCaseString(String st) {
		char[] cs = st.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	/**
	 * 判断文件后缀
	 *
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return "";
	}

	/**
	 * 　　* 保存文件 　　* @param toSaveString 　　* @param filePath 　　
	 */
	public static void saveFile(String toSaveString, String filePath) {
		try {
			String topeasy = Environment.getExternalStorageDirectory()
					+ "/Movingbricks/";
			File saveFile = new File(topeasy+filePath);
			if (!saveFile.exists()) {
				File dir = new File(saveFile.getParent());
				dir.mkdirs();
				saveFile.createNewFile();
			}

			FileOutputStream outStream = new FileOutputStream(saveFile);
			outStream.write(toSaveString.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 　　* 读取文件内容 　　* @param filePath 　　* @return 文件内容 　　
	 */
	public static String readFile(String filePath) {
		String str = "";
		try {
			File readFile = new File(filePath);
			if (!readFile.exists()) {
				return null;
			}
			FileInputStream inStream = new FileInputStream(readFile);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			str = stream.toString();
			stream.close();
			inStream.close();
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}