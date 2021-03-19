//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.mob;

import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.Range;

public enum Mob {
    Robber(40.0F, new Item[]{Item.ironKnife, Item.smallAllowance}, new Range(5, 17), new Range(15, 32), new String[]{"Разбойник", "Разбойника", "Разбойнику"});

    String[] names;
    float health;
    Item[] drop;
    Range damage;
    Range defence;
    float standardHealth;
    Mob.MobType type;

    private Mob(float standardHealth, Item[] drop, String... names) {
        this.setType(Mob.MobType.animal);
        this.StandardHealth(standardHealth);
        this.Drop(drop);
        this.Names(names);
    }

    private Mob() {
        this.setType(Mob.MobType.npc);
    }

    private Mob(float standardHealth, Item[] drop, Range damage, Range defence, String... names) {
        this.setType(Mob.MobType.enemy);
        this.Names(names);
        this.Damage(damage);
        this.Drop(drop);
        this.Defence(defence);
        this.StandardHealth(standardHealth);
    }

    void Names(String[] names) {
        this.names = names;
    }

    public String Name(int id) {
        return this.names[id];
    }

    public void Health(float health) {
        this.health = health;
    }

    public float Health() {
        return this.health;
    }

    private void Drop(Item[] drop) {
        this.drop = drop;
    }

    public Item[] Drop() {
        return this.drop;
    }

    private void Damage(Range damage) {
        this.damage = damage;
    }

    public Range Damage() {
        return this.damage;
    }

    private void Defence(Range defence) {
        this.defence = defence;
    }

    public Range Defence() {
        return this.defence;
    }

    private void StandardHealth(float standardHealth) {
        this.standardHealth = standardHealth;
    }

    public float StandardHealth() {
        return this.standardHealth;
    }

    private void setType(Mob.MobType type) {
        this.type = type;
    }

    public Mob.MobType getType() {
        return this.type;
    }

    public static enum MobType {
        animal,
        npc,
        enemy;

        private MobType() {
        }
    }
}
