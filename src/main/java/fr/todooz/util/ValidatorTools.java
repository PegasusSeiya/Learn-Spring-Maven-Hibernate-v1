package fr.todooz.util;

import java.util.List;
import java.util.Map;

public class ValidatorTools {
	
	public static boolean isNotNullAndNotEmpty(List<? extends Object> list){
		
		return (list!=null && !list.isEmpty());
		
	}
	
	
	public static boolean isNotNullAndNotEmpty(Map<? extends Object, ? extends Object> map){
		
		return (map!=null && !map.isEmpty());
		
	}
}
