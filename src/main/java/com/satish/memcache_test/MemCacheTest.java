package com.satish.memcache_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.satish.memcache_test.stats.CaptureStats;
import com.satish.memcache_test.stats.OutputStats;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

/**
 * 
 * @author satishkamavaram
 *
 */
public class MemCacheTest 
{
    public static void main( String[] args ) throws Exception
    {
       XMemcachedClientBuilder xmemcache = new XMemcachedClientBuilder("localhost:11211");
       MemcachedClient memCache = xmemcache.build();
       memCache.flushAll();
       
       CaptureStats captureStats = new OutputStats(1);
       new Thread(captureStats).start();
       
       List<Integer> listOfExecutors = Arrays.asList(500);
       //Approach in JDK7 and below
   /*    ExecutorService es = null;
       Future<Integer> future = null;
       List<Future> listOfFutures = new ArrayList<Future>();*/
      /* for(Integer thread : threads)
       {
    	   es = Executors.newFixedThreadPool(thread);
    	   System.out.println(es);
    	   future = es.submit(new MemeCacheThread(memCache));
    	   listOfFutures.add(future);
       }*/
       
     //  System.out.println("Date        Time          TotalWrites   WriteTime  TotalReads    ReadTime  ReadHits  ReadMisses ThreadName");
       
       //JDK8 - Functional way writing code.
       //Geting list of all executors based on list of executors to be created with different thread count (In realtime , we will have only executor service)
      List<ExecutorService> listOfexecutorServices =  listOfExecutors.stream().map(MemCacheTest::getFixedThreads).collect(Collectors.toList());
      BiFunction<ExecutorService, MemcachedClient, Future<Integer>> bi = (ExecutorService executorService ,MemcachedClient memClient ) ->  {
    	  return executorService.submit(new MemeCacheThread(memClient));};
      //Creating and Submitting tasks.
      List<Map<ExecutorService,List<Future<Integer>>>> listOfallExecutorsAndItsFutures = listOfexecutorServices.stream()
    		  .map((ExecutorService es1) -> MemCacheTest.getFixedThreads(es1, memCache))
    		 .collect(Collectors.toList());
      //Getting Async future results.
      long count = listOfallExecutorsAndItsFutures.stream().map(MemCacheTest::getFutures).flatMap(l ->l.stream()).mapToInt(MemCacheTest::getFutureResponse).sum();
     //listOfFutures.stream().mapToInt( App::getFutureResponse).count();
   //  System.out.println("Total Number of Read and Write opertions to MemCache :::"+count);
	
    }
    
    private static Integer getFutureResponse(Future<Integer> future) 
    {
    	try {
			return  future.get();
		} catch (InterruptedException | ExecutionException e) {
			//Converting checked exception to unchecked exceptions as functional calls in streams are not easy to handle checked exceptions.
			throw new UncheckedException(e);
		}
    }
    
    private static ExecutorService  getFixedThreads(Integer i)
    {
    	//System.out.println("i::::"+i);
    	return  Executors.newFixedThreadPool(i);
    }
    
    private static Map<ExecutorService,List<Future<Integer>>>  getFixedThreads(ExecutorService es,MemcachedClient memCache )
    {
    	//System.out.println("submitting tasks.....");
    	Map<ExecutorService,List<Future<Integer>>> listOffutures = new HashMap<>();
    	List<Future<Integer>> list = new ArrayList<>();
    	ThreadPoolExecutor tpe = (ThreadPoolExecutor)es;
    //	System.out.println("tpe"+tpe.getCorePoolSize());
    	for(int i = 0;i<tpe.getCorePoolSize();i++){
    	 	//System.out.println("submitting task");
    	list.add(es.submit(new MemeCacheThread(memCache)));
    	}
    	listOffutures.put(es, list);
    	return listOffutures;
    }
    
    private static List<Future<Integer>> getFutures(Map<ExecutorService,List<Future<Integer>>> map)
    {
    	for(ExecutorService  es : map.keySet())
    	{
    		return map.get(es);
    	}
		return null;
    }
}
