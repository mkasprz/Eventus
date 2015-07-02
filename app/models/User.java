package models;

import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @Constraints.Email
    public String email;

    public String name;

}
