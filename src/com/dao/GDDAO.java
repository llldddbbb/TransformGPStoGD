package com.dao;

import com.model.DBResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldb on 2017/3/21.
 */
public class GDDAO {

    public Integer insert(Connection con, Double longitude, Double latitude)throws Exception{
        String sql="insert into test values(null,?,?)";
        PreparedStatement pstat=con.prepareStatement(sql);
        pstat.setDouble(1,longitude);
        pstat.setDouble(2,latitude);
        return pstat.executeUpdate();
    }
    public List<DBResult> listDBResult(Connection con,String tableName)throws Exception{
        String sql="select longitude,latitude from "+tableName;
        PreparedStatement pstat=con.prepareStatement(sql);
        ResultSet resultSet=pstat.executeQuery();
        List<DBResult> dbResultList=new ArrayList<>();
        while (resultSet.next()){
            DBResult dbResult=new DBResult();
            dbResult.setLongitude(resultSet.getDouble("longitude"));
            dbResult.setLatitude(resultSet.getDouble("latitude"));
            dbResultList.add(dbResult);
        }
    }

}
