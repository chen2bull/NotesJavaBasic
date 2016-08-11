package corejava.corejava2.ch04;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * This program tests that the database and the JDBC driver are correctly configured.
 * 
 * @version 1.01 2004-09-24
 * @author Cay Horstmann
 */
class CH_04_01_TestDB {
    public static void main(String args[]) {
        try {
            runTest();
        } catch (SQLException ex) {
            for (Throwable t : ex)
                t.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Runs a test by creating a table, adding a value, showing the table contents, and removing the
     * table.
     */
    public static void runTest() throws SQLException, IOException {
        Connection conn = getConnection();  // A 自定义函数,实现了访问数据库必要的操作,返回一个Connection对象
        try {
            // B 从Connection中获得Statement
            Statement stat = conn.createStatement();

            // C 使用Statement对象执行SQL语句
            stat.executeUpdate("CREATE TABLE Greetings (Message CHAR(20))");
            stat.executeUpdate("INSERT INTO Greetings VALUES ('Hello, World!')");

            // D 返回SQL语句查询得到的结果集
            ResultSet result = stat.executeQuery("SELECT * FROM Greetings");
            if (result.next())
                System.out.println(result.getString(1));
            result.close();
            stat.executeUpdate("DROP TABLE Greetings");
        } finally {
            conn.close();
        }
    }

    /**
     * Gets a connection from the properties specified in the file database.properties
     * 
     * @return the database connection
     */
    public static Connection getConnection() throws SQLException, IOException {
        // 1 读取配置文件到Properties中
        Properties props = new Properties();
        FileInputStream in = new FileInputStream("src/corejava.corejava2/ch04/database.properties");
        props.load(in);
        in.close();

        // 2 从配置文件中获得jdbc.drivers
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);
//        try { // 被注释掉的代码的作用和上两行一样
//            Class.forName(drivers);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        
        // 3 以下为读配置文件中的配置项(非必要)
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }
}
