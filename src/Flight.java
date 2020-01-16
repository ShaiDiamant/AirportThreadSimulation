
abstract public class Flight implements Runnable {

	protected String flightCode;//Flight code for this flight
	protected int numOfPassengers;//Number of passengers on plane
	protected int arrivalTime;//Delay of arrival\departure
	protected QueueManager qm;//QueueManager instance for access to queues
	protected Object latestTreater;//Latest treater marker for queue management
	protected int totalTime;//Total time in process(not including queue times)
	
	public Flight (String flightCode, int numOfPassengers, int arrivalTime, QueueManager qm) {//Basic builder method
		this.flightCode=flightCode;
		this.numOfPassengers=numOfPassengers;
		this.arrivalTime=arrivalTime*1000;
		this.qm = qm;
		this.latestTreater = null;
		this.totalTime=0;
	}

	abstract public FlightDetails getFlightDetails();//Each type of flight makes a different type of FlightDetails object
	
	public Object getLatestTreater() {//Latest treater getter
		return this.latestTreater;
	}
	public void setLatestTreater(Object o) {//Updates latest treater
		this.latestTreater = o;
	}
	
	public void increaseTime(int t) {//Increases this flight's time in airport by t seconds
		this.totalTime=this.totalTime+t;
	}

}
