package de.alarm_monitor.email;

import org.aeonbits.owner.Config;


@Config.HotReload
@Config.Sources("file:${emailconfig}")
public interface EMailConfiguration extends Config {

    @Key("username")
    String username();

    @Key("password")
    String password();

    @Key("SMTP_Host")
    String smtpHost();

    @Key("SMTP_Port")
    int smtpPort();

    @DefaultValue("AlarmFax")
    @Key("email_betreff")
    String getEmailTopic();

}
