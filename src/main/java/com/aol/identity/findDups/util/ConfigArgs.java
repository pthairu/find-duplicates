package com.aol.identity.findDups.util;

import com.beust.jcommander.Parameter;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 6/1/12
 * Time: 12:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigArgs {
    @Parameter(names = {"-f", "--datafile"}, description =  "Input json data file")
    private String dataFile = "guids_*";


    @Parameter(names = {"-c", "--configfile"}, description =  "properties file")
    private String configFile = "findDups.properties";

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

}
