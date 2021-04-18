package com.uweeldteam.game.npc;

import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;

public class Quest {

    public boolean isCompleted;

    private QuestPart[] questParts;

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
        //балванка
        class Part {

        }

        public abstract class Dialog extends Part {
            String text;
            String subjectName = Main.Engine().Game().Player().Stats().name;
        }

        public class Action extends Part {
            Task task;
        }
    }

    public abstract class Task {
        abstract void Complete();
        abstract void Start();
    }

}
