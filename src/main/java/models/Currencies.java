package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Currencies {
    private long id;
    private long playerId;
    private long resourceId;
    private String name;
    private long count;

    @JsonCreator
    public Currencies(@JsonProperty("id") long id,
                      @JsonProperty("playerId") long playerId,
                      @JsonProperty("resourceId") long resourceId,
                      @JsonProperty("name") String name,
                      @JsonProperty("count") long count) {
        this.id = id;
        this.playerId = playerId;
        this.resourceId = resourceId;
        this.name = name;
        this.count = count;
    }}
