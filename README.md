# ik1203

task 1:
The first programming assignment is to implement a TCP client, called TCPAsk. TCPAsk operates in a straight-forward manner:

Open a TCP connection to a server at a given host address and port number.
Take any data that the server sends, and and print the data.
TCPAsk takes an optional string as parameter. This string is sent as data to the server when the TCP connection is opened, followed by a newline character (linefeed '\n').

task 2:
In this part, you will implement a web server. It is a web server that does not do very much, but you will probably find it useful for the rest of this assignment. The server accepts an incoming TCP connection, reads data from it until the client closes the connection, and returns ("echoes") an HTTP response back with the data in it. 

This may seem a bit odd. But the idea is that if you use your web browser to navigate to this server, what you will see in your browser window is the complete HTTP request that your browser sent.

task 3:
In this part, you will implement another web server – HTTPAsk. This is a web server that uses the client you did in Task 1. When you send an HTTP request to HTTPAsk, you provide a hostname, a port number, and optionally a string as parameters for the request.

When HTTPAsk receives the HTTP request, it will call the method TCPClient.askServer, (which you wrote for Task 1, remember?), and return the output as an HTTP response. This may seem confusing, but it is really very simple: instead of running TCPAsk from the command line, you will build a web server that runs TCPAsk for you, and presents the result as a web page (an HTTP response).

What you will learn here is to:

Read and analyse an HTTP GET request, and extract a query string from it. 
Launch an application from your server, where the application provides the data that the server returns in response to the HTTP get. 

task 4:
In this task, you will take the HTTPAsk server you did in Task 3, and turn it into a concurrent server. The server you did for Task 3 deals with one client at a time (most likely). A concurrent server can handle multiple clients in parallel.

What you will learn here is to:

Use Java threading to implement a concurrent server that can handle many clients at the same time. 
The description here is almost the same as for Task 3: You should implement a class called ConcHTTPAsk ("Conc" because it is concurrent). It's "main" method implements the server. It takes one argument: the port number.

The difference between ConcHTTPAsk and HTTPAsk from Task 3 is that as soon as a client contacts your ConcHTTPAsk server, the client will be served immediately. It does not have to wait for ConcHTTPAsk to finish serving the current client.   
