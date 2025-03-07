package client;

import connector.DBConnector;
import entity.Phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 전체 사용자의 기능 - 상품 조회
public class MenuService {
    Connection conn = DBConnector.getConnection();
    Statement stmt = conn.createStatement();
    PreparedStatement psmt = null;

    public MenuService() throws SQLException, ClassNotFoundException {
    }

    public List<Phone> getPhones(int page, int pageSize) throws SQLException {
        List<Phone> phones = new ArrayList<>();
        String query = "SELECT * FROM phone LIMIT ?, ?";

        psmt = conn.prepareStatement(query);
        psmt.setInt(1, (page - 1) * pageSize);
        psmt.setInt(2, pageSize);

        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            phones.add(new Phone(
                    rs.getInt("phone_id"),
                    rs.getString("phone_name"),
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getInt("price"),
                    rs.getInt("remain")
            ));
        }

        return phones;
    }

    // 사용자 별 장바구니 가져오기 ( 개인 할인가 적용 )
    public List<Phone> getBasketById( int c_id, int page, int pageSize )throws SQLException{
        List<Phone> basket = new ArrayList<>();
        String query = "SELECT p.phone_id, p.phone_name, p.color, p.brand, b.saleprice, p.remain "
                + "FROM basket b "
                + "JOIN phone p ON b.phone_phone_id = p.phone_id "
                + "WHERE b.customer_cust_id = ? "
                + "LIMIT ?, ?" ;

        psmt = conn.prepareStatement(query);
        psmt.setInt(1, c_id);
        psmt.setInt(2, (page - 1) * pageSize); // offset 계산
        psmt.setInt(3, pageSize); // 가져올 행 개수

        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            basket.add(new Phone(
                    rs.getInt("phone_id"),
                    rs.getString("phone_name"),
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getInt("saleprice"),
                    rs.getInt("remain")
            ));
        }

        return basket;
    }
}
