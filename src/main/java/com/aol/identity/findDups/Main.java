package com.aol.identity.findDups;

import com.aol.identity.findDups.util.ConfigArgs;
import com.aol.identity.findDups.util.ConfigProps;
import com.beust.jcommander.JCommander;
import com.github.joschi.jadconfig.JadConfig;
import com.github.joschi.jadconfig.RepositoryException;
import com.github.joschi.jadconfig.ValidationException;
import com.github.joschi.jadconfig.repositories.PropertiesRepository;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 6/1/12
 * Time: 1:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final Logger _LOG = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        //process command line args
        final ConfigArgs configArgs = new ConfigArgs();
        final JCommander jCommander = new JCommander(configArgs, args);
        jCommander.setProgramName("findDups");

        String dataFile = configArgs.getDataFile();
        String configFile = configArgs.getConfigFile();


        //process properties config file
        final ConfigProps configProps = new ConfigProps();
        JadConfig jadConfig =  new JadConfig(new PropertiesRepository(configFile), configProps);
        try {
            jadConfig.process();
        } catch (RepositoryException e) {
            _LOG.error("Config repo error", e);
        } catch (ValidationException e) {
            _LOG.error("Config validation error", e);
        }

        //BloomDups bloomDups = new BloomDups(configProps);
        //bloomDups.loadBloom(listFiles(dataFile));
        //bloomDups.printSummary();

        //Bloom bloom = new Bloom(configProps);
        //bloom.loadBloom(listFiles(dataFile));
        //bloom.printSummary();

        //BloomGuava bloomGuava = new BloomGuava(configProps);
        //bloomGuava.loadBloom(listFiles(dataFile));
        //bloomGuava.printSummary();

        BloomCass bloomCass = new BloomCass(configProps);
        bloomCass.loadBloom(listFiles(dataFile));
        bloomCass.printSummary();
    }

    private static ArrayList<String> listFiles(String pattern) {
        File dir = new File("/data/servers/data/*/CAT/");
        FileFilter fileFilter = new RegexFileFilter(pattern);
        //return dir.listFiles(fileFilter);

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{"**/guids_*"});
        scanner.setBasedir("/data/servers/data");
        scanner.scan();
        String[] files = scanner.getIncludedFiles();
        _LOG.info(Arrays.toString(files));
        return new ArrayList<String>(Arrays.asList(files));
    }

}
