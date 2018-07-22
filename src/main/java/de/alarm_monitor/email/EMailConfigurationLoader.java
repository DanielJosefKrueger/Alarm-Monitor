package de.alarm_monitor.email;


import com.google.inject.Inject;
import com.google.inject.Provider;
import de.alarm_monitor.main.SystemInformation;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;

public class EMailConfigurationLoader implements Provider<EMailConfiguration> {

    private final static String EMAIL_CONFIG_FILR = "email_config.properties";

    @Inject
    private EMailConfigurationLoader(final SystemInformation systemInformation) {
        ConfigFactory.setProperty("emailconfig", new File(systemInformation.getConfigFolder(), EMAIL_CONFIG_FILR).toURI().getRawPath());
    }

    public EMailConfiguration get() {
        return ConfigCache.getOrCreate(EMailConfiguration.class);
    }
}
