// ws = new WebSocket("wss://localhost:8074/pong");
ws = new WebSocket("wss://109.233.108.126:8074/pong");

ws.onopen = function (){
    action('Open connection!');
}

ws.onmessage = function (ev){
    action(ev.data);
}

ws.onclose= function (){
    action('Connection close!');
}

function action(message){
    var output = document.getElementById('stack');

    var newP = document.createElement('p');
    newP.appendChild(document.createTextNode(message));
    output.appendChild(newP);
}

function ping(){
    var message = document.getElementById('message').value;
    action('sent ' + message);
    ws.send(message);
}