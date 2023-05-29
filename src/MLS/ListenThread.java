package MLS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ListenThread extends Thread {
    private Gui gui;

    public ListenThread(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = gui.getServer().serverSocket.accept();

                gui.writeToConsol("Accepted connection from " + clientSocket.getInetAddress() + ':'
                        + clientSocket.getPort());

                Client client = new Client();
                client.outToKlient = new DataOutputStream(clientSocket.getOutputStream());
                client.inFromKlient = new DataInputStream(clientSocket.getInputStream());
                gui.getServer().clientSocket.add(clientSocket);
                new ClientThread(gui, client, gui.getServer().clientSocket.size()).start();
            } catch (Exception e) {
                gui.writeToConsol("Server down");
                return;
            }
        }
    }

}
