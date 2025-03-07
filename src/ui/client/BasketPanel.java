package ui.client;

import client.CustService;
import client.MenuService;
import entity.Customer;
import entity.Phone;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// 장바구니
// 여유있으면 구현
public class BasketPanel extends JPanel {
    private static final int PAGE_SIZE = 4;
    private int currentPage = 1;
    private Customer customer;
    private JButton prevButton;
    private JButton nextButton;
    private JButton reloadBtn;
    private JLabel pageLabel;
    private CustService custService;
    private JPanel gridPanel; // 그리드 패널을 필드로 선언

    public void init() throws SQLException, ClassNotFoundException {
        custService = new CustService();
        setLayout(new BorderLayout());

        reloadBtn = new JButton("새로고침");
        add(reloadBtn, BorderLayout.NORTH);
        reloadBtn.addActionListener(e -> {
            try {
                loadPageData(gridPanel, currentPage); // 그리드 패널 갱신
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // 그리드 레이아웃에 데이터 추가 (그리드 패널을 미리 선언)
        gridPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2 그리드
        loadPageData(gridPanel, currentPage);  // 초기 페이지 데이터 로드
        add(gridPanel, BorderLayout.CENTER); // 중앙에 그리드 배치

        // 페이지네이션 버튼
        JPanel paginationPanel = createPaginationPanel(); // 페이지네이션 패널 생성
        add(paginationPanel, BorderLayout.SOUTH); // 하단에 페이지네이션 패널 배치
    }

    public BasketPanel(Customer customerIn) throws SQLException, ClassNotFoundException {
        customer = customerIn;
        init();
    }

    private JPanel addMenuItem(int p_id, String imagePath, String name, int price, int stock, String color) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        Dimension btnSize = new Dimension(100, 25);

        // btn Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 2));

        // id 받아와서 생성할때 이벤트리스너 달아주면 되지않나?
        JButton btnBasket = new JButton("제거");
        btnBasket.setPreferredSize(btnSize);
        JButton btnBuy = new JButton("구매");
        btnBuy.setPreferredSize(btnSize);
        btnPanel.add(btnBasket);
        btnPanel.add(btnBuy);

        // EventListener
        btnBuy.addActionListener(e -> {
            try {
                if(stock < 1){
                    JOptionPane.showMessageDialog(this, "재고 소진!");
                } else {
                    custService.buy(p_id, customer); // TODO : 구현 해야함
                    JOptionPane.showMessageDialog(this, "주문 완료!");
                    loadPageData(gridPanel, currentPage); // 그리드 갱신
                }
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnBasket.addActionListener(e -> {
            try {
                custService.deleteBasket(p_id, customer);
                JOptionPane.showMessageDialog(this, "삭제 완료!");
                loadPageData(gridPanel, currentPage); // 그리드 갱신
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // 이미지 크기 조정
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 크기 조정 (200x200으로)
        ImageIcon resizedIcon = new ImageIcon(resizedImage); // 크기 조정된 아이콘 생성

        // 이미지 추가
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // 텍스트 패널 (이름, 가격, 재고, 장바구니 버튼, 구매 버튼)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(4, 2));

        textPanel.add(new JLabel("기기 : ", JLabel.CENTER));
        textPanel.add(new JLabel(name, JLabel.CENTER));
        textPanel.add(new JLabel("색상 : ", JLabel.CENTER));
        textPanel.add(new JLabel(color, JLabel.CENTER));
        textPanel.add(new JLabel("회원 할인가 : ", JLabel.CENTER));
        textPanel.add(new JLabel(String.valueOf(price), JLabel.CENTER));
        textPanel.add(new JLabel("재고 : ", JLabel.CENTER));
        textPanel.add(new JLabel(String.valueOf(stock), JLabel.CENTER));

        itemPanel.add(btnPanel, BorderLayout.NORTH);
        itemPanel.add(imageLabel, BorderLayout.CENTER);
        itemPanel.add(textPanel, BorderLayout.SOUTH);
        add(itemPanel);

        return itemPanel;
    }

    // 폰 목록 보여주기
    private void loadPageData(JPanel gridPanel, int page) throws SQLException, ClassNotFoundException {
        MenuService menuService = new MenuService();

        gridPanel.removeAll(); // 그리드 초기화
        List<Phone> phones = menuService.getBasketById(customer.getId(), page, PAGE_SIZE);
        for (Phone phone : phones) {
            gridPanel.add(addMenuItem(phone.getId(), "img/no-image.png", phone.getModel(), phone.getPrice(), phone.getAmount(), phone.getColor()));
        }

        gridPanel.revalidate(); // 레이아웃 갱신
        gridPanel.repaint(); // 화면 갱신
    }

    // 페이지네이션 패널 생성 (예시로 이전, 다음 버튼 추가)
    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new FlowLayout());
        JButton prevButton = new JButton("이전");
        JButton nextButton = new JButton("다음");

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--; // 이전 페이지로 이동
                try {
                    loadPageData(gridPanel, currentPage); // 데이터 로드
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        nextButton.addActionListener(e -> {
            currentPage++; // 다음 페이지로 이동
            try {
                loadPageData(gridPanel, currentPage); // 데이터 로드
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);

        return paginationPanel;
    }

}