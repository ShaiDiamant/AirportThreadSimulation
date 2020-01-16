import java.util.Vector;

public class ArrivalFlightDetails extends FlightDetails {
	private int cargo;
	private int cost;
	private int amountOfFuel;
	private boolean securityIssue;
	private Vector<FuelCrew> fuelCrews = new Vector<FuelCrew>();

	public ArrivalFlightDetails(String flightCode, int numOfPassengers, int timeInAirfield,
			int cargo, int cost, boolean securityIssue, Vector<FuelCrew> fuelCrews) {
		super(flightCode, numOfPassengers, timeInAirfield);
		this.cargo = cargo;
		this.cost = cost;
		this.securityIssue = securityIssue;
		this.amountOfFuel=0;
		this.fuelCrews=fuelCrews;
	}
	public int getCost() {
		return this.cost; 
	}
	
	public boolean getSecurityIssue() {
		return this.securityIssue;
	}
	
	public int getCargo() {
		return this.cargo;
	}
	
	public int getamountOfFuel() {
		for(int i=0; i<fuelCrews.size(); i++) {
			FuelCrew curr = fuelCrews.get(i);
			this.amountOfFuel = this.amountOfFuel + curr.getNumOfFuels()*1000;
		}
		return this.amountOfFuel;
	}

}
