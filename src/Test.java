import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    private int input[];
    private int expected[];
    private int actual[];
    private Boolean pass;

    public Test (int input[], int expected[]) {
        this.input = input;
        this.expected = expected;
    }

    public Test insertionSortTest() {
        int[] inputCopy = new int[input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);
        int[] insertionSort = new InsertionSort().sort(inputCopy);
        this.actual = insertionSort;
        this.pass = (Arrays.equals(actual,expected) ? true : false);
        return this;
    }

    public Test mergeSortTest() {
        int[] inputCopy = new int[input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);
        int[] mergeSort = new MergeSort().sort(inputCopy,0,inputCopy.length-1);
        this.actual = mergeSort;
        this.pass = (Arrays.equals(actual,expected) ? true : false);
        return this;
    }

    public String toString() {
        String passFail = pass ? "✅" : "❌";
        String baseOutput = "Input: " + Arrays.toString(input) + ". Output: " + Arrays.toString(actual);
        return passFail + " " + baseOutput;
    }

    public int[] getInput() {
        return input;
    }

    public int[] getExpected() {
        return expected;
    }

    public int[] getActual() {
        return actual;
    }

    public Boolean getPass() {
        return pass;
    }

}
