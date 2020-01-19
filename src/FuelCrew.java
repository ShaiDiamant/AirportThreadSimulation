import java.util.*;
public class FuelCrew extends Crew {

	private int maxCapacity;//Max fuel capacity in truck
	private int numOfFuels;//Number of fueling actions performed
	private int currCapacity;//Current fuel status

	public FuelCrew(String name, int maxCapacity, Airport ap) {//Basic builder method
		super(name, ap);
		this.maxCapacity=maxCapacity;
		this.currCapacity = this.maxCapacity;
		this.numOfFuels=0;
		Thread t= new Thread(this);
		t.start();
	}

	@Override
	public void doWork() {//will fuel a plane and forward it to next queue. if extracts null from queue, day ended
		Arrival curr;
		curr = qm.fuelingQ.extract();
		if(curr == null) {
			this.dayEnd=true;
			return;
		}
		fuelTest(curr);
		forwardPlane(curr);
	}

	public void fuelTest(Arrival curr) {//will check to see fuel amount and refuel if needed, fuel the plane and count
		if(currCapacity>=1000) {
			Random rand = new Random();
			int fuelTime = rand.nextInt(2)+3;
			this.currCapacity=this.currCapacity-1000;
			this.numOfFuels++;
			try {
				Thread.sleep(fuelTime*1000);
				curr.increaseTime(fuelTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			qm.fuelingQ.insert(curr);
			try {
				Thread.sleep(5000);
				this.currCapacity = this.maxCapacity;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void forwardPlane(Arrival curr) {//35% to have technical issue and send to tech q, else to management q
		curr.setLatestTreater(this);
		if(Math.random()<=0.35) {
			qm.technicalQ.insert(curr);
		}
		else {
			FlightDetails f = curr.getFlightDetails();
			qm.managementQ.insert(f);
		}
	}

	public int getNumOfFuels(){//Num of fuels getter
		return this.numOfFuels;
	}
}
