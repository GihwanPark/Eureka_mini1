package ui.client;

import entity.Customer;
import ui.admin.OrdersPanel;
import ui.admin.SellsPanel;

import javax.swing.*;

public class ClientPanel extends JFrame {
    private JTabbedPane tabbedPane;
    private Customer customer;

    public void init(){
        setTitle("휴대폰 판매 프로그램 - 고객용");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        try {
            MyOrdersPanel ordersPanel = new MyOrdersPanel(customer); // 주문 테이블 패널
            BuyPanel buyPanel = new BuyPanel(customer); // 구매 패널
            BasketPanel basketPanel = new BasketPanel(customer); // 장바구니
            tabbedPane.addTab("구매", buyPanel);
            tabbedPane.addTab("내 주문 내역", ordersPanel);
            tabbedPane.addTab("장바구니", basketPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(tabbedPane);
        setVisible(true);
    }

    public ClientPanel(Customer customerIn){
        customer = customerIn;
        init();
    }
}
