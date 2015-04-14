package com.example.sujayvittal.compmaps;
/**
 * Created by sujayvittal on 18/02/15.
 */
class TimeNode
{
    public long data;
    public TimeNode next;

    /**
     * Constructor which creates a node based on the information given by the parameter dataPortion
     * @param dataPortion represents the data which is going to be contained by the node.
     */
    public TimeNode(long dataPortion)
    {
        data=dataPortion;
        next=null;
    }

    /**
     * Constructor which creates a node in front of nextNode using the information contained in dataPortion
     * @param dataPortion represents the information which will be contained by the node
     * @param nextNode represents the node in front of which we will create a new one
     */
    public TimeNode(long dataPortion, TimeNode nextNode)
    {
        data=dataPortion;
        next=nextNode;
    }

    /**
     * The getData method is used to return the data information contained in a certain node.
     * @return returns the information contained by the node.
     */
    public long getData()
    {
        return data;
    }

    /**
     * The setData method is used to change or set the data of the node.
     * @param newData
     */
    public void setData(long newData)
    {
        data=newData;
    }

    /**
     * The getNextNode method is used to return the reference to the next node.
     * @return returns the reference to the next node.
     */
    public TimeNode getNextNode()
    {
        return next;
    }

    /**
     * The setNextNode is used to set a new node after the current one.
     * @param nextNode represents the node that is going to be placed after the current one.
     */
    public void setNextNode(TimeNode nextNode)
    {
        next=nextNode;
    }

}//end node class