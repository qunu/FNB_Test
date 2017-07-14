package rest;


import db.Database;
import models.CalcBreakDownResponse;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ryan
 */
@Path("/CalcBreakDownResource")
public class CalcBreakDown {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String calcBreakDown(@QueryParam("userId") String userId, @QueryParam("dispense") BigDecimal dispense, @QueryParam("balance") BigDecimal balance) {
        String resultString;
        CalcBreakDownResponse result = new CalcBreakDownResponse();
        result.setSuccess(false);

        //Check paramers not null
        if (dispense != null && balance != null && userId != null) {
            //Process to check valid input to break up 
            //Need to remove decimals to not get precision errors (*100)
            BigDecimal workingDispense = dispense.multiply(new BigDecimal(100));
            BigDecimal workingBalance = balance.multiply(new BigDecimal(100));
            
            //Check no remainder when divided by 5c (00.5*100=5)
            BigDecimal validDenomination = workingDispense.remainder(new BigDecimal(5), MathContext.UNLIMITED);
            if (validDenomination.compareTo(new BigDecimal(BigInteger.ZERO)) == 0) {
                //calculate new current balance
                BigDecimal newBalance = workingBalance.subtract(workingDispense);
                //chech user has enough money
                if (dispense.compareTo(balance) < 0) {
                    //Get break down fo amount
                    List<String> breakMe = getBreakDown(workingDispense);
                    //Update the users current balance after breakdown created
                    updateBalance(userId, newBalance.divide(new BigDecimal(100)));
                    //set successfull response
                    result.setSuccess(true);
                    result.setBreakDown(breakMe);
                } else {
                    result.setSuccess(false);
                    result.setError("you dont have enough money");
                }

            } else {
                result.setSuccess(false);
                result.setError("1. Valid rand denominations are R100, R50, R20 and R10.\n"
                        + "	2. Valid coin denominations are R5, R2, R1, 50c, 25c, 10c, 5c.");
                System.out.println("1. Valid rand denominations are R100, R50, R20 and R10.\n"
                        + "	2. Valid coin denominations are R5, R2, R1, 50c, 25c, 10c, 5c.");
            }
        }
        
        Gson gson = new Gson();
        
        resultString = gson.toJson(result, CalcBreakDownResponse.class);
        System.out.println("login result : " + resultString);
        return resultString;
        
    }

//    1. Valid rand denominations are R100, R50, R20 and R10.
//	2. Valid coin denominations are R5, R2, R1, 50c, 25c, 10c, 5c.
//    
    private List<String> getBreakDown(BigDecimal dispense) {
        List<String> breakMe = new ArrayList<>();
        int value = dispense.divide(new BigDecimal(10000), MathContext.UNLIMITED).intValue();
        System.out.println("100 : " + value);
        if (value > 0) {
            breakMe.add(value + " x R100");
            dispense = dispense.subtract(new BigDecimal(value * 10000));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(5000), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x R50");
            dispense = dispense.subtract(new BigDecimal(value * 5000));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(2000), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x R20");
            dispense = dispense.subtract(new BigDecimal(value * 2000));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(1000), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x R10");
            dispense = dispense.subtract(new BigDecimal(value * 1000));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(500), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x R5");
            dispense = dispense.subtract(new BigDecimal(value * 500));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(200), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x R2");
            dispense = dispense.subtract(new BigDecimal(value * 200));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(100), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x R1");
            dispense = dispense.subtract(new BigDecimal(value * 100));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(50), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x 50c");
            dispense = dispense.subtract(new BigDecimal(value * 50));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(25), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x 25c");
            dispense = dispense.subtract(new BigDecimal(value * 25));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(10), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x 10c");
            dispense = dispense.subtract(new BigDecimal(value * 10));
        }
        value = dispense.divideToIntegralValue(new BigDecimal(5), MathContext.UNLIMITED).intValue();
        if (value > 0) {
            breakMe.add(value + " x 5c");
            dispense = dispense.subtract(new BigDecimal(value * 5));
        }

        return breakMe;
    }

    private boolean updateBalance(String userId, BigDecimal balance) {
        Database db = new Database();
        db.connectDB();
        boolean pass = db.updateUserBalance(userId, balance);
        db.closeDB();
        return pass;
    }

}
