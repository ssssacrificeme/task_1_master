package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Progresses {
    private long id;
    private long playerId;
    private long resourceId;
    private long score;
    private long maxScore;

    @JsonCreator
    public Progresses(@JsonProperty("id") long id,
                      @JsonProperty("playedId") long playerId,
                      @JsonProperty("resourceId") long resourceId,
                      @JsonProperty("score") long score,
                      @JsonProperty("maxScore") long maxScore) {
        this.id = id;
        this.playerId = playerId;
        this.resourceId = resourceId;
        this.score = score;
        this.maxScore = maxScore;
    }
}