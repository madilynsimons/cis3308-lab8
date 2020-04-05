package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.park.*;

// classes in my project
import dbUtils.*;

public class ParkView {

    public static StringDataList allParksAPI(DbConn dbc) {

        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT park_id, name, park.image, description, rating, cost, "+
                    "park.web_user_id, " +
                    "web_user.web_user_id, web_user.image, web_user.user_email "+
                    "FROM park, web_user where park.web_user_id = web_user.web_user_id " + 
                    "ORDER BY park_id ";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.add(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in ParkView.allParksAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

}