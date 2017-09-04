package com.gourd.erwa.hadoop;

/**
 * @author wei.Li by 2017/3/31
 */
public interface PathConstant {

    String HDFS_PATH = "hdfs://%s/gourderwa/demo";

    /**
     * @see com.gourd.erwa.hadoop.eventcount.EventCount
     */
    String EVENT_COUNT_IN = "/input/Event-count";
    String EVENT_COUNT_OUT = "/out/Event-count";


    /**
     * @see com.gourd.erwa.hadoop.userbasestation.OperatorsUserBaseStation
     */
    String OPERATORS_USER_BASE_STATION_IN = "/input/Telecom-operators-user-base-station-for-data-statistics";
    String OPERATORS_USER_BASE_STATION_OUT = "/out/Telecom-operators-user-base-station-for-data-statistics";


    static String getHDFSParentDirectory(String host) {
        return String.format(HDFS_PATH, host);
    }


    static String getHDFSDirectory(String host, String subDirectories) {
        return String.format(HDFS_PATH, host) + subDirectories;
    }

}
