
/**
 * UID: xxxxxx
 * Caltech CS Placement Exam
 * Problem 2: The Tower of Hanoi
 */
import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * Problem2 is a response to Problem 2: The Tower of Hanoi
 * 
 * @author UID xxxxxx
 */
public class Problem2 {
    public static void main(String[] args) {
        // inputWarning used to reject illegal user inputs
        String inputWarning = "\nArgument must be a single nonnegative integer.";
        // try-catch blocks to handle exceptions
        try {
            Problem2 problem2 = new Problem2();
            // convert String[] args to an integer and validate it
            int numDisks = problem2.prepareUserInput(args);
            // create object Hanoi to solve the puzzle for given number of disks
            problem2.new Hanoi(numDisks);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input(s): illegal command-line argument"
                    + inputWarning);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                    "Invalid input(s): one legal command-line argument required"
                            + inputWarning);
        } catch (EmptyStackException e) {
            System.out.println("Program terminated.");
        }
    }

    /**
     * Converts user input to an Integer and rejects by throwing an exception if
     * invalid ("-0" is understood as "0", and is therefore valid)
     * 
     * @param argumentStr
     *            the command-line arguments inputed by user
     * @return the command-line argument as a single integer
     */
    private int prepareUserInput(String[] argumentStr) {
        // convert index-0 argument to an integer
        int numDisks = Integer.parseInt(argumentStr[0]);
        // if argument is less than 0 or there are too many arguments, throw an
        // exception
        if (numDisks < 0 || argumentStr.length != 1)
            throw new IllegalArgumentException();
        return numDisks;
    }

    /**
     * The Hanoi Class uses multiple Stacks to replicate and display the Tower
     * of Hanoi algorithms
     */
    private class Hanoi {
        // stacks is an ArrayList that will consist of three Stack objects
        // stacks will represent which disks are on which pegs at any given time
        private ArrayList<Stack> stacks;
        // numDisks is user-inputed number of disks to solve for
        private int numDisks;

        /**
         * The Hanoi constructor uses numDisks to solve the Tower of Hanoi game
         * 
         * @param numD
         *            the number of disks to be represented in simulation
         */
        private Hanoi(int numD) {
            // get an ArrayList of three empty Stack objects
            stacks = getStacks();
            // instantiate numDisks
            numDisks = numD;
            // begin solving the Hanoi algorithm
            solve();
        }

        /**
         * Returns an ArrayList of three Stacks that will represent the
         * towers/pegs
         * 
         * @return an ArrayList<Stack> of three Stacks
         */
        private ArrayList<Stack> getStacks() {
            // instantiate an empty ArrayList<Stack>
            ArrayList<Stack> stcks = new ArrayList<Stack>();
            // add three empty Stack objects to stcks
            for (int i = 0; i < 3; i++) {
                stcks.add(new Stack());
            }
            return stcks;
        }

        /**
         * Prints the Towers of Hanoi in appropriately-formatted output
         * 
         * @param s
         *            the Towers/pegs to be displayed, represented in a Stack
         */
        private void display(ArrayList<Stack> s) {
            // prints out each Stack A, B, and C, followed by a blank new line
            System.out.println("A: " + s.get(0).asString());
            System.out.println("B: " + s.get(1).asString());
            System.out.println("C: " + s.get(2).asString() + "\n");
        }

        /**
         * Begins the process of recreating the Tower of Hanoi game with no
         * arguments
         */
        private void solve() {
            // add appropriate numDisks to stackA
            for (int i = numDisks; i > 0; i--) {
                stacks.get(0).push(i);
            }
            // print out the initial towers display (before solving)
            display(stacks);
            // helper method that begins recursion for solving
            // 0, 1, and 2 are the indices of towers
            // Tower A, "source" =0 ; Tower B, "destination" =1 ;
            // Tower C, "auxiliary" =2
            solveHelper(0, 1, 2, numDisks);
        }

        /**
         * Recursive algorithm to solve the Towers of Hanoi
         * 
         * @param src
         *            the index of the source peg
         * @param dest
         *            the index of the destination peg
         * @param aux
         *            the index of the auxiliary peg
         * @param diskNum
         *            the number of disks being solved for
         */
        private void solveHelper(int src, int dest, int aux, int diskNum) {
            // base case: the number of disks to move is equal to 1
            if (diskNum == 1) {
                // move top disk of source to destination
                move(src, dest);
                // display the updated state of ArrayList stacks after movement
                display(stacks);
            } else if (diskNum > 1) {
                // recursively move the top M-1 disks from peg A to peg C
                solveHelper(src, aux, dest, diskNum - 1);
                // move top disk of source to destination
                move(src, dest);
                // display the updated state of ArrayList stacks after movement
                display(stacks);
                // recursively move the top M-1 disks from peg C to peg B
                solveHelper(aux, dest, src, diskNum - 1);
            }
        }

        /**
         * Moves an integer from the src-indexed peg to the dest-indexed peg
         * Checks to confirm absence of illegal operations An
         * IllegalArgumentException is thrown if: -The supplied tower indices
         * are the same index -The disk to be moved is being duplicated -The
         * disk to be moved is placed on top of a lower-value disk
         * 
         * @param src
         *            the index of the source peg
         * @param dest
         *            the index of the destination peg
         * @throws IllegalArgumentException
         *             for illegal operations
         */
        private void move(int src, int dest) {
            // if the src tower and dest tower are the same tower or
            // invalid indices, reject
            if (src == dest || src > 2 || src < 0 || dest > 2 || dest < 0)
                throw new IllegalArgumentException();
            int diskMoving = stacks.get(src).pop();
            Stack destStack = stacks.get(dest);
            // if the moving disk is the wrong size or duplicated, reject
            if (!destStack.isEmpty() && destStack.peek() <= diskMoving)
                throw new IllegalArgumentException();
            // moves the top disk, diskMoving, FROM the source stack (src)
            // TO destStack, the destination stack (dest)
            destStack.push(diskMoving);
        }
    }

    /**
     * The Stack class represents integers in a stack data structure
     */
    private class Stack {
        // an ArrayList of Integers to represent a stack
        private ArrayList<Integer> stack;

        /**
         * Represents integers as a Stack data structure within an ArrayList
         */
        private Stack() {
            // instantiate the ArrayList of Integers called stack
            stack = new ArrayList<Integer>();
        }

        /**
         * Adds an integer to top of the Stack
         * 
         * @param i
         *            the integer to be added to the Stack
         */
        private void push(int i) {
            // add integer i to the top of the stack
            stack.add(i);
        }

        /**
         * Removes an integer from the top of the Stack
         * 
         * @return the integer at the top of the Stack
         * @throws EmptyStackException
         *             if the Stack is empty
         */
        private int pop() {
            // if there is nothing to pop, throw EmptyStackException
            if (stack.isEmpty())
                throw new EmptyStackException();
            // otherwise, remove and return the top of the stack
            return stack.remove(stack.size() - 1);
        }

        /**
         * Checks if the stack is empty (contains no values)
         * 
         * @return if the stack is empty
         */
        private boolean isEmpty() {
            // return whether or not there are values in the Stack object
            return stack.isEmpty();
        }

        /**
         * Returns the integer from the top of the Stack without removing
         * 
         * @return the integer from the top of the Stack
         * @throws EmptyStackException
         *             if the Stack is Empty
         */
        private int peek() {
            // if there is nothing to peek, throw EmptyStackException
            if (stack.isEmpty())
                throw new EmptyStackException();
            // otherwise, return (without removing) the top of the stack
            return stack.get(stack.size() - 1);
        }

        /**
         * Returns the Stack represented as a String with spaces between values
         * 
         * @return the Stack represented as a String
         */
        private String asString() {
            // instantiate an empty String object to be returned
            String str = "";
            // add all values of the ArrayList<Integer> stacks
            // with each value followed by " "
            for (int i : stack) {
                str += " " + i;
            }
            return str;
        }
    }
}
