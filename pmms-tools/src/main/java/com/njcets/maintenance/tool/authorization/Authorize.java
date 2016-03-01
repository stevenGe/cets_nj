package com.njcets.maintenance.tool.authorization;

/**
 * Created by gexin on 3/1/2016.
 */
public interface Authorize {

    public void encrypt(String filePath);

    public void decrypt(String encryptedFilePath);

}
