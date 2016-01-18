package com.njcets.tools.core.reader;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public abstract class AbstractFileReader {
    protected Logger logger = Logger.getLogger(AbstractFileReader.class);

    protected BufferedReader bufferedReader;

    public void open(String filePath){

        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            logger.info("Open file successfully. File: " + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public List<String> readLines(){
        logger.info("Read lines in file...");
        List<String> lines = new ArrayList<String>();
        try {
            String oneLine;
            if(bufferedReader != null) {
                while((oneLine = bufferedReader.readLine()) != null) {
                    if(oneLine.length() != 0 && oneLine.trim().length() != 0) {
                        lines.add(oneLine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return lines.size() == 0 ? null : lines;
    }

    public void close() {
        if(bufferedReader != null) {
            try {
                bufferedReader.close();
                logger.info("close file successfully");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
