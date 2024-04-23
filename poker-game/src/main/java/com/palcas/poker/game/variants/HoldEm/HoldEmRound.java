package com.palcas.poker.game.variants.HoldEm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import com.palcas.poker.display.BoardDisplay;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.Round;
import com.palcas.poker.game.evaluation.HandEvaluationService;
import com.palcas.poker.game.evaluation.TexasHoldEmHandEvaluationService;
import com.palcas.poker.game.poker_bot.BotActionService;
import com.palcas.poker.input.BetChoice;
import com.palcas.poker.input.RaiseChoice;
import com.palcas.poker.model.PlayerState;

public class HoldEmRound extends Round {
    private GameState gameState;
    private BotActionService botActionService;
    private List<Card> mainPlayerCards;
    private List<Card> communityCards;
    private static Entry<Player, Integer> playerToHighestBet;

    public HoldEmRound(GameState gameState, BotActionService botActionService) {
        super(gameState, botActionService, gameState.mainPlayer.getPocket().getCards());
    }

    @Override
    public GameState executeRound() {
        // Distribute pocket cards
        HashMap<Player, HoldEmPocket> playersWithPockets = distributePocketCards();
        List<Card> mainPlayerCards = playersWithPockets.get(gameState.mainPlayer).getCards();
        List<Card> communityCards = new ArrayList<Card>();    

        // Preflop-Betting
        BoardDisplay.printPreFlopBoard("Preflop-Betting", mainPlayerCards);
        bettingLoop(gameState.bigBlindIndex);

        
        // Flop
        for (int i = 0; i < 3; i++) {
            communityCards.add(gameState.getDeck().drawCard());
        }
        BoardDisplay.printPostFlopBoard("Flop", mainPlayerCards, communityCards);
        System.out.println("Starting flop betting.");
        bettingLoop(gameState.bigBlindIndex);

        // Turn
        communityCards.add(gameState.getDeck().drawCard());
        BoardDisplay.printPostFlopBoard("Turn", mainPlayerCards, communityCards);
        System.out.println("Starting turn betting.");
        bettingLoop(gameState.bigBlindIndex);

        // River
        communityCards.add(gameState.getDeck().drawCard());
        BoardDisplay.printPostFlopBoard("River", mainPlayerCards, communityCards);
        System.out.println("Starting river betting.");
        bettingLoop(gameState.bigBlindIndex);

        // Check winner
        gameState.setWinners(determineWinners(playersWithPockets, communityCards));

        return gameState;	
    }

    @Override
    protected LinkedHashMap<Player, HoldEmPocket> distributePocketCards() {
        LinkedHashMap<Player, HoldEmPocket> playersWithPockets = new LinkedHashMap<>();
        for (Player player : this.gameState.getPlayers()) {
            HoldEmPocket newPocket = new HoldEmPocket().populatePocket(this.gameState.getDeck());
            player.setPocket(newPocket);
            playersWithPockets.put(player, newPocket);
        }
        return playersWithPockets;
    }

