package com.palcas.poker.display;

import java.util.Map;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;

public class CardDisplay {
    static final String space = " ";

    private static final Map<Suit, String> suitDisplay = Map.of(
            // todo Check charset display problem
            Suit.DIAMONDS, space.repeat(3) + "D" + space.repeat(3),
            Suit.SPADES, space.repeat(3) + "S" + space.repeat(3),
            Suit.CLUBS, space.repeat(3) + "C" + space.repeat(3),
            Suit.HEARTS, space.repeat(3) + "H" + space.repeat(3));

    private static final Map<Rank, String> rankDisplay = Map.ofEntries(
            Map.entry(Rank.TWO, "  2  "),
            Map.entry(Rank.THREE, "  3  "),
            Map.entry(Rank.FOUR, "  4  "),
            Map.entry(Rank.FIVE, "  5  "),
            Map.entry(Rank.SIX, "  6  "),
            Map.entry(Rank.SEVEN, "  7  "),
            Map.entry(Rank.EIGHT, "  8  "),
            Map.entry(Rank.NINE, "  9  "),
            Map.entry(Rank.TEN, "  10 "),
            Map.entry(Rank.JACK, " Jack"),
            Map.entry(Rank.QUEEN, "Queen"),
            Map.entry(Rank.KING, " King"),
            Map.entry(Rank.ACE, " Ace "));

    private static final Map<Suit, String> colorDisplay = Map.of(
            Suit.DIAMONDS, "\u001B[31m",
            Suit.HEARTS, "\u001B[31m",
            Suit.SPADES, "\u001B[0m",
            Suit.CLUBS, "\u001B[0m");

    public static String getFormattedRank(Card card) {
        return rankDisplay.get(card.getRank());
    }

    public static String getFormattedSuit(Card card) {
        return suitDisplay.get(card.getSuit());
    }

    public static String getColorCode(Card card) {
        return colorDisplay.get(card.getSuit());
    }
}
