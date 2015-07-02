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

        Form userForm = Form.form(User.class).bindFromRequest();
        if (!userForm.hasErrors()) {
//            return badRequest();

            User user = (User) userForm.get();
            JPA.em().persist(userForm.get());
            session().clear();
            session("email", user.email);
//            response().setCookie("Eventus", user.id);
//        }
        }

        return redirect(routes.Application.index());
    }

    @Transactional
    public Result getUsers() {
        List<User> users = (List<User>) JPA.em().createQuery("select u from User u").getResultList();
        return ok(toJson(users));
    }

    @Transactional
    public Result addEvent() {
        Event event = Form.form(Event.class).bindFromRequest().get();
        event.user = JPA.em().find(User.class, session().get("email"));
        JPA.em().persist(event);
        return redirect(routes.Application.index());
    }

    @Transactional(readOnly = true)
    public Result getEvents() {
        List<Event> events = (List<Event>) JPA.em().createQuery("select e from Event e").getResultList();
        return ok(toJson(events));
    }

    @Transactional
    public Result postComment() {
        Comment comment = Form.form(Comment.class).bindFromRequest().get();
        comment.author = JPA.em().find(User.class, session().get("email"));
        comment.event = JPA.em().find(Event.class, Form.form().bindFromRequest().get("eventId"));
        JPA.em().persist(comment);
        return redirect(routes.Application.index());

    }

    @Transactional
    public Result getComments() {
        List<Comment> comments = (List<Comment>) JPA.em().createQuery("select c from Comment c").getResultList();
        return ok(toJson(comments));
    }

    public Result logout(){
        session().clear();
        return redirect(routes.Application.index());
    }

    @Transactional
    public Result increaseScore() {
        User user = JPA.em().find(User.class, session().get("email"));

        Comment comment = JPA.em().find(Comment.class, Form.form().bindFromRequest().get("id"));

        if (comment.propsedBy.contains(user)){
            return redirect(routes.Application.index());
        }

        comment.increaseScore();
        comment.propsedBy.add(user);

        return redirect(routes.Application.index());
    }

    @Transactional
    public Result decreaseScore() {
        User user = JPA.em().find(User.class, session().get("email"));

        Comment comment = JPA.em().find(Comment.class, Form.form().bindFromRequest().get("id"));

        if (comment.propsedBy.contains(user)){
            return redirect(routes.Application.index());
        }

        comment.decreaseScore();
        comment.propsedBy.add(user);

        return redirect(routes.Application.index());
    }


}
