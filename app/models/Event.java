package models;

import javax.persistence.*;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public String id;

    public String name;
}
