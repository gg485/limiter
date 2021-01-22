package dao;

import model.ResourceCommon;

import java.sql.*;


//由于dao比较简单，就用了原生jdbc
//没有提供写db的方法，因为资源限制一般由业务决定，而不是限流器本身。
public class QuotaDao {
    private static Connection c = null;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public QuotaDao() throws SQLException {
        String url="jdbc:mysql://127.0.0.1:3306/quota?characterEncoding=UTF-8&serverTimezone=UTC";
        c=DriverManager.getConnection(url,"root","gddsygy_12345");
    }
    public ResourceCommon DBResource(String key) throws SQLException {
        String sql="SELECT capacity, refresh_interval, algo FROM resources WHERE name = ? AND state = 0";
        PreparedStatement ps=c.prepareStatement(sql);
        ps.setString(1,key);
        ResultSet resultSet = ps.executeQuery();
        ResourceCommon resource=null;
        if(resultSet.next()){
            resource=new ResourceCommon();
            resource.setCapacity(resultSet.getLong("capacity"));
            resource.setRefreshInterval(resultSet.getLong("refresh_interval"));
            resource.setAlgorithm(resultSet.getLong("algo"));
        }
        return resource;
    }
}
