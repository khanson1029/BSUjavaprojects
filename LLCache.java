import java.util.LinkedList;

public class LLCache<T>{
    private LinkedList<T> newCache;
    private LinkedList<T> secondCache;
    private int cacheSize;
    private int secondLevelSize;

    public LLCache(int size){
        this.newCache = new LinkedList<>();
        this.cacheSize = size;

    }
    
    public LLCache(int firstLevelSize, int secondLevelSize){
        this.newCache = new LinkedList<>();
        this.secondCache = new LinkedList<>();
        this.cacheSize = firstLevelSize;
        this.secondLevelSize = secondLevelSize;
    }

    public T getObject(T objectToGet){
        if(newCache.indexOf(objectToGet) == -1){
            return null;
        }else{
        return newCache.get(newCache.indexOf(objectToGet));
        }
    }

    public void addObject(T newObject){
    if(newCache.size() >= cacheSize){
        newCache.removeLast();
    }
    newCache.addFirst(newObject);
    }
    
    public void removeObject(T oldObject){
        newCache.remove(oldObject);
    }

    public void clearCache(){
        newCache.clear();
    }

    public T getObjectTwo(T objectToGet){
        if(secondCache.indexOf(objectToGet) == -1){
            return null;
        }else{
        return secondCache.get(secondCache.indexOf(objectToGet));
        }
    }

    public void addObjectTwo(T newObject){
       if(secondCache.size() >= secondLevelSize){
           secondCache.removeLast();
       }
       secondCache.addFirst(newObject);
    }
    
    public void removeObjectTwo(T oldObject){
        secondCache.remove(oldObject);
    }

    public void clearCacheTwo(){
        secondCache.clear();
    }

    public String toString(){
        return ("First Level cache with " + cacheSize + " entries has been created\n" +
        "Second level cache with " + secondLevelSize + " entries has been created\n" +
        "................................\n" +
        "References: "  +"\n" + 
        "Hits: "  + "\n" + 
        "\n" +
        "Global hit ratio: "  + "\n" +
        "\n" +
        "Number of 1st-level cache references: "  + "\n" +
        "Number of 1st-level cache hits: " + "\n" +
        "1st-level cache hit ratio: "  + "\n" +
        "\n" +
        "Number of 2nd-level cache references: "  + "\n" +
        "Number of 2nd-level cache hits: "  + "\n" +
        "2nd-level cache hit ratio: "  + "\n");

    }
}