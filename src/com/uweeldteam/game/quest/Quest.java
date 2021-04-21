package com.uweeldteam.game.quest;

public class Quest {

    public boolean isCompleted;

    private final QuestPart[] questParts;

    public Quest(QuestPart[] questParts){
        this.questParts = questParts;
    }

    public QuestPart[] QuestParts(){
        return questParts;
    }


}
