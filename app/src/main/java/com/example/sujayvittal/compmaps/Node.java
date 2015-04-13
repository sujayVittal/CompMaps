package com.example.sujayvittal.compmaps;
/**
 * Created by sujayvittal on 18/02/15.
 */
class Node
{
    public int data;
    public Node next;

    /**
     * Constructor which creates a node based on the information given by the parameter dataPortion
     * @param dataPortion represents the data which is going to be contained by the node.
     */
    public Node(int dataPortion)
    {
        data=dataPortion;
        next=null;
    }

    /**
     * Constructor which creates a node in front of nextNode using the information contained in dataPortion
     * @param dataPortion represents the information which will be contained by the node
     * @param nextNode represents the node in front of which we will create a new one
     */
    public Node(int dataPortion, Node nextNode)
    {
        data=dataPortion;
        next=nextNode;
    }

    /**
     * The getData method is used to return the data information contained in a certain node.
     * @return returns the information contained by the node.
     */
    public int getData()
    {
        return data;
    }

    /**
     * The setData method is used to change or set the data of the node.
     * @param newData
     */
    public void setData(int newData)
    {
        data=newData;
    }

    /**
     * The getNextNode method is used to return the reference to the next node.
     * @return returns the reference to the next node.
     */
    public Node getNextNode()
    {
        return next;
    }

    /**
     * The setNextNode is used to set a new node after the current one.
     * @param nextNode represents the node that is going to be placed after the current one.
     */
    public void setNextNode(Node nextNode)
    {
        next=nextNode;
    }

}//end node class

/*class Node
{
    protected int data1;
    protected int data2;
    protected Node link;

    /*  Constructor
    public Node()
    {
        link = null;
        data1 = 0;
        data2 = 0;
    }
    /*  Constructor
    public Node(int d1, Node n)
    {
        data1 = d1;

        link = n;
    }
    /*  Function to set link to next Node
    public void setLink(Node n)
    {
        link = n;
    }
    /*  Function to set data to current Node
    public void setData(int d1)
    {
        data1 = d1;
    }
    /*  Function to get link to next node
    public Node getLink()
    {
        return link;
    }
    /*  Function to get data from current Node
    public int getData()
    {
        return data1;
    }
    }*/
