package internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ValueUnitsModel {
    @JsonProperty("value")
    String value;

    @JsonProperty("units")
    String units;
}
