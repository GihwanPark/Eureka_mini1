package ui.admin;

import admin.AdService;
import admin.FilterService;
import client.MenuService;
import entity.Orders;
import entity.Phone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;

public class ManagementPanel extends JPanel {
    private static final int PAGE_SIZE = 20;
    private int currentPage = 1;
    private JTable table;
    private JLabel pageLabel;
    private JFormattedTextField searchField;
    private DefaultTableModel tableModel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton searchButton;
    private FilterService filterService = new FilterService();

    public ManagementPanel() throws SQLException, ClassNotFoundException {
        init();
        addListener();
    }

    public void init() throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout());

        // 검색 패널 추가
        JPanel searchPanel = new JPanel();
        searchField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        searchField.setColumns(10);
        searchButton = new JButton("이하 검색");

        searchPanel.add(new JLabel("재고 : "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH); // 검색창을 상단에 배치

        // 테이블 모델 설정
        String[] columnNames = {"기기 이름", "출고가", "색상", "재고", "제조사"};
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
        searchButton.addActionListener(e -> {
            tableModel.setRowCount(0); // 기존 데이터 삭제
            List<Phone> phones = null;
            try {
                int value = ((Number) searchField.getValue()).intValue();
                phones = filterService.getUnderRemain(value);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            // 데이터 출력 파트
            for(Phone phone : phones){
                // "기기 이름", "출고가", "색상", "재고", "제조사"
                tableModel.addRow(new Object[]{
                        phone.getModel(), phone.getPrice(), phone.getColor(), phone.getAmount(), phone.getBrand()});
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
        MenuService menuService = new MenuService();
        List<Phone> phones = menuService.getPhones(currentPage, PAGE_SIZE);

        // 데이터 출력 파트
        for(Phone phone : phones){
            // "기기 이름", "출고가", "색상", "재고", "제조사"
            tableModel.addRow(new Object[]{
                    phone.getModel(), phone.getPrice(), phone.getColor(), phone.getAmount(), phone.getBrand()});
        }

        pageLabel.setText("Page: " + currentPage);
    }
}
