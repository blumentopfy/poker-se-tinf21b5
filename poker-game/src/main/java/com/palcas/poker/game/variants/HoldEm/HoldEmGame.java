package com.palcas.poker.game.variants.HoldEm;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.BoardDisplay;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.evaluation.HandEvaluationService;
import com.palcas.poker.game.evaluation.TexasHoldEmHandEvaluationService;
import com.palcas.poker.input.*;
import com.palcas.poker.model.PlayerState;

public class HoldEmGame {
    private final Scanner scanner;
    private final Player mainPlayer;
    private List<Player> players;

    private static int bigBlind;
    private static int smallBlind;
    private static int smallBlindIndex;
    private static int bigBlindIndex;
    private static int pot;
    private static Entry<Player, Integer> playerToHighestBet;


    private Deck deck;

    public HoldEmGame(Player mainPlayer, ArrayList<Player> players) {
        this.mainPlayer = mainPlayer;
        this.players = players;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        DisplayElements.clearConsole();
        System.out.println("Starting a game of Texas Hold'em...");

        // Query player for number of players he wants to play with and how many chips everyone should have
        int playerCount = new PlayerCountChoice(scanner).executeChoice().get();
        DisplayElements.clearConsole();
        new StakeLevelChoice(scanner)
                .addOption("Low Stakes (Blinds: 5/10)").withAction(() -> initializeBlinds(5))
                .addOption("Medium Stakes (Blinds: 25/50)").withAction(() -> initializeBlinds(25))
                .addOption("High Stakes (Blinds: 100/200)").withAction(() -> initializeBlinds(100))
                .addOption("Very High Stakes (Blinds: 500/1000)").withAction(() -> initializeBlinds(500))
                .executeChoice();

        DisplayElements.clearConsole();

        // Populate player list with the main player and the number of players he wants to play with
        Collections.shuffle(PlayerNames.NAMES);
        List<String> playerNames = PlayerNames.NAMES.subList(0, playerCount);

        // Initialize players with random chips between 20x and 40x big blind
        this.players = playerNames.stream()
            .map(player -> new Player(player, (int) (bigBlind * ((Math.random() * 20) + 20))))
            .collect(Collectors.toList());
        this.players.add(this.mainPlayer);

        System.out.println(DisplayElements.SEPERATOR);
        System.out.println("Ready to play some Poker, " + mainPlayer.getName() + "? Let's meet the other players:");

        for (Player player : players) {
            if (player != mainPlayer) {
                System.out.println(player.getName() + " - Chips: " + player.getChips());
            }
        }
        System.out.println("(press enter to proceed)");
        scanner.nextLine();
        
        // Create a new deck and shuffle it
        this.deck = new Deck().shuffle();

        startPokerGameLoop();

        //TODO save progress of the player

    }

    private void startPokerGameLoop() {
        int round = 0;
        while (true) {
            DisplayElements.clearConsole();
            System.out.println("Starting round " + round + ".");
            // reset player states to WAITING_TO_BET and bets to 0
            resetStatesAndBets();

            deck.shuffle();

            setBlinds();

            roundLoop(bigBlindIndex);

            checkLosers();

            adjustBlinds(round++);

            // rotate blinds
            smallBlindIndex = (smallBlindIndex + 1) % players.size();
            bigBlindIndex = (bigBlindIndex + 1) % players.size();
        }
    }

    private void resetStatesAndBets() {
        for (Player player : this.players) {
            player.setState(PlayerState.WAITING_TO_BET);
        }

        this.players.stream().forEach(player -> player.setBet(0));
    }

    private void setBlinds() {
        if (players.get(smallBlindIndex) == mainPlayer) {
            System.out.println("You set the small blind of " + smallBlind + ".");
        } else {
            System.out.println(players.get(smallBlindIndex).getName() + " sets the small blind of " + smallBlind + ".");
        }
        players.get(smallBlindIndex).setBet(smallBlind);


        if (players.get(bigBlindIndex) == mainPlayer) {
            System.out.println("You set the big blind of " + bigBlind + ".");
        } else {
            System.out.println(players.get(bigBlindIndex).getName() + " sets the big blind of " + bigBlind + ".");
        }
        players.get(bigBlindIndex).setBet(bigBlind);

        pot = smallBlind + bigBlind;
        HoldEmGame.playerToHighestBet = new SimpleEntry<>(players.get(bigBlindIndex), bigBlind);
    }

