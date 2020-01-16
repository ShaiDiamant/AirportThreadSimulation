
public class BoundedQueue<T> extends Queue<T> {

	private final int maxSize=8;//max size given in this assignment

	public BoundedQueue() {//basic builder method uses inherited builder
		super();
	}

	public synchronized boolean insert(T t) {//synchronized insert up to max size with wait
		while(queue.size() >= maxSize) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		boolean status = this.queue.add(t);
		notifyAll();
		return status;
	}
	
	public synchronized T extract() {//synchronized extract with wait
		while(queue.isEmpty()) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		T t = queue.get(0);
		queue.remove(t);
		notifyAll();
		return t;
	}

}
