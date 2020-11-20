import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombinedSortResults {
    //MapA = Config | MapB
    //       _________________|_____
    //       5000 items       | MapB_1
    //       10,000 items     | MapB_2

    //MapB = SortName   | List_Of_SortResults
    //       ___________|____________________
    //       Quicksort  | <3 comps, 2 comps...>
    //       Bogo       | <230182 comps, 29342 comps...>

    private final HashMap<SortTestConfig, HashMap<ISortInterface, List<SortResult>>> averageMap = new HashMap<>();

    public void add(SortTestConfig config, ISortInterface sortInterface, SortResult sortResult) {
        List<SortResult> list_of_sort_results = getList(config, sortInterface);
        list_of_sort_results.add(sortResult);
    }

    private List<SortResult> getList(SortTestConfig config, ISortInterface sortInterface) {
        HashMap<ISortInterface, List<SortResult>> sort_name_to_list_of_sort_results;
        if (averageMap.containsKey(config)) {
            sort_name_to_list_of_sort_results = averageMap.get(config);
        } else {
            sort_name_to_list_of_sort_results = new HashMap<>();
            averageMap.put(config, sort_name_to_list_of_sort_results);
        }

        List<SortResult> list_of_sort_results;
        if (sort_name_to_list_of_sort_results.containsKey(sortInterface)) {
            list_of_sort_results = sort_name_to_list_of_sort_results.get(sortInterface);
        } else {
            list_of_sort_results = new ArrayList<>();
            sort_name_to_list_of_sort_results.put(sortInterface, list_of_sort_results);
        }
        return list_of_sort_results;
    }

    public AveragedSortResult getAverage(SortTestConfig config, ISortInterface sortInterface) {
        List<SortResult> list = getList(config, sortInterface);

        double comparisons = 0, copyArrayAssigns = 0,
                durationNs = 0, swaps = 0;
        final double count = list.size();
        //When averaging, divide each value by the total count then sum
        //to avoid generating huge valued sums, then dividing
        // to avoid saturating any variables
        for (SortResult result : list) {
            comparisons += result.getComparisons() / count;
            copyArrayAssigns += result.getCopyArrayAssigns() / count;
            durationNs += result.getDurationNs() / count;
            swaps += result.getSwaps() / count;
        }
        return new AveragedSortResult(durationNs, comparisons, swaps, copyArrayAssigns, count);
    }
}
