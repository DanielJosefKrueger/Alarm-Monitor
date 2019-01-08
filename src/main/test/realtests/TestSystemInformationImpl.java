package realtests;

import de.alarm_monitor.main.SystemInformation;

import java.io.File;


public class TestSystemInformationImpl implements SystemInformation {

    private final File root;

    public TestSystemInformationImpl(File root) {
        this.root = root;
    }


    @Override
    public File getWorkingFolder() {
        return root;
    }

    @Override
    public File getConfigFolder() {
        return new File("testingfolder\\config");
    }

    @Override
    public File getLoggingFolder() {
        return root;
    }

    @Override
    public File getProjectDirectory() {
        return root;
    }
}
