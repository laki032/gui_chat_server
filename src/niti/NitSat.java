package niti;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author Lazar Vujadinovic
 */
public class NitSat extends Thread {

    private JLabel jlbl;

    public NitSat(JLabel jlbl) {
        this.jlbl = jlbl;
        start();
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
        while (true) {
            jlbl.setText(sdf.format(new Date()));
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

}
