package com.palcas.poker.game.poker_bot;

public class BotAction {
    private ActionType actionType;

    // this is the relative raise amount by which the bet of the player will be increased
    private Integer raiseAmount;

    public enum ActionType {
        CHECK, CALL, FOLD, RAISE, ALL_IN
    }

    public BotAction(ActionType actionType) {
        this.actionType = actionType;
    }

    public BotAction(ActionType actionType, int raiseAmount) {
        this.actionType = actionType;
        this.raiseAmount = raiseAmount;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Integer getRaiseAmount() {
        return raiseAmount;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public void setRaiseAmount(Integer raiseAmount) {
        this.raiseAmount = raiseAmount;
    }
}
