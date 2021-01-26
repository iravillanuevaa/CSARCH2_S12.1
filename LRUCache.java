// Java program to implement LRU cache
// using LinkedHashSet
import java.util.*;

class LRUCache {

	Set<String> cache_list;
    int capacity;
    LinkedList<String> result;
    public static int hit;
    public static int miss;

	public LRUCache(int capacity){
		this.cache_list = new LinkedHashSet<String>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<String>(); 
	}

	// This function returns false if key is not
	// present in cache. Else it moves the key to
	// front by first removing it and then adding
	// it, and returns true.
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

	/* Refers key x with in the LRU cache */
	public void refer(String key){	 
		if (get(key) == false){
            put(key);
        }
	}

	// displays contents of cache in Reverse Order
	public void display(){
        //LinkedList<Integer> list = new LinkedList<>(cache);
        
        // The descendingIterator() method of java.util.LinkedList
        // class is used to return an iterator over the elements
        // in this LinkedList in reverse sequential order
        Iterator<String> itr = result.iterator(); 
        //System.out.println(result);

        while (itr.hasNext())
            System.out.print(itr.next() + " ");
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
	
	public static void main(String[] args)
	{
        Scanner scan = new Scanner(System.in);
        

        System.out.print("How many values would the memory contain? ");
        int values = scan.nextInt();
        System.out.print("Cache block? ");
        int block = scan.nextInt();
        System.out.print("Block size(word/s)? ");
        int word = scan.nextInt();
        System.out.print("Cache access time (ns)? ");
        int cache_access = scan.nextInt();
        System.out.print("Memory access time (ns)? ");
        int memory_access = scan.nextInt();

        //Initialize cache
        LRUCache cache = new LRUCache(block);
        
        //get values from user
        String data = scan.nextLine();
        for (int i = 0 ; i < values; i++){
            System.out.print("Enter value: ");
            data = scan.nextLine();
            cache.refer(data);
        }
        scan.close();

        float hit_rate = (float) hit/values;
        float miss_rate = (float) miss/values;
        
        //Miss penalty (non-load thru)
        int misspenalty = cache_access + word*memory_access + cache_access;

        //Average access time
        float average_access = ((hit_rate*cache_access) + (miss_rate*misspenalty));

        //total access time
        int total_acc = ((hit*word*cache_access) + ((cache_access+memory_access)*word*miss) + (miss*cache_access));

        //Print output
        System.out.println("Hit rate: " + hit + "/" + values);
        System.out.println("Miss rate: " + miss + "/" + values);
        System.out.println("Miss Penalty: " + misspenalty);
        System.out.println("Average memory access time: " + average_access);
        System.out.println("Total memory access time: " + total_acc);
        System.out.print("Cache memory: ");
		
		cache.display();
	}
}
