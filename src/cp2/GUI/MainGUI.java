package cp2.GUI;

import cp2.logic.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MainGUI extends Application {
    private static final String[] cardsImage = new String[53];

    private int hitNumber=0;
    private int money=1000;

    private Label betL;
    private TextField bet;
    private Label moneyL;
    private Button start;
    private Button hit;
    private Button doubleDown;
    private Button stand;
    private HBox botBox;

    private ImageView deck;
    private HBox deckBox;

    private ImageView[] dealerHand;
    private HBox centerTopBox;
    private ImageView[] playerHand;
    private HBox centerBotBox;
    private VBox centerBox;

    private BorderPane pane;
    private Scene scene;

    @Override
    public void start(Stage stage){
        pane = new BorderPane();
        pane.setBackground(Background.fill(Paint.valueOf("#35654d")));

        betL = new Label("Bet:");
        betL.setStyle("-Fx-text-fill: #ffffff;");
        bet = new TextField("0");
        bet.setPromptText("insert here your bet (20)");
        start = new Button("start a new game");
        hit = new Button("hit");
        doubleDown = new Button("doubleDown");
        stand = new Button("stand");
        start.setOnAction(e -> startHandle(stage));
        moneyL = new Label();
        moneyL.setText("Your money: "+money+"$");
        moneyL.setFont(new Font("verdana",22));
        moneyL.setStyle("-Fx-text-fill: #ffffff;");

        botBox = new HBox(40,betL,bet,start,hit,doubleDown,stand,moneyL);
        botBox.setAlignment(Pos.CENTER);
        botBox.setPadding(new Insets(21));

        deck = new ImageView(cardsImage[0]);
        deck.setFitHeight(270);
        deck.setFitWidth(270);
        deckBox = new HBox(deck);
        deckBox.setAlignment(Pos.CENTER);
        deckBox.setPadding(new Insets(0,100,0,0));

        playerHand = new ImageView[20];

        centerBotBox = new HBox(15);
        centerBotBox.setAlignment(Pos.CENTER);

        dealerHand = new ImageView[20];

        centerTopBox = new HBox(15);
        centerTopBox.setAlignment(Pos.CENTER);

        centerBox = new VBox(100,centerTopBox,centerBotBox);
        centerBox.setAlignment(Pos.CENTER);

        pane.setRight(deckBox);
        pane.setCenter(centerBox);
        pane.setBottom(botBox);

        scene = new Scene(pane,1920,1080);
        stage.setScene(scene);
        stage.setTitle("Black Jack");
        stage.show();
    }


    private void startHandle(Stage stage){
        start.setOnAction(event -> {});
        Game game = new Game(Integer.parseInt(bet.getText()));
        money = money-game.getBet();
        moneyL.setText("Your money: "+money+"$");
        hit.setOnAction(event -> {
            doubleDown.setOnAction(actionEvent -> {});
            hitNumber++;
            if(game.hit()){
                int j = 0;
                switch (game.getHand().get(hitNumber).getSeme()){
                    case QUORI -> j=0;
                    case QUADRI -> j=13;
                    case FIORI -> j=26;
                    case PICCHE -> j=39;
                }
                playerHand[hitNumber] = new ImageView(cardsImage[j+game.getHand().get(hitNumber).getNumber()]);
                playerHand[hitNumber].setFitHeight(198);
                playerHand[hitNumber].setFitWidth(144);
                centerBotBox.getChildren().add(playerHand[hitNumber]);
            }else{
                int j = 0;
                switch (game.getHand().get(hitNumber).getSeme()){
                    case QUORI -> j=0;
                    case QUADRI -> j=13;
                    case FIORI -> j=26;
                    case PICCHE -> j=39;
                }
                playerHand[hitNumber] = new ImageView(cardsImage[j+game.getHand().get(hitNumber).getNumber()]);
                playerHand[hitNumber].setFitHeight(198);
                playerHand[hitNumber].setFitWidth(144);
                centerBotBox.getChildren().add(playerHand[hitNumber]);
                if(ConfirmationBox.show("YOU LOSS, would you try again?","Result of the game","Yes","No")){
                    playerHand = new ImageView[20];
                    centerBotBox = new HBox(15);
                    centerBotBox.setAlignment(Pos.CENTER);
                    dealerHand = new ImageView[20];
                    centerTopBox = new HBox(15);
                    centerTopBox.setAlignment(Pos.CENTER);

                    centerBox = new VBox(100,centerTopBox,centerBotBox);
                    centerBox.setAlignment(Pos.CENTER);
                    pane.setCenter(centerBox);
                    hitNumber = 0;
                    start.setOnAction(e -> startHandle(stage));
                    hit.setOnAction(e -> {});
                    stand.setOnAction(e -> {});
                    doubleDown.setOnAction(e ->{});
                }else {
                    stage.close();
                }
            }
        });
        doubleDown.setOnAction(event ->{
            doubleDown.setOnAction(actionEvent -> {});
            money = money-game.getBet();
            moneyL.setText("Your money: "+money+"$");
            game.doubleDown();
            hit.setOnAction(event1 -> {});
            int j = 0;
            switch (game.getHand().get(0).getSeme()){
                case QUORI -> j=0;
                case QUADRI -> j=13;
                case FIORI -> j=26;
                case PICCHE -> j=39;
            }
            playerHand[1] = new ImageView(cardsImage[j+game.getHand().get(1).getNumber()]);
            playerHand[1].setFitHeight(198);
            playerHand[1].setFitWidth(144);
            centerBotBox.getChildren().add(playerHand[1]);
        });
        stand.setOnAction(event ->{
            game.setWinner(game.stand());

            for (int k=1;k<game.getDealer().size();k++){
                int j = 0;
                switch (game.getDealer().get(k).getSeme()){
                    case QUORI -> j=0;
                    case QUADRI -> j=13;
                    case FIORI -> j=26;
                    case PICCHE -> j=39;
                }
                dealerHand[k] = new ImageView(cardsImage[j+game.getDealer().get(k).getNumber()]);
                dealerHand[k].setFitHeight(198);
                dealerHand[k].setFitWidth(144);
                centerTopBox.getChildren().add(dealerHand[k]);
                stage.show();
                wait(500);
            }
            if(game.isWinner() == 1){
                money = money+(2*game.getBet());
                moneyL.setText("Your money: "+money+"$");
                if(ConfirmationBox.show("YOU WIN!!!, would you play again?","Result of the game","Yes","No")){
                    playerHand = new ImageView[20];
                    centerBotBox = new HBox(15);
                    centerBotBox.setAlignment(Pos.CENTER);
                    dealerHand = new ImageView[20];
                    centerTopBox = new HBox(15);
                    centerTopBox.setAlignment(Pos.CENTER);

                    centerBox = new VBox(100,centerTopBox,centerBotBox);
                    centerBox.setAlignment(Pos.CENTER);
                    pane.setCenter(centerBox);
                    hitNumber = 0;
                    start.setOnAction(e -> startHandle(stage));
                    hit.setOnAction(e -> {});
                    stand.setOnAction(e -> {});
                    doubleDown.setOnAction(e ->{});
                }else {
                    stage.close();
                }
            }else if(game.isWinner() == -1){
                if(ConfirmationBox.show("YOU LOSS, would you try again?","Result of the game","Yes","No")){
                    playerHand = new ImageView[20];
                    centerBotBox = new HBox(15);
                    centerBotBox.setAlignment(Pos.CENTER);
                    dealerHand = new ImageView[20];
                    centerTopBox = new HBox(15);
                    centerTopBox.setAlignment(Pos.CENTER);

                    centerBox = new VBox(100,centerTopBox,centerBotBox);
                    centerBox.setAlignment(Pos.CENTER);
                    pane.setCenter(centerBox);
                    hitNumber = 0;
                    start.setOnAction(e -> startHandle(stage));
                    hit.setOnAction(e -> {});
                    stand.setOnAction(e -> {});
                    doubleDown.setOnAction(e ->{});
                }else {
                    stage.close();
                }
            }else if (game.isWinner() == 0){
                money = money+(game.getBet());
                moneyL.setText("Your money: "+money+"$");
                if(ConfirmationBox.show("YOU TIED, would you play again?","Result of the game","Yes","No")){
                    playerHand = new ImageView[20];
                    centerBotBox = new HBox(15);
                    centerBotBox.setAlignment(Pos.CENTER);
                    dealerHand = new ImageView[20];
                    centerTopBox = new HBox(15);
                    centerTopBox.setAlignment(Pos.CENTER);

                    centerBox = new VBox(100,centerTopBox,centerBotBox);
                    centerBox.setAlignment(Pos.CENTER);
                    pane.setCenter(centerBox);
                    hitNumber = 0;
                    start.setOnAction(e -> startHandle(stage));
                    hit.setOnAction(e -> {});
                    stand.setOnAction(e -> {});
                    doubleDown.setOnAction(e ->{});
                }else {
                    stage.close();
                }
            }
        });

        int j = 0;
        switch (game.getHand().get(0).getSeme()){
            case QUORI -> j=0;
            case QUADRI -> j=13;
            case FIORI -> j=26;
            case PICCHE -> j=39;
        }
        playerHand[0] = new ImageView(cardsImage[j+game.getHand().get(0).getNumber()]);
        playerHand[0].setFitHeight(198);
        playerHand[0].setFitWidth(144);
        centerBotBox.getChildren().add(playerHand[0]);
        stage.show();
        switch (game.getDealer().get(0).getSeme()){
            case QUORI -> j=0;
            case QUADRI -> j=13;
            case FIORI -> j=26;
            case PICCHE -> j=39;
        }
        dealerHand[0] = new ImageView(cardsImage[j+game.getDealer().get(0).getNumber()]);
        dealerHand[0].setFitHeight(198);
        dealerHand[0].setFitWidth(144);
        centerTopBox.getChildren().add(dealerHand[0]);
    }

    private void wait(int x){
        long initialTime = System.currentTimeMillis();
        while (System.currentTimeMillis() <= initialTime+x){
            System.currentTimeMillis();
        }
    }


    public static void main(String[] args) {
        {                                                                                                          ///////////////////
            cardsImage[0] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\deckBack.png";              // MAZZO / BACK  //
            cardsImage[1] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\01_Asso_di_cuori.jpg";      // UNO CUORI     //
            cardsImage[2] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\02_Due_di_cuori.jpg";       // DUE CUORI     //
            cardsImage[3] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\03_Tre_di_cuori.jpg";       // TRE CUORI     //
            cardsImage[4] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\04_Quattro_di_cuori.jpg";   // QUATTRO CUORI //
            cardsImage[5] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\05_Cinque_di_cuori.jpg";    // CINQUE CUORI  //
            cardsImage[6] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\06_Sei_di_cuori.jpg";       // SEI CUORI     //
            cardsImage[7] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\07_Sette_di_cuori.jpg";     // SETTE CUORI   //
            cardsImage[8] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\08_Otto_di_cuori.jpg";      // OTTO CUORI    //
            cardsImage[9] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\09_Nove_di_cuori.jpg";      // NOVE CUORI    //
            cardsImage[10] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\10_Dieci_di_cuori.jpg";    // DIECI CUORI   //
            cardsImage[11] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\11_J_di_cuori.jpg";        // JACK CUORI    //
            cardsImage[12] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\12_Q_di_cuori.jpg";        // DONNA CUORI   //
            cardsImage[13] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\13_K_di_cuori.jpg";        // RE CUORI      //
            cardsImage[14] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\14_Asso_di_quadri.jpg";    // UNO Quadri    //
            cardsImage[15] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\15_Due_di_quadri.jpg";     // DUE Quadri    //
            cardsImage[16] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\16_Tre_di_quadri.jpg";     // TRE Quadri    //
            cardsImage[17] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\17_Quattro_di_quadri.jpg"; // QUATTRO Quadri//
            cardsImage[18] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\18_Cinque_di_quadri.jpg";  // CINQUE Quadri //
            cardsImage[19] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\19_Sei_di_quadri.jpg";     // SEI Quadri    //
            cardsImage[20] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\20_Sette_di_quadri.jpg";   // SETTE Quadri  //
            cardsImage[21] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\21_Otto_di_quadri.jpg";    // OTTO Quadri   //
            cardsImage[22] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\22_Nove_di_quadri.jpg";    // NOVE Quadri   //
            cardsImage[23] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\23_Dieci_di_quadri.jpg";   // DIECI Quadri  //
            cardsImage[24] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\24_J_di_quadri.jpg";       // JACK Quadri   //
            cardsImage[25] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\25_Q_di_quadri.jpg";       // DONNA Quadri  //
            cardsImage[26] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\26_K_di_quadri.jpg";       // RE Quadri     //
            cardsImage[27] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\27_Asso_di_fiori.jpg";     // Asso Fiori    //
            cardsImage[28] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\28_Due_di_fiori.jpg";      // DUE Fiori     //
            cardsImage[29] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\29_Tre_di_fiori.jpg";      // TRE Fiori     //
            cardsImage[30] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\30_Quattro_di_fiori.jpg";  // QUATTRO Fiori //
            cardsImage[31] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\31_Cinque_di_fiori.jpg";   // CINQUE Fiori  //
            cardsImage[32] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\32_Sei_di_fiori.jpg";      // SEI Fiori     //
            cardsImage[33] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\33_Sette_di_fiori.jpg";    // SETTE Fiori   //
            cardsImage[34] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\34_Otto_di_fiori.jpg";     // OTTO Fiori    //
            cardsImage[35] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\35_Nove_di_fiori.jpg";     // NOVE Fiori    //
            cardsImage[36] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\36_Dieci_di_fiori.jpg";    // DIECI Fiori   //
            cardsImage[37] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\37_J_di_fiori.jpg";        // JACK Fiori    //
            cardsImage[38] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\38_Q_di_fiori.jpg";        // DONNA Fiori   //
            cardsImage[39] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\39_K_di_fiori.jpg";        // RE Fiori      //
            cardsImage[40] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\40_Asso_di_picche.jpg";    // UNO Picche    //
            cardsImage[41] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\41_Due_di_picche.jpg";     // DUE Picche    //
            cardsImage[42] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\42_Tre_di_picche.jpg";     // TRE Picche    //
            cardsImage[43] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\43_Quattro_di_picche.jpg"; // QUATTRO Picche//
            cardsImage[44] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\44_Cinque_di_picche.jpg";  // CINQUE Picche //
            cardsImage[45] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\45_Sei_di_picche.jpg";     // SEI Picche    //
            cardsImage[46] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\46_Sette_di_picche.jpg";   // SETTE Picche  //
            cardsImage[47] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\47_Otto_di_picche.jpg";    // OTTO Picche   //
            cardsImage[48] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\48_Nove_di_picche.jpg";    // NOVE Picche   //
            cardsImage[49] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\49_Dieci_di_picche.jpg";   // DIECI Picche  //
            cardsImage[50] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\50_J_di_picche.jpg";       // JACK Picche   //
            cardsImage[51] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\51_Q_di_picche.jpg";       // DONNA Picche  //
            cardsImage[52] = "C:\\Users\\giuli\\IdeaProjects\\PokerFx\\src\\cp2\\files\\52_K_di_picche.jpg";       // RE Picche     //
        }                                                                                                          ///////////////////
        launch(args);
    }
}
