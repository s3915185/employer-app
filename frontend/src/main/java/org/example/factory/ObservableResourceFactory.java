package org.example.factory;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

import java.util.*;

/**
 * Credit: https://stackoverflow.com/questions/32464974/javafx-change-application-language-on-the-run
 */
public class ObservableResourceFactory {
    public final static String RESOURCE_BUNDLE_NAME = "bundles/languageBundle";
    private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    @Getter
    private Language language = Language.FR;
    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener(((observable, oldValue, newValue) -> Locale.setDefault(newValue)));
    }

    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
    }
    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(Locale.ENGLISH, Locale.FRANCE));
    }
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    private final List<LanguageChangeListener> languageChangeListeners = new ArrayList<>();

    public void addLanguageChangeListener(LanguageChangeListener listener) {
        languageChangeListeners.add(listener);
    }
    public interface LanguageChangeListener {
        void onLanguageChange(Language newLanguage);
    }

    public final void setupResourceForLanguage() {
        localeProperty().set(Locale.FRENCH);
        Locale.setDefault(Locale.FRENCH);
        resourcesProperty().set(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, language.getLocale()));
        languageChangeListeners.forEach(listener -> listener.onLanguageChange(language));
    }
    public final Language switchResourceForLanguage() {
        if (language.locale.equals(Language.FR.locale)) {
            language = Language.EN;
            localeProperty().set(Locale.ENGLISH);
            Locale.setDefault(Locale.ENGLISH);
        }
        else {
            language = Language.FR;
            localeProperty().set(Locale.FRENCH);
            Locale.setDefault(Locale.FRENCH);
        }
        resourcesProperty().set(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, language.getLocale()));
        languageChangeListeners.forEach(listener -> listener.onLanguageChange(language));
        return language;
    }

    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }
            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }
    
    public enum Language {
        FR(Locale.FRENCH), EN(Locale.ENGLISH);
        private Locale locale;

        Language(Locale locale) {
            this.locale = locale;
        }
        public Locale getLocale() {
            return locale;
        }
    }
}
