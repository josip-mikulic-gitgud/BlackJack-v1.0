package com.example.blackjack;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;


public class BlackJackController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextArea dealerTextArea;

    @FXML
    private TextArea spielerTextArea;

    @FXML
    private Text ausgabeTxt;

    @FXML
    private Label lblDealerSumme;

    @FXML
    private Label lblSpielerSumme;

    private int phase = 0;
    private String eingabe = null;
    private Random random = new Random();
    private int summeSpieler = 0;
    private int summeDealer = 0;
    private Karte dealerKarte1;
    private Karte dealerKarte2;

    private Karte spielerKarte1;
    private Karte spielerKarte2;

    private int asseSpieler = 0;
    private int asseDealer = 0;

    @FXML
    protected void onStartButtonClick() {
        if (phase != 0) {
            return; // Bricht die Methode sofort ab, der Klick wird ignoriert
        }
        chatArea.appendText("\nSpieler: Hallo\n");
        chatArea.appendText("Dealer: Hallo, mein Name ist Lucy ; )\n");
        chatArea.appendText("Lucy: Wollen Sie vielleicht Blackjack spielen? \n");
        phase = 1;
    }

    @FXML
    protected void on_Ja_ButtonClick() {
        eingabe = "JA";
        chatArea.appendText("Spieler: Ja\n");
        blackjackSpiel();
    }

    @FXML
    protected void on_Nein_ButtonClick() {
        eingabe = "NEIN";
        chatArea.appendText("Spieler: Nein\n");
        blackjackSpiel();
    }

    private void blackjackSpiel() {
        if (eingabe != null) {
            switch (this.phase) {
                case 1:  // "Lucy: Wollen Sie vielleicht Blackjack spielen?"
                    if ("JA".equals(this.eingabe)) {
                        starteNeueRunde();
                    } else {
                        chatArea.appendText("Lucy: Schade, dann ein anderes Mal vielleicht ;) \n");
                        phase = 0;
                    }
                    break;

                case 2: // "DealerLucy: Wollen Sie noch eine Karte ziehen? "
                    if ("JA".equals(this.eingabe)) {
                        spielerZugausfuehren();
                    }
                    else /* spieler will keine karten mehr */ {
                        dealerDecktAuf();
                    }
                    break;

                case 3: //"Lucy: Wollen Sie noch eine Runde spielen? "
                    if ("JA".equals(this.eingabe)) {

                        starteNeueRunde();
                    }
                    else {
                        chatArea.appendText("\nLucy: Bis zum nächsten Mal! ;) \n");
                        textloeschen();
                        phase = 0;
                    }
                    break;
            }
            eingabe = null;
        }
    }

    private void starteNeueRunde() {
        phase = 99; // 99 blockiert ALLES (Start, Ja und Nein), bis die Animation fertig ist!
        asseSpieler = 0;
        asseDealer = 0;
        summeSpieler = 0;
        summeDealer = 0;

        spielerKarte1 = zieheKarte();
        spielerKarte2 = zieheKarte();
        dealerKarte1 = zieheKarte();
        dealerKarte2 = zieheKarte();

        if (spielerKarte1.istAs()) {
            asseSpieler++;
        }
        if (spielerKarte2.istAs()) {
            asseSpieler++;
        }

        if (dealerKarte1.istAs()) {
            asseDealer++;
        }
        if (dealerKarte2.istAs()) {
            asseDealer++;
        }

        summeSpieler = spielerKarte1.getValue() + spielerKarte2.getValue();
        summeDealer = dealerKarte1.getValue() + dealerKarte2.getValue();

        if (summeSpieler > 21 && asseSpieler > 0) {
            summeSpieler -= 10;
            asseSpieler--; // Ein As zählt jetzt als 1, das andere bleibt 11
        }

        textloeschen();
        chatArea.appendText("\n--- Neue Runde startet, Karten verden verteilt! ---\n");

        warten(1.0, () ->{      // Erst nach 1,2 Sekunden werden die neuen Karten angezeigt:

            spielerTextArea.setText("Spieler: " + spielerKarte1 + " + " + spielerKarte2 );
            lblSpielerSumme.setText("Spieler Summe: "+ summeSpieler);
            dealerTextArea.setText("Haus: " + dealerKarte1 + " + ( verdeckt )\n");
            chatArea.appendText("Haus-Karten: " + dealerKarte1 + " + ( verdeckt )\n");
            chatArea.appendText("Spieler-Karten: " + spielerKarte1 + " + " + spielerKarte2+ "\n");
            lblDealerSumme.setText("Haus Summe: " + dealerKarte1.getValue());

            if (summeSpieler == 21) {
                chatArea.appendText("BLACKJACK! Spieler gewinnt! :)\n");
                ausgabeTxt.setText("Gewonnen!");
                chatArea.appendText("Weiter spielen? (Ja/Nein) \n");
                phase = 3;
            }
            else {
                chatArea.appendText("Lucy: Wollen Sie noch eine Karte ziehen? \n");
                phase = 2;
            }
        });
    }

    private void spielerZugausfuehren(){
        Karte neueKarte = zieheKarte();

        if (neueKarte.istAs()){
            asseSpieler++;
        }

        spielerTextArea.appendText(" + "+neueKarte);
        chatArea.appendText("Spieler zieht + " +neueKarte+"\n");

        summeSpieler += neueKarte.getValue();
        if(summeSpieler > 21 && asseSpieler > 0) {
            summeSpieler -= 10;
            asseSpieler--;
        }
        lblSpielerSumme.setText("Spieler Summe: "+ summeSpieler);

        if (summeSpieler > 21) {
            ausgabeTxt.setText("Verloren.");
            chatArea.appendText("Spieler hat " + summeSpieler + " Punkte.\n");
            chatArea.appendText("Haus gewinnt. :(\n");
            chatArea.appendText("\nWeiter spielen? (Ja/Nein) \n");
            phase = 3;

        } else if(summeSpieler == 21){
            ausgabeTxt.setText("Gewonnen!");
            chatArea.appendText("Spieler hat " + summeSpieler + " Punkte!\n");
            chatArea.appendText("Spieler gewinnt! :)\n");
            chatArea.appendText("\nWeiter spielen? (Ja/Nein) \n");
            phase = 3;
        }
        else {
            chatArea.appendText("Lucy: Wollen Sie noch eine Karte? \n");}
    }

    private void dealerDecktAuf(){
        phase = 99; // 99 blockiert ALLES (Start, Ja und Nein)

        if(summeDealer > 21 && asseDealer > 0) {
            summeDealer -= 10;
            asseDealer--;
        }
        chatArea.appendText("Spieler hat " + summeSpieler + " Punkte!\n");
        chatArea.appendText("Dealer deckt Karte: " );
        warten(1.0, () -> {
            chatArea.appendText( dealerKarte2 + " auf.\n");
            dealerTextArea.setText("Haus: " + dealerKarte1 + " + ( " + dealerKarte2 + " )");
            lblDealerSumme.setText("Haus Summe: " + summeDealer);

            warten(0.8, ()-> {
                chatArea.appendText("Haus-Karten: " + dealerKarte1 + " + ( " + dealerKarte2 + " )");
                warten(1.0, () -> dealerZugausfuehren());
            });
        });
    }

    private void dealerZugausfuehren() {
        if (summeDealer < 17) {
            Karte neueKarte = zieheKarte();
            if(neueKarte.istAs()){
                asseDealer++;
            }
            summeDealer += neueKarte.getValue();

            if(summeDealer > 21 && asseDealer > 0) {
                summeDealer -= 10;
                asseDealer--;
            }
            dealerTextArea.appendText(" + " + neueKarte);
            chatArea.appendText(" + " + neueKarte);
            lblDealerSumme.setText("Haus Summe: " + summeDealer);

            warten(1.2, () -> dealerZugausfuehren());
        }
        else {
            if (summeDealer > 21) {
                chatArea.appendText("\nHaus hat: " + summeDealer + " Punkte.\n");
                chatArea.appendText("Haus hat sich überkauft, Spieler gewinnt!\n");
                ausgabeTxt.setText("Gewonnen!");
            }
            else if (summeDealer == summeSpieler) {
                chatArea.appendText("\nHaus hat: " + summeDealer + " Punkte.\n");
                chatArea.appendText("\nGleichstand.\n");
                ausgabeTxt.setText("Gleichstand. ");
            } else if (summeDealer < summeSpieler) {
                chatArea.appendText("\nHaus hat: " + summeDealer + " Punkte, Spieler gewinnt!\n");
                ausgabeTxt.setText("Gewonnen! ");
            } else {
                chatArea.appendText("\nHaus hat: " + summeDealer + " Punkte, Haus gewinnt. :(\n");
                ausgabeTxt.setText("Verloren. ");
            }
            chatArea.appendText("\nWeiter spielen? (Ja/Nein)\n");
            phase = 3;
        }

    }

    private Karte zieheKarte() {
        Karte[] karten = Karte.values(); // erstellen Array von Karten nur mit werten
        return karten[random.nextInt(karten.length)]; // gibt einen wert( Karte ) zurück
    }

    private void textloeschen(){
        dealerTextArea.clear();
        spielerTextArea.clear();
        lblSpielerSumme.setText("");
        lblDealerSumme.setText("");
        ausgabeTxt.setText("");
    }

    private void warten(double sekunden, Runnable aktion) {
        PauseTransition pause = new PauseTransition(Duration.seconds(sekunden));
        pause.setOnFinished(e -> aktion.run());
        pause.play();
    }
}