package net.testaholic.config;

/**
 * Created by wrussell on 2/2/16.
 */
public class SeleniumConfig {

    private boolean enableGrid;
    private String grid;
    private String awesome;
    private boolean ishouldbeabool;


    public String getAwesome() {
        return awesome;
    }

    public void setAwesome(String awesome) {
        this.awesome = awesome;
    }

    public boolean ishouldbeabool() {
        return ishouldbeabool;
    }

    public void setIshouldbeabool(boolean ishouldbeabool) {
        this.ishouldbeabool = ishouldbeabool;
    }

    public boolean isEnableGrid() {
        return enableGrid;
    }

    public void setEnableGrid(boolean enableGrid) {
        this.enableGrid = enableGrid;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }
}
