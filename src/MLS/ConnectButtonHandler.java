package MLS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ConnectButtonHandler implements ActionListener {

    private Gui gui;

    public ConnectButtonHandler(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        try {
            if (gui.getConButton().getText().equals("Start")) {
                gui.getServer().serverSocket = new ServerSocket(Integer.parseInt(gui.getPort().getText()), 0,
                        InetAddress.getByName("0.0.0.0"));
                gui.writeToConsol("Started listening on port " + gui.getPort().getText());
                gui.getServer().running = true;
                gui.getStatus().setText("Listening on port:" + gui.getPort().getText());
                gui.getConButton().setText("Stop");
                Thread listenThread = new ListenThread(gui);
                listenThread.start();
            } else {
                for (Socket socket:gui.getServer().clientSocket) {
                    if (socket != null) {
                        socket.close();
                    }
                }
                if (gui.getServer().serverSocket != null) {
                    gui.getServer().serverSocket.close();
                }
                gui.getServer().running = false;
                gui.getConButton().setText("Start");
                gui.getStatus()
                        .setText("not listening");
            }

        } catch (IOException e2) {
            gui.writeToConsol("Server can't start ");
        }
    }

}
