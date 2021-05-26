/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Gabriel Nguyen
 */
public class DBUtils implements Serializable {
    
    public static Connection makeConnection()throws Exception{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn  = DriverManager.getConnection("jdbc:sqlserver://iruadb.c1l548u8mxzm.us-east-2.rds.amazonaws.com;databaseName=LabWeb1", "admin", "1234abcd");
        return conn;
    }
}