    @Override
    protected boolean checkIfBettingOver() {
        // Betting is not over if either...
        for (Player player : gameState.getPlayers()) {
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

    @Override
    // Checks whether a player has won the pot without a showdown, i.e. all other players have folded
    public void checkForWalk() {
        int playersNotFoldedCount = 0;
        Player potentialWinner = null;
        for (Player player : gameState.getPlayers()) {
            if (player.getState() != PlayerState.FOLDED) {
                playersNotFoldedCount++;
                potentialWinner = player;
            }
        }

        if (playersNotFoldedCount == 1) {
            System.out.println(potentialWinner.getName() + " wins the pot of " + gameState.getPot() + " without a showdown!");
            System.out.println("Congratulations, " + potentialWinner.getName() + "! :)");
            potentialWinner.setChips(potentialWinner.getChips() + gameState.getPot());
            gameState.setPot(0);
        }
    }

    @Override
    protected List<Player> determineWinners(HashMap<Player, HoldEmPocket> playersWithPockets, List<Card> communityCards) {
        // Bring Information in right Format for handEvaluationService.determineWinner
        HashMap<Player, Card[]> playersWithPocketAndBoardCards = new HashMap<>();
        for (Player player : gameState.getPlayers()) {
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

    @Override
    protected void bet(Player player) {
        Scanner scanner = new Scanner(System.in);
        if (player == gameState.getMainPlayer()) {
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

    @Override
    protected void check(Player player) {
        if (player.getBet() < playerToHighestBet.getValue()) {
            System.out.println("You have to call at least " + playerToHighestBet.getValue() + " to check.");
            bet(player);
            return;
        } else {
            player.setState(PlayerState.CHECKED);
            if (player == gameState.getMainPlayer()) {
                System.out.println("You check.");
            } else {
                System.out.println(player.getName() + " checks.");
            }
        }
    }

    @Override
    protected void call(Player player) {
        int chipsToCall = playerToHighestBet.getValue() - player.getBet();
        player.setBet(player.getBet() + chipsToCall);
        player.setChips(player.getChips() - chipsToCall);

        if (player == gameState.getMainPlayer()) {
            System.out.println("You call " + chipsToCall + ".");
        } else {
            System.out.println(player.getName() + " calls " + chipsToCall + ".");
        }

        gameState.setPot(gameState.getPot() + chipsToCall);
        System.out.println("The pot is now at " + gameState.getPot() + ".");
    }

    @Override
    protected void raise(Player player) {
        Scanner scanner = new Scanner(System.in);
        Optional<Object> raiseAmountOptional = new RaiseChoice(scanner).executeChoice();
        // confident casting since we know the RaiseChoice returns an Integer
        int chipsToRaise = (int) raiseAmountOptional.get();

        // check if raise is higher than current highest bet
        if (chipsToRaise + player.getBet() < playerToHighestBet.getValue()) {
            System.out.println("You have to raise at least to " + playerToHighestBet.getValue() + " to raise.");
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

            //TODO does this ever get executed?
            if (player != gameState.getMainPlayer()) {
                System.out.println(player.getName() + " raises by " + chipsToRaise + ".");
            }

            playerToHighestBet.setValue(playerToHighestBet.getValue() + chipsToRaise);
            gameState.setPot(gameState.getPot() + chipsToRaise);
            System.out.println("The pot is now at " + gameState.getPot() + ".");
        }
    }

    @Override
    protected void fold(Player player) {
        player.setState(PlayerState.FOLDED);
        if (player == gameState.getMainPlayer()) {
            System.out.println("You fold.");
        } else {
            System.out.println(player.getName() + " folds.");
        }
    }

    @Override
    protected void turn() {
        communityCards.add(gameState.getDeck().drawCard());
        BoardDisplay.printPostFlopBoard("Turn", mainPlayerCards, communityCards);
        System.out.println("Starting turn betting.");
        bettingLoop(gameState.getBigBlindIndex());
    }

    protected void river() {
        communityCards.add(gameState.getDeck().drawCard());
        BoardDisplay.printPostFlopBoard("River", mainPlayerCards, communityCards);
        System.out.println("Starting river betting.");
        bettingLoop(gameState.getBigBlindIndex());
    }

    @Override
    protected void bettingLoop(int bigBlindIndex) {

        // Start betting at index of big blind + 1
        // Since this will overflow, we will take the modulo of the player count
        int playerToBetIndex = bigBlindIndex + 1 % gameState.getPlayers().size();

        boolean bettingOver = false;
        while (!bettingOver) {
            bet(gameState.getPlayers().get(playerToBetIndex));
            bettingOver = checkIfBettingOver();
            playerToBetIndex = ++playerToBetIndex % gameState.getPlayers().size();
        }

        DisplayElements.printSeperator();
        System.out.println("Betting is over.");
        System.out.println("The pot is now at " + gameState.getPot() + ".");
        DisplayElements.printSeperator();

        checkForWalk();
    }

    @Override
    protected void flop() {
        for (int i = 0; i < 3; i++) {
            communityCards.add(gameState.getDeck().drawCard());
        }
        BoardDisplay.printPostFlopBoard("Flop", mainPlayerCards, communityCards);
        System.out.println("Starting flop betting.");
        bettingLoop(gameState.getBigBlindIndex());
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public BotActionService getBotActionService() {
        return this.botActionService;
    }

    public void setBotActionService(BotActionService botActionService) {
        this.botActionService = botActionService;
    }

    public List<Card> getMainPlayerCards() {
        return this.mainPlayerCards;
    }

    public void setMainPlayerCards(List<Card> mainPlayerCards) {
        this.mainPlayerCards = mainPlayerCards;
    }

    public List<Card> getCommunityCards() {
        return this.communityCards;
    }

    public void setCommunityCards(List<Card> communityCards) {
        this.communityCards = communityCards;
    }

    public Round gameState(GameState gameState) {
        setGameState(gameState);
        return this;
    }

    public Round botActionService(BotActionService botActionService) {
        setBotActionService(botActionService);
        return this;
    }

    public Round mainPlayerCards(List<Card> mainPlayerCards) {
        setMainPlayerCards(mainPlayerCards);
        return this;
    }

    public Round communityCards(List<Card> communityCards) {
        setCommunityCards(communityCards);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Round)) {
            return false;
        }
        Round round = (Round) o;
        return Objects.equals(gameState, this.gameState) && Objects.equals(botActionService, this.botActionService) && Objects.equals(mainPlayerCards, gameState.getMainPlayer().getPocket().getCards()) && Objects.equals(communityCards, gameState.getCommunityCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameState, botActionService, mainPlayerCards, communityCards);
    }

    @Override
    public String toString() {
        return "{" +
            " gameState='" + getGameState() + "'" +
            ", botActionService='" + getBotActionService() + "'" +
            ", mainPlayerCards='" + getMainPlayerCards() + "'" +
            ", communityCards='" + getCommunityCards() + "'" +
            "}";
    }
}
