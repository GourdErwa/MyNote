package com.gourd.erwa.db;

import java.io.*;
import java.sql.*;
import java.util.Arrays;

/**
 * @author wei.Li by 2017/7/10
 */
public class MySQLConnectTest {

    //定义MySQL的数据库驱动程序
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    //定义MySQL数据库的连接地址
    private static final String URL = "jdbc:mysql://s1011gsread.rdsmsyezmvgbhtu.rds.bj.baidubce.com:3306/gamedb?tinyInt1isBit=false";
    //MySQL数据库的连接用户名和连接密码
    private static final String USER = "read_user";
    private static final String PASS = "1AD_g3TjtW2";

    public static void main(String[] args) throws SQLException, IOException {

        // TODO Auto-generated method stub
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String name;
        String sql = "SELECT active_data,day_active_data FROM gamedb.role_data WHERE 1=1 AND login_time >= '2017-07-10 00:00:00'";

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println();
                Blob activeDataBlob = rs.getBlob("active_data");
                InputStream activeDataIn = activeDataBlob.getBinaryStream();
                System.out.println(Arrays.toString(BlobToString(activeDataIn)));

                Blob dayActiveDataBlob = rs.getBlob("day_active_data");
                InputStream dayActiveDataIn = dayActiveDataBlob.getBinaryStream();
                System.out.println(Arrays.toString(BlobToString(dayActiveDataIn)));

            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }


    private static int[] BlobToString(InputStream is) throws SQLException, IOException {

        ByteArrayInputStream bais = (ByteArrayInputStream) is;
        byte[] byte_data = new byte[bais.available()]; //bais.available()返回此输入流的字节数
        bais.read(byte_data, 0, byte_data.length);//将输入流中的内容读到指定的数组

        int[] ints = new int[byte_data.length / 4];
        for (int i = 0; i < byte_data.length; ) {

            int value;
            value = (byte_data[i] & 0xFF)
                    | ((byte_data[i + 1] & 0xFF) << 8)
                    | ((byte_data[i + 2] & 0xFF) << 16)
                    | ((byte_data[i + 3] & 0xFF) << 24);
            ints[i / 4] = value;
            i += 4;
        }


        is.close();

        return ints;
    }

}
