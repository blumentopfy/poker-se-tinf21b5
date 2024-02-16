package com.palcas.poker.game;

import com.palcas.poker.display.CardDisplay;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static com.palcas.poker.game.HandRankingChecker.countSuits;
import static com.palcas.poker.game.HandRankingChecker.countRanks;

public class HandRankingCardsSelector {
    public static Card[] selectHandForRoyalFlush(Card[] all7Cards) {
        // calculate the suit of the potential straight flush
        HashMap<CardDisplay.Suit, Integer> countedSuits = countSuits(all7Cards);
        CardDisplay.Suit suitOfPotentialStraightFlush = null;
        int numberOfCardsWithThisSuit = 0;
        for (CardDisplay.Suit suit : countedSuits.keySet()) {
            if (countedSuits.get(suit) >= 5) {
                suitOfPotentialStraightFlush = suit;
                numberOfCardsWithThisSuit = countedSuits.get(suit);
            }
        }

        //fill the array with the cards of the same suit and of the right rank
        Card[] selected5cards = new Card[5];
        int i = 0;
        for (Card card: all7Cards) {
            if(card.getSuit() == suitOfPotentialStraightFlush) {
                if (card.getRank() == CardDisplay.Rank.TEN) {
                    selected5cards[0] = card;
                } else if (card.getRank() == CardDisplay.Rank.JACK) {
                    selected5cards[1] = card;
                } else if (card.getRank() == CardDisplay.Rank.QUEEN) {
                    selected5cards[0] = card;
                } else if (card.getRank() == CardDisplay.Rank.KING) {
                    selected5cards[0] = card;
                } else if (card.getRank() == CardDisplay.Rank.ACE) {
                    selected5cards[0] = card;
                }
            }
        }
        return selected5cards;
    }

    /*
     if there exists a straight flush, this mehods selects the 5 cards that are part of it out of the 7 cards given
     @param cards Array of 7 cards, from which the 5 composing have to be selected
     @returns Array of the 5 cards composing the Straight Flush, with the highest card at the first place
     */
    public static Card[] selectHandForStraightFlush(Card[] all7cards) {
        // calculate the suit of the potential straight flush
        HashMap<CardDisplay.Suit, Integer> countedSuits = countSuits(all7cards);
        CardDisplay.Suit suitOfPotentialStraightFlush = null;
        int numberOfCardsWithThisSuit = 0;
        for (CardDisplay.Suit suit : countedSuits.keySet()) {
            if (countedSuits.get(suit) >= 5) {
                suitOfPotentialStraightFlush = suit;
                numberOfCardsWithThisSuit = countedSuits.get(suit);
            }
        }

        // create new Array with only the cards of the same suit
        Card[] setOfCardsWithFlushSuite = new Card[numberOfCardsWithThisSuit];
        int i = 0;
        for (Card card: all7cards) {
            if(card.getSuit() == suitOfPotentialStraightFlush) {
                setOfCardsWithFlushSuite[i] = card;
                i++;
            }
        }

        // check for straight in only this new Array of Cards
        HashMap<CardDisplay.Rank, Integer> countedRanks = countRanks(setOfCardsWithFlushSuite);
        CardDisplay.Rank[] sortedRanks = CardDisplay.Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(CardDisplay.Rank::getValue).reversed());
        int streak = 0;
        Card[] selected5cards = new Card[5];

