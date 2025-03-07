package client;

import connector.DBConnector;
import entity.Basket;
import entity.Customer;
import entity.Orders;
import entity.Phone;
import entity.enums.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 해당 사용자의 기능 - 장바구니 담기, 주문 내역 조회
public class CustService {
    Connection conn = DBConnector.getConnection();
    Statement stmt = conn.createStatement();
    PreparedStatement psmt = null;

    // 장바구니에 넣기
    public void goBasket(int p_id, Customer customerIn) throws SQLException{
        String query = "INSERT INTO basket (amount, saleprice, phone_phone_id, customer_cust_id) VALUES(1,?,?,?)";

        try (PreparedStatement psmt = conn.prepareStatement(query)) {
            // 할인률 적용
            int myPrice = checkGrade(p_id, customerIn);

            psmt.setInt(1, myPrice);  // 첫 번째 `?` → saleprice
            psmt.setInt(2, p_id);     // 두 번째 `?` → phone_phone_id
            psmt.setInt(3, customerIn.getId()); // 세 번째 `?` → customer_cust_id

            psmt.executeUpdate(); // 실행
        }
    }

    // 장바구니에서 제거
    public void deleteBasket(int p_id, Customer customerIn) throws SQLException{
        String query = "DELETE FROM basket WHERE phone_phone_id = ? AND customer_cust_id = ?";
        psmt = conn.prepareStatement(query);
        psmt.setInt(1, p_id);
        psmt.setInt(2, customerIn.getId());

        psmt.executeUpdate();
    }

    // 장바구니에 있는지 확인 ( 제거 전 )
    public boolean checkBasket(int p_id, Customer customerIn) throws SQLException{
        String query = "SELECT 1 FROM basket WHERE phone_phone_id = ? AND customer_cust_id = ?";
        psmt = conn.prepareStatement(query);
        psmt.setInt(1, p_id);
        psmt.setInt(2, customerIn.getId());
        ResultSet rs = psmt.executeQuery();

        if(rs.next()){
            return true;
        } else{
            return false;
        }
    }
    // 등급 체크 후 할인률 적용
    public int checkGrade (int p_id, Customer customerIn) throws SQLException {
        double myPrice = Double.MAX_VALUE;

        String query = "SELECT price FROM phone WHERE phone_id = ?";
        psmt = conn.prepareStatement(query);
        psmt.setInt(1, p_id);

        ResultSet rs = psmt.executeQuery();
        if(rs.next()) {
            myPrice = rs.getInt("price");
        }

        String grade = customerIn.getGrade();
        switch (grade){
            case "Bronze":{
                myPrice *= 0.95;
                break;
            }
            case "Silver":{
                myPrice *= 0.9;
                break;
            }
            case "Gold":{
                myPrice *= 0.85;
                break;
            }
            case "VIP" : {
                myPrice *= 0.7;
                break;
            }
        }

        return (int) myPrice;
    }

    // 할인률 적용 후 orders 로 보내기 + 장바구니에서 제외 ( 메서드 사용 ) + 재고 낮추기 ( 메서드 사용 )
    public void buy(int p_id, Customer customerIn) throws SQLException{
        boolean isExists = false;
        // 할인률 적용
        int myPrice = checkGrade(p_id, customerIn);

        String query = "INSERT INTO orders (amount, phone_phone_id, customer_cust_id, saleprice, order_date)" +
                " VALUES (1,?,?,?,?)";

        psmt = conn.prepareStatement(query);
        psmt.setInt(1, p_id);
        psmt.setInt(2, customerIn.getId());
        psmt.setInt(3, myPrice);
        psmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));

        psmt.executeUpdate();

        afterBuy(p_id);

        isExists = checkBasket(p_id, customerIn);
        if(isExists){
            deleteBasket(p_id, customerIn);
        }

    }

    // 재고 낮추기
    public void afterBuy(int p_id) throws SQLException{
        String query = "UPDATE phone SET remain = remain - 1 WHERE phone_id = ?";
        psmt = conn.prepareStatement(query);
        psmt.setInt(1, p_id);

        psmt.executeUpdate();
    }

    // 내 주문 내역
    public List<Orders> getOrdersById(int id, int page, int pageSize) throws SQLException{
        List<Orders> orders = new ArrayList<>();
        String query = "SELECT o.order_id, o.saleprice, o.order_date, " +
                "p.phone_name, c.cust_name, c.number, c.grade " +
                "FROM orders o " +
                "JOIN phone p ON o.phone_phone_id = p.phone_id " +
                "JOIN customer c ON o.customer_cust_id = c.cust_id " +
                "WHERE c.cust_id = ? " +
                "LIMIT ?, ?";

        psmt = conn.prepareStatement(query);
        psmt.setInt(1, id);
        psmt.setInt(2, (page - 1) * pageSize); // offset 계산
        psmt.setInt(3, pageSize); // 가져올 행 개수

        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            orders.add(new Orders(
                    rs.getInt("order_id"),
                    rs.getString("grade"),
                    rs.getString("phone_name"),   // 기기 이름 추가
                    rs.getString("cust_name"),    // 고객 이름 추가
                    rs.getString("number"),  // 고객 전화번호 추가
                    rs.getInt("saleprice"),
                    rs.getDate("order_date")
            ));
        }

        return orders;
    }

    public CustService() throws SQLException, ClassNotFoundException {
    }
}
