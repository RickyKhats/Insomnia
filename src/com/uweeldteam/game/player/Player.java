//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.player;

import com.uweeldteam.Engine;
import com.uweeldteam.ExceptionOccurred;
import com.uweeldteam.game.player.inventory.Inventory;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.item.Item;
import com.uweeldteam.game.player.inventory.item.Item.ItemType;
import uweellibs.MonoBehaviour;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends MonoBehaviour {
    public Player.Stats stats;
    ArrayList<Slot> hands = new ArrayList<>(Arrays.asList(new Slot(), new Slot()));
    Inventory inventory;
    private Item backpack;
    private Item pants;
    private Item torso;
    private Item firstWeapon;
    private Item pouch;
    private Item secondWeapon;

    public Player() {
        this.backpack = Item.fabricBackpack;
        this.pants = Item.nullItem;
        this.torso = Item.nullItem;
        this.firstWeapon = Item.ironAxe;
        this.pouch = Item.fabricPouch;
        this.secondWeapon = Item.nullItem;
        this.stats = new Player.Stats();


    }

    public ArrayList<Slot> Hands() {
        return this.hands;
    }

    public Player.Stats Stats() {
        return this.stats;
    }

    public float AllHandsMass() {
        float result = 0.0F;

        for (int i = 0; i < this.Hands().size(); ++i) {
            result += this.Hands().get(i).getAllMass();
        }

        return result;
    }

    public float MaxHandsMass() {
        return 10.0F * (this.stats.strength / 2.0F);
    }

    public void Death() {
        Engine.Println("Поздравляем, вы мертвы!");
    }

    public void PreInit() {
        this.Inventory(new Inventory());
    }

    public void Inventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory Inventory() {
        return this.inventory;
    }

    public Item Backpack() {
        return this.backpack;
    }

    public void Backpack(Item item) {
        if (item.Type() == ItemType.backpack) {
            this.backpack = item;
        } else {
            try {
                throw new IllegalArgumentException("Item is not backpack!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Item Pants() {
        return this.pants;
    }

    public void Pants(Item item) {
        if (item.Type() != ItemType.pants) {
            new ExceptionOccurred(new IllegalArgumentException("Item is not pants!"));
        } else {
            this.pants = item;
        }

    }

    public Item Torso() {
        return this.torso;
    }

    public void Torso(Item item) {
        if (item.Type() != ItemType.torso && item.Type() != ItemType.armorBackpack) {
            new ExceptionOccurred(new IllegalArgumentException("Item is not torso!"));
        } else {
            this.torso = item;
        }

    }

    public Item Pouch() {
        return this.pouch;
    }

    public void Pouch(Item item) {
        if (item.Type() == ItemType.pouch) {
            this.pouch = item;
        } else {
            try {
                throw new IllegalArgumentException("Item is not pouch!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Item FirstWeapon() {
        return this.firstWeapon;
    }

    public void FirstWeapon(Item item) {
        if (item.Type() == ItemType.firstWeapon) {
            this.firstWeapon = item;
        } else {
            try {
                throw new IllegalArgumentException("Item is not first weapon!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Item SecondWeapon() {
        return this.secondWeapon;
    }

    public void SecondWeapon(Item item) {
        if (item.Type() == ItemType.secondWeapon) {
            this.secondWeapon = item;
        } else {
            try {
                throw new IllegalArgumentException("Item is not second weapon!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String toString() {
        return stats.name +
                " (" + String.format("%.2f", this.stats.health) + "%)"
                + "\nУровень силы: " + String.format("%.0f", this.stats.strength) +
                "\nГолод: " + String.format("%.2f", this.stats.food) +
                "%\nЖажда: " + String.format("%.2f", this.stats.water) +
                "%\nЭкипировка:\nОсновное оружие - " + this.firstWeapon.Names(1).toLowerCase() +
                "\nЗапасное оружие - " + this.secondWeapon.Names(1).toLowerCase() +
                "\nРюкзак - " + this.backpack.Names(1).toLowerCase() +
                "\nТорс - " + this.torso.Names(1).toLowerCase() +
                "\nНоги - " + this.pants.Names(1).toLowerCase() +
                "\nПодсумок - " + this.pouch.Names(1).toLowerCase();
    }

    public Item Equip(Item item) {
        Item result = Item.nullItem;
        switch (item.Type()) {
            case torso:
                if (torso != Item.nullItem)
                    result = torso;
                torso = item;
                break;
            case pants:
                if (pants != Item.nullItem)
                    result = pants;
                pants = item;
                break;
            case armorBackpack:
            case backpack:
                if (backpack != Item.nullItem)
                    result = backpack;
                backpack = item;
                break;
            case pouch:
                if (pouch != Item.nullItem)
                    result = pouch;
                pouch = item;
                break;

            case firstWeapon:
                if (firstWeapon != Item.nullItem)
                    result = firstWeapon;
                firstWeapon = item;
                break;
            case secondWeapon:
                if (secondWeapon != Item.nullItem)
                    result = secondWeapon;
                secondWeapon = item;
                break;
            default:
                new ExceptionOccurred(new IllegalArgumentException("Item cannot be equipped!"));
        }
        return result;
    }

    public static class Stats {
        public String name = "tester";
        public float health = 100.0F;
        public float food = 0.0F;
        public float water = 0.0F;
        public float strength = 1.0F;
        public float satiety = 2.0F;
        public float drunkenness = 2.0F;

        public Stats() {

        }
    }
}
