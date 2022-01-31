public class SortedActualAndWorstObj {
    int[][] sortedArrays;
    int[] actualCosts;
    int[] worstCases;

    public SortedActualAndWorstObj(int[][] sortedArrays, int[] actualCosts) {
        this.sortedArrays = sortedArrays;
        this.actualCosts = actualCosts;
    }

    public void setActualCosts(int[] actualCosts) {
        this.actualCosts = actualCosts;
    }

    public void setSortedArrays(int[][] sortedArrays) {
        this.sortedArrays = sortedArrays;
    }

    public void setWorstCases(int[] worstCases) {
        this.worstCases = worstCases;
    }

    public int[] getActualCosts() {
        return actualCosts;
    }

    public int[] getWorstCases() {
        return worstCases;
    }

    public int[][] getSortedArrays() {
        return sortedArrays;
    }

}
