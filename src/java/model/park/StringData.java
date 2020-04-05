package model.park;

import dbUtils.FormatUtils;
import java.sql.ResultSet;


/* The purpose of this class is just to "bundle together" all the 
 * character data that the user might type in when they want to 
 * add a new Customer or edit an existing customer.  This String
 * data is "pre-validated" data, meaning they might have typed 
 * in a character string where a number was expected.
 * 
 * There are no getter or setter methods since we are not trying to
 * protect this data in any way.  We want to let the JSP page have
 * free access to put data in or take it out. */
public class StringData {

    public String parkId = "";
    public String name = "";
    public String image = "";
    public String description = "";
    public String rating = "";
    public String cost = "";
    public String webUserId = ""; // Foreign Key
    public String webUserImage = ""; // getting it from joined table.
    public String webUserEmail = ""; // getting it from joined table.
    
    public String errorMsg = "";

    // default constructor leaves all data members with empty string (Nothing null).
    public StringData() {
    }

    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.parkId = FormatUtils.formatInteger(results.getObject("park_id"));
            this.name = FormatUtils.formatString(results.getObject("name"));
            this.image = FormatUtils.formatString(results.getObject("park.image"));
            this.description = FormatUtils.formatString(results.getObject("description"));
            this.rating = FormatUtils.formatDouble(results.getObject("rating"));
            this.cost = FormatUtils.formatDollar(results.getObject("cost"));
            this.webUserId = FormatUtils.formatInteger(results.getObject("web_user.web_user_id"));
            this.webUserImage = FormatUtils.formatString(results.getObject("web_user.image"));
            this.webUserEmail = FormatUtils.formatString(results.getObject("web_user.user_email"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.park.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }

    public int getCharacterCount() {
        String s = this.parkId + this.name + this.image + this.description
                + this.rating + this.cost + this.webUserId
                + this.webUserImage + this.webUserEmail;
        return s.length();
    }

    public String toString() {
        return "Park Id: " + this.parkId
                + ", Name: " + this.name
                + ", Image: " + this.image
                + ", Description: " + this.description
                + ", Rating: " + this.rating
                + ", Cost: " + this.cost
                + ", Web User Id: " + this.webUserId
                + ", Web User Image: " + this.webUserImage
                + ", Web User Email: " + this.webUserEmail;
    }
}
