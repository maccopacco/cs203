public class QuickSort implements ISortInterface {

    @Override
    public SortResult sort(int[] sort_array, int size) {
        return QuickSort(sort_array, size);
    }

    @Override
    public String getName() {
        return QuickSort.class.getSimpleName();
    }

    public static SortResult QuickSort(int[] sort_array, int size) {
        SortResult sortResult = new SortResult();
        sortResult.startTiming();
        QuickSort(sortResult, sort_array, 0, size - 1);
        sortResult.endTiming();
        return sortResult;
    }

    public static void QuickSort(SortResult sortResult, int[] sort_array, int low, int high) {
        if (low < high) {
            int s = Partition(sortResult, sort_array, low, high);
            QuickSort(sortResult, sort_array, low, s - 1);
            QuickSort(sortResult, sort_array, s + 1, high);
        }
    }

    public static int Partition(SortResult sortResult, int[] sort_array, int low, int high) {
        int pivot = sort_array[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (sort_array[j] <= pivot) {
                i++;
                sortResult.addSwap();
                swap(sort_array, i, j);
            }
            sortResult.addComparison();
        }

        swap(sort_array, i + 1, high);
        sortResult.addSwap();
        return i + 1;
    }
    //p ← A[l]
    //i ← l; j ← r + 1
    //repeat
    //repeat i ← i + 1 until A[i] ≥ p
    //repeat j ← j − 1 until A[j ] ≤ p
    //swap(A[i], A[j ])
    //until i ≥ j
    //swap(A[i], A[j ]) //undo last swap when i ≥ j
    //swap(A[l], A[j ])
    //return j
    //
    //Note that index

    private static void swap(int[] array, int i, int j) {
        int tempSwap = array[i];
        array[i] = array[j];
        array[j] = tempSwap;
    }

}
