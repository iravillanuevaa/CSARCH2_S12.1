import java.util.*;
public class LRUSequential_num_times {
// Java program to implement LRU cache
// using LinkedHashSet



	Set<Integer> cache_list;
    int capacity;
    LinkedList<Integer> result;
    public static int hit;
    public static int miss;
    public static int i;

	public LRUSequential_num_times(int capacity){
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
        


//User Inputs
//remember we have to be able to pass numbers 4 5 6 of ps

        //MM memory Size in blks or wrds
        System.out.print("Will the Main Memory's size be in words (0) or blocks (1): ");
        int mmcheck = scan.nextInt();
        System.out.print("Main Memory Size? ");
        int values = scan.nextInt();

        //cache size blk or wrds
        System.out.print("Will the Cache Memory's size be in words (0) or blocks (1): ");
        int ccheck = scan.nextInt();
        System.out.print("Cache Size? ");
        int block = scan.nextInt();

        //Block Size
        System.out.print("Block size(word/s)? ");
        int word = scan.nextInt();

        System.out.print("Cache access time (ns)? ");
        int cache_access = scan.nextInt();

        System.out.print("Memory access time (ns)? ");
        int memory_access = scan.nextInt();

        System.out.print("Will the Main Memory Sequence  be in hex (0) or blocks (1): ");
        int seqcheck = scan.nextInt();
        System.out.print("Main Memory Sequence ? ex:200 204 ... 2F4 or 12 4E ... CA:");
        String memory_seq = scan.nextLine();

        System.out.print("Number of times to go through sequence? ");
        int times = scan.nextInt();
        scan.close();
        //Initialize cache
         LRUSequential_num_times cache = new  LRUSequential_num_times(block);
   
//edit here?
//FOR NOW ONLY BLOCKS ARE ACCEPTED
        String data_val;
        int seqnum;
        int tmpkey;
        data_val="";
        seqnum=0;
        for (i = 0 ; i < memory_seq.length(); i++){
            if(memory_seq[i]!=32)
            {
                data_val.concat(memory_seq[i]);
            }
            else if(memory_seq[i]==32){
                tmpkey=Integer.parseInt(data_val);
                cache.refer(tmpkey);
                data_val="";
                seqnum++;
            }
        }
        tmpkey=Integer.parseInt(data_val);
        cache.refer(tmpkey);
        data_val="";
        seqnum++;
//read data sequence where rand_int is the data blk variable
        for (i = 0 ; i < (seqnum*times-1); i++){
            for (j = 0 ; j < memory_seq.length(); j++){
                if(memory_seq[i]!=32)
                {
                    data_val.concat(memory_seq[i]);
                }
                else if(memory_seq[i]==32){
                    tmpkey=Integer.parseInt(data_val);
                    cache.refer(tmpkey);
                    data_val="";
                }
            }
            data_val="";
            tmpkey=Integer.parseInt(data_val);
            cache.refer(tmpkey);
            data_val="";
        }
/*
    200 = 100000 00 00 = 0
    204 = 100000 01 00 = 1
    208 = 100000 10 00 = 2
    20C = 100000 11 00 = 3
    2F4 = 101111 01 00 = 1
    2F0 = 101111 00 00 = 0
    200 = 100000 00 00 = 0
    204 = 100000 01 00 = 1
    218 = 100001 10 00 = 2
    21C = 100001 11 00 = 3
    24C = 100100 11 00 = 3
    2F4 = 101111 01 00 = 1
*/ 

        float hit_rate = (float) hit/values;
        float miss_rate = (float) miss/values;
        
        //Miss penalty (non-load thru)
        int misspenalty_nlt = cache_access + word*memory_access + cache_access;

        //Miss penalty (load thru)
        int misspenalty_lt = cache_access + memory_access;

        //Average access time
        float average_access = ((hit_rate*cache_access) + (miss_rate*misspenalty));

        //Total access time
        int total_acc = ((hit*word*cache_access) + ((cache_access+memory_access)*word*miss) + (miss*cache_access));

        //Print output
        System.out.println("Hit rate: " + hit + "/" + values);
        System.out.println("Miss rate: " + miss + "/" + values);
        System.out.println("Miss Penalty (load through): " + misspenalty_lt);
        System.out.println("Miss Penalty (non-load through): " + misspenalty_nlt);
        System.out.println("Average memory access time: " + average_access);
        System.out.println("Total memory access time: " + total_acc);
        System.out.print("Cache memory: ");
//display cache or memory contents	
        cache.display();
//WE MUST BE ABLE TO OUTPUT AS TXT FILE THE CACHE CONTENTS
	}

}
