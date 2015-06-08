import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Comment {

    User author;
    Date date;
    String content;
    int scores;
    Map<User, Integer> userScore;

    public Comment(User author, String content) {
        this.author = author;
        this.date = Calendar.getInstance().getTime();
        this.content = content;
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
