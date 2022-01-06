package domain;

public class Atm {
    //domain实体  只是为存储表格中的数据
    //类可以有很多对象，表中有很多行，一一对应


    private String aname;
    private String username;
    private String apassword;
    private Float abalance;//用包装类，因为数据库中的数据有可能是null

    public Atm(){}
    public Atm(String username,String aname,String apassword,float abalance){
        this.username=username;
        this.aname = aname;
        this.apassword = apassword;
        this.abalance = abalance;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getApassword() {
        return apassword;
    }

    public void setApassword(String apassword) {
        this.apassword = apassword;
    }

    public Float getAbalance() {
        return abalance;
    }

    public void setAbalance(Float abalance) {
        this.abalance = abalance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
