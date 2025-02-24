package base.utils;

public class Threshold {

    private static final long threshold = 1;

    public static boolean isEqual(long varA, long varB) {
        return Math.abs(varA - varB) <= threshold;
    }
}
