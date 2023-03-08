package com.photosharingapp.server.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class InternalApiResponse<TEntity> {
    private FriendlyMessageResponse friendlyMessageResponse;
    private TEntity payload;
    private boolean hasError;
    private List<String> errorMessages;
    private HttpStatus httpStatus;
}
