# Simple Client-Server Chat Room in Java

SERVER
* Server is created using  ServerSocket(port #, max num of users)
* Server waits for someone to connect by listening at given port
* When a client connects using complete socket (IP address and port), socketConnection is created through server.accept() method
* Data can now be transmitted using streams
* Uses a thread that updates the GUI when a message is sent
* Server can now send message to client

CLIENT
* Authenticate users through a Log-in form, connects to a remote MySQL server using JDBC
* MySQL database with a table of Users
* Client is created using Socket(IP address of Server, port #)
* Client connects to server
* Uses a thread that updates the GUI when a message is sent
* User can now send message to server

NOTE
* Client or server can disconnect by typing END


TODO
* Include a DISCONNECT button, both for client and server
* Improve GUI
* Multiple clients can connect to server

SERVER - UBUNTU
* ![client chat](https://github.com/lenmorld/Simple-Client-Server-Messenger/blob/master/screens/client-server-chat4.jpg "Logo Title Text 1")

SERVER - UBUNTU
* ![client chat](https://github.com/lenmorld/Simple-Client-Server-Messenger/blob/master/screens/client-server-chat5.jpg "Logo Title Text 1")

CLIENT LOGIN - UBUNTU
* ![client login](https://github.com/lenmorld/Simple-Client-Server-Messenger/blob/master/screens/client-server-chat.jpg "Logo Title Text 1")

CLIENT CHAT - UBUNTU
* ![client chat](https://github.com/lenmorld/Simple-Client-Server-Messenger/blob/master/screens/client-server-chat2.jpg "Logo Title Text 1")




