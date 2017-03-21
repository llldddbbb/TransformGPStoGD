package com.service;

import com.dao.GDDAO;
import com.model.DBResult;
import com.model.GDResult;
import com.util.DbUtil;
import com.util.JacksonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.List;

/**
 * Created by ldb on 2017/3/21.
 */
public class GDService {

    private GDDAO gddao=new GDDAO();
    private DbUtil dbUtil=new DbUtil();

    public void transform(){
        try {
            Connection con= dbUtil.getCon();
            List<DBResult> dbResultList=gddao.listDBResult(con,"dev9002a170100001_1min");
            for (DBResult dbResult:dbResultList){
                double longitude=dbResult.getLongitude();
                double latitude=dbResult.getLatitude();
                String url="http://restapi.amap.com/v3/assistant/coordinate/convert?locations="+longitude+","+latitude+"&coordsys=gps&output=json&key=322bdd5f9196288600746092338713c6";
                URL realUrl;
                realUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
                conn.connect();
                String result="";
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "UTF-8"));
                String line = "";
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                GDResult vo= JacksonUtil.readValue(result, GDResult.class);
                gddao.insert(con,Double.parseDouble(vo.getLocations().split(",")[0]),Double.parseDouble(vo.getLocations().split(",")[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        GDService gdService=new GDService();
        gdService.transform();
    }
}
