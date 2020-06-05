import com.DronApp.Controllers.DronController;
import com.DronApp.Controllers.FileController;
import com.DronApp.Interfaces.FileInterface;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class FileControllerTest {

    private static List<String> filesDrones;

    @Test
    public void validateStructure() {
        String path = "DDAIAD";
        assertTrue(FileInterface.validateStructure(path));
    }

    @Test
    public void validateStructureFail() {
        String path = "DDAIADSSASASXXXXXX";
        assertFalse(FileInterface.validateStructure(path));
    }

    @Test
    public void obtainRoutes() {
        DronController dronController = new DronController();
        String message = dronController.beginOrders();
        assertTrue(message.equals("The order was genedated succesfully"));
    }

    @Test
    public void listFileForFolder() {
        FileController.listFileForFolder();
        assertTrue(FileController.getFilesDrones()!=null);
    }

    @Test
    public void getFileControllerInstace(){
        FileController fileController = FileController.getFileControllerInstace();
        assertTrue(fileController!=null);
    }
}