package internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValueModel {
    @JsonProperty("value")
    String value;
}
