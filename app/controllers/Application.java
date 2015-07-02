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

import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    @Transactional
    public Result addUser() {

//        User user = Form.form(User.class).bindFromRequest().get();
//        if (!JPA.em().contains(user)) {
//            JPA.em().persist(user);
//        }
//        List<User> users = (List<User>) JPA.em().createQuery("select u from User u").getResultList();
//        for (User u : users){
//            if (user.name.equals(u.name)){
//                return redirect(routes.Application.index());
//            }
//
        if (JPA.em().find(User.class, Form.form().bindFromRequest().get("email")) == null) {
            User user = Form.form(User.class).bindFromRequest().get();
            JPA.em().persist(user);
//            response().setCookie("Eventus", user.id);
        }
        try {
            redirect(routes.Application.index());
        } catch (Exception e)
        {}

        return ok();
//        return ok();

    }

    @Transactional
    public Result getUsers() {
        List<User> users = (List<User>) JPA.em().createQuery("select u from User u").getResultList();
        return ok(toJson(users));
    }

    @Transactional
    public Result addEvent() {
//        User user = Form.form(User.class).bindFromRequest().get();
//        JPA.em().persist(user);
        System.out.println(Form.form().bindFromRequest().get("username"));
        Event event = Form.form(Event.class).bindFromRequest().get();
        event.user = JPA.em().find(User.class, request().cookies().get("Eventus").value());
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
        comment.author = JPA.em().find(User.class, request().cookies().get("Eventus").value());
        comment.event = JPA.em().find(Event.class, Form.form().bindFromRequest().get("eventId"));
        JPA.em().persist(comment);
        return redirect(routes.Application.index());

    }

    @Transactional
    public Result getComments(){
        List<Comment> comments = (List<Comment>) JPA.em().createQuery("select c from Comment c").getResultList();
        return ok(toJson(comments));
    }

    public Result logout(){
        response().discardCookie("Eventus");
        return redirect(routes.Application.index());
    }

    @Transactional
    public Result increaseScore(){
        User user = JPA.em().find(User.class, request().cookies().get("Eventus").value());

        Comment comment = JPA.em().find(Comment.class, Form.form().bindFromRequest().get("id"));

        if (comment.propsedBy.contains(user)){
            return redirect(routes.Application.index());
        }

        comment.increaseScore();
        comment.propsedBy.add(user);

        return redirect(routes.Application.index());
    }

    @Transactional
    public Result decreaseScore(){
        User user = JPA.em().find(User.class, request().cookies().get("Eventus").value());

        Comment comment = JPA.em().find(Comment.class, Form.form().bindFromRequest().get("id"));

        if (comment.propsedBy.contains(user)){
            return redirect(routes.Application.index());
        }

        comment.decreaseScore();
        comment.propsedBy.add(user);

        return redirect(routes.Application.index());
    }


}
