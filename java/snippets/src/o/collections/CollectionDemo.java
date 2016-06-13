package o.collections;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.Iterator;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class CollectionDemo {
  public static void main(String[] args) throws Exception {
	  File file = new File("countrylist.txt");
	  BufferedReader br = new BufferedReader(new FileReader(file));
	  String line = "";
	  String[] split;
      List<String> arrayList = new ArrayList<String>();
	  List<String> linkedList = new LinkedList<String>();
      Set<String> hashSet = new HashSet<String>();
      Set<String> linkedHashSet = new LinkedHashSet<String>();
      Set<String> treeSet = new TreeSet<String>();
	  Map<String, String> hashMap = new HashMap<String, String>();
	  Map<String, String> linkedHashMap = new LinkedHashMap<String, String>();
	  Map<String, String> treeMap = new TreeMap<String, String>();
	  Map<String, String> weakHashMap = new WeakHashMap<String, String>();
	  int counter = 0;
	  while (null != (line = br.readLine())) {
		  split = line.split(";");
		  arrayList.add(split[0]);
		  linkedList.add(split[0]);
		  
		  hashSet.add(split[0]);
		  linkedHashSet.add(split[0]);
		  treeSet.add(split[0]);
		  
		  hashMap.put(split[0], split[1]);
		  linkedHashMap.put(split[0], split[1]);
		  treeMap.put(split[0], split[1]);
		  weakHashMap.put(split[0], split[1]);
		  counter += 1;
	  }
	  System.out.println(counter + " entries.");
	  benchmarkList(arrayList, args[0]);
	  benchmarkList(linkedList, args[0]);
	  benchmarkMap(hashMap, args[0]);
	  benchmarkMap(linkedHashMap, args[0]);
	  benchmarkMap(treeMap, args[0]);
	  benchmarkMap(weakHashMap, args[0]);
	  benchmarkSet(hashSet, args[0]);
	  benchmarkSet(linkedHashSet, args[0]);
	  benchmarkSet(treeSet, args[0]);
  }
  public static void benchmarkList(List<String> l, String criteria) {
	  System.out.print("List - ");
	  long start = System.nanoTime();	  
	  String found = String.valueOf(l.get(l.indexOf(criteria)));
	  long end = System.nanoTime();
	  System.out.println(l.getClass().getName() + " took " + (end - start) + " ns to find " + found);
  }
  public static void benchmarkSet(Set<String> s, String criteria) {
	  System.out.print("Set - ");
	  String str = "";
	  String found = "";
	  long start = System.nanoTime();
	  for (Iterator<String> itr = s.iterator(); itr.hasNext(); ) {
		  str = itr.next();
		  if (str.equals(criteria)) {
		  	found = str;
			break;
		  }	
	  }
	  long end = System.nanoTime();
	  System.out.println(s.getClass().getName() + " took " + (end - start) + " ns to find " + found);
  }
  public static void benchmarkMap(Map<String, String> m, String criteria) {
	  System.out.print("Map - ");
	  long start = System.nanoTime();
	  String found = m.get(criteria);
	  long end = System.nanoTime();
	  System.out.println(m.getClass().getName() + " took " + (end - start) + " ns to find " + found);
  }
}
