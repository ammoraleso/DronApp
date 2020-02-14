import com.DronApp.Constants.Constants;
import com.DronApp.Controllers.DronController;
import com.DronApp.Models.Coordinate;
import com.DronApp.Models.Dron;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DronControllerTest {

    private static Dron dron  = new Dron(new Coordinate(0, 0, Constants.NORTH));
    private static Dron dronF = new Dron(new Coordinate(10, 10, Constants.NORTH));

    @Test
    public void validateCoverage() {
        String line = "AAAAIAA";
        assertTrue(DronController.validateCoverage(line,dron));
    }

    @Test
    public void validateCoverageFail() {
        String line = "AAAAIAA";
        assertFalse(DronController.validateCoverage(line,dronF));
    }


}