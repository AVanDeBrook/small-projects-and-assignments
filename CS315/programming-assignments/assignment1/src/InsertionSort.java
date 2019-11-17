/**
 * CS 315:      Programming Assignment #1
 * File:        InsertionSort.java
 * Author:      Aaron Van De Brook
 * Description: This class implements the insertion sort algorithm as seen in
 *              Dr. Stansbury's lecture notes and module 3 worksheet.
 *              This class also implements the sort method from the SortAlgo
 *              class.
 */
public class InsertionSort extends SortAlgo
{
    /**
     * Constructor to set the name of the algorithm for ease of use printing
     * the times of algorithm execution later.
     */
    public InsertionSort()
    {
        setName("Insertion Sort");
    }

    /**
     * The implementation of the insertion sort algorithm.
     *
     * A relatively simple algorithm, essentially all it does is move down
     * the array and shift elements into their sorted position.
     */
    public void sort(int arr[])
    {
        int temp, i, j;
        for (i = 1; i < arr.length; i++) {
            temp = arr[i];

            // Shift element into its sorted position.
            for (j = i; j > 0 && (temp < arr[j - 1]); j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = temp;
        }
    }
}