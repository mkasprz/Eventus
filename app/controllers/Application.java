package controllers;

import play.mvc.*;
import play.db.jpa.*;
import views.html.*;
import models.Event;
import play.data.Form;
import java.util.List;

import static play.libs.Json.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    @Transactional
    public Result addEvent() {
        Event event = Form.form(Event.class).bindFromRequest().get();
        JPA.em().persist(event);
        return redirect(routes.Application.index());
    }

    @Transactional(readOnly = true)
    public Result getEvents() {
        List<Event> events = (List<Event>) JPA.em().createQuery("select e from Event e").getResultList();
        return ok(toJson(events));
    }
}
