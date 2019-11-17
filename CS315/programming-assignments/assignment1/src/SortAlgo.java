/**
 * CS 315:      Programming Assignment #1
 * File:        SortAlgo.java
 * Author:      Aaron Van De Brook
 * Description: This is an abstract class to help reduce the overall code size
 *              in the Manager class and increase the readability of the
 *              codebase as a whole.
 */
public abstract class SortAlgo
{
    // Stores the name of the algorithm to make it easier to print results
    // later.
    private String name;

    /**
     * An abstract sort function to be implemented by the sorting algorithms
     * later (QuickSort, InsertionSort).
     * @param arr Array of data to be sorted.
     */
    public abstract void sort(int arr[]);

    /******** Accessor Methods ********/

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}