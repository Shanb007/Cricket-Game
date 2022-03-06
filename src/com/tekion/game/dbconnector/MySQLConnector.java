package com.tekion.game.dbconnector;

import java.sql.*;

public class MySQLConnector {

    private static Connection conn = null;

    public static void setUpConn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CricketMatch","root", "shankkb27$");
        System.out.println("Connection to the Database made Successfully.");
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn== null || conn.isClosed()){
            setUpConn();
        }
        return conn;
    }
}
