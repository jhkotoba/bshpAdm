package com.bshp.common;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


public class CommonUtil {

	public static <T> T converterMapToVo(Map<String, Object> data, Class<T> clazz){
		
		T vo = null;
		
		try {
			vo = clazz.getDeclaredConstructor().newInstance();
			
			for (Field field : vo.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				field.set(vo, data.get(field.getName().replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase()));
			}
			
		}catch(IllegalArgumentException | IllegalAccessException 
				| InstantiationException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return vo;
	}
} 
