package com.uweeldteam.game.player.inventory.craftsystem;

import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.Console;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftSystem {
    static ArrayList<Slot> Hands() {
        return Player().Hands();
    }

    public static Player Player() {
        return Main.Engine().Game().Player();
    }

    public static ArrayList<Slot> Backpack() {
        return Player().Backpack().Slots();
    }

    public static ArrayList<Slot> Pants() {
        return Player().Pants().Slots();
    }

    public static ArrayList<Slot> Torso() {
        return Player().Torso().Slots();
    }

    public static ArrayList<Slot> Pouch() {
        return Player().Pouch().Slots();
    }

    public static void Craft(Item item) {
        if (canCraft(item)) {
            Console.Println("Вы скрафтили " + item.Names(0).toLowerCase());
            Player().Inventory().AddItem(new Slot(item, (short) 1), false);
            Player().Inventory().DeleteItems(item.Craft());
            Console.Println("end");
        } else if (item.Craft().length < 1)
            try {
                throw new Exception("this item not have craft");
            } catch (Exception e) {
                e.printStackTrace();
            }
        else
            Console.Println("Недостаточно ресурсов");
    }

    public static boolean canCraft(Item item) {
        if (!Arrays.asList(item).isEmpty())
            return canCraft(item.Craft());
        return false;

    }

    static boolean canCraft(Item... items) {
        ArrayList<Item> craft = new ArrayList<>(Arrays.asList(items));
        ArrayList<ArrayList<Slot>> containers = new ArrayList<>();
        ArrayList<Slot> slots = new ArrayList<>();
        for (Item item : craft) {
            if (!slots.contains(item))
                slots.add(new Slot(item, (short) 1));
            else
                for (Slot slot : slots)
                    if (slot.Item() == item) {
                        slot.Add(new Slot(item, (short) 1));
                        break;
                    }
        }
        try {
            containers.add((ArrayList<Slot>) Hands().clone());
        } catch (NullPointerException ignored) {
        }
        try {
            containers.add((ArrayList<Slot>) Backpack().clone());
        } catch (NullPointerException ignored) {
        }
        try {
            containers.add((ArrayList<Slot>) Pants().clone());
        } catch (NullPointerException ignored) {
        }
        try {
            containers.add((ArrayList<Slot>) Torso().clone());
        } catch (NullPointerException ignored) {
        }
        try {
            containers.add((ArrayList<Slot>) Pouch().clone());
        } catch (NullPointerException ignored) {
        }
        int containerId = 0;
        boolean touched = false;
        do {
            try {
                for (int i = 0; i < containers.get(containerId).size(); i++) {

                    for (int j = 0; j < containers.get(containerId).get(i).Value(); j++) {
                        if (containers.get(containerId).get(i).Item() == slots.get(0).Item()) {
                            containers.get(containerId).get(i).Remove(new Slot(slots.get(0).Item(), (short) 1));
                            craft.remove(0);
                            touched = true;
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
            containerId++;
        } while (containerId < containers.size());
        if (!craft.isEmpty()) {
            if (!touched)
                return false;
            Item[] items1;
            items = craft.toArray(new Item[0]);
            return canCraft(items);
        }
        return true;
    }
}
