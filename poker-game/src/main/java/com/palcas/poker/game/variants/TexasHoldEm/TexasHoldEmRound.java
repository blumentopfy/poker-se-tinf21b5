package com.palcas.poker.game.variants.TexasHoldEm;

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
import com.palcas.poker.game.Pocket;
import com.palcas.poker.game.Round;
import com.palcas.poker.game.evaluation.HandEvaluationService;
import com.palcas.poker.game.evaluation.TexasHoldEmHandEvaluationService;
import com.palcas.poker.game.poker_bot.BotAction;
import com.palcas.poker.game.poker_bot.BotActionService;
import com.palcas.poker.game.poker_bot.IllegalBotActionException;
import com.palcas.poker.game.variants.RoundStatus;
import com.palcas.poker.input.BetChoice;
import com.palcas.poker.input.RaiseChoice;
import com.palcas.poker.model.PlayerState;

public class TexasHoldEmRound extends Round {
    private GameState gameState;
    private BotActionService botActionService;
    private List<Card> mainPlayerCards;
    private List<Card> communityCards;
    private static Entry<Player, Integer> playerToHighestBet;

    public TexasHoldEmRound(GameState gameState, BotActionService botActionService) {
        super(gameState, botActionService);
    }

    @Override
    public GameState executeRound() {
        // Distribute pocket cards
        HashMap<Player, TexasHoldEmPocket> playersWithPockets = distributePocketCards();
        List<Card> mainPlayerCards = playersWithPockets.get(gameState.mainPlayer).getCards();
        List<Card> communityCards = new ArrayList<Card>();

        // Pre-Flop-Betting
        gameState.setRoundStatus(RoundStatus.PRE_FLOP);
        BoardDisplay.printPreFlopBoard("Pre-Flop-Betting", mainPlayerCards);
        bettingLoop(gameState.bigBlindIndex);

        // Flop
        gameState.setRoundStatus(RoundStatus.FLOP);
        for (int i = 0; i < 3; i++) {
            communityCards.add(gameState.getDeck().drawCard());
        }
        BoardDisplay.printPostFlopBoard("Flop", mainPlayerCards, communityCards);
        System.out.println("Starting flop betting.");
        bettingLoop(gameState.bigBlindIndex);

        // Turn
        gameState.setRoundStatus(RoundStatus.TURN);
        communityCards.add(gameState.getDeck().drawCard());
        BoardDisplay.printPostFlopBoard("Turn", mainPlayerCards, communityCards);
        System.out.println("Starting turn betting.");
        bettingLoop(gameState.bigBlindIndex);

        // River
        gameState.setRoundStatus(RoundStatus.RIVER);
        communityCards.add(gameState.getDeck().drawCard());
        BoardDisplay.printPostFlopBoard("River", mainPlayerCards, communityCards);
        System.out.println("Starting river betting.");
        bettingLoop(gameState.bigBlindIndex);

        // Check winner
        gameState.setWinners(determineWinners(playersWithPockets, communityCards));

        return gameState;
    }

    @Override
    protected LinkedHashMap<Player, TexasHoldEmPocket> distributePocketCards() {
        LinkedHashMap<Player, TexasHoldEmPocket> playersWithPockets = new LinkedHashMap<>();
        for (Player player : this.gameState.getPlayers()) {
            TexasHoldEmPocket newPocket = new TexasHoldEmPocket().populatePocket(this.gameState.getDeck());
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
    // Checks whether a player has won the pot without a showdown, i.e. all other
    // players have folded
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
            System.out.println(
                    potentialWinner.getName() + " wins the pot of " + gameState.getPot() + " without a showdown!");
            System.out.println("Congratulations, " + potentialWinner.getName() + "! :)");
            potentialWinner.setChips(potentialWinner.getChips() + gameState.getPot());

            if (potentialWinner == gameState.getMainPlayer()) {
                TexasHoldEmGame.chipsWon += gameState.getPot();
            }

            gameState.setPot(0);
        }
    }

