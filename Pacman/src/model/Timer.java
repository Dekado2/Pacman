package model;

import java.util.concurrent.TimeUnit;

public class Timer {
/**
 * start time - starts from 0
 */
	private long startTime;
	/**
	 * elapsed time so far
	 */
	private long elapsed;
	/**
	 * is the timer currently running
	 */
	private boolean running;
	
	/**
	 * Timer Constructor
	 * @param startTime
	 * @param elapsed
	 * @param running
	 */
	public Timer (long startTime, long elapsed, boolean running)
	{
		this.startTime=startTime;
		this.elapsed=elapsed;
		this.running=running;
	}
/**
 * start the timer
 */
	public void start() {
	    startTime = System.currentTimeMillis();
	    running = true;
    }
/**
 * stop the timer
 */
    public void stop() {
	    elapsed = elapsed + System.currentTimeMillis() - startTime; 
	    running = false;   
	}
   /**
    * get elapsed time in milliseconds 
    * @return
    */
    public long getElapsedInMillis() {
	    if (running) {
	        return elapsed + System.currentTimeMillis() - startTime;
	    } else {
	        return elapsed;
	      }
	}
/**
 * get elapsed time in hh:mm:ss format
 * @return
 */
    public String getElapsed() {
    	long temp;
	    if (running) 
	    	temp = elapsed + System.currentTimeMillis() - startTime;
	     else 
	    	temp = elapsed;
	    final long hr = TimeUnit.MILLISECONDS.toHours(temp);
        final long min = TimeUnit.MILLISECONDS.toMinutes(temp - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(temp - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
	}
    
 /**
  *  translate elapsed time from milliseconds to hh:mm:ss
  * @return
  */
 	public String formatInterval()
     {
         final long hr = TimeUnit.MILLISECONDS.toHours(elapsed);
         final long min = TimeUnit.MILLISECONDS.toMinutes(elapsed - TimeUnit.HOURS.toMillis(hr));
         final long sec = TimeUnit.MILLISECONDS.toSeconds(elapsed - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
         return String.format("%02d:%02d:%02d", hr, min, sec);
     }
 	
 	/**
 	 *  translate hour:minute:second format to millis
 	 * @param s
 	 * @return
 	 */
 	public long toMillis (String s) {
 		
 		long millis=0;
 		String[] time = s.split(":");
 		millis+=TimeUnit.HOURS.toMillis(Long.valueOf(time[0]));
 		millis+=TimeUnit.MINUTES.toMillis(Long.valueOf(time[1]));
 		millis+=TimeUnit.SECONDS.toMillis(Long.valueOf(time[2]));
 		return millis;
 	}

	public long getStartTime() {
		return startTime;
	}

	public boolean isRunning() {
		return running;
	}

	protected void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (elapsed ^ (elapsed >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timer other = (Timer) obj;
		if (elapsed != other.elapsed)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return formatInterval();
	}
 	
}
