
public class SecurityMan implements Runnable{
	
	private String rank;
	private int checkTime;
	private boolean stop;
	private BoundedQueue<Arrival> fuelingQ;
	private Queue<Arrival> securityQ;
	
	public SecurityMan (String rank, int checkTime) {
		this.rank=rank;
		this.checkTime=checkTime;
		this.stop=false;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		Arrival curr;
		while(!stop) {
			curr = securityQ.extract();
			suspiciousObjectCheck(curr);
			forwardPlane(curr);
		}
	}
	
	public void suspiciousObjectCheck(Arrival curr) {
		try {
			Thread.sleep(checkTime*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random()<=0.05) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		fuelingQ.insert(curr);
	}
	
	public void stop() {
		this.stop = true;
	}

}
