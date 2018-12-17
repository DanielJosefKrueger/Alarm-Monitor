package testutil;

import de.alarm_monitor.main.AlarmFax;
import de.alarm_monitor.visual.IDisplay;


public class TestDisplay implements IDisplay {

    private String reporter;
    private String operationResources;
    private AlarmFax alarmfax;


    String operationNumber;
    private String alarmTime;
    private String keyWord;
    private String comment;
    private String address;
    private String operationRessources;

    @Override
    public void changeReporter(final String name) {
    }

    @Override
    public void changeOperationNumber(final String operationNumber) {
        this.operationNumber = operationNumber;
    }

    @Override
    public void changeAlarmTime(final String alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Override
    public void changeKeyWord(final String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void changeComment(final String comment) {
        this.comment = comment;
    }

    @Override
    public void changeAddress(final String adresse) {
        this.address = adresse;
    }

    @Override
    public void changeOperationRessources(final String operationRessources) {
        this.operationRessources = operationRessources;
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


    public String getReporter() {
        return reporter;
    }

    public String getOperationResources() {
        return operationResources;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getComment() {
        return comment;
    }

    public String getAddress() {
        return address;
    }

    public String getOperationRessources() {
        return operationRessources;
    }
}
