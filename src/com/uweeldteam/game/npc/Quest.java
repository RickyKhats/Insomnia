package com.uweeldteam.game.npc;

public class Quest {

    public boolean isCompleted;

    private final QuestPart[] questParts;

    public Quest(QuestPart[] questParts){
        this.questParts = questParts;
    }

    public QuestPart[] QuestParts(){
        return questParts;
    }


    public static class QuestPart {
        Part part;
        public QuestPart(Part part){
            this.part = part;
        }
        public Part Part(){
            return part;
        }
        //балванка
        public abstract class Part {
            public native String Text();
        }

        public abstract class Dialog extends Part {
            public abstract String Text();
        }
    }

}
