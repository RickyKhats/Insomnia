package com.uweeldteam.game.mob;

import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.*;


import static com.uweeldteam.game.mob.Mob.MobType.*;
import static com.uweeldteam.game.player.inventory.item.Item.*;

public enum Mob {
    Robber(40, new Item[]{ironKnife, smallAllowance}, new Range(5, 17), new Range(15, 32), "Разбойник", "Разбойника", "Разбойнику");


    String[] names;

    float health;
    Item[] drop;
    Range damage;
    Range defence;
    float standardHealth;
    MobType type;

    //animal
    Mob(float standardHealth, Item[] drop, String... names) {
        setType(animal);
        StandardHealth(standardHealth);
        Drop(drop);
        Names(names);
    }

    //npc
    Mob() {
        setType(npc);
    }

    //enemy
    Mob(float standardHealth, Item[] drop, Range damage, Range defence, String... names) {
        setType(enemy);
        Names(names);
        Damage(damage);
        Drop(drop);
        Defence(defence);
        StandardHealth(standardHealth);
    }

    void Names(String[] names) {
        this.names = names;
    }
    public String Name(int id) {
        return names[id];
    }

    public void Health(float health) {
        this.health = health;
    }
    public float Health() {
        return health;
    }

    private void Drop(Item[] drop) {
        this.drop = drop;
    }
    public Item[] Drop() {
        return drop;
    }

    private void Damage(Range damage) {
        this.damage = damage;
    }
    public Range Damage() {
        return damage;
    }

    private  void Defence(Range defence) {
        this.defence = defence;
    }
    public Range Defence() {
        return defence;
    }

    private void StandardHealth(float standardHealth) {
        this.standardHealth = standardHealth;
    }
    public float StandardHealth() {
        return standardHealth;
    }

    private void setType(MobType type) {
        this.type = type;
    }
    public MobType getType() {
        return type;
    }

    public enum MobType {
        //green, yellow, red
        animal, npc, enemy
    }
}
