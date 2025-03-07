import ui.admin.AdminPanel;
import ui.client.LoginPanel;

import java.sql.SQLException;

// 끝
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new LoginPanel();
        new AdminPanel();
    }
}