package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.evaluation.pokerHands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TexasHoldEmHandEvaluationService implements HandEvaluationService {
    private CardsStatisticsService cardsStatisticsService;
    private RoyalFlush royalFlush;
    private StraightFlush straightFlush;
    private FourOfAKind fourOfAKind;
    private FullHouse fullHouse;
    private Flush flush;
    private Straight straight;
    private ThreeOfAKind threeOfAKind;
    private TwoPairs twoPairs;
    private Pair pair;
    private HighCard highCard;

    public TexasHoldEmHandEvaluationService() {
        cardsStatisticsService = new CardsStatisticsService();
        this.royalFlush = new RoyalFlush(cardsStatisticsService);
        this.straightFlush = new StraightFlush(cardsStatisticsService);
        this.fourOfAKind = new FourOfAKind(cardsStatisticsService);
        this.fullHouse = new FullHouse(cardsStatisticsService);
        this.flush = new Flush(cardsStatisticsService);
        this.straight = new Straight(cardsStatisticsService);
        this.threeOfAKind = new ThreeOfAKind(cardsStatisticsService);
        this.twoPairs = new TwoPairs(cardsStatisticsService);
        this.pair = new Pair(cardsStatisticsService);
        this.highCard = new HighCard();
    }

    /*
    @param all7cards an Array of 7 Cards, which are the 5 board cards and the 2 pocket cards
    @return HandRanking (e.g. "Full House") which is the highest possible combination with those 7 cards
     */
    @Override
    public HandRanking evaluate(Card[] all7cards) {
        if (royalFlush.containsRoyalFlush(all7cards)) {
            return HandRanking.ROYAL_FLUSH;
        } else if (straightFlush.containsStraightFlush(all7cards)) {
            return HandRanking.STRAIGHT_FLUSH;
        } else if (fourOfAKind.containsFourOfAKind(all7cards)) {
            return HandRanking.FOUR_OF_A_KIND;
        } else if (fullHouse.containsFullHouse(all7cards)) {
            return HandRanking.FULL_HOUSE;
        } else if (flush.containsFlush(all7cards)) {
            return HandRanking.FLUSH;
        } else if (straight.containsStraight(all7cards)) {
            return HandRanking.STRAIGHT;
        } else if (threeOfAKind.containsThreeOfAKind(all7cards)) {
            return HandRanking.THREE_OF_A_KIND;
        } else if (twoPairs.containsTwoPairs(all7cards)) {
            return HandRanking.TWO_PAIRS;
        } else if (pair.containsPair(all7cards)) {
            return HandRanking.PAIR;
        } else {
            return HandRanking.HIGH_CARD;
        }
    }

    /*
    @param all7cards an Array of 7 Cards, which are the 5 board cards and the 2 pocket cards
    @return Card[] of size 5 with the 5 cards, that form the best possible combination
     */
    @Override
    public Card[] select(Card[] all7cards) {
        HandRanking handRanking = evaluate(all7cards);
        return switch (handRanking) {
            case ROYAL_FLUSH -> royalFlush.selectHandForRoyalFlush(all7cards);
            case STRAIGHT_FLUSH -> straightFlush.selectHandForStraightFlush(all7cards);
            case FOUR_OF_A_KIND -> fourOfAKind.selectHandForFourOfAKind(all7cards);
            case FULL_HOUSE -> fullHouse.selectHandForFullHouse(all7cards);
            case FLUSH -> flush.selectHandForFlush(all7cards);
            case STRAIGHT -> straight.selectHandForStraight(all7cards);
            case THREE_OF_A_KIND -> threeOfAKind.selectHandForThreeOfAKind(all7cards);
            case TWO_PAIRS -> twoPairs.selectHandForTwoPairs(all7cards);
            case PAIR -> pair.selectHandForPair(all7cards);
            case HIGH_CARD -> highCard.selectHandForHighCard(all7cards);
            default -> throw new IllegalStateException("Unexpected value: " + handRanking);
        };
    }

    /*
    @param all7cardsA an Array of 7 Cards of Player A, which are the 5 board cards and the 2 pocket cards of Player A
    @param all7cardsB an Array of 7 Cards of Player B, which are the 5 board cards and the 2 pocket cards of Player B
    @return 1 if Player A has the better combination, -1 if Player B has the better combination, 0 if both combinations are equally good
     */
    @Override
    public int compare(Card[] all7cardsA, Card[] all7cardsB) {
        Card[] handA = select(all7cardsA);
        Card[] handB = select(all7cardsB);
        HandRanking handARanking = evaluate(all7cardsA);
        HandRanking handBRanking = evaluate(all7cardsB);
        if (handARanking.getValue() > handBRanking.getValue()) {
            return 1;
        } else if (handARanking.getValue() < handBRanking.getValue()) {
            return -1;
        } else {
            return switch (handARanking) {
                case ROYAL_FLUSH -> royalFlush.compareRoyalFlushHands(handA, handB);
                case STRAIGHT_FLUSH -> straightFlush.compareStraightFlushHands(handA, handB);
                case FOUR_OF_A_KIND -> fourOfAKind.compareFourOfAKindHands(handA, handB);
                case FULL_HOUSE -> fullHouse.compareFullHouseHands(handA, handB);
                case FLUSH -> flush.compareFlushHands(handA, handB);
                case STRAIGHT -> straight.compareStraightHands(handA, handB);
                case THREE_OF_A_KIND -> threeOfAKind.compareThreeOfAKindHands(handA, handB);
                case TWO_PAIRS -> twoPairs.compareTwoPairsHands(handA, handB);
                case PAIR -> pair.comparePairHands(handA, handB);
                case HIGH_CARD -> highCard.compareHighCardHands(handA, handB);
                default -> throw new IllegalStateException("Unexpected value: " + handARanking);
            };
        }
    }

    /*
    @param player7cards a Hashmap from Player to the 7 cards (board + pocket), from which he can choose 5 for a combiantion
    @return Player[] with those Players that have the highest combination out of all Players in the provided Hashmap. Typically, this will only be one Player, but it is possible for multiple people to have a comination exactly equally good
     */
    @Override
    public Player[] determineWinner(HashMap<Player, Card[]> player7cards) {
        Player[] winner;
        HashMap<Player, HandRanking> playerHandRanking = new HashMap<>();
        for (Player player : player7cards.keySet()) {
            HandRanking handRanking = evaluate(player7cards.get(player));
            player7cards.put(player, select(player7cards.get(player)));
            playerHandRanking.put(player, handRanking);
        }

        List<Player> playersSortedByHandRanking = new ArrayList<>(player7cards.keySet());
        playersSortedByHandRanking.sort((p1, p2) -> {
            Integer valuep1 = playerHandRanking.get(p1).getValue();
            Integer valuep2 = playerHandRanking.get(p2).getValue();
            return valuep1.compareTo(valuep2);
        });
        HandRanking highestHandRanking = playerHandRanking.get(playersSortedByHandRanking.get(playersSortedByHandRanking.size() - 1));
        List<Player> playersWithHighestHandRanking = new ArrayList<>();
        for (Player player : playersSortedByHandRanking) {
            if (playerHandRanking.get(player) == highestHandRanking) {
                playersWithHighestHandRanking.add(player);
            }
        }

        if (playersWithHighestHandRanking.size() == 1) {
            winner = new Player[1];
            winner[0] = playersWithHighestHandRanking.get(0);
            return winner;
        } else {
            Card[] highestHand = player7cards.get(playersWithHighestHandRanking.get(0));
            for (Card[] hand : player7cards.values()) {
                if (compare(highestHand, hand) < 0) {
                    highestHand = hand;
                }
            }
            List<Player> winnerlist = new ArrayList<>();
            for (Player player : playersWithHighestHandRanking) {
                if (compare(highestHand, player7cards.get(player)) <= 0) {
                    winnerlist.add(player);
                }
            }
            return winnerlist.toArray(new Player[winnerlist.size()]);
        }
    }
}
