import java.util.Date;
import java.util.List;
import java.util.Map;

public class Event {

    User author;
    Date date;
    String name;
    String description;
    int scores;
    Map<User, Integer> userScore;
    List<Comment> comments;
    List<Tweet> tweets;
    String tag;

    public Event(User author, Date date, String name, String description) {
        this.author = author;
        this.date = date;
        this.name = name;
        this.description = description;
    }

    void comment(User user, String content) {
        comments.add(new Comment(user, content));
    }

    void increaseScore(User user) {
        if (!userScore.containsKey(user)) {
            userScore.put(user, 1);
            scores ++;
        }
        else if (userScore.get(user) < 1) {
            userScore.put(user, userScore.get(user) + 1);
            scores ++;
        }
    }

    void decreaseScore(User user) {
        if (!userScore.containsKey(user)) {
            userScore.put(user, -1);
            scores --;
        }
        else if (userScore.get(user) > -1) {
            userScore.put(user, userScore.get(user) - 1);
            scores --;
        }
    }

}
