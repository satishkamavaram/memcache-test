package com.satish.memcache_test.stats;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author satishkamavaram
 *
 */
public class MemeStatsRegistry {
	
	static MemCacheStats memCache = new MemCacheStats();
	
	public static void incrementReadHit() {
		
		memCache.incrementReadHits();
	}

	public static void incrementWrite() {
		//System.out.println("....");
		memCache.incrementWrites();
	}

	public static void incrementReadMisses() {
		
		memCache.incrementReadMisses();
	}

	
	public static MemCacheStats getMemCacheStats()
	{
		return memCache;
	}

	public static AtomicInteger getWrites()
	{
		return memCache.getWrites();
	}
	
	public static AtomicInteger getReadHits()
	{
		return memCache.getReadHits();
	}
	
	public static AtomicInteger getReadMisses()
	{
		return memCache.getReadMisses();
	}
}