    private void roundLoop(int bigBlindIndex) {

        // Distribute pocket cards
        HashMap<Player, HoldEmPocket> playersWithPockets = distributePocketCards();
        List<Card> mainPlayerCards = playersWithPockets.get(this.mainPlayer).getCards();
        List<Card> communityCards = new ArrayList<Card>();        

        // Preflop-Betting
        BoardDisplay.printPreFlopBoard("Preflop-Betting", mainPlayerCards);
        
        bettingLoop(bigBlindIndex);

        // Flop
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.drawCard());
        }
        BoardDisplay.printPostFlopBoard("Flop", mainPlayerCards, communityCards);
        System.out.println("Starting flop betting.");
        bettingLoop(bigBlindIndex);

        // Turn
        communityCards.add(deck.drawCard());
        BoardDisplay.printPostFlopBoard("Turn", mainPlayerCards, communityCards);
        System.out.println("Starting turn betting.");
        bettingLoop(bigBlindIndex);

        // River
        communityCards.add(deck.drawCard());
        BoardDisplay.printPostFlopBoard("River", mainPlayerCards, communityCards);
        System.out.println("Starting river betting.");
        bettingLoop(bigBlindIndex);

        // Check winner
        ArrayList<Player> winners = determineWinners(playersWithPockets, communityCards);
    }

    private void splitPot(List<Player> winners) {
        if (winners.isEmpty()) {
            System.out.println("No winners to split the pot.");
            return;
        }
        int chipsPerWinner = pot / winners.size();  // this automatically rounds down to the nearest whole number
        for (Player winner : winners) {
            winner.addChips(chipsPerWinner);
            System.out.println(winner.getName() + " wins " + chipsPerWinner + " chips!");
        }

        // If there's a remainder, keep them in the pot for next round
        int remainder = pot % winners.size();
        if (remainder > 0 && !winners.isEmpty()) {
            System.out.println("The remaining " + remainder + " chips can't be distributed equally, so they stay in the pot for the next round!");
        }
        pot = remainder;
    }

    private ArrayList<Player> determineWinners(HashMap<Player, HoldEmPocket> playersWithPockets, List<Card> communityCards) {
        // Bring Information in right Format for handEvaluationService.determineWinner
        HashMap<Player, Card[]> playersWithPocketAndBoardCards = new HashMap<>();
        for (Player player : players) {
            Card[] all7cardsOfPlayer = new Card[7];
            for (int i = 0; i < 5; i++) {
                all7cardsOfPlayer[i] = communityCards.get(i);
            }
            all7cardsOfPlayer[5] = playersWithPockets.get(player).getCards().get(0);
            all7cardsOfPlayer[6] = playersWithPockets.get(player).getCards().get(0);
            playersWithPocketAndBoardCards.put(player, all7cardsOfPlayer);
        }
        // Determine Winner
        HandEvaluationService handEvaluationService = new TexasHoldEmHandEvaluationService();
        return new ArrayList<>(Arrays.asList(handEvaluationService.determineWinner(playersWithPocketAndBoardCards)));
    }


    private LinkedHashMap<Player, HoldEmPocket> distributePocketCards() {
        LinkedHashMap<Player, HoldEmPocket> playersWithPockets = new LinkedHashMap<>();
        for (Player player : this.players) {
            HoldEmPocket newPocket = new HoldEmPocket().populatePocket(deck);
            player.setPocket(newPocket);
            playersWithPockets.put(player, newPocket);
        }
        return playersWithPockets;
    }

    private void bet(Player player) {

        if (player == this.mainPlayer) {
            new BetChoice(scanner, playerToHighestBet)
            .addOption("(C)heck").withAction(() -> check(player))
            .addOption("(CALL)").withAction(() -> call(player))
            .addOption("(R)aise").withAction(() -> raise(player))
            .addOption("(F)old").withAction(() -> fold(player))
            .executeChoice();
        } else {
            //TODO implement AI
            // AIBehavior.decideAction();
            // should return 
            player.setState(PlayerState.CALLED);
            System.out.println(player.getName() + " calls.");
        }
    }

    private void check(Player player) {
        if (player.getBet() < playerToHighestBet.getValue()) {
            System.out.println("You have to call at least " + playerToHighestBet.getValue() + " to check.");
            bet(player);
            return;
        } else {
            player.setState(PlayerState.CHECKED);
            if (player == this.mainPlayer) {
                System.out.println("You check.");
            } else {
                System.out.println(player.getName() + " checks.");
            }
        }
    }

    private void call(Player player) {
        int chipsToCall = playerToHighestBet.getValue() - player.getBet();
        player.setBet(player.getBet() + chipsToCall);
        player.setChips(player.getChips() - chipsToCall);

        if (player == this.mainPlayer) {
            System.out.println("You call " + chipsToCall + ".");
        } else {
            System.out.println(player.getName() + " calls " + chipsToCall + ".");
        }

        pot += chipsToCall;
        System.out.println("The pot is now at " + pot + ".");
    }

    private void raise(Player player) {
        Optional<Object> raiseAmountOptional = new RaiseChoice(scanner).executeChoice();
        // confident casting since we know the RaiseChoice returns an Integer
        int chipsToRaise = (int) raiseAmountOptional.get();

        // check if raise is higher than current highest bet
        if (chipsToRaise < playerToHighestBet.getValue()) {
            System.out.println("You have to raise at least " + playerToHighestBet.getValue() + " to raise.");
            bet(player);
            return;
        // check if player has enough chips
        } else if (chipsToRaise > player.getChips()) {
            System.out.println("You don't have enough chips to raise by " + chipsToRaise + ".");
            bet(player);
            return;
        } else {
            player.setBet(player.getBet() + chipsToRaise);
            player.setChips(player.getChips() - chipsToRaise);

            if (player != this.mainPlayer) {
                System.out.println(player.getName() + " raises by " + chipsToRaise + ".");
            }

            playerToHighestBet.setValue(playerToHighestBet.getValue() + chipsToRaise);
            pot += chipsToRaise;
            System.out.println("The pot is now at " + pot + ".");
        }
    }

    private void fold(Player player) {
        player.setState(PlayerState.FOLDED);
        if (player == this.mainPlayer) {
            System.out.println("You fold.");
        } else {
            System.out.println(player.getName() + " folds.");
        }
    }

    private static void adjustBlinds(int round) {
        if (round % 2 == 0) {
            bigBlind = (int) Math.ceil(bigBlind * 1.25);
            smallBlind = (int) Math.ceil(smallBlind * 1.25);
        }
    }

    private void checkLosers() {
        for (Player player : players) {
            if (player.getChips() == 0) {
                System.out.println(player.getName() + " is out of chips and has lost the game.");
                players.remove(player);
            }
        }
    }

    private boolean checkIfBettingOver() {
        // Betting is not over if either...
        for (Player player : players) {
            // ... a player is still waiting to bet
            if (player.getState() == PlayerState.WAITING_TO_BET) {
                return false;
            // ... or a player has checked and has not called the highest bet/raise
            } else if (player.getState() == PlayerState.CHECKED && player.getBet() != playerToHighestBet.getValue()) {
                return false;
            // ... or a player has raised but not to the currently highest bet
            } else if (player.getState() == PlayerState.RAISED && player.getBet() != playerToHighestBet.getValue()) {
                return false;
            }
        }

    // If none of the conditions above are met, betting is over
    return true;
    }

    private void bettingLoop(int bigBlindIndex) {

        // Start betting at index of big blind + 1
        // Since this will overflow, we will take the modulo of the player count
        int playerToBetIndex = bigBlindIndex + 1 % this.players.size();

        boolean bettingOver = false;
        while (!bettingOver) {
            bet(this.players.get(playerToBetIndex));
            bettingOver = checkIfBettingOver();
            playerToBetIndex = ++playerToBetIndex % this.players.size();
        }

        DisplayElements.printSeperator();
        System.out.println("Betting is over.");
        System.out.println("The pot is now at " + pot + ".");
        DisplayElements.printSeperator();

        checkForWalk();
    }

    // Checks whether a player has won the pot without a showdown, i.e. all other players have folded
    public void checkForWalk() {
        int playersNotFoldedCount = 0;
        Player potentialWinner = null;
        for (Player player : players) {
            if (player.getState() != PlayerState.FOLDED) {
                playersNotFoldedCount++;
                potentialWinner = player;
            }
        }

        if (playersNotFoldedCount == 1) {
            System.out.println(potentialWinner.getName() + " wins the pot of " + pot + " without a showdown!");
            System.out.println("Congratulations, " + potentialWinner.getName() + "! :)");
            potentialWinner.setChips(potentialWinner.getChips() + pot);
            pot = 0;
        }
    }

    private void initializeBlinds(int smallBlindValue) {
        smallBlindIndex = 0;
        bigBlindIndex = 1;
        smallBlind = smallBlindValue;
        bigBlind = 2*smallBlindValue;
    }
}
