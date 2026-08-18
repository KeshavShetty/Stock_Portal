package org.stock.portal.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.stock.portal.domain.dto.OptionGreek;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;


public class RedisCache {

	private static final Logger log = LogManager.getLogger(RedisCache.class);
	
	private static final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
	private static final //Gson gson = new GsonBuilder().setLenient().create();
	Gson gson = new GsonBuilder()
	        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX") // Supports milliseconds and ISO timezones
	        .create();
	
	
	
	
	public static List<OptionGreek> getMatchingOptionGreek(String targetPattern) {
		// The specific pattern to match "OptionGreek:NIFTY*"
        Set<String> discoveredKeys = new HashSet<String>();

        // Step 1: Scan for the keys matching "Product:1*"
        try (Jedis jedis = jedisPool.getResource()) {
            String cursor = ScanParams.SCAN_POINTER_START; // Initialized to "0"
            ScanParams scanParams = new ScanParams().match(targetPattern).count(100);

            do {
                ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
                discoveredKeys.addAll(scanResult.getResult());
                cursor = scanResult.getCursor(); // Track the next block position
            } while (!cursor.equals(ScanParams.SCAN_POINTER_START));
        } catch (Exception e) {
            System.err.println("Scan error: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<OptionGreek>();
        }

        if (discoveredKeys.isEmpty()) {
            return new ArrayList<>();
        }

        // Step 2: Use a pipeline to fetch all data contents concurrently
        List<OptionGreek> matches = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            List<Response<String>> dataFutures = new ArrayList<>();

            for (String key : discoveredKeys) {
                dataFutures.add(pipeline.get(key));
            }
            pipeline.sync(); // Run all calls in one single network blast

            // Step 3: Map JSON string arrays directly to your Object definitions
            for (Response<String> futureJson : dataFutures) {
                String rawJson = futureJson.get();
                if (rawJson != null) {
                    matches.add(gson.fromJson(rawJson, OptionGreek.class));
                }
            }
        } catch (Exception e) {
            System.err.println("Pipeline error: " + e.getMessage());
        }

        return matches;
	}
	
	
	public static void putOptionGreek(String keyVal, OptionGreek value) {
		String cacheKey = "OptionGreek:" + keyVal;
		
		try (Jedis jedis = jedisPool.getResource()) {
			 String jsonToCache = gson.toJson(value);
             jedis.set(cacheKey, jsonToCache, redis.clients.jedis.params.SetParams.setParams().ex(10*60)); 
        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println("Redis error, putTradingSymbolExchangeCache: " + e.getMessage());
        }
	}
	
	
	
	public static void main(String[] args) {
		
		
    }
}
