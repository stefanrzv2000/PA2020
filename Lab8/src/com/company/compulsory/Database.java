package com.company.compulsory;

import com.company.exceptions.DataBaseException;
import com.company.exceptions.InvalidTableNameException;
import com.company.optional.DBData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {

    private static Database instance;

    private Connection conn;

    private Database(){

    }

    private void connect() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost/MusicAlbums?useLegacyDatetimeCode=false&serverTimezone=America/New_York&useSSL=false";
        String USER = "dba";
        String PASS = "sql";
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public List<String> getTableNames() throws SQLException {
        List<String> tables = new ArrayList<>();

        connect();

        var dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getTables(null,null,null,null);

        while(rs.next()){
            tables.add(rs.getString("TABLE_NAME"));
        }

        conn.close();

        return tables;
    }

    public int insert(String table, Map<String, String> values) throws InvalidTableNameException, SQLException {

        if(!getTableNames().contains(table)){
            throw new InvalidTableNameException(table);
        }

        StringBuilder columnNames = new StringBuilder(), valuesToAdd = new StringBuilder();

        for(var column:values.keySet()){
            columnNames.append(column).append(", ");
            valuesToAdd.append(values.get(column)).append(", ");
        }

        columnNames.setLength(columnNames.length() - 2);
        valuesToAdd.setLength(valuesToAdd.length() - 2);

        String sql = "insert into " + table + "(" + columnNames.toString() + ")" + " values (" + valuesToAdd.toString() + ");";
        System.out.println(sql);

        connect();

        Statement statement = conn.createStatement();

        int i = statement.executeUpdate(sql,1);

        var rs = statement.getGeneratedKeys();

        int columns = rs.getMetaData().getColumnCount();

        int id = 0;

        if(columns > 0) {
            while (rs.next()) {
                id = rs.getInt(1);
            }
        }

        conn.close();

        return id;
    }

    public DBData select(String sql) throws DataBaseException, SQLException {

        if(sql.contains(";")) throw new DataBaseException("Invalid SQL statement: more than one statement!");

        connect();

        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql + ";");

        //System.out.println(sql);

        DBData data = new DBData(rs);

        conn.close();

        return data;
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
}
