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
    Simpleton simpleton;
    
    public Simpleton_LRU(int capacity, Simpleton simpleton){
	this.cache_list = new LinkedHashSet<String>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<String>(); 
        this.simpleton = simpleton;
        
    }
    
     public boolean get(String key){
	if (!cache_list.contains(key)){
            miss++;
            return false;
        } else{
            cache_list.remove(key);
            cache_list.add(key);
            hit++;
            simpleton.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            simpleton.simulation.append("Cache: " + result + "\n");
            return true;
        }
    }
    
    public void put(String key){
        if (cache_list.size() == capacity) {
            String firstKey = cache_list.iterator().next();
            int index = result.indexOf(firstKey);

            simpleton.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            simpleton.simulation.append("Cache: " + result + "\n");
            result.remove(index);
            simpleton.simulation.append("Cache: " + result + "\n");
            result.add(index, key);
            simpleton.simulation.append("Cache: " + result + "\n");
            cache_list.remove(firstKey);
            cache_list.add(key);
                                
        } else{
            cache_list.add(key);
            result.add(key);
            simpleton.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            simpleton.simulation.append("Cache: " + result + "\n");
        }
    }
    
    public void refer(String key){	 
	if (get(key) == false){
            put(key);
        }
    }
}
