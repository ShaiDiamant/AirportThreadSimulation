
abstract public class Crew implements Runnable {
	
	protected String nameCrew;
	protected boolean stop;
	protected QueueManager qm;
	
	public Crew (String name, QueueManager qm) {
		this.nameCrew=name;
		this.qm = qm;
		this.stop=false;
	}
	public void stop() {
		this.stop = true;
	}
	public void run(){
		while(!stop){
			doWork();
		}
	}
	abstract public void doWork();
}
