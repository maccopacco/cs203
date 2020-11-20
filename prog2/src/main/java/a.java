import java.util.Arrays;

public class a {
    public static void main(String[] args) {
        int[] list = {3, 2, 1};
        InsertionSort.InsertionSort(list, list.length);
        System.out.println(Arrays.toString(list));
    }
}
