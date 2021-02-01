import java.util.*;
import java.lang.Math;
public class LRUSequential {
    // Java program to implement LRU cache
// using LinkedHashSet



	Set<Integer> cache_list;
    int capacity;
    LinkedList<Integer> result;
    public static int hit;
    public static int miss;
    public static int i;
    public static int cache_word;
    public static int iterate = 0;
    public static int  result_iterator;
    public static Integer[] index = new Integer[100];

    public LRUSequential(int capacity) {
        this.cache_list = new LinkedHashSet<Integer>(capacity);
        this.capacity = capacity;
        this.result = new LinkedList<Integer>();
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
            return true;
        }
    }

    /* Refers key x with in the LRU cache */
    public void refer(int key) {
        if (get(key) == false) {
            put(key);
        }
    }

    // displays contents of cache in Reverse Order
    public void display() {

        Iterator<Integer> itr = result.iterator();

        while (itr.hasNext()){
            System.out.print(itr.next() + " ");
            index[iterate] = result.get(iterate);
            iterate++;
        }
    }

    public void put(int key) {
        
        if (cache_list.size() == capacity) {
            int firstKey = cache_list.iterator().next();
            int index = result.indexOf(firstKey);

            result.remove(index);
            result.add(index, key);

            cache_list.remove(firstKey);
            cache_list.add(key);

        } else {
            cache_list.add(key);
            result.add(key);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int block = 0;

        String[] data = new String[100];
        

        System.out.print("Until what value should the processor fetch (0-n)? ");
        int values = scan.nextInt();
        System.out.print("Number of iterations: ");
        int repeat = scan.nextInt();
        System.out.print("Cache: [1] block [2] word ");
        int cache_choice = scan.nextInt();
        if (cache_choice == 1) {
            System.out.print("Cache block? ");
            block = scan.nextInt();
        } else {
            System.out.print("Cache word? ");
            cache_word = scan.nextInt();
        }
        System.out.print("Block size(word/s)? ");
        int word = scan.nextInt();
        System.out.print("Cache access time (ns)? ");
        int cache_access = scan.nextInt();
        System.out.print("Memory access time (ns)? ");
        int memory_access = scan.nextInt();

        /* Convert cache word to block */
        int flag = 0;
        int temp = 0;
        int i = 1;
        /* while 2^i is not equal or greater than cache_word */
        if (cache_choice == 2) {
            while (flag == 0) {
                temp = (int) Math.pow(2, i);
                
                if (temp >= cache_word) {
                    flag = 1;
                }
                i++;
            }
            /* i = exponent of cache memory word */
            i--;
            //System.out.println("i: " + i);
            int x = 1;
            flag = 0;
            int temp2 = 0;
            while (flag == 0) {
                temp2 = (int) Math.pow(2, x);
                //System.out.println("Temp: " + temp2);
                if (temp2 >= word) {
                    flag = 1;
                }
                x++; /* x = exponent of block size */
            }
            x--;
            //System.out.println("x: " + x);
            /*
             * divide exponent of cache memory size (word) / exponent of block size (word)
             */
            int expo = i - x;
            //System.out.println("Expo: " + expo);
            /* block = 2^expo */
            block = (int) Math.pow(2, expo);
        }
        //int num_block = values / word;

        float var = (float) values / word;
        int num_block =  (int) Math.ceil(var);
        //System.out.println("Num block: " + num_block);

        int start = -1;
        for (int y = 0; y < num_block; y++) {
            start = start + 1; // 0, 5
            String temp1 = Integer.toString(start); // 0,5
            start = start + word - 1; // 4
            if(start > values){
                start = values;
            }
            String temp2 = Integer.toString(start); // 4
            data[y] = temp1 + "-" + temp2; // 0 - 4
        }



        // Initialize cache
        LRUSequential cache = new LRUSequential(block);

        // randomize values from user

        for (int x = 0 ; x < repeat ; x ++){
        for (i = 0; i < num_block; i++) {
            cache.refer(i);
         }
        }
        scan.close();

        float hit_rate = (float) hit / values;
        float miss_rate = (float) miss / values;

        // Miss penalty (non-load thru)
        int misspenalty = cache_access + word * memory_access + cache_access;

        // Average access time
        float average_access = ((hit_rate * cache_access) + (miss_rate * misspenalty));

        // total access time
        int total_acc = ((hit * word * cache_access) + ((cache_access + memory_access) * word * miss)
                + (miss * cache_access));

        // Print output
        System.out.println("\nResults");
        System.out.println("Hit rate: " + hit + "/" + num_block*repeat);
        System.out.println("Miss rate: " + miss + "/" + num_block*repeat);
        System.out.println("Miss Penalty: " + misspenalty);
        System.out.println("Average memory access time: " + average_access);
        System.out.println("Total memory access time: " + total_acc);
        System.out.print("Cache memory: ");

        cache.display();

        System.out.println("");
        for (int x = 0; x < block; x++) {
            System.out.println("Block value " + index[x] + ": " + data[index[x]]);
        }
        
	 }

}
