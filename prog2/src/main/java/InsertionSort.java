public class InsertionSort implements ISortInterface {
    public static SortResult InsertionSort(int[] sort_array, int size) {
        SortResult sortResult = new SortResult();
        sortResult.startTiming();

        for (int iteration = 0; iteration < size - 1; ++iteration) {
            //Value at iteration
            int v = sort_array[iteration];

            //Index of value previous
            int before = iteration - 1;

            //For all values before current iteration that are bigger than
            //current v, move them forward
            while (before >= 0 && sort_array[before] > v) {
                sortResult.addComparison(); //sort_array[before] > v

                sort_array[before + 1] = sort_array[before];
                sortResult.addSwap(); //^^
                before--;

            }
            //before must be less than current iteration value or at beginning, and all greater than
            ///current interation value have been moved forward one index, therefore there is space at
            //before+1 for value

            sort_array[before + 1] = v;
            sortResult.addSwap();
        }

        sortResult.endTiming();
        return sortResult;
    }

    //for i ← 1 to n − 1 do
    //v ← A[i]
    //j ← i − 1
    //while j ≥ 0 and A[j ] > v do
    //A[j + 1]← A[j ]
    //j ← j − 1
    //A[j + 1] ← v

    @Override
    public SortResult sort(int[] array, int size) {
        return InsertionSort(array, size);
    }

    @Override
    public String getName() {
        return InsertionSort.class.getSimpleName();
    }
}
