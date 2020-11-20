public class SortTestConfig {
    private final int size;
    private final int low;
    private final int high;
    private int[] array;

    public SortTestConfig(int size, int low, int high) {
        this.size = size;
        this.low = low;
        this.high = high;

        initializeArray();
    }

    public void initializeArray() {
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = getRandom();
        }
    }

    public int getRandom() {
        return (int) ((Math.random() * (high - low)) + low);
    }

    public int getSize() {
        return size;
    }

    public int[] getCopy() {
        int[] newArray = new int[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }
}
