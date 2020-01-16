
public class LogisticsCrew extends Crew {

	private int cargoCapacity;
	private QueueManager qm;
	private int numOfCargoTrucks;

	public LogisticsCrew(String name, int cargoCapacity, QueueManager qm) {
		super(name,qm);
		this.cargoCapacity=cargoCapacity;
		this.qm = qm;
		this.numOfCargoTrucks=0;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void doWork() {
		Arrival curr;
		curr = qm.logisticsQ.extract();
		if(curr == null) {
			this.dayEnd=true;
			return;
		}
		capacityCheck(curr);
		forwardPlane(curr);
	}

	private void capacityCheck(Arrival curr) {
		if(cargoCapacity >= curr.getNumOfBags()){
			int timeWait= curr.getNumOfBags();
			try {
				Thread.sleep(timeWait*100);
				curr.increaseTime(timeWait/10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			int timeWait= curr.getNumOfBags();
			try {
				Thread.sleep(2000);
				curr.increaseTime(2);
				this.numOfCargoTrucks++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(timeWait*100);
				curr.increaseTime(timeWait/10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		if(Math.random()<=0.1) {
			qm.technicalQ.insert(curr);
		}
		else {
			qm.securityQ.insert(curr);
		}
	}

	public int getNumOfCargoTrucks(){
		return this.numOfCargoTrucks;
	}
}
