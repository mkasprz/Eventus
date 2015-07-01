package controllers;

import models.Comment;
import models.Event;
import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    private String loggedUserName;

    @Transactional
    public Result addUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        List<User> users = (List<User>) JPA.em().createQuery("select u from User u").getResultList();
        for (User u : users){
            if (user.name.equals(u.name)){
                return redirect(routes.Application.index());
            }
        }
        try {
            JPA.em().persist(user);
            loggedUserName = user.name;
        } catch (Exception e){
            e.printStackTrace();

        }
        return redirect(routes.Application.index());
    }


    @Transactional
    public Result getUsers() {
        List<User> users = (List<User>) JPA.em().createQuery("select u from User u").getResultList();
        return  ok(toJson(users));
    }

    @Transactional
    public Result addEvent() {
//        User user = Form.form(User.class).bindFromRequest().get();
//        JPA.em().persist(user);
        System.out.println(Form.form().bindFromRequest().get("username"));
        Event event = Form.form(Event.class).bindFromRequest().get();
        event.user = JPA.em().find(User.class, Form.form().bindFromRequest().get("username"));

        JPA.em().persist(event);
        return redirect(routes.Application.index());
    }

    @Transactional(readOnly = true)
    public Result getEvents() {
        List<Event> events = (List<Event>) JPA.em().createQuery("select e from Event e").getResultList();
        return ok(toJson(events));
    }

    @Transactional
    public Result postComment(){
        Comment comment = Form.form(Comment.class).bindFromRequest().get();
        comment.author = JPA.em().find(User.class, Form.form().bindFromRequest().get("authorId"));
        comment.event = JPA.em().find(Event.class, Form.form().bindFromRequest().get("eventId"));
        JPA.em().persist(comment);
        return redirect(routes.Application.index());

    }

    @Transactional
    public Result getComments(){
        List<Comment> comments = (List<Comment>) JPA.em().createQuery("select c from Comment c").getResultList();
        return ok(toJson(comments));
    }

    public Result getLogged(){
        return ok(toJson(loggedUserName));
    }

}
