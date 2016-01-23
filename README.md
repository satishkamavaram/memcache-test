Memcached distributed caching framework load testing using JDK8.

MemCacheTest.java is a launcher class where following actions taskes place :
 1. Connecting to local memcached server.
 2. Lauching a thread for logging the stats to console 
 3. Creating ExecurtorService with fixed number of threads.
 4. Creating and submitting the tasks to ExecutorService for running.
 5. MemeCacheThread class is a worker task responsible for writing key/value to and reading key/value from memcached server by hardcoding total calls to memcached.
 6. MemeCacheReadThread class is a worker task responsible for reading key/value from memcached server.
 7. As and when write and read calls are compeleted , worker task updates the count to MemeStatsRegistry class.
 8. OutputStats class gets read/write concurrent call count from MemstatsRegistry and ouputs to console.  

Please refer below link for Memcached Benchmarking

http://satishkamavaram.blogspot.in/2015/12/memcached-benchmarking-statistics.html


