package csarch2simulationproj;
import java.util.*;
import java.lang.Math;

public class Sequential_LRU {
    // Java program to implement LRU cache
// using LinkedHashSet



	Set<Integer> cache_list;
    int capacity;
    LinkedList<Integer> result;
    public static int hit;
    public static int miss;
    
    //Sequential sequential
    /*
    public static int i;
    public static int cache_word;
    public static int iterate = 0;
    public static int  result_iterator;
    public static Integer[] index = new Integer[100];
*/
    public Sequential_LRU(int capacity, Sequential sequential) {
        this.cache_list = new LinkedHashSet<Integer>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<Integer>();
        this.sequential = sequential;
    }

    // This function returns false if key is not
    // present in cache. Else it moves the key to
    // front by first removing it and then adding
    // it, and returns true.
    public boolean get(int key) {
        if (!cache_list.contains(key)) {
            miss++;
            return false;
        } else {
            cache_list.remove(key);
            cache_list.add(key);
            hit++;
            //added
            sequential.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            sequential.simulation.append("Cache: " + result + "\n");
            return true;
        }
    }

    /* Refers key x with in the LRU cache */
    public void refer(int key) {
        if (get(key) == false) {
            put(key);
        }
    }
    /*
    // displays contents of cache in Reverse Order
    public void display() {

        Iterator<Integer> itr = result.iterator();

        while (itr.hasNext()){
            System.out.print(itr.next() + " ");
            index[iterate] = result.get(iterate);
            iterate++;
        }
    }
    */
    public void put(int key) {
        
        if (cache_list.size() == capacity) {
            int firstKey = cache_list.iterator().next();
            int index = result.indexOf(firstKey);

            sequential.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            sequential.simulation.append("Cache: " + result + "\n");
            //added above

            result.remove(index);

            //added
            sequential.simulation.append("Cache: " + result + "\n");

            result.add(index, key);

            //added
            sequential.simulation.append("Cache: " + result + "\n");

            cache_list.remove(firstKey);
            cache_list.add(key);

        } else {
            cache_list.add(key);
            result.add(key);
            //added
            sequential.simulation.append("\n" + "Value: " + key + " | Hit: " + hit + " | Miss: " + miss + "\n");
            sequential.simulation.append("Cache: " + result + "\n");
        }
    }

    

}
