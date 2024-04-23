package com.palcas.poker.game.poker_bot;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.evaluation.BotPocketEvaluator;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEmStatisticalBotActionService implements BotActionService{
    private BotPocketEvaluator pocketEvaluator;
    private int mentalCapacity;

    public TexasHoldEmStatisticalBotActionService(int mentalCapacity) {
        pocketEvaluator = new BotPocketEvaluator();
        this.mentalCapacity = mentalCapacity;
    }

    @Override
    public BotAction decidePreFlopAction(Player bot, List<Player> players, int bigBlindAmount) {
        return null;
    }

    @Override
    public BotAction decidePostFlopAction(Player bot, List<Card> communityCards, GameState gameState) {
        ArrayList<Card> botPocket = new ArrayList<>(bot.getPocket().getCards());
        int timesWonAgainstNOtherPockets = pocketEvaluator.evaluatePocketAgainstNRandomPockets(
                new ArrayList<>(communityCards),
                botPocket,
                mentalCapacity);

        double winRateAgainstRandomOtherPockets = timesWonAgainstNOtherPockets / (double) mentalCapacity;
        double botAggressionLevelDouble = bot.getAggressionLevel() / 100.0;
        int overallChipsOfBot = bot.getChips() + bot.getBet();
        int highestJustifiableBet = (int) (botAggressionLevelDouble * winRateAgainstRandomOtherPockets * overallChipsOfBot);

        // if close to all in, then go all in
        if (highestJustifiableBet / (double) overallChipsOfBot >= 0.95) {
            highestJustifiableBet = overallChipsOfBot;
        }
        int highestBetFromOtherPlayer = gameState.getPlayerToHighestBet().getValue();

        if (highestJustifiableBet < highestBetFromOtherPlayer) {
            // if all in would be justifiable:
            if (highestJustifiableBet == overallChipsOfBot) {
                // if already all in:
                if(bot.getChips() == 0) {
                    throw new IllegalStateException("bot is already all in, he doesn't have to decide this round");
                } else {
                    BotAction allInAction = new BotAction(BotAction.ActionType.RAISE);
                    allInAction.setRaiseAmount(overallChipsOfBot);
                    return allInAction;
                }
            }
            // if no AllIn justifiable, then fold, because highestJustifiableBet is smaller than what is needed
            return new BotAction(BotAction.ActionType.FOLD);
        } else if (highestJustifiableBet == highestBetFromOtherPlayer) {
            //if a raise is neither necessary nor wanted, then just check
            return new BotAction(BotAction.ActionType.CHECK);
        } else {
            // if highest justifiable bet is higher than what is bet at the moment --> bet higher
            BotAction raiseAction = new BotAction(BotAction.ActionType.RAISE);
            raiseAction.setRaiseAmount(highestJustifiableBet);
            return raiseAction;
        }
    }

    @Override
    public boolean foldRandomlyWithProbabilityOf(double foldProbability) {
        return Math.random() < foldProbability;
    }

    private ArrayList<BotAction> determinePossibleActions(Player bot) {
        ArrayList<BotAction> possibleActions = new ArrayList<>();

        return null;
    }
}
