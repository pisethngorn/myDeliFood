package com.DeliFood.dw;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Piseth on 11/20/16.
 */
public class EventConfiguration extends Configuration{
    @NotEmpty
    @JsonProperty
    private String template = "initialTempalte";

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }
}
