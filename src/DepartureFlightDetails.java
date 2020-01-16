
public class DepartureFlightDetails extends FlightDetails {
	private String dest;//Flight destination

	public DepartureFlightDetails(String flightCode, int numOfPassengers, int timeInAirfield, String destination) {//Basic builder method
		super(flightCode, numOfPassengers, timeInAirfield);
		this.dest = destination;
	}
	
	public String getDestination() {//Destination getter
		return this.dest;
	}

}
