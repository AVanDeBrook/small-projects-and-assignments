/**
 * Implementation of an queue for storing Evacuees
 */

public class EvacuationQueue {

	//@todo declare class variables here
	Evacuee[] queue;
	int count, head, tail;
	public final int size = 100;

	/**
	 * Constructor to initialize queue
	 */
	public EvacuationQueue() {
		//@todo initialize queue data structure.
		this.queue = new Evacuee[this.size];
		this.count = 0;
		this.head = this.tail = 0;
	}

	/**
	 * Implementation of enqueue
	 * @param evacuee - evacuee to enqueue
	 */
	public void enqueue(Evacuee evacuee) {
		//@todo implement enqueue method
		if (isFull()) { 
			System.out.println("dequeue() - empty queue");
		} else {
			queue[tail] = evacuee;
			tail = (tail + 1) % queue.length;
			count++;
		}
	}


	/**
	 * Implementation of dequeue
	 * @return dequeued evacuee
	 */
	public Evacuee dequeue() {
		//@todo implement dequeue method
		if (isEmpty()) {
			System.out.println("dequeue() - empty queue");
			return null;
		} else {
			Evacuee tmp = queue[head];
			head = (head + 1) % queue.length;
			count--;
			return tmp;
		}
	}

	/**
	 * Implementation of peek
	 * @return front of queue
	 */
	public Evacuee peek() {
		//@todo implement peek method
		if (isEmpty()) {
			System.out.println("peek() - empty queue");
			return null;
		} else {
			return queue[head];
		}
	}

	/**
	 * Implementation of isEmpty();
	 * @return true if full, false otherwise
	 */
	public boolean isEmpty() {
		//@todo implement isEmpty method
		return count == 0;
	}

	/**
	 * Implementation of isFull
	 * @return true if full, false otherwise
	 */
	public boolean isFull() {
		//@todo implement isEmpty method
		return count == queue.length;
	}
	
    public static void main(String [] args) {
        EvacuationQueue test = new EvacuationQueue();
        System.out.println("Created queue");
        System.out.println("isEmpty() returns " + test.isEmpty());
        System.out.println("isFull() returns " + test.isFull());
        System.out.println("peek() returns " + test.peek());
        test.enqueue(new Evacuee("Tester"));
        System.out.println("Enqueued new evacuee named Tester");
        System.out.println("isEmpty() returns " + test.isEmpty());
        System.out.println("isFull() returns " + test.isFull());
        System.out.println("peek() returns " + test.peek());
        test.dequeue();
        System.out.println("Dequeued");
        System.out.println("isEmpty() returns " + test.isEmpty());
        System.out.println("isFull() returns " + test.isFull());
        System.out.println("peek() returns " + test.peek());
     }
}
