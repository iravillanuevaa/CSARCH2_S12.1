import java.util.*;

public class LRURandom {
    // Java program to implement LRU cache
// using LinkedHashSet



	Set<Integer> cache_list;
    int capacity;
    LinkedList<Integer> result;
    public static int hit;
    public static int miss;

	public LRURandom(int capacity){
		this.cache_list = new LinkedHashSet<Integer>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<Integer>(); 
    }
    

	// This function returns false if key is not
	// present in cache. Else it moves the key to
	// front by first removing it and then adding
	// it, and returns true.
	public boolean get(int key){
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
	public void refer(int key){	 
		if (get(key) == false){
            put(key);
        }
	}

	// displays contents of cache in Reverse Order
	public void display(){
        
        Iterator<Integer> itr = result.iterator(); 
       

        while (itr.hasNext())
            System.out.print(itr.next() + " ");
	}
	
	public void put(int key){
        if (cache_list.size() == capacity) {
                int firstKey = cache_list.iterator().next();
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
        LRURandom cache = new LRURandom(block);
        
        //get values from user
    

        Random rand = new Random(); 
       
        System.out.println("Value generated: ");
        for (int i = 0 ; i < values; i++){
            int rand_int = rand.nextInt(100); 
            System.out.print(rand_int + " ");
            cache.refer(rand_int);
        }
        System.out.println("");
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
