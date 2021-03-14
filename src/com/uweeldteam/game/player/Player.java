package com.uweeldteam.game.player;

import com.uweeldteam.game.player.inventory.Inventory;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.item.Item;
import com.uweeldteam.game.player.inventory.item.ItemType;
import uweellibs.*;

import java.util.ArrayList;
import java.util.Arrays;

import static com.uweeldteam.game.player.inventory.item.Item.*;

public class Player extends MonoBehaviour {

    public Player() {
        stats = new Stats();
    }

    public float MaxHandsMass() {
        return 10 * (stats.strength / 2);
    }

    public float AllHandsMass() {
        float result = 0;
        for (int i = 0; i < Hands().size(); i++) {
            result += Hands().get(i).getAllMass();
        }
        return result;
    }

    public Stats Stats() {
        return stats;
    }

    public void Death() {
        Console.Println("Поздравляем, вы мертвы!");
    }

    public class Stats {
        public String name = "Ricky Khats";
        public float
                health = 100,
                food = 0,
                water = 0,
                strength = 1,
                satiety = 2,
                drunkenness = 2;
    }

    public Stats stats;


    ArrayList<Slot> hands = new ArrayList<>(Arrays.asList(new Slot(), new Slot()));

    public ArrayList<Slot> Hands() {
        return hands;
    }

    public void PreInit() {
        Inventory(new Inventory());
    }

    //inventory
    Inventory inventory;

    //getter and setter to inventory
    public void Inventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory Inventory() {
        return inventory;
    }

    //equipment
    private Item
            backpack = Item.backpack,
            pants = nullItem,
            torso = nullItem,
            firstWeapon = ironAxe,
            pouch = nullItem,
            secondWeapon = nullItem;

    //getters and setters to equipment
    public Item Backpack() {
        return backpack;
    }

    public void Backpack(Item item) {
        if (item.Type() == ItemType.backpack)
            this.backpack = item;
        else
            try {
                throw new IllegalArgumentException("Item is not backpack!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public Item Pants() {
        return pants;
    }

    public void Pants(Item item) {
        if (item.Type() == ItemType.armor || item.Type() == ItemType.armorBackpack)
            this.pants = item;
        else
            try {
                throw new IllegalArgumentException("Item is not pants!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public Item Torso() {
        return torso;
    }

    public void Torso(Item item) {
        if (item.Type() == ItemType.armor || item.Type() == ItemType.armorBackpack)
            this.torso = item;
        else
            try {
                throw new IllegalArgumentException("Item is not torso!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public Item Pouch() {
        return pouch;
    }

    public void Pouch(Item item) {
        if (item.Type() == ItemType.pouch)
            this.pouch = item;
        else
            try {
                throw new IllegalArgumentException("Item is not pouch!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public Item FirstWeapon() {
        return firstWeapon;
    }

    public void FirstWeapon(Item item) {
        if (item.Type() == ItemType.firstWeapon)
            this.firstWeapon = item;
        else
            try {
                throw new IllegalArgumentException("Item is not first weapon!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public Item SecondWeapon() {
        return secondWeapon;
    }

    public void SecondWeapon(Item item) {
        if (item.Type() == ItemType.secondWeapon)
            this.secondWeapon = item;
        else
            try {
                throw new IllegalArgumentException("Item is not second weapon!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public String toString() {
        return stats.name + " (" + String.format("%.2f", stats.health) + "%)"
                + "\nУровень силы: " + String.format("%.0f", stats.strength)
                + "\nГолод: " + String.format("%.2f", stats.food)
                + "%\nЖажда: " + String.format("%.2f", stats.water)
                + "%\nЭкипировка:"
                + "\nОсновное оружие - " + firstWeapon.Names(1).toLowerCase()
                + "\nЗапасное оружие - " + secondWeapon.Names(1).toLowerCase()
                + "\nРюкзак - " + backpack.Names(1).toLowerCase()
                + "\nТорс - " + torso.Names(1).toLowerCase()
                + "\nНоги - " + pants.Names(1).toLowerCase()
                + "\nПодсумок - " + pouch.Names(1).toLowerCase();
    }
}
