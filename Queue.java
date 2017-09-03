package logic;

public class Queue {

    private int head;
    private int tail;
    private final Node[] queue;

    public Queue() {
        head = -1;
        tail = 0;
        queue = new Node[50];
    }

    public Node[] getQueue() {
        return queue;
    }

    public void enqueue(Node item) {
        if (head != tail) {
            queue[tail] = item;
            if (head == -1) {
                head = tail;
            }
            if (tail == queue.length - 1) {
                tail = 0;
            } else {
                tail++;
            }
        }
    }

    public Node dequeue() {
        Node x = null;
        if (head != -1) {
            x = queue[head];
            if (head == queue.length - 1) {
                head = 0;
            } else {
                head++;
            }
            if (head == tail) {
                head = -1;
            }
        }
        return x;
    }

    public boolean isEmpty() {
        return head == -1;
    }
}
