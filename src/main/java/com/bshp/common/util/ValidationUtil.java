package com.bshp.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 유효성검사
 * @author JeHoon
 *
 */
public class ValidationUtil {
	
	/**
	 * 문자열 빈값 체크
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if(value == null || "".equals(value.trim())) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 비밀번호 체크(8~20자리, 영문,숫자 특수문자 조합)
	 * @param value
	 * @return
	 */
	public static boolean isPassword(String value) {
		Matcher match = Pattern.compile("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$").matcher(value);
		return match.find();		 
	}
	
	/**
	 * 전화번호 체크
	 * @param value
	 * @return
	 */
	public static boolean isPhone(String value) {		
		if(value.length() > 8 && value.length() < 12 && value.matches("[+-]?\\d*(\\.\\d+)?")) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 이메일 체크
	 * @param value
	 * @return
	 */
	public static boolean isEmail(String value) {
		if(value.length() <= 50 && value.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$")) {
			return true;
		}else {
			return false;
		}
	}
	
} 
