/**
 * Implement of a stack for Evacuees representing a bus
 *
 */

public class Bus {

    int id;
    //@todo declare any class variables for the array-based stack implementation here.
    public int size, capacity;
    public Evacuee stack[];

    /**
     * Implement Default constructor
     * @param id - identifier for the bus (integer)
     * @param capacity - capacity of stack.
     */
    public Bus(int id, int capacity) {
        this.id = id;
        //@todo - initialize busStack
        this.stack = new Evacuee[capacity];
        this.capacity = capacity;
    }

    /**
     * Implement push
     * @param evac - evacuee pushed onto stack
     */
    public void push(Evacuee evac) {
        //@todo - implement array-based push method.
    	if (!isFull()) {
    		this.size++;
    		this.stack[size-1] = evac;
    	} else {
    		System.out.println("push() - full stack");
    	}
    }

    /**
     * Implement pop method
     * @return popped evacuee
     */
    public Evacuee pop() {
        //@todo - implement array-based pop method.
    	if (!isEmpty()) {
    		return this.stack[--size];
    	}
    	System.out.println("pop() - empty stack");
        return null;
    }

    /**
     * Implement top method
     * @return top evacuee
     */
    public Evacuee top() {
        //@todo - implement array-based top method
    	if (!isEmpty()) {
    		return this.stack[size-1];
    	} else {
	    	System.out.println("top() - empty stack");
	        return null;
    	}
    }

    /**
     * Implement isEmpty method
     * @return true if empty
     */
    public boolean isEmpty() {
        //@todo - implement array-based isEmpty method
    	return this.size == 0;
    }

    /**
     * Implement isFull method
     * @return true if full.
     */
    public boolean isFull() {
        //@todo - implement array-based isFull method
        return this.size == capacity;
    }
    
    public static void main(String [] args) {
       Bus test = new Bus(0,1);
       System.out.println("Created bus with capacity of 1");
       System.out.println("isEmpty() returns " + test.isEmpty());
       System.out.println("isFull() returns " + test.isFull());
       test.push(new Evacuee("Tester"));
       System.out.println("Pushed new evacuee named Tester");
       System.out.println("top() returns " + test.top());
       System.out.println("isEmpty() returns " + test.isEmpty());
       System.out.println("isFull() returns " + test.isFull());
       System.out.println("Popped Tester");
       System.out.println("pop() returns " + test.pop());
       System.out.println("isEmpty() returns " + test.isEmpty());
       System.out.println("isFull() returns " + test.isFull());
    }
    
}
