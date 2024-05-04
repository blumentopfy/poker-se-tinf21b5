package com.palcas.poker.game.variants.TexasHoldEm;

import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.checker.pokerHands.mocks.BotActionServiceMock;
import com.palcas.poker.game.poker_bot.BotAction;
import com.palcas.poker.game.poker_bot.BotActionService;
import com.palcas.poker.game.poker_bot.IllegalBotActionException;
import com.palcas.poker.game.variants.TexasHoldEm.TexasHoldEmRound;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEmRoundTest {
    static TexasHoldEmRound texasHoldEmRound;
    static GameState gameState;
    static Player bot;
    static ArrayList<Player> players;

    @BeforeAll
    public static void setUp() {
        Player mainPlayer = new Player("mainPlayer", 1337);
        bot = new Player("bot1", 420);
        players = new ArrayList<>();
        players.add(mainPlayer);
        players.add(bot);

        gameState = new GameState(mainPlayer, players);

    }

    // #Bugfix
    @Test
    public void botActionServiceReturnedRaiseButDidntProvideRaiseValue() {
        BotActionService corruptBotActionService = new BotActionServiceMock();
        texasHoldEmRound = new TexasHoldEmRound(gameState, corruptBotActionService);

        BotAction botAction = corruptBotActionService.decidePreFlopAction(bot, players, gameState);

        assertThrows(IllegalBotActionException.class, () -> texasHoldEmRound.botRaise(bot, botAction));
    }
}
