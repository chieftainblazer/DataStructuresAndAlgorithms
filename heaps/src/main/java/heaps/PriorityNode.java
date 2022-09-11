package heaps;

public class PriorityNode<T> {
    private T item;
    private double priority;

    public PriorityNode(T item, double priority) {
        this.item = item;
        this.priority = priority;
    }

    public T getItem() {
        return this.item;
    }

    public double getPriority() {
        return this.priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }
}
