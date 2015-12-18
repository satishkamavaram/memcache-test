package com.satish.memcache_test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import net.rubyeye.xmemcached.MemcachedClient;

import com.satish.memcache_test.stats.MemeStatsRegistry;
/**
 * 
 * @author satishkamavaram
 *
 */
public class MemeCacheThread  implements Callable<Integer> {
	
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
		int totalcalls = 500;
		
		List<String> listOfkeys = new ArrayList<>();
		//System.out.println("adding key value to memcache");
		//adding keys
		
		//long writeStartTime = System.nanoTime();
				
		
		for (int i = 0; i < totalcalls; i++) {
			id = Thread.currentThread().getId();
			time = System.nanoTime();
			key  = String.valueOf(id+time);
		//System.out.println("...");
			this.memCache.add(key, 0, String.valueOf(key).concat(Thread.currentThread().getName()));
			MemeStatsRegistry.incrementWrite();
			listOfkeys.add(key);
		}
		//long totalTimeWrites = System.nanoTime() - writeStartTime;
		
		//System.out.println("getting key value from memcache");
		//Reading Keys
		//long readStartTime = System.nanoTime();
		for (int i = 0; i < totalcalls; i++) {
			String s = this.memCache.get(listOfkeys.get(i));
			if(s==null)
				MemeStatsRegistry.incrementReadMisses();
			else
				MemeStatsRegistry.incrementReadHit();
			//System.out.println(this.memCache.get(listOfkeys.get(i)));
		}
	//	long totalTimeReads = System.nanoTime() - readStartTime;
		
		/*System.out.println(LocalDate.now()+"  "+LocalTime.now()+"  "
				+totalcalls+"           "+TimeUnit.SECONDS.convert(totalTimeWrites, TimeUnit.NANOSECONDS)+
				"          "+totalcalls+"           "+TimeUnit.SECONDS.convert(totalTimeReads, TimeUnit.NANOSECONDS)
				+"         "+readHits+"       "+readMisses+"          "+Thread.currentThread().getName());
		*/
		return totalcalls*2;
	}
	
}
