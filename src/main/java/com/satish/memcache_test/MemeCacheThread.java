package com.satish.memcache_test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import net.rubyeye.xmemcached.MemcachedClient;
/**
 * 
 * @author satishkamavaram
 *
 */
public class MemeCacheThread implements Callable<Integer> {
	
	MemcachedClient memCache;
	
	MemeCacheThread(MemcachedClient memCache)
	{
		this.memCache = memCache;
	}

	public Integer call() throws Exception {
		//System.out.println("........");
		long id = 0;
		long time = 0;
		String key  = null;
		int totalcalls = 100;
		
		List<String> listOfkeys = new ArrayList<>();
		//System.out.println("adding key value to memcache");
		//adding keys
		for (int i = 0; i < totalcalls; i++) {
			id = Thread.currentThread().getId();
			time = System.nanoTime();
			key  = String.valueOf(id+time);
		
			this.memCache.add(key, 0, String.valueOf(key).concat(Thread.currentThread().getName()));
			listOfkeys.add(key);
		}
		//System.out.println("getting key value from memcache");
		//Reading Keys
		for (int i = 0; i < totalcalls; i++) {
			this.memCache.get(listOfkeys.get(i));
			//System.out.println(this.memCache.get(listOfkeys.get(i)));
		}
		return totalcalls*2;
	}

	

}
