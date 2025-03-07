package admin;

import connector.DBConnector;
import entity.Orders;
import entity.Sell;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 상품 등록, 판매량 조회 ( 각종 필터 ex- 갤럭시 or 애플, 기기 이름 ), 주문 내역 조회 ( 전체 ), 재고 충전
public class AdService  {
    Connection conn = DBConnector.getConnection();
    Statement stmt = conn.createStatement();
    PreparedStatement psmt = null;

    // 상품 등록
    public void register(JTextField name, JTextField price, JTextField color, JTextField remain, JTextField brand) throws SQLException {
        String query = "INSERT INTO phone (phone_name, price, color, remain, brand) VALUES(?,?,?,?,?)";
        psmt = conn.prepareStatement(query);
        psmt.setString(1, name.getText());
        psmt.setString(2, price.getText());
        psmt.setString(3, color.getText());
        psmt.setString(4, remain.getText());
        psmt.setString(5, brand.getText());

        psmt.executeUpdate();
    }

    // 중복 확인
    public boolean isExists(JTextField name, JTextField color) throws SQLException{
        String query = "SELECT 1 FROM phone WHERE phone_name = ? AND color = ? LIMIT 1";
        psmt = conn.prepareStatement(query);
        psmt.setString(1, name.getText());
        psmt.setString(2, color.getText());

        ResultSet rs = psmt.executeQuery();
        return rs.next();
    }

    // 수정
    public void update(JTextField name, JTextField price, JTextField color, JTextField remain, JTextField brand) throws SQLException {
        String query = "UPDATE phone " + "SET " +
                "phone_name = ?, " + "    price = ?, " + "    color = ?, " + "    remain = ?, " + "    brand = ? " +
                "WHERE phone_name = ? AND color = ?";

        psmt = conn.prepareStatement(query);
        psmt.setString(1, name.getText());
        psmt.setString(2, price.getText());
        psmt.setString(3, color.getText());
        psmt.setString(4, remain.getText());
        psmt.setString(5, brand.getText());
        psmt.setString(6, name.getText());
        psmt.setString(7, color.getText());

        psmt.executeUpdate();
    }

    // 기계별 판매량 ( 기본 페이지 데이터 )
    public List<Sell> getSalesByDevice(int page, int pageSize) throws SQLException {
        List<Sell> sells = new ArrayList<>();
        String query = "SELECT SUM(o.saleprice) as total_saleprice, SUM(o.amount) as total_amount, p.phone_name , p.color " +
                "FROM orders o " +
                "JOIN phone p ON o.phone_phone_id = p.phone_id " +
                "GROUP BY o.phone_phone_id LIMIT ?, ?";

        psmt = conn.prepareStatement(query);
        psmt.setInt(1, (page - 1) * pageSize); // offset 계산
        psmt.setInt(2, pageSize); // 가져올 행 개수

        ResultSet rs = psmt.executeQuery();
        while(rs.next()){
            sells.add(new Sell(
                    rs.getString("phone_name"),
                    rs.getInt("total_amount"),
                    rs.getInt("total_saleprice"),
                    rs.getString("color")
            ));
        }

        return sells;
    }

    // 총 주문 내역
    public List<Orders> getOrders(int page, int pageSize) throws SQLException{
        List<Orders> orders = new ArrayList<>();
        String query = "SELECT o.order_id, o.saleprice, o.order_date, " +
                "p.phone_name, c.cust_name, c.number, c.grade " +
                "FROM orders o " +
                "JOIN phone p ON o.phone_phone_id = p.phone_id " +
                "JOIN customer c ON o.customer_cust_id = c.cust_id " +
                "ORDER BY order_id "+
                "LIMIT ?, ?";

        psmt = conn.prepareStatement(query);
        psmt.setInt(1, (page - 1) * pageSize); // offset 계산
        psmt.setInt(2, pageSize); // 가져올 행 개수

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

    public AdService() throws SQLException, ClassNotFoundException {
    }

}
