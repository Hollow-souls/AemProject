package domain;

public class trans {
    private String trans_time;
    private String trans_account;
    private String trans_money;
    private String trans_type;

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getTrans_account() {
        return trans_account;
    }

    public void setTrans_account(String trans_account) {
        this.trans_account = trans_account;
    }

    public String getTrans_money() {
        return trans_money;
    }

    public void setTrans_money(String trans_money) {
        this.trans_money = trans_money;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public trans(String trans_time, String trans_account, String trans_money, String trans_type) {
        this.trans_time = trans_time;
        this.trans_account = trans_account;
        this.trans_money = trans_money;
        this.trans_type = trans_type;
    }

    public trans() {
    }
}
