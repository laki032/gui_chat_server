package kontroler;

import gui.GlavnaForma;
import java.util.ArrayList;
import java.util.List;
import niti.NitKlijent;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Kontroler {

    private static Kontroler INSTANCE;
    private GlavnaForma gf;
    List<NitKlijent> klijenti;

    private Kontroler() {
        klijenti = new ArrayList<>();
    }

    public static Kontroler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Kontroler();
        }
        return INSTANCE;
    }

    /**
     * metoda kod kontrolera cuva referencu na glavnu formu
     *
     */
    public void postaviGlavnuFormu(GlavnaForma gf) {
        this.gf = gf;
    }

    /**
     * metoda poziva metodu ispisi na glavnoj formi
     *
     */
    public void ispisi(String poruka) {
        gf.ispisi(poruka);
    }

    /**
     * metoda u listu online korisnika ubacuje korisnika koji je upravo
     * pristupio serveru (korisnika nk) i ostale obavestava o tome
     *
     */
    public void dodajNovogKlijenta(NitKlijent nk) {
        klijenti.add(nk);
        posaljiSvimaNovuListu(nk);
    }

    /**
     * metoda obavestava ostale korisnike da je klijent nk poslao javnu poruku
     *
     * @param poruka javna poruka
     * @param nk korisnik koji je posalo javnu poruku
     */
    public void obavestiOstale(String poruka, NitKlijent nk) {
        for (NitKlijent kl : klijenti) {
            if (!kl.equals(nk)) {
                kl.posalji(poruka);
            }
        }
        ispisi("Klijent [" + nk.getImeKlijenta() + nk.getAddress() + "] " + poruka);
    }

    /**
     * metoda iz liste izbacuje korisnika koji je napustio chat sobu
     *
     */
    public void odjaviKlijenta(NitKlijent aThis) {
        for (NitKlijent nk : klijenti) {
            if (nk.equals(aThis)) {
                klijenti.remove(nk);
                return;
            }
        }
    }

    /**
     * metoda vraca listu imena korisnika koji su online
     *
     */
    public List<String> vratiOnlineKorisnike() {
        List<String> l = new ArrayList<>();
        for (NitKlijent nk : klijenti) {
            l.add(nk.getImeKlijenta());
        }
        return l;
    }

    /**
     * metoda svima sem klijentu k salje listu online korisnika
     *
     */
    private void posaljiSvimaNovuListu(NitKlijent k) {
        for (NitKlijent nk : klijenti) {
            if (!nk.equals(k)) {
                nk.posaljiListuKlijenata();
            }
        }
    }

    /**
     * metoda salje privatnu poruku koju je posalo klijent nk u poruci je
     * sadrzano ime klijenta kome se salje privatna poruka
     *
     */
    public void proslediPrivatnuPoruku(String poruka, NitKlijent nk) {
        //Privatna poruka za [ime] od {imena}: tekst poruke
        String ime = poruka.substring(poruka.indexOf("[") + 1, poruka.indexOf("]"));
        for (NitKlijent kl : klijenti) {
            if (kl.getImeKlijenta().equals(ime)) {
                kl.posalji(poruka);
            }
        }
        ispisi("Klijent [" + nk.getImeKlijenta() + nk.getAddress() + "] poslao privatnu poruku klijentu: " + ime + "\ntekst poruke: " + poruka);
    }

}
