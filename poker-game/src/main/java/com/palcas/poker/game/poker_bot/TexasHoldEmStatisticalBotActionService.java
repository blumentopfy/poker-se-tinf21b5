package com.palcas.poker.game.poker_bot;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.evaluation.BotPocketEvaluator;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEmStatisticalBotActionService implements BotActionService {
    private BotPocketEvaluator pocketEvaluator;
    private int mentalCapacity;

    public TexasHoldEmStatisticalBotActionService(int mentalCapacity) {
        pocketEvaluator = new BotPocketEvaluator();
        this.mentalCapacity = mentalCapacity;
    }

    @Override
    public BotAction decidePreFlopAction(Player bot, List<Player> players, GameState gameState) {
        ArrayList<Card> botPocket = new ArrayList<>(bot.getPocket().getCards());
        int timesWonAgainstNOtherPockets = pocketEvaluator.evaluatePreFlopPocket(botPocket, mentalCapacity);
        double winRateAgainstRandomOtherPockets = timesWonAgainstNOtherPockets / (double) mentalCapacity;
        double botAggressionLevelDouble = bot.getAggressionLevel() / 100.0;
        int highestBetFromOtherPlayer = gameState.getPlayerToHighestBet().getValue();

        if (bot.equals(gameState.getSmallBlindPlayer()) && bot.getBet() < (gameState.getSmallBlind())) {
            // if bot is small blind and hasn't paid the small blind yet, then do it now
            return raiseBy(gameState.getSmallBlind() - bot.getBet(), bot);
        } else if (bot.equals(gameState.getBigBlindPlayer()) && bot.getBet() < (gameState.getBigBlind())) {
            // if bot is big blind and hasn't paid the big blind yet, then do it now
            return raiseBy(gameState.getBigBlind() - bot.getBet(), bot);
        } else if (winRateAgainstRandomOtherPockets < 0.3) {
            // don't invest any further money because the pocket is bad
            if (bot.getBet() < highestBetFromOtherPlayer) {
                return new BotAction(BotAction.ActionType.FOLD);
            } else if (bot.getBet() >= highestBetFromOtherPlayer) {
                return new BotAction(BotAction.ActionType.CHECK);
            }
        } else if (winRateAgainstRandomOtherPockets >= 0.3 && winRateAgainstRandomOtherPockets < 0.7) {
            // play passively because cards are okay; don't raise; but call if necessary
            // amount is justifiable
            // if winRate is very average (0.5), then call up to 2 times bigBlind
            int maxJustifiableAmountToCall = (int) (winRateAgainstRandomOtherPockets * 4 * gameState.getBigBlind());
            if (bot.getBet() >= highestBetFromOtherPlayer) {
                // check because no higher investment necessary
                return new BotAction(BotAction.ActionType.CHECK);
            } else if (bot.getBet() < highestBetFromOtherPlayer) {
                if (highestBetFromOtherPlayer <= maxJustifiableAmountToCall) {
                    // call if necessary bet is still justifiable
                    return call(gameState, bot);
                } else {
                    // fold because necessary bet is too high
                    return new BotAction(BotAction.ActionType.FOLD);
                }
            }
        } else if (winRateAgainstRandomOtherPockets >= 0.7) {
            // play aggressively because cards are good. Raise
            int highestJustifiableBet = (int) (winRateAgainstRandomOtherPockets * 5 * gameState.getBigBlind());
            return raiseBy(highestJustifiableBet - bot.getBet(), bot);
        }
        throw new IllegalStateException("The bot was unable to make a decision pre flop");
    }

    @Override
    public BotAction decidePostFlopAction(Player bot, List<Card> communityCards, GameState gameState) {
        ArrayList<Card> botPocket = new ArrayList<>(bot.getPocket().getCards());
        int timesWonAgainstNOtherPockets = pocketEvaluator.evaluatePostFlopPocket(
                new ArrayList<>(communityCards),
                botPocket,
                mentalCapacity);
        double winRateAgainstRandomOtherPockets = timesWonAgainstNOtherPockets / (double) mentalCapacity;
        double botAggressionLevelDouble = bot.getAggressionLevel() / 100.0;
        int overallChipsOfBot = bot.getChips() + bot.getBet();
        int highestJustifiableBet = (int) (botAggressionLevelDouble * winRateAgainstRandomOtherPockets
                * overallChipsOfBot);

        // if close to all in, then go all in
        if (highestJustifiableBet / (double) overallChipsOfBot >= 0.95) {
            return new BotAction(BotAction.ActionType.ALL_IN);
        }
        int highestBetFromOtherPlayer = gameState.getPlayerToHighestBet().getValue();

        if (highestJustifiableBet < highestBetFromOtherPlayer) {
            // if all in would be justifiable:
            if (overallChipsOfBot <= highestJustifiableBet) {
                // if already all in:
                if (bot.getChips() > 0) {
                    return new BotAction(BotAction.ActionType.ALL_IN);
                } else {
                    throw new IllegalStateException("bot is already all in, he doesn't have to decide this round");
                }
            }
            // if no ALL_IN justifiable, then fold, because highestJustifiableBet is smaller
            // than what is needed
            return new BotAction(BotAction.ActionType.FOLD);
        } else if (highestJustifiableBet == highestBetFromOtherPlayer) {
            // if a raise is neither necessary nor wanted, then just check
            return new BotAction(BotAction.ActionType.CHECK);
        } else {
            // if highest justifiable bet is higher than what is bet at the moment --> bet
            // higher
            return raiseBy(highestJustifiableBet - bot.getBet(), bot);
        }
    }

    private BotAction raiseBy(int raiseAmount, Player bot) {
        if (bot.getChips() < raiseAmount) {
            return new BotAction(BotAction.ActionType.ALL_IN);
        } else {
            BotAction raiseAction = new BotAction(BotAction.ActionType.RAISE);
            raiseAction.setRaiseAmount(raiseAmount);
            return raiseAction;
        }
    }

    private BotAction call(GameState gameState, Player bot) {
        int highestBet = gameState.getPlayerToHighestBet().getValue();
        if (bot.getChips() + bot.getBet() <= highestBet) {
            return new BotAction(BotAction.ActionType.ALL_IN);
        } else {
            return new BotAction(BotAction.ActionType.CALL);
        }
    }
}
