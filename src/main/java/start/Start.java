package start;

import service.CacheService;
import service.CacheServiceImpl;

public class Start {

    public static void main(String... str) {
        final CacheService<Integer, String> cacheService = new CacheServiceImpl<>();
        cacheService.put(1,"fuck");
        cacheService.put(2,"fuck2");
        cacheService.put(3,"fuck3");
        cacheService.put(2,"fuck4");
    }
}
