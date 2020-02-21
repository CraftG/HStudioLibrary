package xyz.hstudio.hstudiolibrary.nms;

import xyz.hstudio.hstudiolibrary.utils.Version;

public class McAccessor {

    public static final IMcAccessor INSTANCE;

    static {
        switch (Version.VERSION) {
            case v1_8_R3:
                INSTANCE = new v1_8_R3();
                break;
            case v1_9_R2:
                INSTANCE = new v1_9_R2();
                break;
            case v1_10_R1:
                INSTANCE = new v1_10_R1();
                break;
            case v1_11_R1:
                INSTANCE = new v1_11_R1();
                break;
            case v1_12_R1:
                INSTANCE = new v1_12_R1();
                break;
            case v1_13_R2:
                INSTANCE = new v1_13_R2();
                break;
            case v1_14_R1:
                INSTANCE = new v1_14_R1();
                break;
            case v1_15_R1:
                INSTANCE = new v1_15_R1();
                break;
            default:
                INSTANCE = null;
                break;
        }
    }
}