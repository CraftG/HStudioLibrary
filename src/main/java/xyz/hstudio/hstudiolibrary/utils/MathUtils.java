package xyz.hstudio.hstudiolibrary.utils;

public class MathUtils {

    /**
     * 取两个点的欧几里得距离
     *
     * @param xDiff 两点x轴的差值
     * @param zDiff 两点z轴的差值
     * @return 距离
     */
    public static double distance2d(final double xDiff, final double zDiff) {
        return Math.sqrt(xDiff * xDiff + zDiff * zDiff);
    }

    /**
     * 取两个点的欧几里得距离
     *
     * @param xDiff 两点x轴的差值
     * @param yDiff 两点y轴的差值
     * @param zDiff 两点z轴的差值
     * @return 距离
     */
    public static double distance3d(final double xDiff, final double yDiff, final double zDiff) {
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
}