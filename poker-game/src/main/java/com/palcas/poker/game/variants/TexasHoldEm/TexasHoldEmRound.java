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
import java.util.function.Consumer;

import com.palcas.poker.App;
import com.palcas.poker.display.CommunityCardsDisplay;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.display.GameStateDisplay;
import com.palcas.poker.display.PauseDisplay;
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
    private Entry<Player, Integer> playerToHighestBet;
    private static boolean walkOccured = false;

    public TexasHoldEmRound(GameState gameState, BotActionService botActionService) {
        this.playerToHighestBet = gameState.getPlayerToHighestBet();
        this.gameState = gameState;
        this.botActionService = botActionService;
    }

    @Override
    public GameState executeRound() {
        // Distribute pocket cards
        HashMap<Player, TexasHoldEmPocket> playersWithPockets = distributePocketCards();
        List<Card> mainPlayerCards = playersWithPockets.get(gameState.mainPlayer).getCards();
        List<Card> communityCards = new ArrayList<Card>();

        // Pre-Flop-Betting
        gameState.setRoundStatus(RoundStatus.PRE_FLOP);
        CommunityCardsDisplay.printPreFlopBoard("Pre-Flop-Betting", mainPlayerCards);
        bettingLoop(gameState.bigBlindIndex);

        // Flop
        if (!walkOccured) {
            gameState.setRoundStatus(RoundStatus.FLOP);
            for (int i = 0; i < 3; i++) {
                communityCards.add(gameState.getDeck().drawCard());
            }
            CommunityCardsDisplay.printPostFlopBoard("Flop", mainPlayerCards, communityCards);
            System.out.println("Starting flop betting.");
            bettingLoop(gameState.bigBlindIndex);
        }

        // Turn
        if (!walkOccured) {
            gameState.setRoundStatus(RoundStatus.TURN);
            communityCards.add(gameState.getDeck().drawCard());
            CommunityCardsDisplay.printPostFlopBoard("Turn", mainPlayerCards, communityCards);
            System.out.println("Starting turn betting.");
            bettingLoop(gameState.bigBlindIndex);
        }

        // River
        if (!walkOccured) {
            gameState.setRoundStatus(RoundStatus.RIVER);
            communityCards.add(gameState.getDeck().drawCard());
            CommunityCardsDisplay.printPostFlopBoard("River", mainPlayerCards, communityCards);
            System.out.println("Starting river betting.");
            bettingLoop(gameState.bigBlindIndex);
        }

        // Check winner
        if (!walkOccured) {
            gameState.setWinners(determineWinners(playersWithPockets, communityCards));
        }

        return gameState;
    }

    @Override
    protected LinkedHashMap<Player, TexasHoldEmPocket> distributePocketCards() {
        LinkedHashMap<Player, TexasHoldEmPocket> playersWithPockets = new LinkedHashMap<>();
        for (Player player : gameState.getPlayers()) {
            TexasHoldEmPocket newPocket = new TexasHoldEmPocket().populatePocket(gameState.getDeck());
            player.setPocket(newPocket);
            playersWithPockets.put(player, newPocket);
        }
        return playersWithPockets;
    }

    @Override
    protected boolean checkIfBettingOver() {
        // Betting is not over if any player is still waiting to bet
        for (Player player : gameState.getPlayers()) {
            if (player.getState() == PlayerState.WAITING_TO_BET) {
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
            DisplayElements.printSeperator();
            System.out.println("Congratulations, " + potentialWinner.getName() + "! :)");
            potentialWinner.setChips(potentialWinner.getChips() + gameState.getPot());
            PauseDisplay.continueWithEnter();

            if (potentialWinner == gameState.getMainPlayer()) {
                TexasHoldEmGame.chipsWon += gameState.getPot();
            }

            gameState.setWinners(List.of(potentialWinner));
            walkOccured = true;
        }
    }

    @Override
    protected List<Player> determineWinners(HashMap<Player, ? extends Pocket> playersWithPockets,
            List<Card> communityCards) {
        // Bring Information in right Format for handEvaluationService.determineWinner
        HashMap<Player, Card[]> playersWithPocketAndBoardCards = new HashMap<>();
        for (Player player : gameState.getPlayers()) {
            if (player.getState() == PlayerState.FOLDED) {
                continue;
            }

            Card[] all7cardsOfPlayer = new Card[7];
            for (int i = 0; i < 5; i++) {
                all7cardsOfPlayer[i] = communityCards.get(i);
            }
            all7cardsOfPlayer[5] = player.getPocket().getCards().get(0);
            all7cardsOfPlayer[6] = player.getPocket().getCards().get(1);
            playersWithPocketAndBoardCards.put(player, all7cardsOfPlayer);
        }
        // Determine Winner
        HandEvaluationService handEvaluationService = new TexasHoldEmHandEvaluationService();
        return new ArrayList<>(Arrays.asList(handEvaluationService.determineWinner(playersWithPocketAndBoardCards)));
    }

    @Override
    protected void bet(Player player) {
        Scanner scanner = App.globalScanner;
        if (player == gameState.getMainPlayer()) {
            GameStateDisplay.display(gameState);
            if (player.getState() == PlayerState.FOLDED) {
                System.out.println("You already folded, continuing with the next player...");
                return;
            }
            PauseDisplay.continueWithEnter();
            String option;
            Consumer<Player> action;

            // If there hasn't been any bets yet, calling is not an option
            if (playerToHighestBet.getValue() == 0) {
                option = "(C)heck";
                action = this::mainPlayerCheck;
            } else {
                option = "(CALL)";
                action = this::mainPlayerCall;
            }
            new BetChoice(scanner, playerToHighestBet)
                    .addOption(option).withAction(() -> action.accept(player))
                    .addOption("(R)aise").withAction(() -> mainPlayerRaise(player))
                    .addOption("(F)old").withAction(() -> mainPlayerFold(player))
                    .addOption("(A)ll in").withAction(() -> mainPlayerAllIn(player))
                    .executeChoice();
        } else {
            if (player.getState() == PlayerState.FOLDED) {
                System.out.println(player.getName() + " already folded, continuing with the next player...");
                return;
            }
            if (player.getState() == PlayerState.IS_ALL_IN) {
                System.out.println(player.getName() + " is already all in, continuing with the next player...");
                return;
            }

            if (playerToHighestBet.getKey() == player) {
                System.out.println(
                        player.getName() + " is already the highest better, continuing with the next player...");
                return;
            }

            BotAction botAction;
            if (gameState.getRoundStatus() == RoundStatus.PRE_FLOP) {
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
                player.setState(PlayerState.FOLDED);
                System.out.println("instead, " + player.getName() + " will fold and feel bad for being a bad bot");
            }
        }
    }

    private BotAction decideForBotIfItHasNoChoiceAnyways(Player bot) {

        return null;
    }

    @Override
    protected void botFold(Player bot) {
        bot.setState(PlayerState.FOLDED);
        System.out.println(bot.getName() + " folds.");
    }

    @Override
    protected void botCall(Player bot) throws IllegalBotActionException {
        int chipsToCall = gameState.getPlayerToHighestBet().getValue() - bot.getBet();
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
            throw new IllegalBotActionException(
                    "Bot tried to Check, even though the highest Bet is larger than his bet");
        } else {
            bot.setState(PlayerState.CHECKED);
            System.out.println(bot.getName() + " checks.");
        }
    }

    @Override
    protected void botRaise(Player bot, BotAction botAction) throws IllegalBotActionException {
        Optional<Integer> raiseAmountOptional = botAction.getRaiseAmount();
        int chipsToRaise = raiseAmountOptional
                .orElseThrow(() -> new IllegalBotActionException(
                        bot.getName() + " tried to raise, but didn't provide by how much"));

        // check if raise is higher than current highest bet
        if (chipsToRaise + bot.getBet() < playerToHighestBet.getValue()) {
            throw new IllegalBotActionException(bot.getName() + " tried to raise, but didn't raise enough");
            // check if player has enough chips
        } else if (chipsToRaise > bot.getChips()) {
            throw new IllegalBotActionException(
                    bot.getName() + " tried to raise, but doesn't have enough chips to do so");
        } else {
            // Update bot data
            bot.setBet(bot.getBet() + chipsToRaise);
            bot.setChips(bot.getChips() - chipsToRaise);
            bot.setState(PlayerState.RAISED);
            System.out.println(bot.getName() + " raises by " + chipsToRaise + ".");

            // Update game state
            playerToHighestBet.setValue(playerToHighestBet.getValue() + chipsToRaise);
            gameState.setPot(gameState.getPot() + chipsToRaise);
            System.out.println("The pot is now at " + gameState.getPot() + ".");

            setPlayersBackToWaitingToBet(bot);
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
        player.setState(PlayerState.CALLED);
        System.out.println("You call " + chipsToCall + ".");
        gameState.setPot(gameState.getPot() + chipsToCall);
        System.out.println("The pot is now at " + gameState.getPot() + ".");
    }

    @Override
    protected void mainPlayerRaise(Player player) {
        Scanner scanner = App.globalScanner;
        Optional<Object> raiseAmountOptional = new RaiseChoice(scanner).executeChoice();
        // confident casting since we know the RaiseChoice returns an Integer
        int chipsToRaise = (int) raiseAmountOptional.get();

        // check if raise is higher than current highest bet
        if (chipsToRaise + player.getBet() <= playerToHighestBet.getValue()) {
            System.out.println("You have to raise at least to " + playerToHighestBet.getValue() + " to raise.");
            mainPlayerRaise(player);
            return;
            // check if player has enough chips
        } else if (chipsToRaise > player.getChips()) {
            System.out.println("You don't have enough chips to raise by " + chipsToRaise + ".");
            mainPlayerRaise(player);
            return;
        } else {
            // Update player data
            player.setBet(player.getBet() + chipsToRaise);
            player.setChips(player.getChips() - chipsToRaise);
            player.setState(PlayerState.RAISED);

            // Update game state
            playerToHighestBet.setValue(playerToHighestBet.getValue() + chipsToRaise);
            gameState.setPot(gameState.getPot() + chipsToRaise);
            System.out.println("The pot is now at " + gameState.getPot() + ".");

            setPlayersBackToWaitingToBet(player);
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
        System.out.println("You go all in with a total of " + allInAmount + "!");
        if (playerToHighestBet.getValue() < allInAmount) {
            playerToHighestBet.setValue(allInAmount);
        }
        gameState.setPot(gameState.getPot() + chipsToRaise);
        System.out.println("The pot is now at " + gameState.getPot() + ".");
    }

    @Override
    protected void turn() {
        communityCards.add(gameState.getDeck().drawCard());
        CommunityCardsDisplay.printPostFlopBoard("Turn", mainPlayerCards, communityCards);
        System.out.println("Starting turn betting.");
        bettingLoop(gameState.getBigBlindIndex());
    }

    protected void river() {
        communityCards.add(gameState.getDeck().drawCard());
        CommunityCardsDisplay.printPostFlopBoard("River", mainPlayerCards, communityCards);
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
        CommunityCardsDisplay.printPostFlopBoard("Flop", mainPlayerCards, communityCards);
        System.out.println("Starting flop betting.");
        bettingLoop(gameState.getBigBlindIndex());
    }

    private void setPlayersBackToWaitingToBet(Player playerToExclude) {
        for (Player player : this.gameState.getPlayers()) {
            // For all players other than the one raising, reset their state to
            // WAITING_TO_BET in case they haven't FOLDED or are ALL_IN
            if (player.getState() == PlayerState.CHECKED
                    || player.getState() == PlayerState.RAISED
                    || player.getState() == PlayerState.CALLED) {
                if (player != playerToExclude) {
                    player.setState(PlayerState.WAITING_TO_BET);
                }
            }
        }
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
