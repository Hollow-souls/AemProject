package dao;

import domain.Atm;
import domain.trans;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AtmDao {

    //负责的是纯粹的JDBC操作  没有任何的逻辑
    //对于atm表格的新增  修改  删除  查询单条记录

    private String className = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://150.158.17.181:3306/myatm?serverTimezone=CST";
    private String user = "root";
    private String password = "Xiaohua0528.";

    private Connection connection = null;
    private PreparedStatement pstat = null;
    private PreparedStatement pstat1 = null;


    public List<trans> selectTwo(String aname){
        trans trans = null;
        String sql = "SELECT trans_account,trans_money,trans_time,trans_type FROM trans WHERE trans_account = ? ";
        ResultSet rs = null;
        List<trans> list=new ArrayList<>();
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,aname);
            rs = pstat.executeQuery();
            while(rs.next()){
                trans= new trans();
                trans.setTrans_account(rs.getString("trans_account"));
                trans.setTrans_money(rs.getString("trans_money"));
                trans.setTrans_time(rs.getString("trans_time"));
                trans.setTrans_type(rs.getString("trans_type"));
                list.add(trans);
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }






    //设计一个方法，负责将某一条记录删掉
    public int delete(String aname){
        int count = 0;
        String sql = "DELETE FROM account WHERE ANAME = ?";
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,aname);
            count = pstat.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    //设计一个方法，负责将一行新的记录写入数据库中
    public void insert(Atm atm){
        String sql = "INSERT INTO account (username,aname,apassword,abalance) VALUES(?,?,?,?)";
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat1 =connection.prepareStatement("INSERT INTO log_table (account,TIME,TYPE,remarks) VALUEs("+atm.getAname()+",now(),'开户','') ");
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,atm.getUsername());
            pstat.setString(2,atm.getAname());
            pstat.setString(3,atm.getApassword());
            pstat.setFloat(4,atm.getAbalance());
            pstat.executeUpdate();
            pstat1.executeUpdate();
            pstat1.close();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    //查询一行记录（这是复用代码：登录、查询...）
    public Atm selectOne(String aname){
        Atm atm = null;
        String sql = "SELECT username,ANAME,APASSWORD,ABALANCE FROM account WHERE ANAME = ? ";
        ResultSet rs = null;
        try{
            System.out.println(aname);
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,aname);
            rs = pstat.executeQuery();
            if(rs.next()){
                atm = new Atm();
                atm.setUsername(rs.getString("username"));
                atm.setAname(rs.getString("aname"));
                atm.setAbalance(rs.getFloat("abalance"));
                atm.setApassword(rs.getString("apassword"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return atm;
    }
    public Atm login(String aname){
        Atm atm = null;
        String sql = "SELECT username,ANAME,APASSWORD,ABALANCE FROM account WHERE ANAME = ? ";
        //String sql1="INSERT INTO log_table (account,TIME,TYPE,remarks) VALUEs("+atm.getAname()+",now(),'登录','') ";
        ResultSet rs = null;
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,aname);
             rs = pstat.executeQuery();
            if(rs.next()){
                atm = new Atm();
                atm.setUsername(rs.getString("username"));
                atm.setAname(rs.getString("aname"));
                atm.setAbalance(rs.getFloat("abalance"));
                atm.setApassword(rs.getString("apassword"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return atm;
    }



    //修改一行记录（复用代码：取款，存款）
    public int update(Atm atm,float money,String type){
        int count = 0;//记录更改的行数
        String sql = "UPDATE account SET APASSWORD = ?,ABALANCE = ? WHERE ANAME = ?";
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,atm.getApassword());
            pstat.setFloat(2,atm.getAbalance());
            pstat.setString(3,atm.getAname());
             count = pstat.executeUpdate();
            pstat =connection.prepareStatement("INSERT INTO trans (trans_account,trans_money,trans_time,trans_type) VALUES ("+atm.getAname()+","+money+",now(),'"+type+"');");

        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
