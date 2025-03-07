package ui.client;

import client.LoginService;
import entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginPanel extends JFrame {
    private JLabel labPhone;
    private JTextField texPhone;
    private JButton btnLogin;
    private JButton btnJoin;

    public LoginPanel() throws SQLException, ClassNotFoundException {
        init();
        addListener();
    }

    public void init() {
        Dimension labSize = new Dimension(80,50);
        int texSize = 10;
        Dimension btnSize = new Dimension(100,25);
        JPanel loginPanel = new JPanel(new GridLayout(3,2,5,0));

        labPhone = new JLabel("전화번호");
        labPhone.setPreferredSize(labSize);
        labPhone.setForeground(Color.black);
        texPhone = new JTextField(texSize);
        texPhone.setBackground(Color.orange);
        texPhone.setForeground(Color.black);
        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(btnSize);
        btnLogin.setBackground(Color.white);
        btnJoin = new JButton("Join");
        btnJoin.setPreferredSize(btnSize);
        btnJoin.setBackground(Color.white);

        loginPanel.add(labPhone);
        loginPanel.add(texPhone);
        loginPanel.add(btnLogin);
        loginPanel.add(btnJoin);
        loginPanel.setSize(350,320);
        add(loginPanel,BorderLayout.SOUTH);
        loginPanel.setBackground(Color.orange);

        setSize(350,380);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void addListener() throws SQLException, ClassNotFoundException {
        //TODO: 먹통수정, for문 제대로 안돌음. 검사 안거침, for문 들어가서 그냥 빠져나옴
        LoginService customerService = new LoginService();
        btnLogin.addActionListener(e ->  {
            boolean isExist = false;
            try {
                isExist = customerService.isExists(texPhone);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if(isExist){
                try {
                    Customer customer = customerService.login(texPhone);
                    new ClientPanel(customer);
                    setVisible(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "회원이 아님!");
            }
        });

        btnJoin.addActionListener(e -> {
            try {
                new JoinPanel();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            setVisible(false);
        });
    }
}
