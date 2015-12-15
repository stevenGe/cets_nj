package com.njcets.tools.core.file.reader;

/**
 * @author gexinl
 */

@Deprecated
public interface IFileReader {

    // public String readLine();

    public void open();

    public String[] readLines();

    public void close();

}
