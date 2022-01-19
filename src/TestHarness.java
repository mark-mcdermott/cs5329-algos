import java.util.List;

public class TestHarness {
    private String suiteName;
    private String testsOutput;
    private String result;

    public TestHarness(String suiteName, List tests) {
        this.suiteName = suiteName;
        this.testsOutput = "";
        this.result = "";

        int pass = 0;
        int total = 0;
        for (Object testObject : tests) {
            total++;
            Test test = (Test) testObject;
            if (test.getPass()) pass++;
            this.testsOutput = this.testsOutput + total + ". " + test + "\n";
        }
        String passFail = pass == total ? "✅" : "❌";
        String baseResultOutput = pass + "/" + total + " tests passed";
        this.result = passFail + " " + baseResultOutput;
    }

    public String toString() {
        return this.suiteName + "\n" + this.testsOutput + this.result;
    }

}
