package rest;


import db.Database;
import models.LoginResponse;
import models.UserModel;
import com.google.gson.Gson;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ryan
 */
@Path("/LoginResource")
public class Login {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("user") String user, @QueryParam("pass") String pass) {
        LoginResponse result = new LoginResponse();
        String resultString;
        //Try lookup user and password
        UserModel userModel = getUserPass(user, pass);
        //see if user retrieved
        if (userModel == null || userModel.getUser() == null) {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setUserModel(userModel);
            System.out.println("Login success" + new Date().toString());
        }
        //convert to json
        Gson gson = new Gson();
        resultString = gson.toJson(result, LoginResponse.class);
        return resultString;
    }

    private UserModel getUserPass(String user, String pass) {
        Database db = new Database();
        db.connectDB();
        UserModel userModel = db.getUserPass(user, pass);
        db.closeDB();      
        return userModel;
    }

}
