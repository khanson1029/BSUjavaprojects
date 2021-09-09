/** 
 * Class for testing the LLCache implementation of a doubly linked list cache
 * 
 * @author Kyle Hanso
 */

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class CacheTest{
    private static LLCache<String> cacheOne;
    private static LLCache<String> twoLayerCache;
    private static int cacheOneReferences = 0;
    private static int cacheOneHits = 0;
    private static int cacheTwoHits = 0;
    private static int cacheTwoReferences = 0;
    private static int totalCacheHits;
    private static double oneLevelCacheHR;
    private static double twoLevelCacheHR;
    private static double globalHR;

    private static void printUsage(){
        System.out.println("Invalid or missing args. \n" +
                            "Usage: java CacheTest <(1) level cache or (2) level cache> <First level cache size(an int)> <Second level cache size (if using a second level cache - an int)> <input textfile name>\n" +
                            "ex1.  java CacheTest <1> <10> <small.txt> \n" +
                            "ex2.  java CacheTest <2> <10> <20> <small.txt>");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {
        String file;
        long startTime = System.currentTimeMillis();


        if (args.length < 3){
            printUsage();
            System.exit(1);
        }

        if(args[0].equalsIgnoreCase("1")){
            cacheOne = new LLCache(Integer.parseInt(args[1]));
            file = args[2];
            Scanner lineScan = new Scanner(Paths.get(file));

            while(lineScan.hasNext()){
                Scanner wordScan = new Scanner(lineScan.nextLine());
                // wordScan.useDelimiter("\t ");
                while(wordScan.hasNext()){
                    String word = wordScan.next();
                    cacheOneReferences++;   
                    if(cacheOne.getObject(word) != null){
                        cacheOneHits++;
                        cacheOne.removeObject(word);
                        cacheOne.addObject(word);
                    }else{
                        cacheOne.addObject(word);
                    }
                }
                
            }
            oneLevelCacheHR = (double)cacheOneHits/cacheOneReferences;
            System.out.println("References: " + cacheOneReferences +"\n" + 
                               "Hits: " + cacheOneHits + "\n" + 
                               "Hit Ratio: " + oneLevelCacheHR);
            
        }
        if(args[0].equalsIgnoreCase("2")){
            twoLayerCache = new LLCache(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            file = args[3];
            Scanner lineScan = new Scanner(Paths.get(file));

            while(lineScan.hasNextLine()){
                Scanner wordScan = new Scanner(lineScan.nextLine());
                while(wordScan.hasNext()){
                    String word = wordScan.next();
                    // if(twoLayerCache.getObject(word) == null){
                    //     cacheOneReferences++;

                    //     if(twoLayerCache.getObjectTwo(word) == null){
                    //         cacheTwoReferences++;
                    //         twoLayerCache.addObject(word);
                    //         twoLayerCache.addObjectTwo(word);
                    //     }else{
                    //         cacheTwoReferences++;
                    //         cacheTwoHits++;
                    //         twoLayerCache.removeObjectTwo(word);
                    //         twoLayerCache.addObjectTwo(word);
                    //         twoLayerCache.addObject(word);
                    //     }
                    // }else{
                    //     cacheOneReferences++;
                    //     cacheOneHits++;
                    //     twoLayerCache.removeObject(word);
                    //     twoLayerCache.addObject(word);
                    //     twoLayerCache.removeObjectTwo(word);
                    //     twoLayerCache.addObjectTwo(word);
                    // }
                    cacheOneReferences++;
                    if(twoLayerCache.getObject(word) != null){
                        cacheOneHits++;
                        twoLayerCache.removeObject(word);
                        twoLayerCache.addObject(word);
                        twoLayerCache.removeObjectTwo(word);
                        twoLayerCache.addObjectTwo(word);
                    }else{
                        cacheTwoReferences++;
                        if(twoLayerCache.getObjectTwo(word) != null){
                                cacheTwoHits++;
                                twoLayerCache.removeObjectTwo(word);
                                twoLayerCache.addObjectTwo(word);
                                twoLayerCache.addObject(word); 
                        }else{
                            twoLayerCache.addObject(word); 
                            twoLayerCache.addObjectTwo(word);
                    }
                }

        }

            totalCacheHits = cacheOneHits + cacheTwoHits;
            oneLevelCacheHR = (double)cacheOneHits/cacheOneReferences;
            twoLevelCacheHR = (double)cacheTwoHits/cacheTwoReferences;
            globalHR = (double)totalCacheHits/(double)cacheOneReferences;
        }



                System.out.println("First Level cache with " + Integer.parseInt(args[1]) + " entries has been created\n" +
                                "Second level cache with " + Integer.parseInt(args[2]) + " entries has been created\n" +
                                "................................\n" +
                                "References: " + cacheOneReferences +"\n" + 
                                "Hits: " + totalCacheHits + "\n" + 
                                "\n" +
                                "Global hit ratio: " + globalHR + "\n" +
                                "\n" +
                                "Number of 1st-level cache references: " + cacheOneReferences + "\n" +
                                "Number of 1st-level cache hits: " + cacheOneHits + "\n" +
                                "1st-level cache hit ratio: " + oneLevelCacheHR + "\n" +
                                "\n" +
                                "Number of 2nd-level cache references: " + cacheTwoReferences + "\n" +
                                "Number of 2nd-level cache hits: " + cacheTwoHits + "\n" +
                                "2nd-level cache hit ratio: " + twoLevelCacheHR + "\n");
                                System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + " milliseconds.");
                                
            
        }

    }
}