    @Override
    protected List<Player> determineWinners(HashMap<Player, ? extends Pocket> playersWithPockets,
            List<Card> communityCards) {
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
                    .addOption("(C)heck").withAction(() -> mainPlayerCheck(player))
                    .addOption("(CALL)").withAction(() -> mainPlayerCall(player))
                    .addOption("(R)aise").withAction(() -> mainPlayerRaise(player))
                    .addOption("(F)old").withAction(() -> mainPlayerFold(player))
                    .addOption("(A)ll in").withAction(() -> mainPlayerAllIn(player))
                    .executeChoice();
        } else {
            // TODO Szenarien in denen der Bot keine Wahl hat abfangen
            BotAction botAction;
            if(gameState.getRoundStatus() == RoundStatus.PRE_FLOP) {
                botAction = botActionService.decidePreFlopAction(player, gameState.getPlayers(), gameState);
            } else {
                botAction = botActionService.decidePostFlopAction(player, communityCards, gameState);
            }
            try {
                switch (botAction.getActionType()) {
                    case FOLD -> botFold(player);
                    case CALL -> botCall(player);
                    case CHECK -> botCheck(player);
                    case RAISE -> botRaise(player, botAction);
                    case ALL_IN -> botAllIn(player);
                }
            } catch (IllegalBotActionException e) {
                System.out.println(e.getMessage());
                System.out.println("instead, " + player.getName() + " will fold and feel bad for being a bad bot");
            }
        }
    }

    @Override
    protected void botFold(Player bot) {
        bot.setState(PlayerState.FOLDED);
        System.out.println(bot.getName() + " folds.");
    }

    @Override
    protected void botCall(Player bot) throws IllegalBotActionException {
        int chipsToCall = playerToHighestBet.getValue() - bot.getBet();
        if (bot.getChips() < chipsToCall) {
            throw new IllegalBotActionException("Bot tried to call, but doesn't have enough chips");
        }
        bot.setBet(bot.getBet() + chipsToCall);
        bot.setChips(bot.getChips() - chipsToCall);
        System.out.println(bot.getName() + " calls " + chipsToCall + ".");
        gameState.setPot(gameState.getPot() + chipsToCall);
        System.out.println("The pot is now at " + gameState.getPot() + ".");
    }

    protected void botCheck(Player bot) throws IllegalBotActionException {
        if (bot.getBet() < playerToHighestBet.getValue()) {
            throw new IllegalBotActionException("Bot tried to Check, even though the highest Bet is larger than his bet");
        } else {
            bot.setState(PlayerState.CHECKED);
            System.out.println(bot.getName() + " checks.");
        }
    }

    @Override
    protected void botRaise(Player bot, BotAction botAction) throws IllegalBotActionException {
        Optional<Integer> raiseAmountOptional = botAction.getRaiseAmount();
        int chipsToRaise = raiseAmountOptional.orElseThrow(() -> new IllegalBotActionException("Bot tried to raise, but didn't provide by how much"));

        // check if raise is higher than current highest bet
        if (chipsToRaise + bot.getBet() < playerToHighestBet.getValue()) {
            throw new IllegalBotActionException("Bot tried to raise, but didn't raise enough");
        // check if player has enough chips
        } else if (chipsToRaise > bot.getChips()) {
            throw new IllegalBotActionException("Bot tried to raise, but doesn't have enough chips to do so");
        } else {
            bot.setBet(bot.getBet() + chipsToRaise);
            bot.setChips(bot.getChips() - chipsToRaise);
            System.out.println(bot.getName() + " raises by " + chipsToRaise + ".");
            playerToHighestBet.setValue(playerToHighestBet.getValue() + chipsToRaise);
            gameState.setPot(gameState.getPot() + chipsToRaise);
            System.out.println("The pot is now at " + gameState.getPot() + ".");
        }
    }

    protected void botAllIn(Player bot) {
        int chipsToRaise = bot.getChips();
        int allInAmount = bot.getChips() + bot.getBet();
        bot.setBet(allInAmount);
        bot.setChips(0);
        bot.setState(PlayerState.IS_ALL_IN);
        System.out.println(bot.getName() + " goes all in with a total of" + allInAmount + "!");
        if (playerToHighestBet.getValue() < allInAmount) {
            playerToHighestBet.setValue(allInAmount);
        }
        gameState.setPot(gameState.getPot() + chipsToRaise);
        System.out.println("The pot is now at " + gameState.getPot() + ".");
    }


    @Override
    protected void mainPlayerCheck(Player player) {
        if (player.getBet() < playerToHighestBet.getValue()) {
            System.out.println("You have to call at least " + playerToHighestBet.getValue() + " to check.");
            bet(player);
            return;
        } else {
            player.setState(PlayerState.CHECKED);
            System.out.println("You check.");
        }
    }

    @Override
    protected void mainPlayerCall(Player player) {
        int chipsToCall = playerToHighestBet.getValue() - player.getBet();
        player.setBet(player.getBet() + chipsToCall);
        player.setChips(player.getChips() - chipsToCall);
        System.out.println("You call " + chipsToCall + ".");
        gameState.setPot(gameState.getPot() + chipsToCall);
        System.out.println("The pot is now at " + gameState.getPot() + ".");
    }

    @Override
    protected void mainPlayerRaise(Player player) {
        //TODO don't use a new scanner, pass down the singleton instead
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

            playerToHighestBet.setValue(playerToHighestBet.getValue() + chipsToRaise);
            gameState.setPot(gameState.getPot() + chipsToRaise);
            System.out.println("The pot is now at " + gameState.getPot() + ".");
        }
    }

    @Override
    protected void mainPlayerFold(Player player) {
        player.setState(PlayerState.FOLDED);
        System.out.println("You fold.");
    }

    @Override
    protected void mainPlayerAllIn(Player player) {
        int chipsToRaise = player.getChips();
        int allInAmount = player.getChips() + player.getBet();
        player.setBet(allInAmount);
        player.setChips(0);
        player.setState(PlayerState.IS_ALL_IN);
        System.out.println("You go all in with a total of" + allInAmount + "!");
        if (playerToHighestBet.getValue() < allInAmount) {
            playerToHighestBet.setValue(allInAmount);
        }
        gameState.setPot(gameState.getPot() + chipsToRaise);
        System.out.println("The pot is now at " + gameState.getPot() + ".");
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
        return Objects.equals(gameState, this.gameState) && Objects.equals(botActionService, this.botActionService)
                && Objects.equals(mainPlayerCards, gameState.getMainPlayer().getPocket().getCards())
                && Objects.equals(communityCards, gameState.getCommunityCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameState, botActionService, mainPlayerCards, communityCards);
    }

    @Override
    public String toString() {
        return "TexasHoldEmRound{" +
                "gameState=" + gameState +
                ", botActionService=" + botActionService +
                ", mainPlayerCards=" + mainPlayerCards +
                ", communityCards=" + communityCards +
                '}';
    }
}
