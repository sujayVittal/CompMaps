package com.example.sujayvittal.compmaps;

import java.util.NoSuchElementException;

/**
 * Created by sujayvittal on 18/02/15.
 * Data structure to store the directions
 */
public class LinkedQueue {
    protected Node front, rear;
    public int size;

    /* Constructor */
    public LinkedQueue()
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
    public void insert(int data1, int data2)
    {
        Node nptr = new Node(data1, data2, null);
        if (rear == null)
        {
            front = nptr;
            rear = nptr;
        }
        else
        {
            rear.setLink(nptr);
            rear = rear.getLink();
        }
        size++ ;
    }
    /*  Function to remove front element from the queue */
    public int[] remove()
    {
        if (isEmpty() )
            throw new NoSuchElementException("Underflow Exception");
        Node ptr = front;
        front = ptr.getLink();
        if (front == null)
            rear = null;
        size-- ;
        return ptr.getData();
    }
    /*  Function to check the front element of the queue */
    public int[] peek()
    {
        if (isEmpty() )
            throw new NoSuchElementException("Underflow Exception");
        return front.getData();
    }
    /*  Function to display the status of the queue */
    public String display()
    {
        int[] data= new int[2];
        
        String result=null;
        if (size == 0)
        {
            System.out.print("Empty\n");
            return null;
        }
        Node ptr = front;
        while (ptr != rear.getLink() )
        {

            data = ptr.getData();
           // int a[] = new int[2];
            result = ("Go "+data[0]+" for "+data[1]+" seconds!");
            //System.out.println(result);
            ptr = ptr.getLink();
            return result;

           // System.out.print(ptr.getData() + " ");

        }

        System.out.println();
        return result;
    }
}
