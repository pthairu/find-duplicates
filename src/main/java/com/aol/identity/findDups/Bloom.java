package com.aol.identity.findDups;

import com.aol.identity.findDups.util.ConfigProps;
import com.aol.identity.findDups.util.DataFile;
import com.skjegstad.utils.BloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 5/31/12
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bloom {
    private static final Logger _LOG = LoggerFactory.getLogger(Bloom.class);

    private BloomFilter<String> bloomFilter;
    private int expectedNumElements = 1000000;
    private double falsePositiveProbability = 0.001;
    private int dupCount = 0;
    private int keyCount = 0;

    public Bloom() {}

    public Bloom(int numElements){
        this.expectedNumElements = numElements;
    }

    public Bloom(ConfigProps configProps) {
        this.expectedNumElements = configProps.getElements();
        this.falsePositiveProbability = configProps.getProbability();
    }

    public void loadBloom(String [] arr) {
        double falsePositiveProbability = 0.001;
        int expectedNumElements = 1000000;

       bloomFilter = new BloomFilter<String>(falsePositiveProbability,
                expectedNumElements);

        for (String s: arr) {
            if(bloomFilter.contains(s)) {
                dupCount++;
            } else {
                bloomFilter.add(s);
                keyCount++;
            }
        }
    }

    public void loadBloom(ArrayList<String> data_files) {
        DataFile dataFile = null;
        bloomFilter =  new BloomFilter<String>(falsePositiveProbability,
                expectedNumElements);

        for (String data_file: data_files) {
            _LOG.info("Processing {} ....", data_file);
            try {
                dataFile = new DataFile(data_file);
            } catch (Exception e) {
                _LOG.error("Data file processing error");
            }

            for(String id: dataFile) {
                if(bloomFilter.contains(id)) {
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

        Bloom b = new Bloom();
        String[] arr = {"test1", "test2", "test1"};
        b.loadBloom(arr);
    }

    public void printSummary() {
        _LOG.info("keyCount = {}", keyCount);
        _LOG.info("False positive probability = {}",
                bloomFilter.expectedFalsePositiveProbability());
        _LOG.info("BloomFilter size() = {}", bloomFilter.size());
        _LOG.info("BloomFilter count() = {}", bloomFilter.count());
        _LOG.info("Found {} duplicates", dupCount);
    }
}
