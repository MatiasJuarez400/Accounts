# Accounts

This is a standalone project that simulates transferences between accounts.

When you run the project, a server will start running on localhost:8090
The base path is /demoapp

So if you want to get all available currencies you can work with, send a request to http://localhost:8090/demoapp/currencies

The project makes use of an in-memory database. 
You can access a console from your browser to see the current status of the database at http://localhost:8082
Use JDBC URL : jdbc:h2:mem:myDb <br/>
user: admin <br/>
pass: admin

If you need to change server configuration please edit server.properties 
If you need to change database configuration, please edit h2.properties

