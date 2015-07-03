$ ->
  $.get '/events', (events) ->
    $.each events, (index, event) ->
      $('#events').append $('<li><a href = "/events/' + event.id + '">' + event.id + ': ' + event.name + '</a></li>')