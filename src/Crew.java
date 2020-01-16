
abstract public class Crew implements Runnable {
	
	protected String nameCrew; //Unique name for the crew
	protected QueueManager qm;//QueueManager instance for access to all queues
	protected boolean dayEnd;//Has the day ended

	public Crew (String name, QueueManager qm) {//Basic builder method
		this.nameCrew=name;
		this.dayEnd=false;
		this.qm = qm;
	}

	public void run(){//Will run until dayEnd will become true
		while(!dayEnd){
			doWork();
		}
		System.out.println("Thread for crew "+this.nameCrew+" ended");
	}

	abstract public void doWork();//Each crew and it's own doWork method
}
