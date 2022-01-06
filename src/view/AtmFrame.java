package view;

import domain.Atm;
import domain.trans;
import service.AtmService;
import util.BaseFrame;
import util.MySpring;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

@SuppressWarnings("all")
public class AtmFrame extends BaseFrame {

    private AtmFrame(String aname){
        super("中国银行");
        this.aname = aname;
        this.init();
    }
    private static AtmFrame atmFrame = null;
    public synchronized static AtmFrame getAtmFrame(String aname){
        if(atmFrame == null){
            atmFrame = new AtmFrame(aname);
        }
        return atmFrame;
    }

    //添加一个用来管理当前用户的用户名
    private String aname;

    //创建一个AtmService对象作为属性，支持着所有的业务：查询，存款，取款，转账
    private AtmService atmService = MySpring.getBean("service.AtmService");

    private JPanel mainPanel = new JPanel();
    private JLabel logoLabel = new JLabel();
    private JLabel titleLabel = new JLabel("中国银行");
    private JLabel balanceLabelCN = new JLabel();
    private JLabel balanceLabelEN = new JLabel();
    private JLabel selectServerLabelCN = new JLabel("您好！请选择所需服务");
    private JLabel selectServerLabelEN = new JLabel("Hello,Please Select Service");
    private JButton dropButton = new JButton("查询");
    private JButton tradeButton = new JButton("查询交易明细");
    private JButton exitButton = new JButton("退出");
    private JButton depositButton = new JButton("存款");
    private JButton withdrawalButton = new JButton("取款");
    private JButton transferButton = new JButton("转账");


    @Override
    protected void setFontAndSoOn() {
        mainPanel.setLayout(null);
        logoLabel.setBounds(20,20,80,80);
        logoLabel.setIcon(this.drawImage("src//img//logo.jpg",80,80));
        titleLabel.setBounds(120,30,260,60);
        titleLabel.setFont(new Font("微软雅黑",Font.ITALIC,32));

      /**  balanceLabelCN.setBounds(240,200,300,40);
        balanceLabelCN.setFont(new Font("微软雅黑",Font.BOLD,24));
        balanceLabelCN.setHorizontalAlignment(JTextField.CENTER);
        balanceLabelCN.setText("账户余额:￥"+atmService.query(aname));
        balanceLabelEN.setBounds(240,240,400,40);
        balanceLabelEN.setFont(new Font("微软雅黑",Font.BOLD,24));
        balanceLabelEN.setHorizontalAlignment(JTextField.CENTER);
        balanceLabelEN.setText("Account Balance:￥"+atmService.query(aname));
**/
        selectServerLabelCN.setBounds(260,190,300,40);
        selectServerLabelCN.setFont(new Font("微软雅黑",Font.BOLD,24));
        selectServerLabelCN.setHorizontalAlignment(JTextField.CENTER);
        selectServerLabelEN.setBounds(220,220,360,40);
        selectServerLabelEN.setFont(new Font("微软雅黑",Font.BOLD,24));
        selectServerLabelEN.setHorizontalAlignment(JTextField.CENTER);

        dropButton.setBounds(10,150,120,40);
        dropButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        dropButton.setBackground(Color.lightGray);
        dropButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tradeButton.setBounds(10,270,120,40);;
        tradeButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        tradeButton.setBackground(Color.lightGray);
        tradeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        exitButton.setBounds(10,390,120,40);
        exitButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        exitButton.setBackground(Color.lightGray);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        depositButton.setBounds(670,150,120,40);
        depositButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        depositButton.setBackground(Color.lightGray);
        depositButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        withdrawalButton.setBounds(670,270,120,40);
        withdrawalButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        withdrawalButton.setBackground(Color.lightGray);
        withdrawalButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        transferButton.setBounds(670,390,120,40);
        transferButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        transferButton.setBackground(Color.lightGray);
        transferButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void addElements() {
        mainPanel.add(logoLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(balanceLabelCN);
        mainPanel.add(balanceLabelEN);
        mainPanel.add(selectServerLabelCN);
        mainPanel.add(selectServerLabelEN);
        mainPanel.add(dropButton);
        mainPanel.add(tradeButton);
        mainPanel.add(exitButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawalButton);
        mainPanel.add(transferButton);
        this.add(mainPanel);
    }

    @Override
    protected void addListener() {

        tradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
                JLabel jl = new JLabel();// 注意类名别写错了。
                frame.setBounds(400, 300, 800, 600);//父窗口的坐标和大小
                JTable table=null;
                String[] index={"trans_count","trans_money","trans_time","trans_type"};
                Object[][] data=new Object[atmService.query1(aname).size()][index.length];
                for(int i=0;i<atmService.query1(aname).size();i++){
                    trans trans=atmService.query1(aname).get(i);
                    data[i][0]=trans.getTrans_account();
                    data[i][1]=trans.getTrans_money();
                    data[i][2]=trans.getTrans_time();
                    data[i][3]=trans.getTrans_type();
                }
                DefaultTableModel defaultModel = new DefaultTableModel(data, index);
                table=new JTable(defaultModel);
                table.setPreferredScrollableViewportSize(new Dimension(200, 200));//JTable的高度和宽度按照设定
                table.setFillsViewportHeight(true);
                frame.add(table);
                frame.setVisible(true);
            }
        });


        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
                JLabel jl = new JLabel();// 注意类名别写错了。
                frame.getContentPane().add(jl);

