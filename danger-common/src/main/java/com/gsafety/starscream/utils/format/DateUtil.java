package com.gsafety.starscream.utils.format;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * 日期操作工具
 * 
 * @author wistronITS
 * 
 */
public class DateUtil {
	/**
	 * 时间格式化正则表达式
	 */
	private static final Pattern RELEASE_DATE_PATTERN = Pattern
			.compile("[^\\d\\s-:/]+");

	/**
	 * 时间格式化模式
	 */
	private static final String[] RELEASE_PATTERNS = new String[] {
			"yyyy年MM月dd日", "yyyy年MM月" };

	private static final Logger log = Logger.getLogger(DateUtil.class);
	private final Date startDate = new Date(new Date().getTime());
	private final Date endDate = new Date(new Date().getTime());

	/**
	 * Generates a time stamp yyyymmddhhmmss
	 * 
	 * @return String
	 */
	public static String generateTimeStamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}

	/**
	 * 格式化日期yyyy-MM-dd
	 * 
	 * @param dt
	 * @return String
	 */
	public static String formatDate(Date dt) {
		return formatDate(dt, "yyyy-MM-dd");

	}

	/**
	 * 格式化日期yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dt
	 * @return String
	 */
	public static String formatDateTime(Date dt) {
		return formatDate(dt, "yyyy-MM-dd HH:mm:ss");

	}

	/**
	 * 格式化日期yyyy-MM-dd HH:mm
	 * 
	 * @param dt
	 * @return String
	 */
	public static String formatDateHHmm(Date dt) {
		return formatDate(dt, "yyyy-MM-dd HH:mm");

	}

	/**
	 * 格式化日期
	 * 
	 * @param value
	 *            date
	 * @param pattern
	 *            default"yyyy-MM-dd"
	 * @return date string
	 */
	public static String formatDate(java.util.Date value, String pattern) {
		String pat = "yyyy-MM-dd";
		if (pattern != null) {
			pat = pattern;
		}
		String result = "";
		if (value != null) {
			SimpleDateFormat htmlDf = new SimpleDateFormat(pat);
			result = htmlDf.format(value).toString();
		}
		return result;
	}

	/**
	 * 格式化日期yy-MMM-dd
	 * 
	 * @param dt
	 * @return String
	 */
	public static String formatDateMonthString(Date dt) {

		if (dt == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
		return format.format(dt);

	}

	/**
	 * @param date
	 * @return Date
	 * @throws Exception
	 */
	public static Date getDate(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (date.length() == 16) {
			myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		if (date.length() == 19) {
			myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		if (date.length() == 13) {
			myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		}
		try {
			return myDateFormat.parse(date);
		} catch (ParseException e) {
			log.debug("试图将" + date + "转换成yyyy-MM-dd格式的日期类型，转换失败！", e);
			return null;
		}
	}

	/**
	 * @param date
	 * @return Date yyyy-MM-dd HH:mm:ss
	 * @throws Exception
	 */
	public static Date getDateTime(String date) {
		return getDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param date
	 * @return Date yyyy-MM-dd HH:mm
	 * @throws Exception
	 */
	public static Date getDateHHmm(String date) {
		return getDateTime(date, "yyyy-MM-dd HH:mm");
	}

	public static Date getDateTime(String dateTime, String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		if (StringUtils.isEmpty(dateTime)) {
			return null;
		}
		DateFormat myDateFormat = new SimpleDateFormat(pattern);
		try {
			return myDateFormat.parse(dateTime);
		} catch (ParseException e) {
			log.debug("试图将" + dateTime + "转换成" + pattern + "格式的日期类型，转换失败！", e);
			return null;
		}
	}

	/**
	 * @param days
	 * @return Date
	 */
	public static Date addDaysToCurrentDate(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, days);
		return c.getTime();

	}

	/**
	 * 获得时间
	 * 
	 * @return Date
	 */
	public static Date getDate() {

		return new Date(new Date().getTime());

	}

	/**
	 * 获得当前时间
	 * 
	 * @return Date
	 */
	public static String getPresentDate() {

		Date dt = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(dt.getTime()));
	}

	/**
	 * 获得年
	 * 
	 * @return String
	 */
	public static String getPresentYear() {

		Date dt = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(new Date(dt.getTime()));
	}

	/**
	 * 获得小时数
	 * 
	 * @return String
	 */
	public static int getHours(String year, String month, String day) {
		int ret = 0;

		try {
			if (StringUtils.isNotBlank(year)) {
				ret += Integer.parseInt(year) * 360 * 24;
			}

			if (StringUtils.isNotBlank(month)) {
				ret += Integer.parseInt(month) * 30 * 24;
			}

			if (StringUtils.isNotBlank(day)) {
				ret += Integer.parseInt(day) * 24;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 判断字符日期能否正确转换为给定的日期格式
	 * 
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static boolean isDate(String dateTime, String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		if (StringUtils.isEmpty(dateTime)) {
			return false;
		}
		DateFormat myDateFormat = new SimpleDateFormat(pattern);
		try {
			myDateFormat.parse(dateTime);
		} catch (ParseException e) {
			log.debug("试图将" + dateTime + "转换成" + pattern + "格式的日期类型，转换失败！", e);
			return false;
		}
		return true;
	}

	/**
	 * @param days
	 * @return Date
	 */
	public static Date addToCurrentDate(int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(field, amount);
		return c.getTime();

	}

	/**
	 * 取得只含有年月日的当前时间
	 * 
	 * @return 只含有年月日的当前时间
	 */
	public static Date truncateToday() {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 取得只含有年月日的时间,并加上日偏差
	 * 
	 * @param offsetDays
	 *            偏差天数
	 * @return 计算偏差后的时间
	 */
	public static Date truncateToday(int offsetDays) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, offsetDays);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 取得只含有年月日的时间
	 * 
	 * @param date
	 *            时间对象
	 * @return 只含有年月日的时间
	 */
	public static Date truncateDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return truncateDate(calendar);
	}

	/**
	 * 取得只含有年月日的时间
	 * 
	 * @param calendar
	 *            日历对象
	 * @return 只含有年月日的时间
	 */
	public static Date truncateDate(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * <pre>
	 * 截去指定field之后的日期属性
	 * 例如日期2010-3-23 11:25:32.100截去Calendar.HOUR_OF_DAY之后
	 * 为2010-3-23 11:00:00.000;截去Calendar.DAY_OF_MONTH之后为2010-3-23 0:00:00.000
	 * 
	 * @param date
	 *            时间对象
	 * @param field 
	 * @return 只含有年月日的时间
	 * </pre>
	 */
	public static Date truncateDate(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return truncateDate(calendar, field);
	}

	/**
	 * <pre>
	 * 截去指定field之后的日期属性
	 * 例如日期2010-3-23 11:25:32.100截去Calendar.HOUR_OF_DAY之后
	 * 为2010-3-23 11:00:00.000;截去Calendar.DAY_OF_MONTH之后为2010-3-23 0:00:00.000
	 * 
	 * @param calendar
	 *            日历对象
	 * @param field 
	 * @return 只含有年月日的时间
	 * </pre>
	 */
	public static Date truncateDate(Calendar calendar, int field) {
		if (field == Calendar.SECOND) {
			calendar.set(Calendar.MILLISECOND, 0);
		} else if (field == Calendar.MINUTE) {
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} else if (field == Calendar.HOUR_OF_DAY) {
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} else if (field == Calendar.DAY_OF_MONTH) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} else if (field == Calendar.MONTH) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} else if (field == Calendar.YEAR) {
			calendar.set(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}

		return calendar.getTime();
	}

	/**
	 * 格式化不规则发行日期
	 * 
	 * @param releaseDate
	 *            发行日期
	 * @return 规则发行日期
	 */
	public static String formatReleaseDate(String releaseDate) {
		if (StringUtils.isBlank(releaseDate)) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.parseDate(releaseDate, RELEASE_PATTERNS));
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			Matcher m = RELEASE_DATE_PATTERN.matcher(releaseDate);
			int last = releaseDate.length();
			int end = 0;
			while (m.find()) {
				sb.append(releaseDate.substring(end, m.start()));
				end = m.end();
				if (end != last) {
					sb.append("-");
				}
			}
			if (end != last) {
				sb.append(releaseDate.substring(end));
			}

			String validYear = sb.toString();
			if (validYear.length() == 2) {
				if (validYear.compareTo("70") < 0) {
					return "20" + validYear;
				}
				return "19" + validYear;
			}
			return validYear;
		}
	}

	public static final int[] WEEKS = { 7, 1, 2, 3, 4, 5, 6 };

	/**
	 * 取得中国的星期几
	 * 
	 * @return 中国的星期几
	 */
	public static int findChineseDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		return WEEKS[day - 1];
	}

	/**
	 * 取得最近几年的年份集合
	 * @param latestNum
	 * @return
	 */
	public static Map<String, String> getYearMap(int latestNum) {
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		return getYearMap(curYear-latestNum, curYear);
	}

	public static Map<String, String> getYearMap(int startYear, int endYear) {
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		do {
			yearMap.put(startYear + "", startYear + "");
			startYear++;
		} while (!(endYear < startYear));
		return yearMap;
	}
	
	public static List<String> getYearList(int latestNum) {
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		return getYearList(curYear-latestNum, curYear);
	}
	
	public static List<String> getYearList(int startYear, int endYear) {
		List<String> yearList = new ArrayList<String>();
		do {
			yearList.add(""+startYear);
			startYear++; 
		} while (!(endYear < startYear));
		return yearList;
	}
	
}
