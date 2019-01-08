package realtests;

import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.correcting.TextCorrecterImpl;
import de.alarm_monitor.email.EMailList;
import de.alarm_monitor.extracting.ExtractorImpl;
import de.alarm_monitor.main.Start;
import de.alarm_monitor.main.SystemInformation;
import de.alarm_monitor.parsing.OCRProcessorImpl1;
import de.alarm_monitor.parsing.PngConverter;
import de.alarm_monitor.processing.FaxProcessor;
import de.alarm_monitor.processing.FaxProzessorImpl;
import de.alarm_monitor.util.AddressFinder;
import de.alarm_monitor.util.AlarmResetter;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testutil.TestDisplay;

import java.io.File;

public class TestinEnvironment {
    private TestDisplay display;
    private SystemInformation systemInformation;
    private final File root;

    @Mock
    AlarmResetter alarmResetter;

    @Mock
    EMailList eMailList;

    @Mock
    AddressFinder addressFinder;

    FaxProcessor faxProcessor;

    TestinEnvironment(File root) {


        this.root = root;
    }

    public void processPdf(File pdf) {
        MockitoAnnotations.initMocks(this);
        systemInformation = new TestSystemInformationImpl(root);


        Provider<MainConfiguration> mainConfigurationProvider = new TestConfiguration.TestingConfigurationProvider(root);
        // Provider<MainConfiguration> mainConfigurationProvider = new MainConfigurationLoader(systemInformation);

        display = new TestDisplay();
        Start.setDisplay(display);
        TestAlertAdminReporter testAlertAdminReporter = new TestAlertAdminReporter(mainConfigurationProvider);


        // new PrintingService(testAlertAdminReporter, pdf, TestinEnvironment.mainConfiguration).start();
        faxProcessor  = new FaxProzessorImpl(alarmResetter,
                new OCRProcessorImpl1(mainConfigurationProvider, new PngConverter(systemInformation, mainConfigurationProvider), systemInformation),
                new TextCorrecterImpl(systemInformation),
                new ExtractorImpl(mainConfigurationProvider),
                eMailList,
                mainConfigurationProvider,
                addressFinder,
                testAlertAdminReporter);
        faxProcessor.processAlarmFax(pdf);

    }

    public TestDisplay getDisplay() {
        return display;
    }


}