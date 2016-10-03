
/**
 * UID: xxxxxx
 * Caltech CS Placement Exam
 * Problem 1: Implementing a One-Dimensional Cellular Automaton
 */
import java.util.Random;

/**
 * Problem1 is a response to Problem 1: Implementing a One-Dimensional Cellular
 * Automaton
 * 
 * @author UID xxxxxx
 */
public class Problem1 {
    /**
     * Collects user input via the command-line and creates subsequent
     * generations in accordance with update rules
     * 
     * @param args
     *            the command-line arguments
     */
    public static void main(String[] args) {
        // inputWarning used to reject illegal user inputs
        String inputWarning = "\nThe first two arguments must be nonnegative integers.\n"
                + "The following four arguments must each be 0 or 1.";
        // try-catch blocks to handle exceptions
        try {
            Problem1 problem1 = new Problem1();
            // convert String[] args to int[] arguments
            int[] arguments = problem1.convertUserInput(args);
            // further confirm validity of user input
            problem1.validateUserInput(arguments);
            // create an array of the the last four values in int[] arguments
            int[] updateRules = problem1.getUpdateRulesArray(arguments);
            // begin the array creation & generation spawning
            problem1.start(arguments[0], arguments[1], updateRules);
        } catch (IllegalArgumentException e) {
            System.out
                    .println("Invalid input(s): illegal command-line arguments"
                            + inputWarning);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                    "Invalid input(s): six legal command-line arguments required"
                            + inputWarning);
        } catch (NegativeArraySizeException e) {
            System.out
                    .println("Invalid input(s): negative command-line arguments"
                            + inputWarning);
        }
    }

    /**
     * Returns the update rules as an array extracted from an integer array of
     * arguments
     * 
     * @param arguments
     *            the validated command-line arguments supplied by user
     * @return the update rules as an array (with intuitive indexing by sum)
     */
    private int[] getUpdateRulesArray(int[] arguments) {
        return new int[] { arguments[2], arguments[3], arguments[4],
                arguments[5] };
    }

    /**
     * Converts command-line arguments from Strings to Integers and throws
     * IllegalArgumentException if argumentStr contains non-Integer Strings
     * 
     * @param argumentStr
     *            the command-line arguments array
     * @return the command-line arguments array represented as Integers
     */
    private int[] convertUserInput(String[] argumentStr) {
        // instantiate an integer array to move arguments into
        int[] argumentInt = new int[argumentStr.length];
        // move String arguments into integer array (may throw exception)
        for (int i = 0; i < argumentStr.length; i++)
            argumentInt[i] = Integer.parseInt(argumentStr[i]);
        return argumentInt;
    }

    /**
     * Validates user input, which may be declared invalid by throwing an
     * appropriate exception ("-0" is understood as "0", and is therefore valid)
     * 
     * @param args
     *            the command-line arguments as represented by an integer array
     * @throws ArrayIndexOutOfBoundsException
     *             if the number of arguments differs from 6
     * @throws NegativeArraySizeException
     *             if the requested number of generations or cells is negative
     * @throws IllegalArgumentException
     *             if any update rule differs from 0 or 1
     */
    private void validateUserInput(int[] args) {
        // confirm that there are a correct number of arguments
        if (args.length != 6)
            throw new ArrayIndexOutOfBoundsException();
        // confirm the number of cells & number of generations are nonnegative
        if (args[0] < 0 || args[1] < 0)
            throw new NegativeArraySizeException();
        // confirm that update rules are each either 0 or 1 (-0 = 0)
        for (int j = 2; j < 6; j++)
            if (args[j] != 0 && args[j] != 1)
                throw new IllegalArgumentException();
    }

    /**
     * Begins the process of the one-dimensional cellular automaton
     * 
     * @param numCells
     *            the number of cells in one generation (displayed in a 'row')
     * @param numGenerations
     *            the number of generations to be spawned based on initial,
     *            random array
     * @param updateRules
     *            the update rules of subsequent generations, as defined by user
     *            input
     */
    private void start(int numCells, int numGenerations, int[] updateRules) {
        // generate an initial random array
        int[] currentArray = generateRandomArray(numCells);
        // print the initial array with proper formatting
        printArray(currentArray);
        // spawn numGenerations generations of currentArray using updateRules
        spawnGenerations(numGenerations, updateRules, currentArray);
    }

    /**
     * Spawns a pre-defined number of generations based on currentArray
     * 
     * @param numGenerations
     *            the number of generations to be spawned
     * @param updateRules
     *            the update rules of subsequent generations, as defined by user
     *            input
     * @param currentArray
     *            the initial, random array as generated in the start() method
     */
    private void spawnGenerations(int numGenerations, int[] updateRules,
            int[] currentArray) {
        // the nextArray (used for printing and storage)
        int[] nextArray;
        for (int i = 0; i < numGenerations; i++) {
            // generate the nextArray based on currentArray and updateRules
            nextArray = getNextArray(updateRules, currentArray);
            // print the nextArray
            printArray(nextArray);
            // store nextArray in currentArray
            currentArray = nextArray;
        }
    }

    /**
     * Generates a random array of 0s and 1s of numCells length
     * 
     * @param numCells
     *            the length of the array
     * @return a random array of 0s and 1s of numCells length
     */
    private int[] generateRandomArray(int numCells) {
        // instantiate the randomArray to return with length of numCells
        int[] randomArray = new int[numCells];
        // Creating a random generator with a time-dependent seed
        Random random = new Random(System.currentTimeMillis());
        // for-loop to generate either 0 or 1 to fill randomArray
        for (int j = 0; j < randomArray.length; j++) {
            randomArray[j] = random.nextInt(2);
        }
        return randomArray;
    }

    /**
     * Creates a new array based on oldArray, as specified in updateRules
     * 
     * @param updateRules
     *            the update rules of subsequent generations, as defined by user
     *            input
     * @param oldArray
     *            the array upon which to apply updateRules for the subsequent
     *            generation
     * @return the subsequent generation as an array of 0s and 1s
     */
    private int[] getNextArray(int[] updateRules, int[] oldArray) {
        // instantiate a tempNextArray to return as the subsequent generation
        int[] tempNextArray = new int[oldArray.length];
        for (int m = 0; m < oldArray.length; m++) {
            // get the value at the relevant index
            int sum = oldArray[m];
            // get the value of the left neighbor (or 0 if no neighbor)
            sum += m > 0 ? oldArray[m - 1] : 0;
            // get the value of the right neighbor (or 0 if no neighbor)
            sum += m < oldArray.length - 1 ? oldArray[m + 1] : 0;
            // store either 0 or 1 in the tempNextArray based on updateRules
            tempNextArray[m] = updateRules[sum];
        }
        return tempNextArray;
    }

    /**
     * Prints the supplied array as a single line with no breaks or spaces
     * 
     * @param arrayToPrint
     *            the supplied array to be printed
     */
    private void printArray(int[] arrayToPrint) {
        // for-each loop to print each cell in arrayToPrint
        for (int k : arrayToPrint) {
            System.out.print(k);
        }
        // println() used to create break between generations
        System.out.println();
    }
}
