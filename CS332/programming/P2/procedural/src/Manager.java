/**
 * CS 332 - Programming Assignment 2 (Procedural Version)
 * Date: 11/15/2019
 * Name: Aaron Van De Brook
 *
 * Implementation of a FSM following the rules of a procedural language as
 * closely as possible.
 *
 * Since main is a static function, there are 2 design choices for
 * implementing functions:
 *      1) Declare every function as static so that they can be called
 *         implicitly from main (bad practice, and can potentially lead to
 *         some weird bugs).
 *      2) Create an object of the Manager class in main and call the functions
 *         from that object (strays slightly from the procedural approach to
 *         the problem, but stays within best practices).
 *
 * The 2nd design choice was used for this assignment, mostly because I have
 * been taught to declare attributes and functions as static in very specific
 * situations (and this is not one of them). Also, declaring every attribute
 * and function as static is fairly annoying.
 *
 * The FSM is to be defined in a text file that will be passed to the program
 * through the first argument to the program (arg[0]).
 * The FSM shall have the following format:
 *      Line 1) States (comma seperated e.g 0, 1, 2, 3, 4, 5)
 *      Line 2) Initial State (e.g. 0)
 *      Line 3) Final State(s) (this can be 1 or more states e.g. 4)
 *      Line 4) Transition Table (this is a 2D array of size states.length x 2)
 *              It should have the following form (in the text file):
 *              1, 5
 *              2, 5
 *              ...
 *
 *              Here is how the table would normally look:
 *                      | 'a' | 'b' |
 *                      |-----|-----|
 *              State 0 |  1  |  5  |
 *              State 1 |  2  |  5  |
 *                ...   | ... | ... |
 *
 * Strings will also be defined in their own text file, similar to the FSM.
 * Strings shall be defined one per line, e.g.:
 *      Line 1) aabababaaa
 *      Line 2) aaabababaa
 *      ...
 *      And so on for as many strings as desired.
 *
 * To run this program:
 *      1) Compile the java file normally (`javac Manager.java`).
 *      2) The program accepts the inputs files as arguments, so the program
 *         can be run like so: `java Manager fsm.txt strings.txt`.
 *
 *         It is important that the fsm definition is passed to the program
 *         in the first argument and the strings are passed in the second.
 *
 * Important assumption: The alphabet of this language is assumed to be {a, b}.
 *                       Because of the way the getNextState function works,
 *                       anything that is not an 'a' is assumed to be a 'b'.
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Manager {

    // Attributes for FSM (assume and alphabet of {a, b})
    public String states[];
    private String initState;
    private String finalStates[];
    private String transistions[][];

    // Strings to test with FSM
    //public String strings[] = new String[20];
    public LinkedList<String> strings = new LinkedList<String>();

    public static void main(String[] args) {
        // Use an object of the class to avoid declaring all functions as static
        Manager main = new Manager();
        boolean result;
        try {
            main.parseFSM(args[0]);
            main.parseStrings(args[1]);

            System.out.println("State Machine Results:");
            // Loop through list of strings and determine whether they're in the language.
            for (String string : main.strings) {
                System.out.print(string + " : ");
                /**
                 * Kinda confusing one-liner.
                 * runStateMachine requires a character array and returns true
                 * to accept a string and false to reject a string.
                 * So, we just use a ternary operator to print accept or reject on the string.
                 */
                //System.out.println((main.runStateMachine(string.toCharArray()) == true) ? "accept" : "reject");

                result = main.runStateMachine(string.toCharArray());
                if (result) {
                    System.out.println("accept");
                } else {
                    System.out.println("reject");
                }
            }
            System.out.println();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Specifiy input files for the program like so: java Manager [fsm file] [string file]");
            System.err.println("e.g. java Manager fsm.txt strings.txt");
        }
    }

    /**
     * Reads an FSM definition from a file.
     *
     * @param fileName  Name of the file containing the FSM definition.
     */
    public void parseFSM(String fileName) {
        try {
            Scanner fileIn = new Scanner(new BufferedReader(new FileReader(fileName)));

            // Assumes different values are comma seperated.
            // Use trim and replaceAll to get rid of white space.
            states = fileIn.nextLine().trim().replaceAll(" ", "").split(",");
            initState = fileIn.nextLine();
            finalStates = fileIn.nextLine().trim().replaceAll(" ", "").split(",");
            transistions = new String[states.length][2];

            for (int i = 0; i < states.length; i++) {
                transistions[i] = fileIn.nextLine().trim().replaceAll(" ", "").split(",");
            }

            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Print states
            System.out.print("States: ");
            for (String state : states) {
                System.out.printf("%s, ", state);
            }
            // Print initial state
            System.out.println("\nInitial State: " + initState);
            // Print final states
            System.out.print("Final States: ");
            for (String state : finalStates) {
                System.out.printf("%s, ", state);
            }
            // Print transition table
            System.out.println("\nTransition Table:");
            for (int i = 0; i < states.length; i++) {
                System.out.printf("%s, %s\n", transistions[i][0], transistions[i][1]);
            }
            System.out.println();
        }
    }

    /**
     * Reads all strings in a file and stores then in a linked list.
     *
     * @param fileName  Name of the file containing strings to parse.
     */
    public void parseStrings(String fileName) {
        try {
            Scanner fileIn = new Scanner(new BufferedReader(new FileReader(fileName)));

            while (fileIn.hasNextLine()) {
                strings.add(fileIn.nextLine().trim());
            }

            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Print strings to verify
            System.out.println("Strings:");
            for (String string : strings) {
                System.out.println(string);
            }
            System.out.println();
        }
    }

    /**
     * Function that runs the state machine on an individual string to
     * determine whether the string is actually in the defined language.
     *
     * @param str   String to accept or reject.
     * @return      Whether to accept or reject the current string.
     */
    public boolean runStateMachine(char str[]) {
        boolean output = false;
        String currentState = initState;

        // Iterate through each symbol in the given string.
        for (char c : str) {
            currentState = getNextState(currentState, c);
        }

        // Check if the current state of the machine is in the set of final
        // states.
        for (String state : finalStates) {
            if (currentState.equals(state)) {
                output = true;
            }
        }

        return output;
    }

    /**
     * This is essentially the transition function of an FSM. The next state
     * is determined based on the current state of the FSM and next symbol
     * recieved.
     *
     * @param currentState  Current state of the state machine, aka state to
     *                      transition from.
     * @param nextChar      Next character recieved by the FSM.
     * @return              The state to transition to, based on definied
     *                      transition table.
     */
    public String getNextState(String currentState, char nextChar) {
        int stateIndex = 0;

        // Determine the index of the state
        // (because they're represented by strings)
        for (int i = 0; i < states.length; i++) {
            if (currentState.equals(states[i])) {
                stateIndex = i;
            }
        }

        // 0 = 'a', 1 = 'b'
        return transistions[stateIndex][(nextChar == 'a') ? 0 : 1];
    }
}