/**
 * CS 315:      Programming Assignment #1
 * File:        Manager.java
 * Author:      Aaron Van De Brook
 * Description: This file is responsible for generating the array data
 *              and running the sorting algorithms on the generated data.
 */

public class Manager
{
    // Constants for the small and large array sizes for ease of testing.
    private static final int smallDataSize = 1000;
    private static final int largeDataSize = 50000;

    // Control whether arrays are printed or not, because arrays of 10,000+
    // elements can take a while to print.
    private static final boolean print = true;

    // 3 sets of array data to show the average, best, and worst case times.
    private static int randomData[];
    private static int ascendingData[];
    private static int descendingData[];

    public static void main(String args[])
    {
        Manager manager = new Manager();
        SortAlgo sortAlgs[] = {new InsertionSort(), new QuickSort()};

        for (int i = 0; i < sortAlgs.length; i++) {
            // Generate small array data
            manager.generateData(smallDataSize);
            System.out.println("Small Data:");

            if (print) {
                System.out.println("\nUnsorted Data:");
                manager.printArrays();
            }

            // Run sorting algorithm tests for small data
            manager.runTests(sortAlgs[i]);

            if (print) {
                System.out.println("\nSorted Data:");
                manager.printArrays();
            }

            // Generate large array data
            manager.generateData(largeDataSize);
            System.out.println("\nLarge Data:");

            if (print) {
                System.out.println("\nUnsorted Data:");
                manager.printArrays();
            }

            // Run sorting algorithm tests for large data
            manager.runTests(sortAlgs[i]);

            if (print) {
                System.out.println("\nSorted Data:");
                manager.printArrays();
            }

            System.out.println();
        }
    }

    /**
     * Simple helper method that generates data in random, ascending,
     * and descending order based on a size passed to the method.
     */
    public void generateData(int size)
    {
        randomData = new int[size];
        ascendingData = new int[size];
        descendingData = new int[size];

        for (int i = 0; i < size; i++) {
            randomData[i] = (int) (Math.random() * 50);
            ascendingData[i] = i;
            descendingData[i] = (size - 1) - i;
        }
    }

    /**
     * Another simple helper function that will time and run whatever sorthing
     * algorithm is passed to the method.
     *
     * This is a good example of how and why I used an abstract class to reduce
     * code size and increase readability.
     */
    public void runTests(SortAlgo alg)
    {
        long startTime, endTime;

        startTime = System.nanoTime();
        alg.sort(randomData);
        endTime = System.nanoTime();
        System.out.printf("%s: time to sort data in random order: %d ns (%d ms)\n", alg.getName(), (endTime - startTime), (endTime - startTime)/1000000);

        startTime = System.nanoTime();
        alg.sort(ascendingData);
        endTime = System.nanoTime();
        System.out.printf("%s: time to sort data in ascending order: %d ns (%d ms)\n", alg.getName(), (endTime - startTime), (endTime - startTime)/1000000);

        startTime = System.nanoTime();
        alg.sort(descendingData);
        endTime = System.nanoTime();
        System.out.printf("%s: time to sort data in descending order: %d ns (%d ms)\n", alg.getName(), (endTime - startTime), (endTime - startTime)/1000000);
    }

    /**
     * Method to print the contents of the 3 data arrays in an
     * organized manner.
     */
    public void printArrays()
    {
        int i;
        System.out.print("- Random Data:\n[");
        for (i = 0; i < randomData.length - 1; i++) {
            if (i != 0 && i % 10 == 0) {
                System.out.printf("\n ");
            }
            System.out.printf("%d,\t", randomData[i]);
        }
        System.out.printf("%d]\n\n", randomData[i]);

        System.out.print("- Ascending Data:\n[");
        for (i = 0; i < ascendingData.length - 1; i++) {
            if (i != 0 && i % 10 == 0) {
                System.out.printf("\n ");
            }
            System.out.printf("%d,\t", ascendingData[i]);
        }
        System.out.printf("%d]\n\n", ascendingData[i]);

        System.out.print("- Descending Data:\n[");
        for (i = 0; i < descendingData.length - 1; i++) {
            if (i != 0 && i % 10 == 0) {
                System.out.printf("\n ");
            }
            System.out.printf("%d,\t", descendingData[i]);
        }
        System.out.printf("%d]\n\n\n", descendingData[i]);
    }
}