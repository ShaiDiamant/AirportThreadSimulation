
abstract public class FlightDetails {
	protected String flightCode;
	protected int numOfPassangers;
	protected int timeInAirfield;
	
	public FlightDetails(String flightCode, int numOfPassengers, int timeInAirfield) {
		this.flightCode = flightCode;
		this.numOfPassangers = numOfPassengers;
		this.timeInAirfield = timeInAirfield;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public int getNumOfPassangers() {
		return numOfPassangers;
	}

	public int getTimeInAirfield() {
		return timeInAirfield;
	}
	

}
