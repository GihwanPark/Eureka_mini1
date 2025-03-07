package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
// Statement 사용
public class DBConnector {
    private static Connection dbCon;

    private DBConnector() {}

    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        // 한 번 연결된 객체를 계속 사용
        // 즉, 연결되지 않은 경우에만 연결을 시도하겠다는 의미
        // → 싱글톤(디자인 패턴)

        if (dbCon == null)
        {
            String url = "jdbc:mysql://localhost:3306/sellphone";
            String user = "root";
            String pwd = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");	// Class 라는 이름을 가진 클래스, forName : 이름으로 찾아준다.

            dbCon = DriverManager.getConnection(url, user, pwd);

        }
        return dbCon;
        // 구성된 연결 객체 반환
    }

    public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException
    {
        if (dbCon == null)
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            dbCon = DriverManager.getConnection(url, user, pwd);
        }
        return dbCon;
    }

    public static void close() throws SQLException
    {
        if (dbCon != null)
        {
            if (!dbCon.isClosed())
            {
                dbCon.close();
            }
        }
        dbCon = null;
    }

//    public static void main(String[] args) throws Exception{
//        // MySQL 에 접근하기 위해 필요한 3가지
//        String url = "jdbc:mysql://localhost:3306/madang";
//        String user = "root";
//        String pwd = "root";
//        // Driver 테스트 ( DriverManager 에 자동 등록 )
////
//        // JDBC 드라이버 객체를 DriverManager 에 등록 단계 필요 <= 자동으로 처리
//
//        // Connection ( DB 연결 )
//        Connection con = DriverManager.getConnection(url, user, pwd);
//
//        // Statement ( sql 전달 객체 )
//        Statement stmt = con.createStatement();
//
//        // insert
//        {
//            String insertSql = "insert into customer values ( 6, '손흥민', '영국 토트넘', '010-6666-6666' ); ";
//            int ret = stmt.executeUpdate(insertSql);
//            System.out.println(ret);
//        }
//
//
//        con.close();
//    }
}