                //frame.getContentPane().add(balanceLabelCN);
                //frame.getContentPane().add(balanceLabelEN);
                frame.setBounds(400,300,400,300);
                jl.setText("账户余额:￥"+atmService.query(aname));
                jl.setFont(new Font("微软雅黑",Font.BOLD,24));
                jl.setBounds(400, 300, 400, 300);//父窗口的坐标和大小
                jl.setVerticalAlignment(JLabel.CENTER);
                jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
                // 参数 APPLICATION_MODAL：阻塞同一 Java 应用程序中的所有顶层窗口（它自己的子层次
                frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
                frame.setVisible(true);

            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = JOptionPane.showConfirmDialog(AtmFrame.this,"确定退出吗？");
                //0是  1否  2取消
                if(value == 0){
                    System.exit(0);
                }
            }
        });


        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String value = JOptionPane.showInputDialog(AtmFrame.this,"请输入存款金额");
                    if(value != null && !"".equals(value)){
                        Float money = Float.parseFloat(value);

                            if(money <= 0){
                                throw new NumberFormatException();
                            }
                        if(money%100==0) {
                            int count = atmService.deposit(aname, money);
                            if (count == 1) {
                                JOptionPane.showMessageDialog(AtmFrame.this, "存款成功");
                                balanceLabelCN.setText("账户余额:￥" + atmService.query(aname));
                                balanceLabelEN.setText("Account Balance:￥" + atmService.query(aname));
                            } else {
                                JOptionPane.showMessageDialog(AtmFrame.this, "存款失败");
                            }
                        }
                        else JOptionPane.showMessageDialog(AtmFrame.this, "存款必须为100的整数！");
                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this,"输入金额有误");
                }
            }
        });

        withdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String value = JOptionPane.showInputDialog(AtmFrame.this,"请输入取款金额");
                    if(value != null && !"".equals(value)){
                        Float money = Float.parseFloat(value);
                        if(money <= 0){
                            throw new NumberFormatException();
                        }
                        int count = atmService.withdrawal(aname,money);
                        if(money%100==0)
                        {
                            if (count == 1) {
                                JOptionPane.showMessageDialog(AtmFrame.this, "取款成功");
                                balanceLabelCN.setText("账户余额:￥" + atmService.query(aname));
                                balanceLabelEN.setText("Account Balance:￥" + atmService.query(aname));
                            } else if (count == -1) {
                                JOptionPane.showMessageDialog(AtmFrame.this, "余额不足");
                            } else {
                                JOptionPane.showMessageDialog(AtmFrame.this, "取款失败");
                            }
                        }
                        else JOptionPane.showMessageDialog(AtmFrame.this, "取款金额必须为100的倍数！");

                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this,"输入金额有误");
                }
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String to = JOptionPane.showInputDialog(AtmFrame.this,"请输入转账账户");
                    if((to.equals(aname)))
                        JOptionPane.showMessageDialog(AtmFrame.this,"不能对本账号进行转账！");
                    else {
                        if (to != null && !"".equals(to) && atmService.isExit(to)) {
                            String value = JOptionPane.showInputDialog(AtmFrame.this, "请输入转账金额");
                            if (value != null && !"".equals(value)) {
                                Float money = Float.parseFloat(value);
                                if (money <= 0) {
                                    throw new NumberFormatException();
                                }
                                int count = atmService.transfer(aname, to, money);
                                if (count == 2) {
                                    JOptionPane.showMessageDialog(AtmFrame.this, "转账成功");
                                    //balanceLabelCN.setText("账户余额:￥" + atmService.query(aname));
                                   // balanceLabelEN.setText("Account Balance:￥" + atmService.query(aname));
                                } else if (count == -1) {
                                    JOptionPane.showMessageDialog(AtmFrame.this, "余额不足");
                                } else {
                                    JOptionPane.showMessageDialog(AtmFrame.this, "转账失败");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(AtmFrame.this, "输入账号不存在");
                        }
                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this,"输入金额有误");
                }
            }
        });

    }

    @Override
    protected void setFrameSelf() {
        this.setBounds(300,200,800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}
