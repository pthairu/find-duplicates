package com.aol.identity.findDups;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 5/31/12
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class BloomDupsTest {
    static final Logger _LOG = LoggerFactory.getLogger(BloomDupsTest.class);

    @Test
    public void testLoadBloom() throws Exception {
        List<String> list = new ArrayList<String>(10);
        for(int i=0; i < 10000; i++) {
            list.add(RandomStringUtils.randomAlphanumeric(8));
        }

        BloomDups bloomDups = new BloomDups(10000);
        bloomDups.loadBloom(list.toArray(new String[list.size()]));

        Set<String> set = new HashSet<String>(list);
        if (set.size() == list.size()) {
            _LOG.info("Generated list has no duplicates!");
        } else {
            _LOG.info("Generated list has {} duplicates",
                    Math.abs(list.size() - set.size()));
        }

        Assert.assertNotNull(bloomDups);
    }
}
