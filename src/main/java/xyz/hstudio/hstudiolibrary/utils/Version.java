package xyz.hstudio.hstudiolibrary.utils;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Arrays;

public enum Version {

    v1_7_R4,
    v1_8_R3,
    v1_9_R2,
    v1_10_R1,
    v1_11_R1,
    v1_12_R1,
    v1_13_R2,
    v1_14_R1,
    v1_15_R1,
    UNKNOWN;

    public static final Version VERSION;

    static {
        String rawVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        VERSION = Arrays.stream(Version.values())
                .filter(v -> v.name().equals(rawVersion)).findFirst().orElse(Version.UNKNOWN);
    }
}