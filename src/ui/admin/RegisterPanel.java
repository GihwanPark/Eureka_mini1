package ui.admin;

import admin.AdService;
import ui.client.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegisterPanel extends JPanel {
    private JLabel labName;
    private JLabel labPrice;
    private JLabel labColor;
    private JLabel labRemain;
    private JLabel labBrand;
    private JTextField texName;
    private JTextField texPrice;
    private JTextField texColor;
    private JTextField texRemain;
    private JTextField texBrand;
    private JButton btnJoin;

    public void init() {
        Dimension labSize = new Dimension(80, 30);
        int texSize = 10;
        Dimension btnSize = new Dimension(100, 25);

        setLayout(new GridLayout(6, 2, 5, 5));
        setBackground(Color.orange);

        labName = new JLabel("기기 : ");
        labName.setPreferredSize(labSize);
        texName = new JTextField(texSize);

        labPrice = new JLabel("출고가 : ");
        labPrice.setPreferredSize(labSize);
        texPrice = new JTextField(texSize);

        labColor = new JLabel("색상 : "); // TODO : 추후에 펼쳐지는 버튼식으로 수정
        labColor.setPreferredSize(labSize);
        texColor = new JTextField(texSize);

        labRemain = new JLabel("재고 : ");
        labRemain.setPreferredSize(labSize);
        texRemain = new JTextField(texSize);

        labBrand = new JLabel("제조사 : ");
        labBrand.setPreferredSize(labSize);
        texBrand = new JTextField(texSize);

        btnJoin = new JButton("추가");
        btnJoin.setPreferredSize(btnSize);
        btnJoin.setBackground(Color.WHITE);
        btnJoin.setEnabled(true);

        add(labName);
        add(texName);
        add(labPrice);
        add(texPrice);
        add(labColor);
        add(texColor);
        add(labRemain);
        add(texRemain);
        add(labBrand);
        add(texBrand);
        add(new JLabel()); // 빈 공간 채우기
        add(btnJoin);
    }

    public void addListener() throws SQLException, ClassNotFoundException {
        AdService  adService = new AdService();
        btnJoin.addActionListener(e -> {
            boolean isExist = false;
            try {
                isExist = adService.isExists(texName, texColor);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if (isExist) {
                JOptionPane.showMessageDialog(this, "이미 등록된 기기입니다.");
            } else {
                try {
                    adService.register(texName, texPrice, texColor, texRemain, texBrand);
                    JOptionPane.showMessageDialog(this, "등록 성공!");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public RegisterPanel() throws SQLException, ClassNotFoundException {
        init();
        addListener();
    }
}
