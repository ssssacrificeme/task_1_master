package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Player {
        private long playerId;
        private String nickname;
        private List<Progresses> progresses;
        private List<Currencies> currencies;
        private List<Items> items;

        @JsonCreator
        public Player(@JsonProperty("playerId") long playerId,
                      @JsonProperty("nickname") String nickname,
                      @JsonProperty("progresses") List<Progresses> progresses,
                      @JsonProperty("currencies") List<Currencies> currencies,
                      @JsonProperty("items") List<Items> items) {
                this.playerId = playerId;
                this.nickname = nickname;
                this.progresses = progresses;
                this.currencies = currencies;
                this.items = items;
        }

        public String toString(){
                return "playerID = " + playerId +"\n"
                        + "nickname = " + nickname + "\n"+
                        "progresses : " + progresses + "\n" +
                        "currencies : " + currencies + "\n" +
                        "items : " + items + "\n";
        }
}
