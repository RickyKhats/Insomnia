package com.uweeldteam.game.mob;

import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.*;


import static com.uweeldteam.game.player.inventory.item.Item.*;

public enum Mob {

    Robber(40, new Item[]{ironKnife, smallAllowance}, new Range(5, 17), new Range(15, 32), "Разбойник", "Разбойника", "Разбойнику");

    public String[] names;
    public float health;
    public Item[] drop;
    public Range damage;
    public Range defence;
    public float standardHealth;

    //
    Mob(float standardHealth, Item[] drop, String... names) {
        this.standardHealth = standardHealth;
        this.drop = drop;
        this.names = names;
    }

    //enemy
    Mob(float standardHealth, Item[] drop, Range damage, Range defence, String... names) {
        this.names = names;
        this.damage = damage;
        this.drop = drop;
        this.defence = defence;
        this.standardHealth = standardHealth;
    }
}
