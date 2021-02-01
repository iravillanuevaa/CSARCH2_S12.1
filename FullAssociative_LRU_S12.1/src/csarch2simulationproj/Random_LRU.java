/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csarch2simulationproj;

import java.util.*;

public class Random_LRU extends Random{
    
    Set<String> cache_list;
    int capacity;
    LinkedList<String> result;
    int hit;
    int miss;
    Random random;
    
    public Random_LRU(int capacity, Random random){
	this.cache_list = new LinkedHashSet<String>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<String>(); 
        this.random = random;
    }
    
     public boolean get(String key){
	if (!cache_list.contains(key)){
            miss++;
            return false;
        } else{
            cache_list.remove(key);
            cache_list.add(key);
            hit++;
            random.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            random.simulation.append("Cache: " + result + "\n");
            return true;
        }
    }
    
    public void put(String key){
        if (cache_list.size() == capacity) {
            String firstKey = cache_list.iterator().next();
            int index = result.indexOf(firstKey);
            
            random.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            random.simulation.append("Cache: " + result + "\n");
            result.remove(index);
            random.simulation.append("Cache: " + result + "\n");
            result.add(index, key);
            random.simulation.append("Cache: " + result + "\n");
            cache_list.remove(firstKey);
            cache_list.add(key);
                                
        } else{
            cache_list.add(key);
            result.add(key);
            random.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            random.simulation.append("Cache: " + result + "\n");
        }
    }
    
    public void refer(String key){	 
	if (get(key) == false){
            put(key);
        }
    }
    
    public LinkedList<String> result(){
        return result;
    }
}
