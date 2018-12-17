package realtests;

import de.alarm_monitor.main.AlarmFax;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

public class RealTest {


    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    private TestinEnvironment testinEnvironment;

    @Before
    public void setup(){
        Configurator.initialize(null, new File("testingfolder/config/logconfig.xml").getAbsolutePath());
        testinEnvironment = new TestinEnvironment(folder.getRoot());
    }

    int counter=0;

    @Test
    public void testpdf1() throws Exception {


        String pdf = "1.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));

        pdf = "2.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));
        assertTrue(operationRessources.contains("Gangkofen 11/1"));




        pdf = "7.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();




        pdf = "9.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();

        pdf = "10.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();

        pdf = "11.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();

        pdf = "12.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();


        pdf = "13.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();

        pdf = "14.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));

        pdf = "15.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();
       // assertTrue(operationRessources.contains("Gangkofen 31/1"));

        pdf = "16.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 20/1"));

        pdf = "17.pdf";
        alarmFax = processTest(pdf, counter++);
        operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 20/1"));

    }

    @Test
    public void testpdf2() throws Exception {

        String pdf = "2.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));
        assertTrue(operationRessources.contains("Gangkofen 11/1"));

    }



    @Test
    public void testpdf3() throws Exception {

        String pdf = "3.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 65/1"));
        assertTrue(operationRessources.contains("Gangkofen 11/1"));

    }

    @Test
    public void testpdf4() throws Exception {
        String pdf = "4.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
    }

    @Test
    public void testpdf5() throws Exception {
        String pdf = "5.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 40/1"));
    }

    @Test
    public void testpdf6() throws Exception {
        String pdf = "6.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));
    }


    @Test
    public void testpdf7() throws Exception {
        String pdf = "7.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();

    }


    @Test
    public void testpdf8() throws Exception {
        String pdf = "8.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));
    }

    @Test
    public void testpdf9() throws Exception {
        String pdf = "9.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 40/1"));

    }

    @Test
    public void testpdf10() throws Exception {
        String pdf = "10.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 20/1"));

    }

    @Test
    public void testpdf11() throws Exception {
        String pdf = "11.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));

    }

    @Test
    public void testpdf12() throws Exception {
        String pdf = "12.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));

    }

    @Test
    public void testpdf13() throws Exception {
        String pdf = "13.pdf";
        AlarmFax alarmFax = processTest(pdf, counter++);
        String operationRessources = alarmFax.getOperationRessources();
        assertTrue(operationRessources.contains("Gangkofen 31/1"));
    }





    private AlarmFax processTest (String pdfPath, int counter) throws Exception {
        String pdf = "originalpdfs\\"+ pdfPath;

        //Files.copy(new File(pdf).toPath(), new File(folder.getRoot(), "pdfNo" + counter).toPath());
        testinEnvironment.processPdf(new File(pdf));
        int waiting = 0;
        while (testinEnvironment.getDisplay().getAlarmfax() == null) {
            if (waiting > 100) {
                throw new Exception("Timeout");
            }
            Thread.sleep(1000);
            waiting++;
        }
        return testinEnvironment.getDisplay().getAlarmfax();

    }



}
