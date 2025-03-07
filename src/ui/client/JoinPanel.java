package ui.client;

import client.LoginService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class JoinPanel extends JFrame {

    private JLabel labName;
    private JLabel labPhone;
    private JLabel labCarrier;
    private JTextField texName;
    private JTextField texPhone;
    private JTextField texCarrier;
    private JButton btnJoin;

    public void init(){
        Dimension labSize = new Dimension(80,30);
        int texSize = 10;
        Dimension btnSize = new Dimension(100,25);
        JPanel joinPanel = new JPanel(new GridLayout(5,2,5,0));
        this.setContentPane(joinPanel);
        joinPanel.setBackground(Color.orange);

        labName = new JLabel("이름");
        labName.setPreferredSize(labSize);
        texName = new JTextField(texSize);
        labPhone = new JLabel("전화번호");
        labPhone.setPreferredSize(labSize);
        texPhone = new JTextField(texSize);
        labCarrier = new JLabel("통신사"); // TODO : 추후에 펼쳐지는 버튼식으로 수정
        labCarrier.setPreferredSize(labSize);
        texCarrier = new JTextField(texSize);
        btnJoin = new JButton("가입");
        btnJoin.setPreferredSize(btnSize);
        btnJoin.setBackground(Color.WHITE);

        joinPanel.add(labName);
        joinPanel.add(texName);
        joinPanel.add(labPhone);
        joinPanel.add(texPhone);
        joinPanel.add(labCarrier);
        joinPanel.add(texCarrier);
        joinPanel.add(btnJoin);

        btnJoin.setEnabled(true);

        setSize(350,150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addListner() throws SQLException, ClassNotFoundException {
        LoginService customerService = new LoginService();
        btnJoin.addActionListener(e -> { //중복검사
            boolean isExist = false;
            try {
                isExist = customerService.isExists(texPhone);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if(isExist){
                JOptionPane.showMessageDialog(this,"중복입니다");
            } else {
                try {
                    customerService.join(texName, texPhone, texCarrier);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, "가입 성공!");
                try {
                    new LoginPanel();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });
    }

    public JoinPanel() throws SQLException, ClassNotFoundException {
        init();
        addListner();
    }
}
