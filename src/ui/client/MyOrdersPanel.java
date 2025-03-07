package ui.client;

import client.CustService;
import entity.Customer;
import entity.Orders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

// 현재 사용자의 주문내역
// 표
public class MyOrdersPanel extends JPanel {
    private static final int PAGE_SIZE = 10;
    private int currentPage = 1;
    private JTable table;
    private JLabel pageLabel;
    private DefaultTableModel tableModel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton reloadBtn; // 새로고침
    private Customer customer;

    public MyOrdersPanel(Customer customerIn) throws SQLException, ClassNotFoundException {
        customer = customerIn;
        init();
        addListener();
    }

    public void init() throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout());

        reloadBtn = new JButton("새로고침");
        add(reloadBtn, BorderLayout.NORTH);

        // 테이블 모델 설정
        String[] columnNames = {"Order ID", "성명", "전화번호", "구매 기기", "구매 가격", "회원 등급", "구매 날짜"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 페이지네이션 패널
        JPanel buttonPanel = new JPanel();
        prevButton = new JButton("이전");
        nextButton = new JButton("다음");
        pageLabel = new JLabel("Page: " + currentPage);

        buttonPanel.add(prevButton);
        buttonPanel.add(pageLabel);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);

        updateTable(); // 초기 데이터 로드
    }

    public void addListener() {
        reloadBtn.addActionListener(e ->  {
            try {
                updateTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                try {
                    updateTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        nextButton.addActionListener(e -> {
            currentPage++;
            try {
                updateTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void updateTable() throws SQLException, ClassNotFoundException {
        tableModel.setRowCount(0); // 기존 데이터 삭제
        CustService custService = new CustService();
        List<Orders> orders = custService.getOrdersById(customer.getId(), currentPage, PAGE_SIZE);

        // 데이터 출력 파트
        for(Orders order : orders){
            // "Order ID", "성명", "전화번호", "구매 기기", "구매 가격", "구매 수량", "구매 날짜"
            tableModel.addRow(new Object[]{
                    order.getId(), order.getCustName(), order.getPhoneNumber(), order.getPhoneName(),
                    order.getSalePrice(), order.getGrade(), order.getDate()});
        }

        pageLabel.setText("Page: " + currentPage);
    }
}
