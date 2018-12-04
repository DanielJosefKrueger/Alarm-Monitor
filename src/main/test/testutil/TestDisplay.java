package testutil;

import de.alarm_monitor.main.AlarmFax;
import de.alarm_monitor.visual.IDisplay;


public class TestDisplay implements IDisplay {

    private String reporter;
    private String operationResources;
    private AlarmFax alarmfax;


    @Override
    public void changeReporter(final String name) {
    }

    @Override
    public void changeOperationNumber(final String operationNumber) {
        final String operationNumber1 = operationNumber;
    }

    @Override
    public void changeAlarmTime(final String alarmTime) {
        final String alarmTime1 = alarmTime;
    }

    @Override
    public void changeKeyWord(final String keyWord) {
        final String keyWord1 = keyWord;
    }

    @Override
    public void changeComment(final String comment) {
        final String comment1 = comment;
    }

    @Override
    public void changeAddress(final String adresse) {
        final String address = adresse;
    }

    @Override
    public void changeOperationRessources(final String operationRessources) {
    }

    @Override
    public void resetAlarm() {

    }

    @Override
    public void activateAlarm(String operationResources) {

    }


    @Override
    public void changeAlarmFax(final AlarmFax alarmFax) {
        this.alarmfax = alarmFax;
    }

    public AlarmFax getAlarmfax() {
        return alarmfax;
    }
}
