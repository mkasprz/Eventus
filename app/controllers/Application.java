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
    public Result login() {
        User user = JPA.em().find(User.class, Form.form().bindFromRequest().get("email"));
        if (user != null) {
            session("email", user.email);
        }
        return redirect(routes.Application.index());
    }

    public void add (User user) {
        JPA.em().persist(user);
    }

    @Transactional
    public Result addUser() {


        Form<User> form = Form.form(User.class).bindFromRequest();
//        if (!form.hasErrors()) {

//            return badRequest(index.render(form));

        User user = form.get();
//            JPA.em().persist(user);

        try {
            JPA.withTransaction(() -> {
                JPA.em().persist(user);
            });
        } catch (Exception e) {
            System.out.println("Cannot insert.");
        }


        session().clear();
        session("email", user.email);
//            response().setCookie("Eventus", user.id);
//        }
//        }

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
    public Result getEvent(String id) {
        List<Event> events = (List<Event>) JPA.em().createQuery("select e from Event e where e.id = '" + id + "'").getResultList();
        return ok(toJson(events));
    }

    @Transactional(readOnly = true)
    public Result getEvents() {
        List<Event> events = (List<Event>) JPA.em().createQuery("select e from Event e").getResultList();
        return ok(toJson(events));
    }

    @Transactional
    public Result postComment() {
        Event event = JPA.em().find(Event.class, Form.form().bindFromRequest().get("eventId"));
        if (event == null){
            return redirect(routes.Application.index());
        }
        Comment comment = Form.form(Comment.class).bindFromRequest().get();
        comment.author = JPA.em().find(User.class, session().get("email"));
        JPA.em().persist(comment);
        event.comments.add(comment);
        return redirect(routes.Application.index());
    }

    @Transactional
    public Result getComments() {
        List<Comment> comments = (List<Comment>) JPA.em().createQuery("select c from Comment c").getResultList();
        return ok(toJson(comments));
    }

    @Transactional
    public Result getComment(String id) {
        List<Comment> comments = (List<Comment>) JPA.em().createQuery("select c from Comment c where c.id = '" + id + "'").getResultList();
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
        if (comment == null || comment.votedBy.contains(user)){
            return redirect(routes.Application.index());

        }

//        if (comment.upvotedBy.contains(user) || comment.downvotedBy.contains(user)){
//            return redirect(routes.Application.index());
//        }

        comment.increaseScore();
        comment.votedBy.add(user);

        return redirect(routes.Application.index());
    }

    @Transactional
    public Result decreaseScore() {
        User user = JPA.em().find(User.class, session().get("email"));

        Comment comment = JPA.em().find(Comment.class, Form.form().bindFromRequest().get("id"));
        if (comment == null || comment.votedBy.contains(user)){
            return redirect(routes.Application.index());

        }

//        if (comment.upvotedBy.contains(user) || comment.downvotedBy.contains(user)){
//            return redirect(routes.Application.index());
//        }

        comment.decreaseScore();
        comment.votedBy.add(user);

        return redirect(routes.Application.index());
    }

    public Result getTweetsByHashtag(String hashtag){
//        Event event = JPA.em().find(Event.class, Form.form().bindFromRequest().get("id"));
//        if (event == null){
//            return redirect(routes.Application.index());
//        }
        return redirect("https://twitter.com/search?q=%23" + hashtag);
    }


}
