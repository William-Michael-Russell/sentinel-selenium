package net.testaholic.config;

/**
 * Created by wrussell on 2/2/16.
 */
public enum ConfigEnum {

    GRID ("grid"),
    ENABLE_GRID("enable_grid"),
    AWESOME("yes");


    private ConfigEnum(String value){
        this.value = value;
    }

    private final String value;

    public String getValue(){
        return value;}
}
