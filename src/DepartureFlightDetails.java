
public class DepartureFlightDetails extends FlightDetails {
	private String dest;

	public DepartureFlightDetails(String flightCode, int numOfPassengers, int timeInAirfield, String destination) {
		super(flightCode, numOfPassengers, timeInAirfield);
		this.dest = destination;
	}
	
	public String getDestination() {
		return this.dest;
	}

}
