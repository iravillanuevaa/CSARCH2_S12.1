/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csarch2simulationproj;

import java.util.*;

public class Sequential_LRU {
    
    Set<Integer> cache_list;
    int capacity;
    LinkedList<Integer> result;
    int hit;
    int miss;
    int i;
    int cache_word;
    int iterate = 0;
    int  result_iterator;
    Integer[] index = new Integer[100];
    Sequential sequential;
    
    public Sequential_LRU(int capacity, Sequential sequential) {
        this.cache_list = new LinkedHashSet<Integer>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<Integer>();
        this.sequential = sequential;
    }
    
    public boolean get(int key) {
        if (!cache_list.contains(key)) {
            miss++;
            return false;
        } else {
            cache_list.remove(key);
            cache_list.add(key);
            hit++;
            sequential.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            sequential.simulation.append("Cache: " + result + "\n");
            return true;
        }
    }
    
    public void put(int key) {
        
        if (cache_list.size() == capacity) {
            int firstKey = cache_list.iterator().next();
            int index = result.indexOf(firstKey);

            //sequential.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            //sequential.simulation.append("Cache: " + result + "\n");
            result.remove(index);
            //sequential.simulation.append("Cache: " + result + "\n");
            result.add(index, key);
            //sequential.simulation.append("Cache: " + result + "\n");
            cache_list.remove(firstKey);
            cache_list.add(key);

        } else {
            cache_list.add(key);
            result.add(key);
            //sequential.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            //sequential.simulation.append("Cache: " + result + "\n");
        }
    }
    
    public void refer(int key) {
        if (get(key) == false) {
            put(key);
        }
    }
    
}
