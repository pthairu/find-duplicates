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
    @Parameter(names = {"-r", "--fileregex"}, description =  "Input file regex")
    private String filePattern = "**/guids_*";

    @Parameter(names = {"-d", "--datadir"}, description =  "Base data directory")
    private String dataDir = "/data/servers/data";

    @Parameter(names = {"-c", "--configfile"}, description =  "properties file")
    private String configFile = "findDups.properties";

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }
}
