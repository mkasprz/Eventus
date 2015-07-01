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

    @ManyToOne
    public User user;

}
