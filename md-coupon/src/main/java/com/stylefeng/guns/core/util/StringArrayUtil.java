package com.stylefeng.guns.core.util;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

public class StringArrayUtil {

	/**
	 * 获取uuid数组
	 * 
	 * @param num
	 * @return
	 */
	public static String[] getStringArrayByUUID(Integer num) {
		String[] strArray = null;
		String str = "";
		for(int i = 1; i<= num ; i++){
			str = str + DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)).toUpperCase().substring(0, 16) + ",";
		}
    	strArray = str.split(",");
    	return strArray;
	}
}
