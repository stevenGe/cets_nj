package com.njcets.tools.core.conf;

import java.io.File;

/**
 * @author gexinl
 */
public class EnvManager {
    private String PMMS_HOME;

    private String PMMS_RESULT_DB_FILE;

    private static EnvManager instance;

    private EnvManager() {
        this.PMMS_HOME = System.getProperty("PMMS_HOME");
    }

    public static EnvManager getInstance() {
        if(instance == null) {
            instance = new EnvManager();
        }
        return instance;
    }

    public String getPMMS_HOME() {
        return PMMS_HOME;
    }

    public String getPMMS_RESULT_DB_FILE() {
        return PMMS_HOME + File.separator + "db" + File.separator + "pmms.db";
    }
}
