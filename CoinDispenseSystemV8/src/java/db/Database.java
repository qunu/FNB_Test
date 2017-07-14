package db;


import models.UserModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author rpovey
 */
public class Database {

    Connection con;
    String driverClass = "com.mysql.jdbc.Driver";
    String username = "fnb";
    String host;
    String dbname;
    int port;
    String pw = "sMil3y-k1Ng #_n0_T3eTh";
    String url = "jdbc:mysql://127.0.0.1:3306/fnb";

    public Database() {
    }
    
    
    /**
     * Connects to Database based on object driver
     *
     */
    public void connectDB() {
        // Load the JDBC driver
        try {
            Class.forName(this.driverClass);
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading driver: " + e);
        }
        try {
            //Get database connection
            this.con = DriverManager.getConnection(this.url, this.username, this.pw);
            System.out.println("Connected!!!");
        } catch (SQLException ex) {
            System.err.println("Error Connecting to DB: " + ex);
        }
    }

    /**
     * Closes the connection to the Database
     *
     *
     */
    public void closeDB() {
        try {
            // Close connection
            this.con.close();
        } catch (SQLException ex) {
            System.err.println("Error Closing DB connection: " + ex);
        }
    }


    public UserModel getUserPass(String user, String pass) {
        UserModel userModel = new UserModel();
        try {
            PreparedStatement getUser = con.prepareStatement("select * from users where username = ? and password = ?");
            getUser.setString(1, user);
            getUser.setString(2, pass);

            ResultSet result = getUser.executeQuery();

            while (result.next()) {
                userModel.setUserId(result.getString("user_id"));
                userModel.setUser(result.getString("username"));
                userModel.setBalance(result.getFloat("current_balance"));
            }

        } catch (SQLException ex) {
            System.err.println("Error createing prepared statement : " + ex);
        }
        return userModel;
    }

    public boolean updateUserBalance(String userId, BigDecimal balance) {
        boolean updateResult = false;
        try {
            PreparedStatement getUser = con.prepareStatement("update users set current_balance = ? where user_id = ?");
            getUser.setBigDecimal(1, balance);
            getUser.setString(2, userId);
            updateResult = getUser.execute();

        } catch (SQLException ex) {
            System.err.println("Error createing prepared statement : " + ex);
        }
        return updateResult;
    }

}
