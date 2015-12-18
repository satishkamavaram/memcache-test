package com.satish.memcache_test.stats;


/**
 * 
 * @author satishkamavaram
 *
 */
public abstract class AbstractCaptureStats implements CaptureStats {

   protected int sleepTime;
   
   protected int writes = 0;
   protected int readHits = 0;
   protected int readMisses = 0;
  
	
   protected MemCacheStats memCache = new MemCacheStats();

   public AbstractCaptureStats(int sleepTime)
   {
	   this.sleepTime = sleepTime;
   }
   
   protected  abstract void logStats();

	@Override
	public  void outputStats() {
	   logStats();
	}
	
	protected void setStatsToLocal(MemCacheStats totalStats)
	{
		this.memCache.setReadHits(totalStats.getReadHits());
		this.memCache.setReadMisses(totalStats.getReadMisses());
		this.memCache.setWrites(totalStats.getWrites());
	}
	
	protected int getWrites(MemCacheStats TotalStats)
	{
		return (TotalStats.getWrites().intValue() - memCache.getWrites().intValue());
	}
	
	protected void updateWrites()
	{
		memCache.getWrites().set(memCache.getWrites().get()+writes);
	}
	
	protected int getReadHits(MemCacheStats TotalStats)
	{
		return (TotalStats.getReadHits().intValue() - memCache.getReadHits().intValue());
	}
	
	protected void updateReadHits()
	{
		memCache.getReadHits().set(memCache.getReadHits().get()+readHits);
	}
	
	protected int getReadMisses(MemCacheStats TotalStats)
	{
		return (TotalStats.getReadMisses().intValue() - memCache.getReadMisses().intValue());
	}
	
	protected void updateReadMisses()
	{
		memCache.getReadMisses().set(memCache.getReadMisses().get()+readMisses);
	}
	
	protected void updateLocalCache()
	{
		updateWrites();
		updateReadHits();
		updateReadMisses();
	}
}
