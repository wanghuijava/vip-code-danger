package com.gsafety.starscream.admin.utils;

import org.springframework.util.StringUtils;

public class AntiSpamUtils {

	public static String safeText(String content){
		if (StringUtils.isEmpty(content)){
			return content;
		}
		return content.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
