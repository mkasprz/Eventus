package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by piotr on 01.07.15.
 */

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;

    public String contents;

    @ManyToOne
    public User author;

//    @ManyToOne
//    public Event event;

    private int score = 0;

    public void increaseScore(){
        score++;
    }

    public void decreaseScore(){
        score--;
    }

    @OneToMany
    public List<User> votedBy;

//    @OneToMany
//    public List<User> downvotedBy;


    public int getScore() {
        return score;
    }
}
