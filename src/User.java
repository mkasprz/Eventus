import java.util.List;

public class User {

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
