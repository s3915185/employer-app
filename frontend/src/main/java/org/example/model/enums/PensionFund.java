package org.example.model.enums;

import com.sun.org.apache.xerces.internal.util.Status;
import org.example.constant.Constant;

import java.util.Locale;

public enum PensionFund {
    NONE(Constant.PENSION_FUND_NONE_DISPLAY_ENGLISH, Constant.PENSION_FUND_NONE_DISPLAY_FRENCH, ""),
    REGIONAL_PENSION_FUND(Constant.PENSION_FUND_REGIONAL_DISPLAY_ENGLISH, Constant.PENSION_FUND_REGIONAL_DATABASE_DISPLAY, Constant.PENSION_FUND_REGIONAL_DATABASE_DISPLAY),
    PROFESSIONAL_PENSION_FUND(Constant.PENSION_FUND_PROFESSIONAL_DISPLAY_ENGLISH, Constant.PENSION_FUND_PROFESSIONAL_DATABASE_DISPLAY, Constant.PENSION_FUND_PROFESSIONAL_DATABASE_DISPLAY);
    private final String englishVersionResourceBundle;
    private final String frenchVersionResourceBundle;
    private final String databaseVersion;

    PensionFund(String englishVersionResourceBundle, String frenchVersionResourceBundle, String databaseVersion) {
        this.englishVersionResourceBundle = englishVersionResourceBundle;
        this.frenchVersionResourceBundle = frenchVersionResourceBundle;
        this.databaseVersion = databaseVersion;
    }

    public static String getDisplayVersionResourceBundle(String databaseVersion, Locale locale) throws RuntimeException {
        for (PensionFund pensionFund : values()) {
            if (pensionFund.databaseVersion.equals(databaseVersion)) {
                return locale == Locale.FRENCH ? pensionFund.frenchVersionResourceBundle : pensionFund.englishVersionResourceBundle;
            }
        }
        throw new RuntimeException(String.valueOf(Status.UNKNOWN));
    }
    public static String getOtherLanguageDisplayVersionResourceBundle(String displayVersion, Locale locale) throws RuntimeException {

        for (PensionFund pensionFund : values()) {
            if (pensionFund.englishVersionResourceBundle.equals(displayVersion)) {
                return pensionFund.frenchVersionResourceBundle;
            }if (pensionFund.frenchVersionResourceBundle.equals(displayVersion)) {
                return pensionFund.englishVersionResourceBundle;
            }
//            if (locale == Locale.FRENCH) {
//                if (pensionFund.englishVersionResourceBundle.equals(displayVersion)) {
//                    return pensionFund.frenchVersionResourceBundle;
//                }
//            }
//            else {
//                if (pensionFund.frenchVersionResourceBundle.equals(displayVersion)) {
//                    return pensionFund.englishVersionResourceBundle;
//                }
//            }
        }
        throw new RuntimeException(String.valueOf(Status.UNKNOWN));
    }

    public static String getDatabaseVersion(String displayVersionResourceBundle, Locale locale) throws RuntimeException {
        if (displayVersionResourceBundle == null) {
            return NONE.databaseVersion;
        }
        for (PensionFund pensionFund : values()) {
            if ((locale == Locale.FRENCH && pensionFund.frenchVersionResourceBundle.equals(displayVersionResourceBundle))
            || locale == Locale.ENGLISH && pensionFund.englishVersionResourceBundle.equals(displayVersionResourceBundle)) {
                return pensionFund.databaseVersion;
            }
        }
        throw new RuntimeException(String.valueOf(Status.UNKNOWN));
    }
}
