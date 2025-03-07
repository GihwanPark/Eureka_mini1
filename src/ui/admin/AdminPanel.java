package ui.admin;

import javax.swing.*;

// 관리자 메인 패널
public class AdminPanel extends JFrame {
    private JTabbedPane tabbedPane;
    public void init(){
        setTitle("휴대폰 판매 관리 시스템 - Admin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        try {
            OrdersPanel ordersPanel = new OrdersPanel(); // 주문 테이블 패널
            SellsPanel sellsPanel = new SellsPanel(); // 판매 조회 패널
            RegisterPanel registerPanel = new RegisterPanel();
            RemainPanel remainPanel = new RemainPanel();
            ManagementPanel managementPanel = new ManagementPanel();

            tabbedPane.addTab("주문 내역", ordersPanel);
            tabbedPane.addTab("판매 조회", sellsPanel);
            tabbedPane.addTab("기기 등록", registerPanel);
            tabbedPane.addTab("정보 수정", remainPanel);
            tabbedPane.addTab("재고 관리", managementPanel);
            tabbedPane.setSelectedIndex(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(tabbedPane);
        setVisible(true);
    }

    public AdminPanel(){
        init();
    }
}
