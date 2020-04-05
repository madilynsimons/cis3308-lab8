package model.park;

import dbUtils.DbConn;
import dbUtils.PrepStatement;
import dbUtils.ValidationUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbMods {

    /*
    Returns a "StringData" object that is full of field level validation
    error messages (or it is full of all empty strings if inputData
    totally passed validation.  
     */
    private static StringData validate(StringData inputData) {

        StringData errorMsgs = new StringData();

        // Validation
        errorMsgs.name = ValidationUtils.stringValidationMsg(inputData.name, 45, true);
        errorMsgs.description = ValidationUtils.stringValidationMsg(inputData.description, 1000, true);

        errorMsgs.rating = ValidationUtils.decimalValidationMsg(inputData.rating, false);
        errorMsgs.cost = ValidationUtils.decimalValidationMsg(inputData.cost, false);
        
        errorMsgs.parkId = ValidationUtils.integerValidationMsg(inputData.parkId, true);

        return errorMsgs;
    } // validate 

    public static StringData update(StringData inputData, DbConn dbc) {

        StringData errorMsgs = new StringData();
        errorMsgs = validate(inputData);
        if (errorMsgs.getCharacterCount() > 0) {  // at least one field has an error, don't go any further.
            errorMsgs.errorMsg = "Please try again";
            return errorMsgs;

        } else { // all fields passed validation

            /*
                String sql = "SELECT web_user_id, user_email, user_password, membership_fee, birthday, "+
                    "web_user.user_role_id, user_role_type "+
                    "FROM web_user, user_role where web_user.user_role_id = user_role.user_role_id " + 
                    "ORDER BY web_user_id ";
             */
            String sql = "UPDATE park SET name=?, description=?, rating=?, cost=? "
                    + "WHERE park_id = ?";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.name); // string type is simple
            pStatement.setString(2, inputData.description);
            pStatement.setBigDecimal(3, ValidationUtils.decimalConversion(inputData.rating));
            pStatement.setBigDecimal(4, ValidationUtils.decimalConversion(inputData.cost));
            pStatement.setInt(5, ValidationUtils.integerConversion(inputData.parkId));

            // here the SQL statement is actually executed
            int numRows = pStatement.executeUpdate();

            // This will return empty string if all went well, else all error messages.
            errorMsgs.errorMsg = pStatement.getErrorMsg();
            if (errorMsgs.errorMsg.length() == 0) {
                if (numRows == 1) {
                    errorMsgs.errorMsg = ""; // This means SUCCESS. Let the user interface decide how to tell this to the user.
                } else {
                    // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                    errorMsgs.errorMsg = numRows + " records were updated (expected to update one record).";
                }
            } else  {
                errorMsgs.errorMsg = "Failed to update";
            } 

        } // customerId is not null and not empty string.
        return errorMsgs;
    } // update

} // class
