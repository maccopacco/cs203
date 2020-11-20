public class SortResult {

    private long startTime, endTime;
    private long comparisons, swaps, copyArrayAssigns;

    public SortResult() {
        this(0, 0, 0, 0);
    }

    public SortResult(double duration, double comparisons, double swaps, double copyArrayAssigns) {
        this.endTime = (long) duration;
        this.startTime = 0;
        this.comparisons = (long) comparisons;
        this.swaps = (long) swaps;
        this.copyArrayAssigns = (long) copyArrayAssigns;
    }

    public void startTiming() {
        startTime = System.nanoTime();
    }

    public SortResult endTiming() {
        endTime = System.nanoTime();
        return this;
    }

    public long getDurationNs() {
        if (endTime == -1 || startTime == -1) {
            return 0;
        }
        return endTime - startTime;
    }

    public void addComparison(int count) {
        comparisons += count;
    }

    public void addComparison() {
        comparisons++;
    }

    public void addSwap(int swaps) {
        this.swaps += swaps;
    }

    public void addSwap() {
        swaps++;
    }

    public void addCopyArrayAssignment() {
        copyArrayAssigns++;
    }

    public void addCopyArrayAssignment(int count) {
        copyArrayAssigns += count;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public long getCopyArrayAssigns() {
        return copyArrayAssigns;
    }
}
