//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.player.inventory;

import com.uweeldteam.Engine;
import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.item.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Inventory {
    public Inventory() {
    }

    public static Player Player() {
        return Main.Engine().Game().Player();
    }

    public static Inventory.Container Hands() {
        return new Inventory.Container(Player().Hands());
    }

    public static Inventory.Container Backpack() {
        return new Inventory.Container(Player().Backpack());
    }

    public static Inventory.Container Pants() {
        return new Inventory.Container(Player().Pants());
    }

    public static Inventory.Container Torso() {
        return new Inventory.Container(Player().Torso());
    }

    public static Inventory.Container Pouch() {
        return new Inventory.Container(Player().Pouch());
    }

    public static ArrayList<Inventory.Container> containers() {
        ArrayList<Inventory.Container> containers = new ArrayList();
        containers.add(Hands());
        containers.add(Backpack());
        containers.add(Pants());
        containers.add(Torso());
        containers.add(Pouch());
        return containers;
    }

    public Item[] AllItems() {
        return Item.values();
    }

    public void DeleteItems(Item... items) {
        ArrayList<Item> craft = new ArrayList(Arrays.asList(items));
        ArrayList<ArrayList<Slot>> containers = new ArrayList();

        for(int i = 0; i < containers().size(); ++i) {
            containers.add(((Inventory.Container)containers().get(i)).slots);
        }

        boolean touched = false;
        Iterator var5 = containers.iterator();

        while(var5.hasNext()) {
            ArrayList<Slot> container = (ArrayList)var5.next();
            Iterator var7 = container.iterator();

            while(var7.hasNext()) {
                Slot slot = (Slot)var7.next();
                Iterator var9 = craft.iterator();

                while(var9.hasNext()) {
                    Item item = (Item)var9.next();

                    try {
                        if (slot.Item() == item) {
                            slot.Remove(new Slot(item, 0));
                            touched = true;
                        }
                    } catch (IndexOutOfBoundsException var12) {
                    }
                }

                if (craft.isEmpty()) {
                    return;
                }
            }
        }

        if (craft.isEmpty() || touched) {
            Engine.Println("End.");
        }
    }

    public void AddItem(Slot slot, boolean showMessage) {
        this.Pickup(slot, 0, showMessage, true);
    }

    public void AddItem(Slot slot) {
        this.Pickup(slot, 0, true, true);
    }

    private void Pickup(Slot slot, int got, boolean showMessage, boolean checkHands) {
        Slot remains = slot;
        ArrayList<Item> containers = new ArrayList();

        int gotten;
        for(gotten = 0; gotten < containers().size(); ++gotten) {
            if (((Inventory.Container)containers().get(gotten)).item != null) {
                containers.add(((Inventory.Container)containers().get(gotten)).item);
            }
        }

        if (checkHands) {
            for(gotten = 0; gotten < Hands().slots.size() && remains.Value() != 0; ++gotten) {
                if (Player().AllHandsMass() + remains.Item().Mass() > Player().MaxHandsMass()) {
                    Engine.Println("Ваши руки не выдержат столько веса");
                    break;
                }

                int f = this.FindFirstAvailableSlot(remains, Hands().slots);

                try {
                    remains = ((Slot)Hands().slots.get(f)).Add(remains);
                } catch (IndexOutOfBoundsException var12) {
                }
            }
        }

        Iterator var13 = containers.iterator();

        while(var13.hasNext()) {
            Item container = (Item)var13.next();
            if (remains.Value() == 0) {
                break;
            }

            if (container.AllMass() + remains.Item().Mass() > container.MaxMass()) {
                if (container != Item.nullItem) {
                    Engine.Println("Ваш " + container.Names(0).toLowerCase() + " не выдержит столько веса");
                }
                break;
            }

            int f = this.FindFirstAvailableSlot(remains, container.Slots());

            try {
                remains = ((Slot)container.Slots().get(f)).Add(remains);
            } catch (IndexOutOfBoundsException var11) {
            }
        }

        String var10000;
        if (remains.Value() > 0) {
            if (remains.Value() != slot.Value()) {
                this.Pickup(remains, got + slot.Value() - remains.Value(), showMessage, false);
            } else {
                var10000 = remains.Item().Names(1).toLowerCase();
                Engine.Println("Недостаточно места на " + var10000 + (remains.Value() > 1 ? " X" + remains.Value() : "."));
            }
        } else {
            gotten = slot.Value() + remains.Value();
            if (showMessage) {
                var10000 = remains.Item().Names(1).toLowerCase();
                Engine.Println("Вы получили " + var10000 + (gotten + got > 1 ? " X" + (gotten + got) : ""));
            }
        }

    }

    public boolean Contains(Item item) {
        for(int i = 0; i < containers().size(); ++i) {
            if (((Inventory.Container)containers().get(i)).item == item) {
                return true;
            }
        }

        return false;
    }

    private int FindFirstAvailableSlot(Slot slot, ArrayList<Slot> slots) {
        int firstFree = -1;

        for(int i = 0; i < slots.size(); ++i) {
            if (((Slot)slots.get(i)).Item().equals(slot.Item()) && ((Slot)slots.get(i)).Value() != ((Slot)slots.get(i)).Item().MaxStack()) {
                return i;
            }

            if (((Slot)slots.get(i)).Item() == Item.nullItem && firstFree == -1) {
                firstFree = i;
            }
        }

        return firstFree;
    }

    String percent(float str) {
        return String.format("%.2f", str);
    }

    public String toString(ArrayList<Slot> slots) {
        StringBuilder returns = new StringBuilder();
        int id = 1;

        for(Iterator var4 = slots.iterator(); var4.hasNext(); ++id) {
            Slot slot = (Slot)var4.next();
            returns.append(id).append(": ").append(slot.toString()).append(id - 1 == slots.size() ? "" : "\n");
        }

        return returns.toString();
    }

    public String toString() {
        String var10000 = this.percent(Player().AllHandsMass());
        String result = "Инвентарь:\nРуки(" + var10000 + "/" + this.percent(Player().MaxHandsMass()) + ")\n" + Player().Inventory().toString(Player().Hands());
        result = result + this.toString(Player().Backpack());
        result = result + this.toString(Player().Pouch());
        result = result + this.toString(Player().Torso());
        result = result + this.toString(Player().Pants());
        return result;
    }

    public String toString(Item item) {
        StringBuilder returns = new StringBuilder();
        if (item != Item.nullItem) {
            String var10002 = item.Names(0);
            returns = new StringBuilder(var10002 + "(" + String.format("%.2f", item.AllMass()) + "/" + String.format("%.2f", item.MaxMass()) + "): \n");
            int id = 1;

            for(Iterator var4 = item.Slots().iterator(); var4.hasNext(); ++id) {
                Slot slot = (Slot)var4.next();
                returns.append(id).append(": ").append(slot.Item().Names(0)).append(slot.Value() < 2 ? "" : " X" + slot.Value()).append(id == item.Slots().size() ? "" : "\n");
            }
        }

        return returns.toString();
    }

    static class Container {
        public ArrayList<Slot> slots;
        public Item item;

        public Container(Item container) {
            this.slots = container.Slots();
            this.item = container;
        }

        public Container(ArrayList<Slot> slots) {
            this.slots = slots;
        }
    }
}
