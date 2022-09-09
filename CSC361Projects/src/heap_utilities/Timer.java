package heap_utilities;
public class Timer
{
    protected long _currentStartTime;
    protected long _runTime;

    public Timer()
	{
		_currentStartTime = -1;
	}

	/**
	 * Resets all components of the timer: 'zeroes' it out.
	 */
	public void reset()
	{
		_currentStartTime = -1;
	}

	/**
	 * Start the time by saving the current time in milliseconds
	 */
	public void start()
	{
		_currentStartTime = System.currentTimeMillis();
	}
	
	/**
     * Stops the clock; returns the interval.
	 * @return the length of the current interval of time between now and the start
	 */
	public long stop()
	{
		// Accumulate the time.
		_runTime = getCurrentInterval();
		
		// Reset the start time to a negative as an error condition
		_currentStartTime = -1;
		
		return _runTime;
	}

	/**
	 * @return the length of the current interval of time between now and the start
	 */
	protected long getCurrentInterval()
	{
		return System.currentTimeMillis() - _currentStartTime;
	}
	
	/**
	 * Returns the elapsed time in a meaningful format (e.g., x : y : z : w ) 
	 */
	public String toString()
	{
		long duration = _runTime;
		
		long millis = duration % 1000;
		long second = (duration / 1000) % 60;
		long minute = (duration / (1000 * 60)) % 60;
		long hour = (duration / (1000 * 60 * 60)) % 24;

		return String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
	}
}
