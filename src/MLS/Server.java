package MLS;

import java.net.Socket;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public List<Socket> clientSocket = new ArrayList<>();
    public ServerSocket serverSocket;
    boolean running;
}
