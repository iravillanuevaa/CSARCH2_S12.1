/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csarch2simulationproj;

import java.util.*;

public class Simpleton_LRU {
    
    Set<String> cache_list;
    int capacity;
    LinkedList<String> result;
    int hit;
    int miss;
    
    public Simpleton_LRU(int capacity){
	this.cache_list = new LinkedHashSet<String>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<String>(); 
    }
    
     public boolean get(String key){
	if (!cache_list.contains(key)){
            miss++;
            return false;
        } else{
            cache_list.remove(key);
            cache_list.add(key);
            hit++;
            return true;
        }
    }
    
    public void put(String key){
        if (cache_list.size() == capacity) {
            String firstKey = cache_list.iterator().next();
            int index = result.indexOf(firstKey);

            result.remove(index);
            result.add(index, key);
            cache_list.remove(firstKey);
            cache_list.add(key);
                                
        } else{
            cache_list.add(key);
            result.add(key);
        }
    }
    
    public void refer(String key){	 
	if (get(key) == false){
            put(key);
        }
    }
}
