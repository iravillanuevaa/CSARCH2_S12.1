package backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {

    // input
    public boolean seqIsBlock;
    public String givenSeq; // raw input 
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
    public boolean mmIsBlock ;
    public int mmSize; // main memory size (blocks)
    public boolean cacheIsBlock;
    public int cacheSize; // cache memory size (blocks); number of blocks 

    public int blockSize ;   // (val) words/block
    public int blockPerSet;
    public int setSize ;     // # of sets
    public float cacheTime ; // cache time in ns     
    public float mmTime ;  // memory time in ns    
    public boolean isLT; // is it a load through

    //process

    public String[] tokens; 
    public int[] given;          // sequence numbers 
    public int[] mappedSet;           // sequence items is mapped on a certain set
    public String[][] cachehex;

    // output

    public int[][] cache; // cache memory
    public float AvgMMtime;
    public float TotalMMtime;
    public int nHit;
    public int nMiss;
    public float missPenalty;
    /*
     * Input: Block size, set size, MM memory size (either blocks or words), cache
     * memory size (either blocks or words), program flow to be simulated n and
     * other parameters deemed needed(example: can simulate cache problem set #
     * 4,5,6) set
     * 
     * Output: number of cache hits, number of cache miss, miss penalty, average
     * memory access time, total memory access time, snapshot of the cache memory.
     * With option to output result in text file.
     */

    /*
        The constructor
    */
    public Server(boolean seqIsBlock, String givenSeq, boolean mmIsBlock, int mmSize,
                  boolean cacheIsBlock, int cacheSize, int blockSize, int setSize, 
                  float cacheTime, float mmTime, boolean isLT){

        this.seqIsBlock = seqIsBlock;
        this.givenSeq = givenSeq;
        this.mmIsBlock = mmIsBlock;
        this.mmSize = mmSize;
        this.cacheIsBlock = cacheIsBlock;
        this.cacheSize = cacheSize;
        this.blockSize = blockSize;
        this.setSize = setSize;
        this.cacheTime = cacheTime;
        this.mmTime = mmTime;
        this.isLT = isLT;

        getOutput();
    };


    public void checkTokensAndSizes() {
        // checks the cache if it is by words, if it is, turn it into blocks
        if (!this.cacheIsBlock){
            this.cacheSize = this.cacheSize/this.blockSize;
        }
        if(!this.mmIsBlock){
            this.mmSize = this.mmSize/this.blockSize;
        }
        // process the loops
        String[] temp1 = this.givenSeq.split("\\s+");  

        int i = 0;
        int j;  

        int start1;
        int end1;

        int start2;
        int end2 = 0;
        int loop2;

        ArrayList<String> dump = new ArrayList<>();
        while (i != temp1.length){
            if (temp1[i].startsWith("L1")){  
                
                String[] parts = temp1[i].split("-");
                int x = Integer.parseInt(parts[1]);  

                start1= i; 
                end1= -1; 
                i++;
                j = 1; 
                while (j != x){
                    
                    if (temp1[i].startsWith("L2")){
                        
                        String[] parts2 = temp1[i].split("-");
                        int y = Integer.parseInt(parts2[1]);
                        start2 = i;
                        end2 = -1;
                        i++;
                        loop2 = 0; 

                        while(loop2 != y){
                            if (temp1[i].startsWith("/L2")){
                                end2= i -1;
                                i = start2;
                                 
                                 loop2 = 1; 
                            }
                            else if (i == end2){  
                                dump.add(temp1[i]) ;
                                 i = start2;
                                 
                                 loop2++;
                             }
                             else { 
                                dump.add(temp1[i]) ;
                             }
                             i++;
                        }
                        i = end2 + 2;
                    }

                    
                    if (temp1[i].startsWith("/L1")){
                        end1= i -1;
                       i = start1;
                   }
                    else if (i == end1){
                        dump.add(temp1[i]) ;
                        i = start1;
                         j++;
                    }
                    else {
                        dump.add(temp1[i]);
                    }
                    i++;
                }
                i = end1 + 2;
            }
            dump.add(temp1[i]);
            i++;
        }


        this.tokens = dump.toArray(new String[0]); 
        this.given = new int[this.tokens.length];

        if (this.seqIsBlock)
            for (i = 0; i < this.tokens.length; i++){
                this.given[i] = Integer.parseInt(this.tokens[i]);
        }

        // updates: given

    }

    public void setSetNumber() {
        int n =  this.given.length;
        this.mappedSet  = new int[n]; 
        if (this.seqIsBlock)
            for (int i  = 0; i < n; i++)
            this.mappedSet[i] = this.given[i] % this.setSize;

        else {
            int word = (int) Math.sqrt(this.blockSize);
            int set = (int) Math.sqrt(this.setSize);
            for (int i = 0; i < n; i++){
                String temp = new BigInteger(this.tokens[i], 16).toString(2); 
                String[] bin = temp.split("");

                bin = Arrays.copyOf(bin, bin.length - word);
                bin = Arrays.copyOfRange(bin, bin.length - set, bin.length);

                StringBuilder strBuilder = new StringBuilder();
                for (int j = 0; j < bin.length; j++) 
                    strBuilder.append(bin[j]);

                String setInBin = strBuilder.toString();

                this.mappedSet[i] = new BigInteger(setInBin, 2).intValue();;
            }
            
        }

    }

    public void MRU() {
        int n =  this.given.length;
        this.blockPerSet = this.cacheSize / this.setSize;
        int[] temp = new int[this.setSize]; // to track the most recent one for each set 
    
        boolean saved = false; 
        this.cache = new int[this.setSize][blockPerSet];
        this.cachehex = new String[this.setSize][blockPerSet];

        for (int i = 0; i < this.setSize; i++) {
            temp[i] = -1; 
            for (int  j = 0; j < blockPerSet; j++){
                this.cache[i][j] = -1;
                this.cachehex[i][j] = "none";
            }
        }

        // i is given 
        if(this.seqIsBlock)
            for (int i = 0; i < n; i++) {
                saved = false;
                int set = this.mappedSet[i];
                
                int recent = temp[set]; // most recent in the set


                for (int  j = 0; j < blockPerSet; j++)
                    if (this.cache[set][j] == this.given[i]){
                        this.nHit++;
                        saved = true;
                        temp[set] = j;
                    }
                
                if (!saved){
                    for (int  j = 0; j < blockPerSet; j++)
                        if (this.cache[set][j] == -1){
                            this.cache[set][j] = this.given[i];
                            this.nMiss++;
                            saved = true;
                            if (j == blockPerSet -1)
                                temp[set] = j;
                            break;
                        }
                }

                if (!saved){
                    this.cache[set][recent] = this.given[i];
                    this.nMiss++;
                    saved = true; 
                }
            }
        
        else {
            for (int i = 0; i < n; i++) {
                saved = false;
                int set = this.mappedSet[i];
                
                int recent = temp[set]; // most recent in the set
    
    
                for (int  j = 0; j < blockPerSet; j++)
                    if (this.cachehex[set][j].equals(this.tokens[i])){
                        this.nHit++;
                        saved = true;
                        temp[set] = j;
                    }
                
                if (!saved){
                    for (int  j = 0; j < blockPerSet; j++)
                        if (this.cachehex[set][j].equals("none")) {
                            this.cachehex[set][j] = this.tokens[i];
                            this.nMiss++;
                            saved = true;
                            if (j == blockPerSet -1)
                                temp[set] = j;
                            break;
                        }
                }
    
                if (!saved){
                    this.cachehex[set][recent] = this.tokens[i];
                    this.nMiss++;
                    saved = true; 
                }
            }
        }
        // updates: nhit, nmiss, cache 
    }

    public void missPenalty() {
        if (this.isLT){
            this.missPenalty = this.cacheTime + this.mmTime;
        }
        else{
            this.missPenalty = this.cacheTime + (this.blockSize * this.mmTime) + this.cacheTime;
        }
        // updates missPenalty
    }

    public void avgTime() {
        this.AvgMMtime = 0;
        // get hit rate and miss rate
        float hit_rate= (float)this.nHit/(float)this.given.length ;
        float miss_rate=(float)this.nMiss/(float)this.given.length ;

        this.AvgMMtime = (hit_rate*this.cacheTime) + (miss_rate*this.missPenalty);
        // updates AvgMMtime
    }

    public void totalTime() {
        this.TotalMMtime = 0;
        this.TotalMMtime += this.nHit*this.blockSize*this.cacheTime;
        if (this.isLT){
            this.TotalMMtime += this.nMiss*this.blockSize*this.mmTime;
        }
        else {
            this.TotalMMtime += this.nMiss*this.blockSize*(this.mmTime+this.cacheTime);
        }
        this.TotalMMtime += this.nMiss*cacheTime;
        // updates TotalMMtime
    }

    public void getOutput(){
        checkTokensAndSizes();
        setSetNumber();    
        missPenalty();
        MRU(); 
        avgTime();
        totalTime();

        // tester for hex input
        
        for (int i = 0; i < this.setSize; i++) {
            for (int  j = 0; j < this.blockPerSet; j++) {//the 2 here is the blocks per set
                if(this.seqIsBlock)
                    System.out.print(this.cache[i][j] + " ");
                else 
                    System.out.print(this.cachehex[i][j] + " ");

            }
            System.out.println();
        }
        
        System.out.println("hit: " + this.nHit);
        System.out.println("miss: " + this.nMiss);
        System.out.println("miss penalty: " + this.missPenalty);
        System.out.println("avg time : " + this.AvgMMtime);
        System.out.println("total time : " + this.TotalMMtime);
    }

    
    public static void main(String args[]) {
        Server myServer = new Server(true, "0 L1-10 1 L2-20 2 3 /L2 4 5 6 7 8 9 /L1 10 11", true, 8,
        false, 32, 4, 4, 1, 10, false);
        myServer.getOutput();
    }
}
