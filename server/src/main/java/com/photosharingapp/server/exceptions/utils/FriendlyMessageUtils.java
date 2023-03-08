package com.photosharingapp.server.exceptions.utils;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.enums.abstracts.IFriendlyMessageCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass
public class FriendlyMessageUtils {
    private static final String RESOURCE_BUNDLE_NAME = "FriendlyMessages";
    private static final String SPECIAL_CHARACTER = "__";

    public static String getFriendlyMessage(Language language, IFriendlyMessageCode friendlyMessageCode) {
        String messageKey = null;
        try {
            Locale locale = new Locale(language.name());
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
            messageKey = friendlyMessageCode.getClass().getSimpleName() + SPECIAL_CHARACTER + friendlyMessageCode;
            return resourceBundle.getString(messageKey);
        } catch (MissingResourceException exception) {
            log.error("Friendly message not found for key: {}", messageKey);
            return null;
        }
    }
}
