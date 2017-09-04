package com.gourd.erwa.util.sql;

import com.google.common.base.Strings;
import com.gourd.erwa.annotation.Nullable;
import lombok.Builder;
import lombok.ToString;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.Li by 2017/4/5
 */
public class Table2XLS {


    public static void main(String[] args) {
        search();
    }


    private static void search() {

        final JDBCConnection jdbcConnection = new JDBCConnection(
                JDBCSetting.builder()
                        .driverClass("com.mysql.cj.jdbc.Driver")
                        .url("jdbc:mysql://s1201gsread.rdsmw3d9g6v2nox.rds.bj.baidubce.com:3306/gamedb")
                        .userName("read_user")
                        .usePassWord("1AD_g3TjtW2")
                        .build()
        );
        try {
            //final ResultData resultData = jdbcConnection.executeSQL("show full columns from gamedb.account_common; ");
            final ResultData resultData = jdbcConnection.executeSQL("SHOW TABLES; ");
            System.out.println(resultData);
        } finally {
            try {
                jdbcConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Builder
    private static class JDBCSetting {
        private String driverClass;
        private String url;
        private String userName;
        private String usePassWord;
    }

    private static class JDBCConnection implements Closeable {

        private Connection conn = null;
        private JDBCSetting jdbcSetting;

        JDBCConnection(JDBCSetting jdbcSetting) {
            this.jdbcSetting = jdbcSetting;
        }

        private @Nullable
        ResultData executeSQL(String sql) {

            PreparedStatement statement = null;
            ResultSet rs = null;

            final ResultData.ResultDataBuilder builder = ResultData.builder();


            //所有数据
            final List<String[]> data = new ArrayList<>();
            //每行数据
            String[] r;

            try {
                if (this.conn == null || this.conn.isClosed()) {
                    Class.forName(this.jdbcSetting.driverClass);
                    this.conn = DriverManager.getConnection(this.jdbcSetting.url, this.jdbcSetting.userName, this.jdbcSetting.usePassWord);
                    this.conn.setReadOnly(true);
                }

                statement = this.conn.prepareStatement(sql);
                rs = statement.executeQuery();

                final ResultSetMetaData metaData = rs.getMetaData();
                //结果内容对应列数
                final int columnCount = metaData.getColumnCount();

                //每列名称
                r = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    r[i - 1] = metaData.getColumnName(i);
                }
                builder.columnNames(r);

                //每行数据
                while (rs.next()) {
                    r = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        final String o = rs.getString(i);
                        r[i - 1] = ((Strings.isNullOrEmpty(o) ? "" : o));
                    }
                    data.add(r);
                }
                builder.datas(data);

                return builder.build();
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException ignored) {
                }
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException ignored) {
                }
            }

            return null;
        }

        @Override
        public void close() throws IOException {
            try {
                if (this.conn != null) {
                    this.conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Builder
    @ToString
    private static class ResultData {

        private String[] columnNames;
        private List<String[]> datas;

    }
}
