package com.palcas.poker.game.poker_bot;

import java.util.ArrayList;
import java.util.List;

import com.palcas.poker.game.*;
import com.palcas.poker.game.evaluation.HandEvaluationService;
import com.palcas.poker.game.evaluation.HandRanking;
import com.palcas.poker.game.evaluation.TexasHoldEmHandEvaluationService;

public class StatisticalBotActionService implements BotActionService {
    private HandEvaluationService handEvaluator;

    public StatisticalBotActionService() {
        this.handEvaluator = new TexasHoldEmHandEvaluationService();
    }

    // TODO implement bluffing
    public BotAction decidePreFlopAction(Player bot, List<Player> players, GameState gameState) {
        int bigBlindAmount = gameState.getBigBlind();
        Pocket pocket = bot.getPocket();
        List<Card> pocketCards = pocket.getCards();

        // Check if the pocket cards have the same rank#
        boolean hasPair = checkForPair(pocketCards);

        // If the bot doesn't have a pair...
        if (!hasPair) {
            // ...it decides on folding or calling based on the rank of the pocket cards and
            // some randomness
            double firstRankMultiplier = pocketCards.get(0).getRank().getValue() / 10;
            double secondRankMultiplier = pocketCards.get(1).getRank().getValue() / 10;
            double finalMultiplier = (firstRankMultiplier + secondRankMultiplier) / 4;

            if (foldRandomlyWithProbabilityOf(finalMultiplier)) {
                return new BotAction(BotAction.ActionType.FOLD);
            } else {
                return new BotAction(BotAction.ActionType.CALL);
            }
        } else {
            // ...otherwise it raises based on the rank of the pair and its aggressiveness
            // level
            int pairRank = pocketCards.get(0).getRank().getValue();

            // Calculate the raise amount based on the pair rank, agression level, and
            // randomness
            int raiseAmount = bigBlindAmount * calculateRaiseMultiplier(pairRank, bot.getAggressionLevel());

            // Checks whether bot has enoug chips to raise, else reduce amount
            raiseAmount = checkIfRaiseAmountIsLegal(bot, raiseAmount);

            return new BotAction(BotAction.ActionType.RAISE, raiseAmount);
        }
    }

    // TODO implement bluffing
    // TODO implement checking for flush and straight draws
    // TODO implement checking for pairs, two pairs, three of a kind, full house,
    // four of a kind
    public BotAction decidePostFlopAction(Player bot, List<Card> communityCards, GameState gameState) {
        // Create new object holding both the community cards and the bot's pocket cards
        List<Card> communityCardsAndBotPocket = new ArrayList<>();
        communityCardsAndBotPocket.addAll(communityCards);
        communityCardsAndBotPocket.addAll(bot.getPocket().getCards());

        // Need to turn it to an array because the evaluate method expects an array
        Card[] communityCardsAndBotPocketAsArray = communityCardsAndBotPocket.toArray(Card[]::new);
        HandRanking botHandRanking = handEvaluator.evaluate(communityCardsAndBotPocketAsArray);

        if (foldRandomlyWithProbabilityOf(botHandRanking.getValue() / 10)) {
            return new BotAction(BotAction.ActionType.FOLD);
        }

        return new BotAction(BotAction.ActionType.CALL);
    }

    public boolean foldRandomlyWithProbabilityOf(double foldProbability) {
        return Math.random() < foldProbability;
    }

    private boolean checkForPair(List<Card> pocketCards) {
        if (pocketCards.get(0).getRank() == pocketCards.get(1).getRank()) {
            return true;
        } else {
            return false;
        }
    }

    private int calculateRaiseMultiplier(int pairRank, int agressionLevel) {
        double agressionMultiplier = (agressionLevel / 100) + 1;
        double pairMultiplier = (pairRank / 10) + 1;
        double unroundedRaiseMultiplier = pairMultiplier + agressionMultiplier;
        int finalMultiplier = (int) Math.ceil(unroundedRaiseMultiplier);
        return finalMultiplier;
    }

    private int checkIfRaiseAmountIsLegal(Player bot, int raiseAmount) {
        int botChips = bot.getChips();
        if (raiseAmount > botChips) {
            raiseAmount = (int) Math.ceil(raiseAmount * 0.9);
            return checkIfRaiseAmountIsLegal(bot, raiseAmount);
        } else {
            return raiseAmount;
        }
    }
}
