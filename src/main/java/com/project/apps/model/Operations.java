package com.project.apps.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Operations {
	
	public static LinkedHashMap<String,Float> sortHashMap(Map<String, Float> map){
		List<Map.Entry<String, Float>> list = new LinkedList<Map.Entry<String,Float>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
			public int compare(Entry<String, Float> o1, Entry<String, Float> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
 
		Map<String, Float> sortedMap = new LinkedHashMap<String,Float>();
		for (Iterator<Map.Entry<String, Float>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Float> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return (LinkedHashMap<String, Float>) sortedMap;
	}


}
