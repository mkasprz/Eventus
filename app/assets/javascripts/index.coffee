$ ->
  $.get "/events", (events) ->
    $.each events, (index, event) ->
      $("#events").append $("<li>").text event.name