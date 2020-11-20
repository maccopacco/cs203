public class MergeSort implements ISortInterface {

    @Override
    public SortResult sort(int[] array, int size) {
        return MergeSort(array, size);
    }

    @Override
    public String getName() {
        return MergeSort.class.getSimpleName();
    }

    public static SortResult MergeSort(int[] sort_array, int size) {
        SortResult sortResult = new SortResult();
        sortResult.startTiming();
        return MergeSort(sortResult, sort_array, size).endTiming();
    }

    public static SortResult MergeSort(SortResult sortResult, int[] sort_array, int size) {
        if (size > 1) {
            int middle = (int) Math.floor(size / 2.0);
            int rightArraySize = (int) Math.ceil(size / 2.0);

            int[] leftSide = new int[middle];
            int[] rightSide = new int[rightArraySize];

            sortResult.addCopyArrayAssignment(size);
            //all items must be be assigned into two different arrays

            for (int i = 0; i < middle; i++) {
                leftSide[i] = sort_array[i];
            }
            for (int i = 0; i < rightArraySize; i++) {
                rightSide[i] = sort_array[i + middle];
            }

            MergeSort(sortResult, leftSide, leftSide.length);
            MergeSort(sortResult, rightSide, rightSide.length);
            Merge(sortResult, leftSide, rightSide, sort_array);
        }
        return sortResult;
    }

    private static void Merge(SortResult sortResult, int[] leftSide, int[] rightSide, int[] sort_array) {
        final int leftCount = leftSide.length;
        final int rightCount = rightSide.length;
        int nextLeftPosition = 0, nextRightPosition = 0, totalIndexes = 0;

        //Pop values off both arrays, and take the smallest of both of them and put it into  sort_array
        while (nextLeftPosition < leftCount && nextRightPosition < rightCount) {
            sortResult.addComparison(2);
            final int nextLeftValue = leftSide[nextLeftPosition];
            final int nextRightValue = rightSide[nextRightPosition];

            if (nextLeftValue <= nextRightValue) {
                sort_array[totalIndexes] = nextLeftValue;
                nextLeftPosition++;
            } else {
                sort_array[totalIndexes] = nextRightValue;
                nextRightPosition++;
            }
            sortResult.addComparison();
            sortResult.addSwap();

            totalIndexes++;
        }
        //Left is empty, copy the rest of right to sort_array
        if (nextLeftPosition == leftCount) {
            for (int i = nextRightPosition; i < rightCount; i++) {
                sort_array[totalIndexes++] = rightSide[i];
                sortResult.addSwap();
            }
        } else { //right is empty, copy the rest of left to sort_array
            for (int i = nextLeftPosition; i < leftCount; i++) {
                sort_array[totalIndexes++] = leftSide[i];
                sortResult.addSwap();
            }
        }
    }

}
