package de.alarm_monitor.main;

import static de.alarm_monitor.main.Start.VERSION;

public class AlarmFax {

    private String reporter = "Mitteiler nicht gesetzt";
    private String operatioNumber = "Einsatznummer nicht gesetzt";
    private String alarmTime = "Keine Alarmzeit gesetzt";
    private String keyword = "Keine Schlüsselwort gesetzt";
    private String comment = "Keine Bemerkung";
    private String address = "Keine Adresse gesetzt";
    private String operationRessources = "Keine Einsatzmittel gesetzt";
    private String link = null;

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(final String reporter) {
        this.reporter = reporter;
    }

    public String getOperationNumber() {
        return operatioNumber;
    }

    public void setOperatioNumber(final String operatioNumber) {
        this.operatioNumber = operatioNumber;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(final String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getOperationRessources() {
        return operationRessources;
    }

    public void setOperationRessources(final String operationRessources) {
        this.operationRessources = operationRessources;
    }


    public String toEmailHtml() {
        final StringBuilder emailBuilder = new StringBuilder();
        emailBuilder.append("<h1>Informationen zum Alarm</h1>");
        emailBuilder.append("<h2>Allgemein</h2>");
        final String operationNumber = this.getOperationNumber();
        if (operationNumber.length() > 1) {
            emailBuilder.append("Einsatznummer: ").append(operationNumber);
        } else {
            emailBuilder.append("Die Einsatznummer konnte nicht ermittelt werden");
        }
        emailBuilder.append("<br>");

        final String alarmTime = this.getAlarmTime();
        if (alarmTime.length() > 1) {
            emailBuilder.append("Alarmzeit: ").append(alarmTime);
        } else {
            emailBuilder.append("Leider konnte die Alarmzeit nicht ermittelt werden");
        }
        emailBuilder.append("<br>");

        final String reporter = this.getReporter();
        if (reporter.length() > 1) {
            emailBuilder.append("Mitteiler: ").append(reporter);
        } else {
            emailBuilder.append("Leider konnte der Melder nicht ermittelt werden");
        }
        emailBuilder.append("<br>");


        final String keyword = this.getKeyword();
        if (keyword.length() > 1) {
            emailBuilder.append("Schlagwort: ").append(keyword);
        } else {
            emailBuilder.append("Leider konnte das Schlagwort nicht ermittelt werden");
        }
        emailBuilder.append("<br><br>");

        final String address = this.getAddress();
        emailBuilder.append("<h2>Adresse: </h2>");
        if (address.length() > 1) {
            emailBuilder.append(address);
        } else {
            emailBuilder.append("Die Adresse konnte nicht ermittelt werden");
        }
        emailBuilder.append("<br><br>");


        final String operationRessources = this.getOperationRessources();
        emailBuilder.append("<h2>Einsatzmittel:</h2>");
        if (operationRessources.length() > 1) {
            emailBuilder.append(operationRessources);
        } else {
            emailBuilder.append("Die Einsatzmittel konnten nicht ermittelt werden");
        }
        emailBuilder.append("<br><br>");

        final String comment = this.getComment();
        emailBuilder.append("<h2>Bemerkung:</h2>");
        if (comment.length() > 1) {
            emailBuilder.append(comment);
        } else {
            emailBuilder.append("Keine Bemerkung gefunden");
        }
        emailBuilder.append("<br><br>");

        emailBuilder.append("<h2>Link zum Routenplaner von Google:</h2>");

        if (link != null) {
            emailBuilder.append("<a href=\"").append(this.getLink()).append("\">Zu Google Maps</a>");
        } else {
            emailBuilder.append("Es konnte kein Link ermittelt werden.").append("<br>");
        }

        emailBuilder.append("<br>Version: " + VERSION);
        final String email = emailBuilder.toString();
        return email.replaceAll("\n", "<br>");
    }

    public String toEmailPlainText() {
        final StringBuilder email = new StringBuilder();
        email.append("Informationen zum Alarm\n");

        final String operationNumber = this.getOperationNumber();
        if (operationNumber.length() > 1) {
            email.append("Einsatznummer: ").append(operationNumber);
        } else {
            email.append("Die Einsatznummer konnte nicht ermittelt werden");
        }
        email.append("\n");

        final String alarmTime = this.getAlarmTime();
        if (alarmTime.length() > 1) {
            email.append("Alarmzeit: ").append(alarmTime);
        } else {
            email.append("Leider konnte die Alarmzeit nicht ermittelt werden");
        }
        email.append("\n");

        final String reporter = this.getReporter();
        if (reporter.length() > 1) {
            email.append("Mitteiler: ").append(reporter);
        } else {
            email.append("Leider konnte der Melder nicht ermittelt werden");
        }
        email.append("\n");


        final String keyword = this.getKeyword();
        if (keyword.length() > 1) {
            email.append("Schlagwort: ").append(keyword);
        } else {
            email.append("Leider konnte das Schlagwort nicht ermittelt werden");
        }
        email.append("\n\n");

        final String address = this.getAddress();
        if (address.length() > 1) {
            email.append("Adresse: \n").append(address);
        } else {
            email.append("Die Adresse konnte nicht ermittelt werden");
        }
        email.append("\n\n");


        final String operationRessources = this.getOperationRessources();
        if (operationRessources.length() > 1) {
            email.append("Einsatzmittel: \n").append(operationRessources);
        } else {
            email.append("Die Einsatzmittel konnten nicht ermittelt werden");
        }
        email.append("\n\n");

        final String comment = this.getComment();
        if (comment.length() > 1) {
            email.append("Bemerkung:\n").append(comment);
        } else {
            email.append("Keine Bemerkung gefunden");
        }
        email.append("\n\n");

        email.append("Link zum Routenplaner von Google:\n");


        if (link != null) {
            email.append(this.getLink()).append("\n");
        } else {
            email.append("Es konnte kein Link ermittelt werden.").append("\n");
        }
        email.append("\nVersion: " + VERSION);
        return email.toString();
    }


}
