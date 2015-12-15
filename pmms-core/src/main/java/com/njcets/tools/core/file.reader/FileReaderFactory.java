package com.njcets.tools.core.file.reader;

/**
 * @author gexinl
 */

public class FileReaderFactory {

    public static AbstractFileReader getFileReader(String fileType) {
        if(fileType.toUpperCase().equals("TXT")) {
            return new TXTFileReader();
        } else {  // default to read txt file.
            return new TXTFileReader();
        }
    }
}
