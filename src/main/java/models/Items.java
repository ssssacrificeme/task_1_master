package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Items {
    private long id;
    private long playerId;
    private long resourceId;
    private long count;
    private long level;

    @JsonCreator
    public Items(@JsonProperty("id") long id,
                 @JsonProperty("playerId") long playerId,
                 @JsonProperty("resourceId") long resourceId,
                 @JsonProperty("count") long count,
                 @JsonProperty("level") long level) {
        this.id = id;
        this.playerId = playerId;
        this.resourceId = resourceId;
        this.count = count;
        this.level = level;
    }
}
