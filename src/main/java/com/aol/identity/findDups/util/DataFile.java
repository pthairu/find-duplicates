package com.aol.identity.findDups.util;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 6/1/12
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataFile implements Iterable<String> {
    private static final Logger _LOG = LoggerFactory.getLogger(DataFile.class);


    private BufferedReader _reader;

    public DataFile(String file) throws Exception {
        _reader = new BufferedReader(new FileReader(file));
    }

    public DataFile(File file) throws Exception {
        _reader = new BufferedReader(new FileReader(file));
    }

    public void close() {
        try {
            _reader.close();
        } catch (IOException e) {
            _LOG.error("Error on close", e);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new FileIterator();
    }

    private class FileIterator implements Iterator<String> {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);

        private String _curr;

        @Override
        public boolean hasNext() {
            try {
                _curr = _reader.readLine();
            } catch (Exception e) {
                _curr = null;
                _LOG.error("Read error", e);
            }

            return _curr != null;
        }

        @Override
        public String next() {
            String id = null;
            try {
                //res = mapper.readValue(_curr, Data.class);
                id = mapper.readTree(_curr).get("_id").asText();
            } catch (IOException e) {
               _LOG.error("JSON parse error", e);
            }
            return id;
        }

        @Override
        public void remove() {
        }
    }
}
