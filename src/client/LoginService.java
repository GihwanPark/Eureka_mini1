package client;

import connector.DBConnector;
import entity.Customer;
import entity.enums.Grade;

import javax.swing.*;
import java.sql.*;

// 회원 가입, 로그인
public class LoginService {
    Connection conn = DBConnector.getConnection();
    Statement stmt = conn.createStatement();
    PreparedStatement psmt = null;

    // 가입 성공 로직
    public void join(JTextField name, JTextField number, JTextField carrier) throws SQLException {
        String query = "INSERT INTO customer (cust_name, carrier, number, grade) VALUES(?,?,?,?)";
        psmt = conn.prepareStatement(query);
        psmt.setString(1, name.getText());
        psmt.setString(2, carrier.getText());
        psmt.setString(3, number.getText());
        psmt.setString(4, Grade.Bronze.toString());

        psmt.executeUpdate();
    }

    // 번호로 중복검사
    public boolean isExists(JTextField number) throws SQLException {
        String query =  "SELECT 1 FROM customer WHERE number = ? LIMIT 1";
        psmt = conn.prepareStatement(query);
        psmt.setString(1, number.getText());

        ResultSet rs = psmt.executeQuery();
        return rs.next();
    }

    // 로그인 성공 로직
    public Customer login(JTextField number) throws SQLException {
        String query = "SELECT * FROM customer WHERE number = ? LIMIT 1";
        psmt = conn.prepareStatement(query);
        psmt.setString(1, number.getText());

        ResultSet rs = psmt.executeQuery();
        rs.next();
        return new Customer(
                rs.getInt("cust_id"),
                rs.getString("cust_name"),
                rs.getString("carrier"),
                rs.getString("number"),
                rs.getString("grade"));
    }

    public LoginService() throws SQLException, ClassNotFoundException {
    }
}
