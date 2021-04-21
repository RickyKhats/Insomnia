package com.uweeldteam.game.npc;

import com.uweeldteam.game.quest.Quest;

import java.util.ArrayList;

public abstract class Npc {
    public abstract ArrayList<Quest> Quests();
    public abstract Quest FirstQuest();
}
