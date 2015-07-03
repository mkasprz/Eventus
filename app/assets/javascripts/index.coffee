$ ->
  $.get '/events', (events) ->
    $.each events, (index, event) ->
      $('#events').append('<li> <a href = "/events/' + event.id + '">' + event.id + ': ' + event.name + ' - ' + event.description + '</a> <a href = "/hashtags/' + event.hashtag + '"> #' + event.hashtag + '</a> </li>')