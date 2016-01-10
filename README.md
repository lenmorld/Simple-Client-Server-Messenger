# Simple Client-Server Chat Room in Java

SERVER
* Server is created using  ServerSocket(port #, max num of users)
* Server waits for someone to connect by listening at given port
* When a client connects using complete socket (IP address and port), socketConnection is created through server.accept() method
* Data can now be transmitted using streams
* Uses a thread that updates the GUI when a message is sent
* Server can now send message to client
* NOTE that this is a broadcast message, everyone receives 

CLIENT
* Authenticate users through a Log-in form, connects to a remote MySQL server using JDBC
* MySQL database with a table of Users
* Client is created using Socket(IP address of Server, port #)
* Client connects to server
* Uses a thread that updates the GUI when a message is sent
* User can now send message to server


NOTE
* Messages are broadcast to everyone connected to the chat room
* Multiple clients can connect to server
* Client or server can disconnect by typing END


TODO
* Include a DISCONNECT button, both for client and server
* Improve GUI



