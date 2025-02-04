package com.alex.ua.client.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CollectionRequestResponse {
    private Long bae;
    private Long mne;
    private Long lke;
    private Long fre;
}
