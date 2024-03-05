package com.palcas.poker.game;

import com.palcas.poker.Rank;
import com.palcas.poker.Suit;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class HandRankingCardsSelector {

    CardsStatisticsService cardsStatistics;

    public HandRankingCardsSelector(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }
    public Card[] selectHandForRoyalFlush(Card[] all7Cards) {
        // calculate the suit of the potential straight flush
        HashMap<Suit, Integer> countedSuits = cardsStatistics.countSuits(all7Cards);
        Suit suitOfPotentialStraightFlush = null;
        int numberOfCardsWithThisSuit = 0;
        for (Suit suit : countedSuits.keySet()) {
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
                if (card.getRank() == Rank.TEN) {
                    selected5cards[0] = card;
                } else if (card.getRank() == Rank.JACK) {
                    selected5cards[1] = card;
                } else if (card.getRank() == Rank.QUEEN) {
                    selected5cards[0] = card;
                } else if (card.getRank() == Rank.KING) {
                    selected5cards[0] = card;
                } else if (card.getRank() == Rank.ACE) {
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
    public Card[] selectHandForStraightFlush(Card[] all7cards) {
        // calculate the suit of the potential straight flush
        HashMap<Suit, Integer> countedSuits = cardsStatistics.countSuits(all7cards);
        Suit suitOfPotentialStraightFlush = null;
        int numberOfCardsWithThisSuit = 0;
        for (Suit suit : countedSuits.keySet()) {
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
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(setOfCardsWithFlushSuite);
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(Rank::getValue).reversed());
        int streak = 0;
        Card[] selected5cards = new Card[5];

        for (Rank rank : sortedRanks) {
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
        if (countedRanks.get(Rank.ACE) >= 1) {
            for (Card card : setOfCardsWithFlushSuite) {
                if(card.getRank() == Rank.ACE) {
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
    public Card[] selectHandForFourOfAKind(Card[] all7cards) {
        // calculate the rank, of which there are 4 of
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank rankOfFourOfAKind = null;
        for (Rank rank : countedRanks.keySet()) {
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
    public Card[] selectHandForFullHouse(Card[] all7cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(Rank::getValue).reversed());

        //determine, which rank do the pairs or triplets have
        Rank pairOfThreeRank = null;
        Rank pairOfTwoRank = null;
        for (Rank rank : sortedRanks) {
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


    public Card[] selectHandForFlush(Card[] all7cards) {
        // calculate the suit of the potential straight flush
        HashMap<Suit, Integer> countedSuits = cardsStatistics.countSuits(all7cards);
        Suit suitOfPotentialStraightFlush = null;
        for (Suit suit : countedSuits.keySet()) {
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


    public Card[] selectHandForStraight(Card[] all7cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(Rank::getValue).reversed());
        int streak = 0;
        Card[] selected5cards = new Card[5];

        for (Rank rank : sortedRanks) {
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
        if (countedRanks.get(Rank.ACE) >= 1) {
            for (Card card : all7cards) {
                if(card.getRank() == Rank.ACE) {
                    selected5cards[streak] = card;
                    break;
                }
            }
        }
        return selected5cards;
    }


    /*
    selects the 3 cards of the same rank together with 2 high cards in the given set of 7 cards
    @param all7cards Array of 7 cards, from which 5 shall be chosen as the Hand
    @returns Card[5] with the first 3 being the 3 of a Kind and the 4th and 5th being the highest possible cards left
     */
    public Card[] selectHandForThreeOfAKind(Card[] all7cards) {
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(Rank::getValue).reversed());

        // calculate the rank, of which there are 3 of
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank rankOfThreeOfAKind = null;
        for (Rank rank : sortedRanks) {
            if (countedRanks.get(rank) == 3) {
                rankOfThreeOfAKind = rank;
                break;
            }
        }

        // sort cards ascending, so we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        int i = 0;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(card.getRank() == rankOfThreeOfAKind) {
                selected5cards[i] = card;
                i++;
            } else if (card.getRank() != rankOfThreeOfAKind && selected5cards[3] == null){
                selected5cards[3] = card;
            } else {
                selected5cards[4] = card;
            }
        }
        return selected5cards;
    }


    public Card[] selectHandForTwoPairs(Card[] all7cards) {
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(Rank::getValue).reversed());
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);

        // sort cards ascending, so we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        int i = 0;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(countedRanks.get(card.getRank()) == 2 && i <= 3) {
                selected5cards[i] = card;
                i++;
            } else {
                selected5cards[4] = card;
            }
            if (selected5cards[4]!=null && selected5cards[3]!=null) {
                break;
            }
        }
        return selected5cards;
    }


    public Card[] selectHandForPair(Card[] all7cards) {
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(sortedRanks, Comparator.comparingInt(Rank::getValue).reversed());
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);

        // sort cards ascending, so we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        int pairIndex = 0;
        int highCardIndex = 2;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(countedRanks.get(card.getRank()) == 2) {
                selected5cards[pairIndex] = card;
                pairIndex++;
            } else if (countedRanks.get(card.getRank()) != 2 && highCardIndex <= 4) {
                selected5cards[highCardIndex] = card;
                highCardIndex++;
            }
        }
        return selected5cards;
    }
}
