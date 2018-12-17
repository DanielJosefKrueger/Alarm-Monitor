package realtests;

import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;
import org.aeonbits.owner.event.ReloadListener;

import java.io.File;

public class TestConfiguration implements MainConfiguration {

    private final File pdfFolder;

    TestConfiguration(File pdfFolder){
        this.pdfFolder = pdfFolder;
    }


    @Override
    public String path_folder() {
        return pdfFolder.getAbsolutePath();
    }

    @Override
    public String path_tesseract() {
        return "C:\\\\Program Files (x86)\\\\Tesseract-OCR";
    }

    @Override
    public Boolean should_filter_einsatzmittel() {
        return false;
    }

    @Override
    public String filter_einsatzmittel() {
        return "Gangkofen";
    }

    @Override
    public boolean isEmailActive() {
        return false;
    }

    @Override
    public boolean isPrintingActive() {
        return false;
    }

    @Override
    public int numerOfCopies() {
        return 0;
    }

    @Override
    public int monitor() {
        return 0;
    }

    @Override
    public String getOcrPacket() {
        return "eng";
    }

    @Override
    public String getRoutingLinkBegin() {
        return null;
    }

    @Override
    public Long getDelayPdf() {
        return 10l;
    }

    @Override
    public String getEmailAdmin() {
        return null;
    }

    @Override
    public long getIntervalEmailAdmin() {
        return 0;
    }

    @Override
    public long getMonitorResetTime() {
        return 0;
    }

    @Override
    public boolean isBackUp() {
        return false;
    }

    @Override
    public int getPort() {
        return 13880;
    }

    @Override
    public boolean convertToPng() {
        return true;
    }

    @Override
    public int getDpiPng() {
        return 300;
    }

    @Override
    public void reload() {

    }

    @Override
    public void addReloadListener(ReloadListener listener) {
    }

    @Override
    public void removeReloadListener(ReloadListener listener) {
    }



    public static class TestingConfigurationProvider implements Provider<MainConfiguration>{

        private final File pdfFolder;

        TestingConfigurationProvider(File pdfFolder){
            this.pdfFolder = pdfFolder;
        }

        @Override
        public MainConfiguration get() {
            return new TestConfiguration(pdfFolder);
        }
    }

}
