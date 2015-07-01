package controllers;

import models.Event;
import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

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

}
