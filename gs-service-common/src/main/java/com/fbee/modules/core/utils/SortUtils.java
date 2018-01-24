package com.fbee.modules.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortUtils {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List compare(List list, final String str){
		if(list == null){
			return list;
		}
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// 进行判断
				return ((Integer) o2.get(str)).compareTo((Integer) o1.get(str));
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", "张三");// 名字
		map1.put("age", 22); // 年龄
		list.add(map1);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", "王五");
		map3.put("age", 38);
		list.add(map3);
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("name", "谢七");
		map5.put("age", 20);
		list.add(map5);
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put("name", "李四");
		map6.put("age", 22);
		list.add(map6);
		list = compare(list,"age");
		for (Map<String, Object> m : list) {
			System.out.println("Map[name=" + m.get("name") + "age=" + m.get("age") + "]");
		}
	}
	
}
