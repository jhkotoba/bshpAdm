package com.bshp.common.util;

import org.springframework.stereotype.Component;

/**
 * 유효성검사
 * @author JeHoon
 *
 */
@Component
public class ValidationUtil {
	
	/**
	 * 문자열 빈값 체크
	 * @param value
	 * @return
	 */
	public boolean isEmpty(String value) {
		return true;
	}
	
	/**
	 * 전화번호 체크
	 * @param value
	 * @return
	 */
	public boolean isPhone(String value) {
		return true;
	}
	
	/**
	 * 이메일 체크
	 * @param value
	 * @return
	 */
	public boolean isEmail(String value) {
		return true;
	}
	
} 
