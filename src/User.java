package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User {

    @Id
    String nickname;
    int reputation;
    List<Event> enlistedEvents;
    List<Event> commentedEvents;

    public User(String nickname) {
        this.nickname = nickname;
    }

    void increaseReputation(int points) {
        reputation += points;
    }

    void decreaseReputation(int points) {
        reputation -= points;
    }

}
