package org.crypto.staticdata;

import org.crypto.domain.BaseSecurity;
import org.crypto.domain.Option;
import org.crypto.domain.Stock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class H2DatabaseOps {

    private static Connection conn = null;
    private static Statement stmt = null;

    public static void createDatabase() {
        try {
            Class.forName("org.h2.Driver");
            conn = getConnection("jdbc:h2:~/crypto", "", "");
            stmt = conn.createStatement();
            ResultSet rset1 = conn.getMetaData().getTables(null, null, "STOCK", null);
            if (rset1.next())
                stmt.execute("drop table STOCK");

            ResultSet rset2 = conn.getMetaData().getTables(null, null, "OPTIONS", null);
            if (rset2.next())
                stmt.execute("drop table OPTIONS");

            stmt.execute("create table STOCK(ticker varchar(100) primary key, initial double , mean double , stddev double)");
            stmt.execute("insert into STOCK values('STOCK1', 100, 0.8, 0.6)");
            stmt.execute("insert into STOCK values('STOCK2', 150, 0.7, 0.3)");
            stmt.execute("create table OPTIONS(ticker varchar(100) primary key, type varchar(100), maturity double , strike double , underlyer varchar(100))");
            stmt.execute("insert into OPTIONS values('CALLOPTION1', 'CALL', 10, 65, 'STOCK1')");
            stmt.execute("insert into OPTIONS values('PUTOPTION1', 'PUT', 15, 145, 'STOCK2')");
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public static List<? super BaseSecurity>  getStocks(){
        List<? super BaseSecurity>  stocks = new ArrayList<>();
        try{
            ResultSet rs = stmt.executeQuery("select * from STOCK");
            while (rs.next()) {
                Stock stock = new Stock(rs.getString("ticker"));
                stock.setMean(rs.getDouble("mean"));
                stock.setStdev(rs.getDouble("stddev"));
                stock.setInitialPrice(rs.getDouble("initial"));
                stocks.add(stock);
            }

        }catch(Exception e){
            System.err.println(e);
        }
        return stocks;
    }

    public static List<? super BaseSecurity>  getOptions(String type){
        List<? super BaseSecurity>  options = new ArrayList<>();
        try{
            ResultSet rs = stmt.executeQuery("select * from OPTIONS");
            while (rs.next()) {
                String dbType = rs.getString("type");
                if(type.equalsIgnoreCase(dbType)) {
                    Option option = new Option(rs.getString("ticker"));
                    option.setType(type);
                    option.setMaturity(rs.getDouble("maturity"));
                    option.setStrike(rs.getDouble("strike"));
                    option.setUnderlyer(rs.getString("underlyer"));
                    options.add(option);
                }
            }

        }catch(Exception e){
            System.err.println(e);
        }
        return options;
    }
}
