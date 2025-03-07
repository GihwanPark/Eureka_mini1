package admin;

import connector.DBConnector;
import entity.Phone;
import entity.Sell;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilterService {
    Connection conn = DBConnector.getConnection();
    Statement stmt = conn.createStatement();
    PreparedStatement psmt = null;

    public FilterService() throws SQLException, ClassNotFoundException {
    }

    public List<Phone> getUnderRemain(int remain) throws SQLException {
        List<Phone> phones = new ArrayList<Phone>();
        String query = "SELECT * FROM phone WHERE remain <= ? ";

        psmt = conn.prepareStatement(query);
        psmt.setInt(1, remain);

        psmt.executeQuery();
        ResultSet rs = psmt.executeQuery();
        while(rs.next()){
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
}
