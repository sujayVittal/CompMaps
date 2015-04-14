package com.example.sujayvittal.compmaps;

/**
 * Created by sujayvittal on 18/02/15.
 * Data structure to store the directions
 */
public class TimeQueue {
    private TimeNode front, rear;
    public int size;

    /* Constructor */
    public TimeQueue()
    {
        front = null;
        rear = null;
        size = 0;
    }
    /*  Function to check if queue is empty */
    public boolean isEmpty()
    {
        return front == null;
    }
    /*  Function to get the size of the queue */
    public int getSize()
    {
        return size;
    }
    /*  Function to insert an element to the queue */
    public void insert(long data1)
    {
        TimeNode nptr = new TimeNode(data1, null);
        if (rear == null) {
            front = nptr;
        }
        else{
            rear.setNextNode(nptr);
            rear = rear.getNextNode();
        }

        size++ ;
        rear = nptr;
    }
    /*  Function to remove front element from the queue */
    public long remove()
    {
        long firstNode = 0;
        size--;
        if (!isEmpty())
        {
            firstNode =front.getData();
            front=front.getNextNode();

            if (front==null)
                rear=null;
        }

        return firstNode;
    }
    /*  Function to check the front element of the queue */
    public long getFront()
    {
        long firstNode = 0;
        if (!isEmpty())
            firstNode = front.getData();
        return firstNode;
    }
    /**
     * The getValues is a method used to return a String containing all the names of the items in the
     * queue
     * @return returns a String containing all the names of the items in the queue.
     */
    public String getValues()
    {
        TimeNode firstNode=front;
        String result="";

        while (firstNode!=null)
        {
            result=(String)result+" "+firstNode.getData();
            firstNode=firstNode.next;
        }
        result=result.substring(1,result.length());

        return result;
    }
    public void display()
    {
        TimeNode firstnode=front;

        while (firstnode!=null)
        {
            System.out.println(firstnode.getData());
            firstnode=firstnode.next;
        }

    }
}