package de.alarm_monitor.validation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    private final MainConfiguration mainConfiguration;

    @Inject
    Validator(final Provider<MainConfiguration> provider) {

        mainConfiguration = provider.get();
    }


    /**
     * @return true: all configuration is valid, false otherwise
     */
    public List<ValidationResult> validateMainConfig() {

        final List<ValidationResult> results = new ArrayList<>();

        results.addAll(testPdfFolder(mainConfiguration));


        return results;
    }


    private List<ValidationResult> testPdfFolder(final MainConfiguration mainConfiguration) {
        final List<ValidationResult> results = new ArrayList<>();
        final String path = mainConfiguration.path_folder();
        try {
            final File file = new File(path);
            if (!file.isDirectory()) {
                results.add(new ValidationResult(ValidationCode.critical, "In KOnfigurationsdatei ist der PDF-Ordner falsch konfioguriert: Kein Ordner"));
            }
        } catch (final Exception e) {
            results.add(new ValidationResult(ValidationCode.critical, "In KOnfigurationsdatei ist der PDF-Ordner falsch konfioguriert, Ordner konnte nicht gefunden werden"));
        }
        return results;
    }


}
