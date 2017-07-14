package config;


import rest.CalcBreakDown;
import rest.Login;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author ryan
 */
@ApplicationPath("/resources")
public class ApplicationConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(Login.class,CalcBreakDown.class));
    } 
}
