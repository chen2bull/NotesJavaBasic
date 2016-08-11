package corejava.corejava2.ch04;

import java.io.*;
import java.util.*;
import java.sql.*;

/**
 * Executes all SQL statements in a file. Call this program as <br>
 * java -classpath driverPath:. ExecSQL commandFile
 * 
 * @version 1.30 2004-08-05
 * @author Cay Horstmann
 */
class CH_04_02_ExecSQL {
    public static void main(String args[]) {
        try {
            Scanner in;
            if (args.length == 0)
                in = new Scanner(System.in);
            else
                in = new Scanner(new File(args[0]));

            Connection conn = getConnection();  // A 自定义函数,实现了访问数据库必要的操作,返回一个Connection对象
            try {
                Statement stat = conn.createStatement();    // B 从Connection中获得Statement

                while (true) {
                    if (args.length == 0)
                        System.out.println("Enter command or EXIT to exit:");

                    if (!in.hasNextLine())
                        return;

                    String line = in.nextLine();
                    if (line.equalsIgnoreCase("EXIT"))
                        return;
                    if (line.trim().endsWith(";")) {    // 由于输入可以是*.sql文件，所以要去除分号
                        line = line.trim();
                        line = line.substring(0, line.length() - 1);
                    }
                    try {
                        // 执行输入流中的sql语句,返回值为是否有结果集
                        boolean hasResultSet = stat.execute(line);
                        if (hasResultSet)
                            showResultSet(stat);
                    } catch (SQLException ex) {
                        for (Throwable e : ex)
                            e.printStackTrace();
                    }
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a connection from the properties specified in the file database.properties
     * 
     * @return the database connection
     */
    public static Connection getConnection() throws SQLException, IOException {
        // 这个函数的功能说明请看CH04_01_TestDB.java
        Properties props = new Properties();
        FileInputStream in = new FileInputStream("src/corejava.corejava2/ch04/database.properties");
        props.load(in);
        in.close();

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Prints a result set.
     * 
     * @param stat
     *            the statement whose result set should be printed
     */
    public static void showResultSet(Statement stat) throws SQLException {
        ResultSet result = stat.getResultSet();     // 得到这个语句执行的结果集
        ResultSetMetaData metaData = result.getMetaData();  // 得到表头数据
        int columnCount = metaData.getColumnCount();    //得到列数

        // 打印表头，注意数据库的列序号从1开始
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1)
                System.out.print(", ");
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();

        // 打印数据，注意数据库的列序号从1开始
        while (result.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1)
                    System.out.print(", ");
                System.out.print(result.getString(i));
            }
            System.out.println();
        }
        result.close();
    }
}
