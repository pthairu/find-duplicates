package com.aol.identity.findDups;

import com.aol.identity.findDups.cassandra.Filter;
import com.aol.identity.findDups.cassandra.FilterFactory;
import com.aol.identity.findDups.util.ConfigProps;
import com.aol.identity.findDups.util.DataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 5/31/12
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BloomCass {
    private static final Logger _LOG = LoggerFactory.getLogger(BloomCass.class);
    private Filter bloomFilter;
    private int expectedNumElements = 1000000;
    private double falsePositiveProbability = 0.001;
    private int dupCount = 0;
    private int keyCount = 0;




    public BloomCass() {}

    public BloomCass(int numElements){
        this.expectedNumElements = numElements;
    }

    public BloomCass(ConfigProps configProps) {
        this.expectedNumElements = configProps.getElements();
        this.falsePositiveProbability = configProps.getProbability();
    }

    public void loadBloom(String[] arr) {
        ByteBuffer buf = ByteBuffer.allocate(256);
        CharBuffer cbuf = buf.asCharBuffer();

        bloomFilter = FilterFactory.getFilter(expectedNumElements, falsePositiveProbability);

        for (String s: arr) {
            cbuf.put(s);
            cbuf.flip();
            if(bloomFilter.isPresent(buf)) {
                _LOG.info("Duplicate?? {}", s);
                dupCount++;
            } else {
                bloomFilter.add(buf);
                keyCount++;
            }
        }
    }

    public void loadBloom(ArrayList<String> data_files) {
        DataFile dataFile = null;
        ByteBuffer buf = ByteBuffer.allocate(256);
        CharBuffer cbuf = buf.asCharBuffer();

        bloomFilter = FilterFactory.getFilter(expectedNumElements, falsePositiveProbability);

        for (String data_file: data_files) {
            _LOG.info("Processing {} ....", data_file);
            try {
                dataFile = new DataFile(data_file);
            } catch (Exception e) {
                _LOG.error("Data file processing error");
            }

            for(String id: dataFile) {
                cbuf.put(id);
                cbuf.flip();
                if(bloomFilter.isPresent(buf)) {
                    _LOG.info("Duplicate?? {}", id);
                    dupCount++;
                } else {
                    bloomFilter.add(buf);
                    keyCount++;
                }
            }
            printSummary();
        }
    }

    public static void main(String [] args) {
        BloomCass b = new BloomCass();
        String[] arr = {"test1", "test2", "test1"};
        b.loadBloom(arr);
    }

    public void printSummary() {
        _LOG.info("keyCount = {}", keyCount);
        _LOG.info("Found {} duplicates", dupCount);
    }


}
