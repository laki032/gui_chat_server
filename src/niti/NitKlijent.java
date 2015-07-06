package niti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import kontroler.Kontroler;

/**
 *
 * @author Lazar Vujadinovic
 */
public class NitKlijent extends Thread {

    Socket s;
    String klijent;

    NitKlijent(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            klijent = in.readLine();
            Kontroler.getInstance().ispisi("Klijent [" + klijent + s.getInetAddress() + "] se povezao na server.");
            Kontroler.getInstance().dodajNovogKlijenta(this);
            posaljiListuKlijenata();
            String poruka;
            while (true) {
                poruka = in.readLine();
                if (poruka != null) {
                    if (poruka.equals("KRAJ")) {
                        break;
                    }
                    if (poruka.startsWith("Privatna poruka")) {
                        Kontroler.getInstance().proslediPrivatnuPoruku(poruka, this);
                    } else {
                        Kontroler.getInstance().obavestiOstale(klijent + " kaze: " + poruka, this);
                    }
                }
            }
            Kontroler.getInstance().odjaviKlijenta(this);
            Kontroler.getInstance().obavestiOstale(klijent + " se odjavio.", this);
        } catch (IOException ex) {
            Kontroler.getInstance().odjaviKlijenta(this);
            Kontroler.getInstance().obavestiOstale(klijent + " nije vise sa nama. Pukla mu je veza.", this);
        }
    }

    /**
     * metoda salje klijentu listu imena korisnika koji su na vezi
     *
     */
    public void posaljiListuKlijenata() {
        List<String> l = Kontroler.getInstance().vratiOnlineKorisnike();
        String dobrodosli = "Klijenti:{";
        for (String s : l) {
            dobrodosli += s + ";";
        }
        dobrodosli += "}";
        posalji(dobrodosli);
    }

    /**
     * metoda koja sluzi za slanje poruka klijentu
     *
     */
    public void posalji(String poruka) {
        try {
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            pw.println(poruka);
        } catch (IOException ex) {

        }

    }

    /**
     * metoda vraca IP adresu korisnika kao string
     *
     */
    public String getAddress() {
        return s.getInetAddress() + "";
    }

    /**
     * metoda Username korisnika kao string
     *
     */
    public String getImeKlijenta() {
        return klijent;
    }

}
