package com.gourd.erwa.work.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.Li
 */
public class InsertOracle {

    public static void main(String[] args) {
        inset166Oracle();
    }

    private static void inset166Oracle() {

        /*
        ##HQL Insert Query Example
        Query query = session.createQuery("insert into Stock(stock_code, stock_name)" +
    			"select stock_code, stock_name from backup_stock");
         */
        final String sql = "Insert into CMDB_VIEW_FAULT_EVENT_TO_HQ " +
                "(SERIAL,NODE,NODEALIAS,AGENT,ALERTGROUP,ALERTKEY,EVENTTYPE,TALLY,SEVERITY,SUMMARYCN,LOCATION,FIRSTOCCURRENCE,LASTOCCURRENCE) " +
                "values " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final int insertSize = 10000;
        int filedLength = 13;
        int minSerial = 0;
        long firstOccurrence = System.currentTimeMillis();
        final long minS = 60L;

        final List<Object[]> values = new ArrayList<>();
        Object[] o;
        for (int j = 0; j < insertSize; j++) {
            o = new Object[filedLength];
            o[0] = ++minSerial;
            o[1] = "10.3.177." + j % 255;
            o[2] = "SHE1X01U09-NM1-C49Z" + j % 10;
            o[3] = "Cisco" + j % 10;
            o[4] = "LINK-3-UPDOWN" + j % 10;
            o[5] = "GigabitEthernet1/15" + j % 10;
            o[6] = j % 3;
            o[7] = j;
            o[8] = j % 5;
            o[9] = "上海网管区,核心设备:SHE1X01U09-NM1-C49Z,端口描述:无,10.3.177.74设备GigabitEthernet1/15,端口的LINK中断";
            o[10] = "上海网管区";

            firstOccurrence += minS;
            o[11] = firstOccurrence;
            o[12] = firstOccurrence + j % 6 * minS;

            values.add(o);
        }
        HibernateUtil.insertBySql(sql, values);
    }

}

