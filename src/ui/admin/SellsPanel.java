package ui.admin;

import admin.AdService;
import entity.Orders;
import entity.Phone;
import entity.Sell;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// 판매량 조회 패널
// 표
public class SellsPanel extends JPanel {
    private static final int PAGE_SIZE = 10;
    private int currentPage = 1;
    private JTable table;
    private JLabel pageLabel;
    private DefaultTableModel tableModel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton reloadBtn; // 새로고침

    public SellsPanel() throws SQLException, ClassNotFoundException {
        init();
        addListener();
    }

    public void init() throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout());

        reloadBtn = new JButton("새로고침");
        add(reloadBtn, BorderLayout.NORTH);

        // 테이블 모델 설정
        String[] columnNames = {"기기 이름", "색상", "총 판매 수량", "총 판매 금액"};
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
        AdService adService = new AdService();
        List<Sell> sells = adService.getSalesByDevice(currentPage, PAGE_SIZE);

        // 데이터 출력 파트
        for(Sell sell : sells){
            // "기기 이름", "총 판매 수량", "총 판매 금액"
            tableModel.addRow(new Object[]{
                    sell.getPhoneName(),sell.getColor(), sell.getTotalAmount(),sell.getTotalSalePrice()
            });
        }

        pageLabel.setText("Page: " + currentPage);
    }
}
