package com.uweeldteam.game.npc;

public class Quest {

    private QuestPart[] questParts;

    public Quest(QuestPart[] questParts){

        this.questParts = questParts;
    }

    public QuestPart[] QuestParts(){
        return questParts;
    }


    public class QuestPart{

        //балванка
        class Part{

        }

        public class Dialog extends Part{
            String text;
            boolean isPlayer;
        }

        public class Action extends Part{
            Task task;
        }

    }

    public class Task {
    }
}
