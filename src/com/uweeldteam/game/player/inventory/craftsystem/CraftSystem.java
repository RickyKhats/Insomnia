//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.player.inventory.craftsystem;

import com.uweeldteam.Engine;
import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.item.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CraftSystem {
    public CraftSystem() {
    }

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
        for(int i = 0; i < item.Craft().size(); ++i) {
            if (canCraft((item.Craft().get(i)).Items())) {
                Player().Inventory().DeleteItems(item.Craft(i));
                Player().Inventory().AddItem(new Slot(item, 1), false);
                Engine.Println("Вы скрафтили " + item.Names(0).toLowerCase());
                return;
            }
        }

        if (item.Craft().size() < 1) {
            try {
                throw new Exception("this item not have craft");
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        } else {
            Engine.Println("Недостаточно ресурсов");
        }

    }

    public static boolean canCraft(Item item) {
        for(int i = 0; i < item.Craft().size(); ++i) {
            if (canCraft(item.Craft(i))) {
                return true;
            }
        }

        return false;
    }

    static boolean canCraft(Item... items) {
        ArrayList<Item> craft = new ArrayList<>(Arrays.asList(items));
        ArrayList<ArrayList<Slot>> containers = new ArrayList<>();
        ArrayList<Slot> slots = new ArrayList<>();
        Iterator<Item> var4 = craft.iterator();

        while(true) {
            while(var4.hasNext()) {
                Item item = var4.next();
                if (!slots.contains(new Slot(item, 1))) {
                    slots.add(new Slot(item, 1));
                } else {
                    Iterator<Slot> var6 = slots.iterator();

                    while(var6.hasNext()) {
                        Slot slot = var6.next();
                        if (slot.Item() == item) {
                            slot.Add(new Slot(item, 1));
                            break;
                        }
                    }
                }
            }

            try {
                containers.add((ArrayList<Slot>) Hands().clone());
            } catch (NullPointerException e) {
            }

            try {
                containers.add((ArrayList<Slot>)Backpack().clone());
            } catch (NullPointerException e) {
            }

            try {
                containers.add((ArrayList<Slot>)Pants().clone());
            } catch (NullPointerException e) {
            }

            try {
                containers.add((ArrayList<Slot>)Torso().clone());
            } catch (NullPointerException e) {
            }

            try {
                containers.add((ArrayList<Slot>)Pouch().clone());
            } catch (NullPointerException e) {
            }

            int containerId = 0;
            boolean touched = false;

            do {
                try {
                    for(int i = 0; i < containers.get(containerId).size(); ++i) {
                        for(int j = 0; j < ((Slot)((ArrayList)containers.get(containerId)).get(i)).Value(); ++j) {
                            if (((Slot)((ArrayList)containers.get(containerId)).get(i)).Item() == slots.get(0).Item()) {
                                ((Slot)((ArrayList)containers.get(containerId)).get(i)).Remove(new Slot(slots.get(0).Item(), 1));
                                craft.remove(0);
                                touched = true;
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException var13) {
                }

                ++containerId;
            } while(containerId < containers.size());

            if (!craft.isEmpty()) {
                if (!touched) {
                    return false;
                }

                items = craft.toArray(new Item[0]);
                return canCraft(items);
            }

            return true;
        }
    }
}
