package com.aol.identity.findDups.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 6/1/12
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataFileTest {
    static final Logger _LOG = LoggerFactory.getLogger(DataFileTest.class);

    @Test
    public void ReadTest() throws Exception {
        DataFile dataFile = new DataFile(
                FileUtils.toFile(
                        this.getClass().getResource("/data.json")));

        for(String id: dataFile) {
            //Assert.assertNotNull(id);
            _LOG.info("ID = {}", id);
        }
    }
}
