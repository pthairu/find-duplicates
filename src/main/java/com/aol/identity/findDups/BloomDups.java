package com.aol.identity.findDups;

import com.aol.identity.findDups.util.ConfigProps;
import com.aol.identity.findDups.util.DataFile;
import com.clearspring.analytics.stream.membership.BloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 5/31/12
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BloomDups {
    private static final Logger _LOG = LoggerFactory.getLogger(BloomDups.class);
    private BloomFilter bloomFilter;
    private int expectedNumElements = 1000000;
    private double falsePositiveProbability = 0.001;
    private int dupCount = 0;
    private int keyCount = 0;


    public BloomDups() {}

    public BloomDups(int numElements){
        this.expectedNumElements = numElements;
    }

    public BloomDups(ConfigProps configProps) {
        this.expectedNumElements = configProps.getElements();
        this.falsePositiveProbability = configProps.getProbability();
    }

    public void loadBloom(String[] arr) {
        bloomFilter = new BloomFilter(expectedNumElements, falsePositiveProbability);

        for (String s: arr) {

            if(bloomFilter.isPresent(s)) {
                _LOG.info("Duplicate?? {}", s);
                dupCount++;
            } else {
                bloomFilter.add(s);
                keyCount++;
            }
        }
    }

    public void loadBloom(ArrayList<String> data_files) {
        DataFile dataFile = null;
        bloomFilter =  new BloomFilter(expectedNumElements, falsePositiveProbability);

        for (String data_file: data_files) {
            _LOG.info("Processing {} ....", data_file);
            try {
                dataFile = new DataFile(data_file);
            } catch (Exception e) {
                _LOG.error("Data file processing error");
            }

            for(String id: dataFile) {
                if(bloomFilter.isPresent(id)) {
                    _LOG.info("Duplicate?? {}", id);
                    dupCount++;
                } else {
                    bloomFilter.add(id);
                    keyCount++;
                }
            }
            printSummary();
        }
    }

    public static void main(String [] args) {
        BloomDups b = new BloomDups();
        String[] arr = {"test1", "test2", "test1"};
        b.loadBloom(arr);
    }

    public void printSummary() {
        _LOG.info("keyCount = {}", keyCount);
        _LOG.info("BloomFilter getHashCount = {}", bloomFilter.getHashCount());
        _LOG.info("BloomFilter buckets() = {}", bloomFilter.buckets());
        _LOG.info("Found {} duplicates", dupCount);
    }


}
