package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.HandRanking;
import com.palcas.poker.game.evaluation.HandEvaluationService;
import com.palcas.poker.game.evaluation.pokerHands.*;

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

    @Override
    public HandRanking check(Card[] all7cards) {
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

    @Override
    public Card[] select(Card[] all7cards) {
        HandRanking handRanking = check(all7cards);
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

    @Override
    public int compare(Card[] handA, Card[] handB) {
        HandRanking handARanking = check(handA);
        HandRanking handBRanking = check(handB);
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
}
