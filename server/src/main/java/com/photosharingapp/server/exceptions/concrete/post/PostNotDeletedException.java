package com.photosharingapp.server.exceptions.concrete.post;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.enums.abstracts.IFriendlyMessageCode;
import com.photosharingapp.server.exceptions.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class PostNotDeletedException extends RuntimeException{
    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public PostNotDeletedException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[PostNotDeletedException] -> message: {} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }
}
