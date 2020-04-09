package com.company.optional;

import com.company.exceptions.DataBaseException;
import com.company.exceptions.InvalidKeyException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class DBData {

    private int rows;

    private Map<String,List<String>> data = new HashMap<>();

    public DBData(ResultSet rs) throws SQLException {
        rows = 0;
        ResultSetMetaData rsmd = rs.getMetaData();
        int n = rsmd.getColumnCount();
        for(int i = 1; i <= n; i++){
            data.put(rsmd.getColumnName(i),new ArrayList<>());
        }

        while(rs.next()){
            rows++;
            for(int i = 1; i <= n; i++){
                data.get(rsmd.getColumnName(i)).add(rs.getString(i));
            }
        }
    }

    @Override
    public String toString() {
        return "DBData{\n" +
                "data = " + data +
                "\n}";
    }

    public int getRows() {
        return rows;
    }
/*
    public Map<String, List<String>> getData() {
        return data;
    }
*/

    public String getData(String key, int index) throws DataBaseException {
        if(!data.containsKey(key)) throw new InvalidKeyException(key);
        if(index >= rows) throw new DataBaseException("Index " + index + " is out of range: there are only " + rows + " rows.");

        return data.get(key).get(index);
    }

    public Set<String> getKeySet(){
        return data.keySet();
    }
}
