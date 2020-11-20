public class AveragedSortResult {
    private final double durationNs;
    private final double comparisons;
    private final double swaps;
    private final double copyArrayAssigns;
    private final double count;

    public AveragedSortResult(double durationNs, double comparisons, double swaps, double copyArrayAssigns, double count) {
        this.durationNs = durationNs;
        this.comparisons = comparisons;
        this.swaps = swaps;
        this.copyArrayAssigns = copyArrayAssigns;
        this.count = count;
    }

    public double getCopyArrayAssigns() {
        return copyArrayAssigns;
    }

    public double getSwaps() {
        return swaps;
    }

    public double getDurationNs() {
        return durationNs;
    }

    public double getComparisons() {
        return comparisons;
    }

    @Override
    public String toString() {
        return "AveragedSortResult{" +
                "durationNs=" + durationNs +
                ", comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", copyArrayAssigns=" + copyArrayAssigns +
                ", count=" + count +
                '}';
    }
}
