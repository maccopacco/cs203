import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**************************************************************/
/* Max Dreher */
/* Login ID: dre489 */
/* CS-203, Fall 2020 */
/* Programming Assignment 2 */
/* Porgam2 class: driver class for sorting analysis */

/**************************************************************/
class Program2 {

    public static void main(String[] args) throws FileNotFoundException {
        System.setOut(new PrintStream(new FileOutputStream(new File("Analysis_Raw.txt"))));

        SortResult entireProgramRuntime = new SortResult();
        entireProgramRuntime.startTiming();
        //Timing entire program for curiosity sake

        final int averages = 50;
        List<Integer> sizes = List.of(1000, 10000, 20000, 50000);
        System.out.printf("Averaging each test %d times...%n", averages);

        final int padSpace = 3;
        final int columnWidth = 30;
        final String paddingSpace = String.format("%" + padSpace + "s", "");

        List<SortTestConfig> sortTestConfigs = new ArrayList<>();
        List<ISortInterface> sortInterfaces = initializeConfigs(sizes, sortTestConfigs);

        CombinedSortResults combiner = runTests(averages, sortTestConfigs, sortInterfaces);

        printResults(padSpace, columnWidth, paddingSpace, sortTestConfigs, sortInterfaces, combiner);
        System.out.printf("Entire program took %6.3f sec to run%n", entireProgramRuntime.endTiming().getDurationNs() * 1e-9);
    }

    /**************************************************************/
    /* Method: initializeConfigs */
    /** Purpose: setup  {@link SortTestConfig}s */
    /* Parameters: */
    /* sizes: list of sizes of arrays we should test*/
    /* sortTestConfigs: list of configs to add configurations to based on 'sizes' param
    /** Returns: List of  {@link ISortInterface}s to sort arrays with*/

    /**************************************************************/
    private static List<ISortInterface> initializeConfigs(List<Integer> sizes, List<SortTestConfig> sortTestConfigs) {
        Integer max = sizes.stream().max(Integer::compareTo).get();
        for (int size : sizes) {
            sortTestConfigs.add(new SortTestConfig(size, 0, max));
        }

        List<ISortInterface> sortInterfaces =
                List.of(new QuickSort(),
                        new InsertionSort(),
                        new MergeSort());
        return sortInterfaces;
    }

    /**************************************************************/
    /* Method: printResults */
    /** Purpose: print results from {@link CombinedSortResults}s */
    /* Parameters: */
    /* padSpace: amount of padding to apply to each column*/
    /* columnWidth: width that content should take up in a column
    /* sortTestConfigs: configs to display
    /* sortInterfaces: sorting algorithms that were run*/

    /**************************************************************/
    private static void printResults(int padSpace,
                                     int columnWidth,
                                     String paddingSpace,
                                     List<SortTestConfig> sortTestConfigs,
                                     List<ISortInterface> sortInterfaces,
                                     CombinedSortResults combiner) {
        final int longestName = (int)
                (sortInterfaces.stream().map(i -> i.getName().length())
                        .max(Integer::compareTo).get() * 1.5);

        StringBuilder headerBuilder = new StringBuilder();
        //Header
        headerBuilder.append(String.format("%-" + longestName + "s", "n") + "|" + paddingSpace);
        String headingFormat = "%-" + columnWidth + "d|";
        //Generate header
        for (int i = 0, amountOfTestConfigs = sortTestConfigs.size(); i < amountOfTestConfigs; i++) {
            SortTestConfig config = sortTestConfigs.get(i);
            headerBuilder.append(String.format(headingFormat, config.getSize()));
            //avoid padding on last
            if (i != amountOfTestConfigs - 1) {
                headerBuilder.append(paddingSpace);
            }
        }
        System.out.println(headerBuilder.toString());
        System.out.println(String.format("%" + headerBuilder.length() + "s", "").replaceAll(" ", "-"));

        //Each row
        for (ISortInterface sortInterface : sortInterfaces) {
            StringBuilder firstRow = new StringBuilder();
            firstRow.append(String.format("%-" + longestName + "s", sortInterface.getName()))
                    .append("|");

            StringBuilder duration = new StringBuilder(), swaps = new StringBuilder();
            duration.append(String.format("%-" + longestName + "s", "")).append("|");
            swaps.append(duration);

            for (SortTestConfig config : sortTestConfigs) {
                AveragedSortResult average = combiner.getAverage(config, sortInterface);
                //First row
                firstRow.append(
                        String.format(paddingSpace + "comps: %" + (columnWidth - 7 - padSpace) + "s%s|",
                                String.format("%.1f", average.getComparisons()), paddingSpace)
                );
                //Second row
                duration.append(String.format(paddingSpace + "ms: %" + (columnWidth - 4 - padSpace) + "s%s|",
                        String.format("%6.5f", average.getDurationNs() * 1e-6), paddingSpace));

                //Third
                swaps.append(String.format(paddingSpace + "swaps: %" + (columnWidth - 7 - padSpace) + "s%s|",
                        String.format("%.1f", average.getSwaps()), paddingSpace));
            }
            System.out.println(firstRow);
            System.out.println(duration);
            System.out.println(swaps);
            System.out.println(String.format("%" + swaps.length() + "s", "").replaceAll(" ", "-"));
        }
    }

    /**************************************************************/
    /* Method: runTests */
    /** Purpose: sort datasets, collect data*/
    /* Parameters: */
    /* averages: amount of items to average*/
    /* sortTestConfigs: list of configs run tests under
    /* sortInterfaces: algorithms to test*/

    /**
     * Returns: {@link CombinedSortResults}s
     **************************************************************/
    private static CombinedSortResults runTests(int averages, List<SortTestConfig> sortTestConfigs, List<ISortInterface> sortInterfaces) {
        CombinedSortResults combiner = new CombinedSortResults();

        boolean firstConfig = true;
        //For each configuration (1000 items, 2000 items...)
        for (SortTestConfig config : sortTestConfigs) {
            //For each thing to test (quicksort, mergesort...)¬
            for (ISortInterface sortInterface : sortInterfaces) {

                //For the amount of times we need to average
                for (int i = 0; i < averages; i++) {
                    config.initializeArray();
                    int[] array = config.getCopy();
                    //get a copy of the array we need to sort
                    SortResult sortResult =
                            sortInterface.sort(array, array.length);

                    if (firstConfig && i == 0) {
                        checkSort(array, sortInterface);
                    }
                    //have the SortingInterface sort that array

                    combiner.add(config, sortInterface, sortResult);
                    //Add the result to the combiner, which will be averaged/combined later
                }
            }
            firstConfig = false;
        }
        return combiner;
    }


    /**************************************************************/
    /* Method: checkSort */
    /** Purpose: manually checks 'sorted' arrays to verify algorithms are working
     /* Parameters: */
    /* array: array to check */
    /* sortInterfaces: algorithm that potentially made an error */

    /****************************************************************/
    private static void checkSort(int[] array, ISortInterface sortInterface) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                System.out.println("ERROR IN " + sortInterface.getName());
            }
        }
    }
}