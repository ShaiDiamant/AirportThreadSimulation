import java.util.*;
public class FuelCrew extends Crew {

	private int maxCapacity;
	private Queue<Arrival> technicalQ;//TODO builder?
	private BoundedQueue<Arrival> fuelingQ;//TODO builder?
	private Queue<FlightDetails> managementQ;

	public FuelCrew(String name, int maxCapacity, Queue<FlightDetails> managementQ) {
		super(name);
		this.maxCapacity=maxCapacity;
		this.managementQ = managementQ;
		Thread t= new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		Arrival curr;
		while(!stop) {
			curr = fuelingQ.extract();
			fuelTest(curr);
			forwardPlane(curr);
		}
	}

	public void fuelTest(Arrival curr) {
		if(maxCapacity>=1000) {
			Random rand = new Random();
			int fuelTime = rand.nextInt(2)+3;
			try {
				Thread.sleep(fuelTime*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			maxCapacity=maxCapacity-1000;
		}
		else {
			fuelingQ.insert(curr);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		if(Math.random()<=0.35) {
			technicalQ.insert(curr);
		}
		else {
			FlightDetails f = curr.getFlightDetails();
			this.managementQ.insert(f);
		}
	}
}
