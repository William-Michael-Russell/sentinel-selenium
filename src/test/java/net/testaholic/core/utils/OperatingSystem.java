package net.testaholic.core.utils;

public class OperatingSystem {

    static enum OS {
        WINDOWS, MAC, LINUX, CONSOLE,
    }

    public static enum ARCH_TYPE {
        X64, X86, X86_64
    }

    public static ARCH_TYPE getSystemArchitecture(){
        switch (System.getProperty("os.arch")){
            case "X64":
                return ARCH_TYPE.X64;
            case "X86":
                return ARCH_TYPE.X86;
            case "x86_64":
                return ARCH_TYPE.X86_64;
            default:
                throw new RuntimeException("Unable to determine system architecture type");
        }
    }


    public static OS getOperatingSystem() {
        String system = System.getProperty("os.name").toUpperCase();
        if (system.contains("windows".toUpperCase())) {
            return OS.WINDOWS;
        } else if (system.contains("LINUX".toUpperCase())) {
            return OS.LINUX;
        } else if (system.contains("Mac".toUpperCase())) {
            return OS.MAC;
        }
        return null;
    }


    public static boolean isLinuxOperatingSystem() {
        boolean isLinux = false;
        if (getOperatingSystem() == OS.LINUX) {
            isLinux = true;
        }
        return isLinux;
    }

    public static boolean isMacOperatingSystem() {
        boolean isMac = false;
        if (getOperatingSystem() == OS.MAC) {
            isMac = true;
        }
        return isMac;
    }

    public static boolean isWindowsOperatingSystem() {
        boolean isWindows = false;
        if (getOperatingSystem() == OS.WINDOWS) {
            isWindows = true;
        }
        return isWindows;
    }



}