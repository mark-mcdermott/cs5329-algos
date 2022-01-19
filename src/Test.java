import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private List input;
    private List expected;
    private List actual;
    private Boolean pass;

    public Test (List input, List expected) {
        this.input = new ArrayList<>(input);
        this.expected = expected;
        InsertionSort insertionSort = new InsertionSort(input);
        this.actual = insertionSort.getSorted();
        this.pass = (actual.equals(expected) ? true : false);
    }

    public String toString() {
        String passFail = pass ? "✅" : "❌";
        String baseOutput = "Input: " + input + ". Output: " + actual;
        return passFail + " " + baseOutput;
    }

    public List getInput() {
        return input;
    }

    public List getExpected() {
        return expected;
    }

    public List getActual() {
        return actual;
    }

    public Boolean getPass() {
        return pass;
    }

}
