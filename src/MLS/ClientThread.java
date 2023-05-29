package MLS;

import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.Charset;
public class ClientThread extends Thread {
    private Gui gui;
    private Client client;
    private int id;

    public ClientThread(Gui gui, Client client, int id) {
        this.gui = gui;
        this.client = client;
        this.id = id;
    }

    public void disConnectClient() {
        try {
            if (client.outToKlient != null) {
                client.outToKlient.close();
            }
            if (client.inFromKlient != null) {
                client.inFromKlient.close();
            }
            gui.getServer().clientSocket.get(id).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String modifiedSentence;
        byte[] bytes = new byte[1024];
        int correct;
        try {
            while (true) {
                if (gui.getServer().running) {
                    if (client.inFromKlient != null && (correct = client.inFromKlient.read(bytes)) != 0) {
                        if (correct == -1) {
                            disConnectClient();
                            gui.getServer().clientSocket.get(id).close();
                            return;
                        }
                        modifiedSentence = new String(bytes, 0, correct);
                        JSONObject json = new JSONObject(modifiedSentence);

                        byte[] buf = modifiedSentence.getBytes(Charset.forName("UTF-8"));
                        client.outToKlient.write(buf);

                    }
                } else {
                    disConnectClient();
                    return;
                }
            }
        } catch (SocketException e) {
            disConnectClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
