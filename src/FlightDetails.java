
abstract public class FlightDetails {
	protected String flightCode;//Flight code for this flight
	protected int numOfPassengers; //Number of passengers on plane
	protected int timeInAirfield; //total time spent in system without queues
	
	public FlightDetails(String flightCode, int numOfPassengers, int timeInAirfield) {//Basic builder method
		this.flightCode = flightCode;
		this.numOfPassengers = numOfPassengers;
		this.timeInAirfield = timeInAirfield;
	}

	public String getFlightCode() {//Flight code getter
		return this.flightCode;
	}

	public int getNumOfPassengers() {//Number of passengers on flight getter
		return this.numOfPassengers;
	}

	public int getTimeInAirfield() {//Time this flight spend in airport getter
		return this.timeInAirfield;
	}
	

}
