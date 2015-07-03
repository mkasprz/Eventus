$ ->
  $.get "/events", (events) ->
    $.each events, (index, event) ->
      $("#events").append $("<li>").text event.id + ": " + event.name + " - " + event.description + " #" + event.hashtag