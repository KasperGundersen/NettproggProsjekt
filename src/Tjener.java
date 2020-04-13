//Two Phase Commit Protocol TJENER
import java.io.*;
import java.net.*;
import java.util.*;

public class Tjener {
    boolean lukket = false, inputFraAlle = false;
    List<KlientTraad> traadListe;
    List<String> data;
    String tjenerNavn = "Per";
    String tjenerNavnGammel = "";

    Tjener() {
        traadListe = new ArrayList<KlientTraad>();
        data = new ArrayList<String>();
    }

    public static void main(String args[]) {
        Socket klientSocket = null;
        ServerSocket tjenerSocket = null;
        int port_number = 1111;
        Tjener tjener = new Tjener();
        try {
            tjenerSocket = new ServerSocket(port_number);
            System.out.println("Navnet mitt er " + tjener.tjenerNavn);
        } catch (IOException e) {
            System.out.println(e);
        }
        while (!tjener.lukket) {
            try {
                klientSocket = tjenerSocket.accept();
                KlientTraad klientTraad = new KlientTraad(tjener, klientSocket);
                (tjener.traadListe).add(klientTraad);
                System.out.println("\nAntall klienter er oppdatert til: " + (tjener.traadListe).size());

                (tjener.data).add("NOT_SENT");
                klientTraad.start();
            } catch (IOException e) { }
        }
        try {

            tjenerSocket.close();
        } catch (Exception e1) { }
    } // end main
} // end class Server

/*
Coordinator                                          Cohorts
                            QUERY TO COMMIT
                -------------------------------->
                              VOTE YES/NO           prepare/abort
                <-------------------------------
commit/abort                 COMMIT/ROLLBACK
                -------------------------------->
                              ACKNOWLEDGMENT        commit/abort
                <--------------------------------
end

 Two Phases :
 1.Prepare and Vote Phase
 2. Commit or Abort Phase

 "Either All Commit Or All RollBack."
 */
