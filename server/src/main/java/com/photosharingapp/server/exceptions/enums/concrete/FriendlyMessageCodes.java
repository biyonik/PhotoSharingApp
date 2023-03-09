package com.photosharingapp.server.exceptions.enums.concrete;

import com.photosharingapp.server.exceptions.enums.abstracts.IFriendlyMessageCode;

public enum FriendlyMessageCodes implements IFriendlyMessageCode {
    OK(1000),
    ERROR(1001),
    SUCCESS(1002),
    USER_NOT_FOUND(1500),
    USER_CREATED_FAILED(1501),
    USER_CREATED_SUCCESSFULLY(1502),
    USER_UPDATED_FAILED(1503),
    USER_UPDATED_SUCCESSFULLY(1504),
    USER_REGISTER_FAILED(1505),
    USER_REGISTER_SUCCESSFULLY(1506),
    USER_LOGIN_FAILED(1507),
    USER_LOGIN_SUCCESSFULLY(1508),
    USER_ACTIVE_STATUS_CHANGE_FAILED(1509),
    USER_ACTIVE_STATUS_CHANGE_SUCCESSFULLY(1510),
    USER_CHANGE_PASSWORD_FAILED(1511),
    USER_CHANGE_PASSWORD_SUCCESSFULLY(1512),
    USER_DELETE_FAILED(1513),
    USER_DELETE_SUCCESSFULLY(1514),
    POST_NOT_FOUND(2000),
    POST_CREATED_FAILED(2001),
    POST_CREATED_SUCCESSFULLY(2002),
    POST_UPDATED_FAILED(2003),
    POST_UPDATED_SUCCESSFULLY(2004),
    POST_ACTIVE_STATUS_CHANGE_FAILED(2005),
    POST_ACTIVE_STATUS_CHANGE_SUCCESSFULLY(2006),
    POST_DELETE_FAILED(2007),
    POST_DELETE_SUCCESSFULLY(2008);

    private final int value;

    FriendlyMessageCodes(int value) {
        this.value = value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
