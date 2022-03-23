package com.bshp.common.util;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;


public class CommonUtil {

	public static <T> T convertMapToVo(Map<String, Object> data, Class<T> clazz){
		
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
	
	public static String getRandomString(int length){ 

        byte[] byteArray = new byte[256];
        
        new Random().nextBytes(byteArray); 

        String string = new String(byteArray, Charset.forName("UTF-8"));
        StringBuffer buffer = new StringBuffer(); 

        for (int m = 0; m < string.length(); m++) { 

            char n = string.charAt(m); 
            if (((n >= 'A' && n <= 'Z') || (n >= '0' && n <= '9')) && (length > 0)) {
            	buffer.append(n); 
            	length--; 
            } 
        } 
        return buffer.toString(); 
    } 
} 
