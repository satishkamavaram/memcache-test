package com.satish.memcache_test.stats;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * 
 * @author satishkamavaram
 *
 */
public class MemCacheStats {

	AtomicInteger writes = new AtomicInteger();
	
	AtomicInteger readHits = new AtomicInteger();
	
	AtomicInteger readMisses = new AtomicInteger();

	public AtomicInteger getWrites() {
		return writes;
	}

	public void setWrites(AtomicInteger writes) {
		this.writes = writes;
	}
	
	public void incrementWrites()
	{
		this.writes.incrementAndGet();
	}

	public AtomicInteger getReadHits() {
		return readHits;
	}

	public void setReadHits(AtomicInteger readHits) {
		this.readHits = readHits;
	}
	
	public void incrementReadHits()
	{
		this.readHits.incrementAndGet();
	}

	public AtomicInteger getReadMisses() {
		return readMisses;
	}

	public void setReadMisses(AtomicInteger readMisses) {
		this.readMisses = readMisses;
	}
	
	public void incrementReadMisses()
	{
		this.readMisses.incrementAndGet();
	}
	
	@Override
	public String toString()
	{
		return this.writes+"  "+this.readHits+"  "+this.readMisses;
	}
}
