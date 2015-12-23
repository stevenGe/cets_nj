package com.njcets.tools.core.reader;

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
    protected BufferedReader bufferedReader;

    public void open(String filePath){
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public List<String> readLines(){
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
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
