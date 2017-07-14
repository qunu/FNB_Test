package models;


import java.util.List;


/**
 *
 * @author ryan
 */
public class CalcBreakDownResponse {

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the breakDown
     */
    public List<String> getBreakDown() {
        return breakDown;
    }

    /**
     * @param breakDown the breakDown to set
     */
    public void setBreakDown(List<String> breakDown) {
        this.breakDown = breakDown;
    }
    
    private boolean success = false;
    private String error;
    private List<String> breakDown;
    
    
}
