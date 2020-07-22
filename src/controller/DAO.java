/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.project2.javaswing.Task;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author dinhviethoang
 */
public class DAO {

    private Connection conn = null;
    static String databaseName = "UserDtb";
    static String url = "jdbc:mysql://localhost:3306/" + databaseName;

    static String userName = "root";
    static String password = "chi1matkhau";

    public DAO() {
        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addTask(Task t) {
        String sql = "INSERT INTO tbTask(date,note,priovity)" + "VALUES(?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new Date(t.getDate().getTime()));
            ps.setString(2, t.getNote());
            ps.setInt(3, t.getPriovity());

            ps.execute();
            return true;

        } catch (Exception e) {

        }
        return false;

    }

    public boolean editTask(Task t) {
        String sql = "UPDATE tbTask SET date=?,priovity=?,note=? where id =?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, new Date(t.getDate().getTime()));
            ps.setString(2, t.getNote());
            ps.setInt(3, t.getPriovity());
            ps.setInt(4, t.getId());

            ps.execute();
            return true;

        } catch (Exception e) {

        }
        return false;

    }

    public boolean deleteTask(Task t) {
        String sql = "DELETE FROM tbTask where id =?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, t.getId());

            ps.execute();
            return true;

        } catch (Exception e) {

        }
        return false;

    }

    public ArrayList<Task> getTaskList() {
        ArrayList<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM tbTask";
        Statement st = null;

        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Task t = new Task(rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getString("note"),
                        rs.getInt("priovity"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    

    public static void main(String[] args) {
        new DAO();
    }
}
