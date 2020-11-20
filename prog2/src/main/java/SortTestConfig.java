public class SortTestConfig {
    private final int size;
    private final int low;
    private final int high;
    private int[] array;

    public SortTestConfig(int size, int low, int high) {
        this.size = size;
        this.low = low;
        this.high = high;
    }

    public int getRandom() {
        return (int) ((Math.random() * (high - low)) + low);
    }

    public int getSize() {
        return size;
    }

    public void setArray(int[] array) {
        this.array = array;
    }


    public int[] getCopy() {
        int[] newArray = new int[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }
}