        for (CardDisplay.Rank rank : sortedRanks) {
            System.out.println(rank.getName());
            if (countedRanks.get(rank) >= 1) {
                for (Card card : setOfCardsWithFlushSuite) {
                    if(card.getRank() == rank) {
                        selected5cards[streak] = card;
                        break;
                    }
                }
                streak++;
            } else {
                streak = 0;
            }
        }
        // check for Ace at both ends of the streak, since it can be the very lowest or very highest card
        if (countedRanks.get(CardDisplay.Rank.ACE) >= 1) {
            for (Card card : setOfCardsWithFlushSuite) {
                if(card.getRank() == CardDisplay.Rank.ACE) {
                    selected5cards[streak] = card;
                    break;
                }
            }
        }
        return selected5cards;
    }

    /*
    selects the 4 cards of the same rank together with a high cards in the given set of 7 cards
    @param all7cards Array of 7 cards, from which 5 shall be chosen as the Hand
    @returns Card[5] with the first 4 being the 4 of a Kind and the 5th being the highest possible card left
     */
    public static Card[] selectHandForFourOfAKind(Card[] all7cards) {
        // calculate the rank, of which there are 4 of
        HashMap<CardDisplay.Rank, Integer> countedRanks = countRanks(all7cards);
        CardDisplay.Rank rankOfFourOfAKind = null;
        for (CardDisplay.Rank rank : countedRanks.keySet()) {
            if (countedRanks.get(rank) == 4) {
                rankOfFourOfAKind = rank;
            }
        }

        // sort cards ascending so, we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> card.getRank().getValue()));
        int i = 0;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(card.getRank() == rankOfFourOfAKind) {
                selected5cards[i] = card;
                i++;
            } else {
                selected5cards[4] = card;
            }
        }
        return selected5cards;
    }

    /*
    selects 5 cards building a full house as Hand out of the 7 given cards
    @param all7cards Array of 7 cards, from which 5 shall be chosen as the Hand
    @returns Card[5] with the first 3 being the (highest possible) set of 3, and the other 2 the (highest possible) pair
     */
    public static Card[] selectHandForFullHouse(Card[] all7cards) {
        HashMap<CardDisplay.Rank, Integer> countedRanks = countRanks(all7cards);
        CardDisplay.Rank[] sortedRanks = CardDisplay.Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(CardDisplay.Rank::getValue).reversed());

        //determine, which rank do the pairs or triplets have
        CardDisplay.Rank pairOfThreeRank = null;
        CardDisplay.Rank pairOfTwoRank = null;
        for (CardDisplay.Rank rank : sortedRanks) {
            if (countedRanks.get(rank) == 3) {
                if(pairOfThreeRank == null) {
                    pairOfThreeRank = rank;
                } else if (pairOfTwoRank == null) {
                    pairOfTwoRank = rank;
                }
            } else if (countedRanks.get(rank) == 2) {
                if (pairOfTwoRank == null) {
                    pairOfTwoRank = rank;
                }
            }
        }

        //fill array with the according card, starting with the pair of three
        Card[] selected5cards = new Card[5];
        int pairOfThreeCounter = 0;
        int pairOfTwoCounter = 3;
        for (Card card : all7cards) {
            if (card.getRank() == pairOfThreeRank && pairOfThreeCounter <= 2) {
                selected5cards[pairOfThreeCounter++] = card;
            } else if (card.getRank() == pairOfTwoRank && pairOfTwoCounter <= 4) {
                selected5cards[pairOfTwoCounter++] = card;
            }
        }
        return selected5cards;
    }


    public static Card[] selectHandForFlush(Card[] all7cards) {
        // calculate the suit of the potential straight flush
        HashMap<CardDisplay.Suit, Integer> countedSuits = countSuits(all7cards);
        CardDisplay.Suit suitOfPotentialStraightFlush = null;
        for (CardDisplay.Suit suit : countedSuits.keySet()) {
            if (countedSuits.get(suit) >= 5) {
                suitOfPotentialStraightFlush = suit;
            }
        }
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        Card[] selected5cards = new Card[5];
        int arrayCounter = 0;
        for (Card card : all7cards) {
            if (card.getSuit() == suitOfPotentialStraightFlush && arrayCounter <= 4) {
                selected5cards[arrayCounter++] = card;
            }
        }
        return selected5cards;
    }


    public static Card[] selectHandForStraight(Card[] all7cards) {
        HashMap<CardDisplay.Rank, Integer> countedRanks = countRanks(all7cards);
        CardDisplay.Rank[] sortedRanks = CardDisplay.Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(CardDisplay.Rank::getValue).reversed());
        int streak = 0;
        Card[] selected5cards = new Card[5];

        for (CardDisplay.Rank rank : sortedRanks) {
            if (streak == 5) {
                break;
            }
            if (countedRanks.get(rank) >= 1) {
                for (Card card : all7cards) {
                    if(card.getRank() == rank) {
                        selected5cards[streak++] = card;
                        break;
                    }
                }
            } else {
                streak = 0;
            }
        }
        // check for Ace at both ends of the streak, since it can be the very lowest or very highest card
        if (countedRanks.get(CardDisplay.Rank.ACE) >= 1) {
            for (Card card : all7cards) {
                if(card.getRank() == CardDisplay.Rank.ACE) {
                    selected5cards[streak] = card;
                    break;
                }
            }
        }
        return selected5cards;
    }

    public static void main(String[] args) {
        //just for testing purposes, main method can be deleted
        Card card1 = new Card(CardDisplay.Suit.SPADES, CardDisplay.Rank.THREE);
        Card card2 = new Card(CardDisplay.Suit.CLUBS, CardDisplay.Rank.FIVE);
        Card card3 = new Card(CardDisplay.Suit.HEARTS, CardDisplay.Rank.SIX);
        Card card4 = new Card(CardDisplay.Suit.SPADES, CardDisplay.Rank.TWO);
        Card card5 = new Card(CardDisplay.Suit.DIAMONDS, CardDisplay.Rank.SEVEN);
        Card card6 = new Card(CardDisplay.Suit.HEARTS, CardDisplay.Rank.JACK);
        Card card7 = new Card(CardDisplay.Suit.HEARTS, CardDisplay.Rank.FOUR);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        Card[] selected5cards = selectHandForStraight(cards);
        for (Card card : selected5cards) {
            System.out.println(card.toString());
        }
    }



}
