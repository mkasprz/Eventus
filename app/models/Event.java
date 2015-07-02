package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public String id;

    public String name;

    public String description;

    @ManyToOne
    public User user;

    public String hashtag;
}
