
abstract public class Flight implements Runnable {

	protected String flightCode;//herpa
	protected int numOfPassengers;
	protected int arrivalTime;
	protected Queue<Flight> flightsQ;
	private Object latestTreater;
	
	public Flight (String flightCode, int numOfPassengers, int arrivalTime, Queue<Flight> flightsQ) {
		this.flightCode=flightCode;
		this.numOfPassengers=numOfPassengers;
		this.arrivalTime=arrivalTime*1000;
		this.flightsQ=flightsQ;
		this.latestTreater = null;
	}
	
	public String getFlightCode() {
		return this.flightCode;
	}
	
	public int getNumOfPassengers() {
		return this.numOfPassengers;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	abstract public FlightDetails getFlightDetails();
	
	public Object getLatestTreater() {
		return this.latestTreater;
	}
	public void setLatestTreater(Object o) {
		this.latestTreater = o;
	}

}
