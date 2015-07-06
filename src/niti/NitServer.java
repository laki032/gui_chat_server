package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import kontroler.Kontroler;

/**
 *
 * @author Lazar Vujadinovic
 */
public class NitServer extends Thread {

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            Kontroler.getInstance().ispisi("Pokrenut server na portu 9000.");
            Kontroler.getInstance().ispisi("Ocekujem klijente.");
            while (true) {
                Socket s = ss.accept();
                NitKlijent nk = new NitKlijent(s);
                nk.start();
            }
        } catch (IOException ex) {

        }
    }

}
