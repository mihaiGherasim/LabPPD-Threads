public class MyQueue {
    private String[] array;
    private int front;
    private int rear;
    private int capacity;
    private int count;

    MyQueue(int size){
        array = new String[size];
        capacity = size;
        rear = -1;
        count = 0;
    }

    public void pushToQueue(String monomial){
        if(isFull()){
            return;
        }
        rear = (rear+1) % capacity;
        array[rear] = monomial;
        count++;
    }

    public String popFromQueue(){
        if(isEmpty()){
            return null;
        }
        String res = array[front];
        front = (front+1)%capacity;
        count--;
        return res;
    }

    public String peek(){
        if (isEmpty()){
            return null;
        }
        return array[front];
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return count;
    }

    public boolean isFull(){
        return size() == capacity;
    }
}
