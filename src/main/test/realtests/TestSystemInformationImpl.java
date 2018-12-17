package realtests;

import de.alarm_monitor.main.SystemInformation;

import java.io.File;


public class TestSystemInformationImpl implements SystemInformation {
    @Override
    public File getWorkingFolder() {
        return new File("testingfolder\\tmp");
    }

    @Override
    public File getConfigFolder() {
        return new File("testingfolder\\config");
    }

    @Override
    public File getLoggingFolder() {
        return new File("testingfolder\\tmp");
    }

    @Override
    public File getProjectDirectory() {
        return new File("testingfolder\\tmp");
    }
}
