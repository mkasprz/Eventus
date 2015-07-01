package models;

import javax.persistence.*;

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

    private int score = 0;

    public void increaseScore(){
        score++;
    }

    public void decreaseScore(){
        score--;
    }

}
