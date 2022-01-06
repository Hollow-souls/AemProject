package view;

import domain.Atm;

import java.sql.*;
import java.util.ArrayList;

public class test {
    private String className = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3309//myatm?serverTimezone=CST";
    private String user = "root";
    private String password = "190591071";

    private Connection connection = null;
    private PreparedStatement pstat = null;

    String aname = "10001";

    {
        Atm atm = null;
        String sql = "SELECT username,ANAME,APASSWORD,ABALANCE FROM account WHERE ANAME = ? ";
        ResultSet rs = null;

        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url, user, password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1, aname);
            rs = pstat.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(aname));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstat != null) {
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}




