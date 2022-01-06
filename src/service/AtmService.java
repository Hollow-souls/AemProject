package service;

import dao.AtmDao;
import domain.Atm;
import domain.trans;
import util.MySpring;

import java.util.List;

public class AtmService {

    //Service层需要Dao持久层的支持
    private AtmDao dao = MySpring.getBean("dao.AtmDao");

    //登录
    public String login(String aname,String apassword){
        String result = "用户名或密码错误";
        Atm atm = dao.selectOne(aname);
        if(atm != null && atm.getApassword().equals(apassword)){
            result = "登录成功";
        }
        return result;
    }


    //查询
    public float query(String aname){
        return dao.selectOne(aname).getAbalance();
    }
    public List<trans> query1(String aname){
        return dao.selectTwo(aname);
    }


    //存款
    public int deposit(String ananme,float money){
        Atm atm = dao.selectOne(ananme);
        atm.setAbalance(atm.getAbalance()+money);
        return dao.update(atm,money,"存款");
    }


    //取款
    public int withdrawal(String aname,float money){
        Atm atm = dao.selectOne(aname);
        if(atm.getAbalance() >= money){
            atm.setAbalance(atm.getAbalance()-money);
            return dao.update(atm,money,"取款");//1成功   0失败
        }else {
            return -1;//余额不足
        }
    }


    //转账
    public int transfer(String from,String to,float money){
        Atm atmFrom = dao.selectOne(from);

        Atm atmTo = dao.selectOne(to);
        if(atmFrom.getAbalance()>=money){
            atmFrom.setAbalance(atmFrom.getAbalance()-money);
            atmTo.setAbalance(atmTo.getAbalance()+money);
            return dao.update(atmFrom,money,"转出到"+atmTo.getAname()) + dao.update(atmTo,money,"转入来自"+atmFrom.getAname());//2成功
        }else{
            return -1;//余额不足
        }

    }

    //开户
    public void open(String username,String aname,String apassword,float abalance){
        dao.insert(new Atm(username,aname,apassword,abalance));
    }

    //设计一个方法  判断账号名是否存在
    public boolean isExit(String aname){
        if(dao.selectOne(aname) != null){
            return true;
        }
        return false;
    }

    //销户
    public int drop(String aname){
        return dao.delete(aname);
    }

}
