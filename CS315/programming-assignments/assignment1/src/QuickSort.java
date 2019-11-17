/**
 * CS 315:      Programming Assignment #1
 * File:        QuickSort.java
 * Author:      Aaron Van De Brook
 * Description: This class implements the quick sort algorithm as seen in
 *              Dr. Stansbury's lecture notes and module 3 worksheet.
 *              This class implements the sort method from the SortAlgo class.
 */
public class QuickSort extends SortAlgo
{
    public QuickSort()
    {
        setName("Quick Sort");
    }

    /**
     * Wrapper method for quicksort to implement the abstract method from the
     * SortAlgo class.
     *
     * This also makes the sorting algorithm easier to call from main.
     */
    public void sort(int arr[])
    {
        quicksort(arr, 0, arr.length);
    }

    /**
     * The quicksort algorithm.
     *
     * This algorithm relies heavily on the partition algorithm (defined after
     * this method). It uses the partition algorithm to sort the array(s) and
     * find a boundry to recurse on.
     *
     * As you can probably tell by the 2 recursive calls at the end of this
     * method, it is a "divide and conquer" algorithm.
     */
    private void quicksort(int arr[], int p0, int p1)
    {
        if (p1 - 1 <= p0) {
            return;
        }
        int boundary = partition(arr, p0, p1);
        quicksort(arr, p0, boundary);
        quicksort(arr, boundary + 1, p1);
    }

    /**
     * The partition algorithm.
     *
     * This is a rather complicated algorithm. Doing a few examples on paper
     * will give a far better understanding than any explanation I can give.
     *
     * Basically, it moves the small and large elements of the array to the
     * front and back halves  of the array, respectively, and returns a
     * boundary for the quicksort algorithm to recurse on (this gives way to
     * the "divide and conquer" part of the algorithm).
     */
    private int partition(int arr[], int p0, int p1)
    {
        int pivot = arr[p0];
        int left = p0 + 1;
        int right = p1 - 1;
        while (true) {
            while ((left < right) && (arr[right] >= pivot)) {
                right--;
            }
            while ((left < right) && (arr[left] < pivot)) {
                left++;
            }
            if (left == right) {
                break;
            }
            swap(arr, left, right);
        }
        if (arr[left] >= pivot) {
            return p0;
        }
        arr[p0] = arr[left];
        arr[left] = pivot;
        return left;
    }

    /**
     * Simple swap method to help with code readability.
     */
    private void swap(int arr[], int x, int y)
    {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}