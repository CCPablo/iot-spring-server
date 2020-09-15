package internal.model.node;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import internal.model.LocationType;
import internal.model.StatusType;
import internal.model.unit.Unit;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@JsonDeserialize(builder = Node.NodeBuilder.class)
@Value
@RequiredArgsConstructor
@Builder
public class Node {

    Integer id;

    String name;

    String description;

    LocationType location;

    String locationName;

    String connectedSince;

    String ip;

    StatusType status;

    List<Unit> units;

    @JsonPOJOBuilder(withPrefix = "")
    public static class NodeBuilder {
    }
}