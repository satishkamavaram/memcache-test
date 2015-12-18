package com.satish.memcache_test;

import java.util.concurrent.Callable;

import net.rubyeye.xmemcached.MemcachedClient;

import com.satish.memcache_test.stats.MemeStatsRegistry;
/**
 * 
 * @author satishkamavaram
 *
 */
public class MemeCacheReadThread  implements Callable<Integer> {
	
	MemcachedClient memCache;
	
	MemeCacheReadThread(MemcachedClient memCache)
	{
		this.memCache = memCache;
	}

	public Integer call() throws Exception {
		long id = 0;
		long time = 0;
		String key  = null;
		int totalcalls = 200;
		for (int i = 0; i < totalcalls; i++) {
			id = Thread.currentThread().getId();
			time = System.nanoTime();
			key  = String.valueOf(id+time);
			String s = this.memCache.get(key);
			if(s==null)
				MemeStatsRegistry.incrementReadMisses();
			else
				MemeStatsRegistry.incrementReadHit();
		}
		return totalcalls*2;
	}
	
}
