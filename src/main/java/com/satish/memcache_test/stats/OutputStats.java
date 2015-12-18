package com.satish.memcache_test.stats;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * @author satishkamavaram
 *
 */
public class OutputStats extends AbstractCaptureStats implements CaptureStats
{
	public OutputStats(int sleepTime)
	{
		 super(sleepTime);
		 System.out.println("Date        Time          TotalWrites  TotalReads  ReadHits  ReadMisses");
	}
	
	public void logStats()
	{
		while(true){
			MemCacheStats totalStats = MemeStatsRegistry.getMemCacheStats();
			//System.out.println("total::"+totalStats.toString());
		//	System.out.println("local::"+memCache.toString());
		    writes = getWrites(totalStats);
		    readHits = getReadHits(totalStats);
		    readMisses = getReadMisses(totalStats);
			int reads = readHits+readMisses;
			System.out.println(LocalDate.now()+"  "+LocalTime.now()+"  "
					+writes+String.format("%"+ getSpaces(13,writes) +"s", " ")+
					reads
					+String.format("%"+ getSpaces(12,reads) +"s", " ")+
					readHits+String.format("%"+ getSpaces(10,reads) +"s", " ")+readMisses);
			updateLocalCache();
		//	System.out.println(memCache.toString());
			try {
				Thread.sleep(sleepTime*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void run() {
		logStats();
		
	}
	

	
	private static int getSpaces(int length, int value)
	{
		if(value==0)
			return length - 1;
		int l =0;
		for (l=0;value>0;++l)
			value/=10;
		return length - l;
	}
	
	
}
