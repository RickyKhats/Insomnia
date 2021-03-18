package com.uweeldteam.game.player.inventory;

import com.uweeldteam.Engine;
import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.Console;

import java.util.ArrayList;
import java.util.Arrays;

import static com.uweeldteam.game.player.inventory.item.Item.nullItem;

public class Inventory {


    public Item[] AllItems(){
        return  Item.values();
    }

    static class Container {
        public ArrayList<Slot> slots;
        public Item item;

        public Container(Item container) {
            slots = container.Slots();
            item = container;
        }

        public Container(ArrayList<Slot> slots) {
            this.slots = slots;
        }
    }

    public static Player Player() {
        return Main.Engine().Game().Player();
    }

    public static Container Hands() {
        return new Container(Player().Hands());
    }

    public static Container Backpack() {
        return new Container(Player().Backpack());
    }

    public static Container Pants() {
        return new Container(Player().Pants());
    }

    public static Container Torso() {
        return new Container(Player().Torso());
    }

    public static Container Pouch() {
        return new Container(Player().Pouch());
    }

    public static ArrayList<Container> containers() {
        ArrayList<Container> containers = new ArrayList<>();
        containers.add(Hands());
        containers.add(Backpack());
        containers.add(Pants());
        containers.add(Torso());
        containers.add(Pouch());
        return containers;
    }

    public void DeleteItems(Item... items) {
        ArrayList<Item> craft = new ArrayList<>(Arrays.asList(items));
        ArrayList<ArrayList<Slot>> containers = new ArrayList<>();
        for (int i = 0; i < containers().size(); i++) {
            containers.add(containers().get(i).slots);
        }
        boolean touched = false;
        for (ArrayList<Slot> container : containers) {
            for (Slot slot : container) {
                for (Item item : craft)
                    try {
                        if (slot.Item() == item) {
                            slot.Remove(new Slot(item, (short) 0));
                            touched = true;
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                if (craft.isEmpty()) return;
            }
        }
        if (!craft.isEmpty()) {
            if (!touched) {
                return;
            }
        }
        Engine.Println("End.");
    }

    public void AddItem(Slot slot, boolean showMessage) {
        Pickup(slot, 0, showMessage, true);
    }

    public void AddItem(Slot slot) {
        Pickup(slot, 0, true, true);
    }

    private void Pickup(Slot slot, int got, boolean showMessage, boolean checkHands) {
        Slot remains = slot;
        ArrayList<Item> containers = new ArrayList<>();
        for (int i = 0; i < containers().size(); i++) {
            if (containers().get(i).item != null)
                containers.add(containers().get(i).item);
        }
        if (checkHands)
            for (int i = 0; i < Hands().slots.size(); i++) {
                if (remains.Value() == 0) break;
                if (Player().AllHandsMass() + remains.Item().Mass() > Player().MaxHandsMass()) {
                    Engine.Println("Ваши руки не выдержат столько веса");
                    break;
                } else {
                    int f = FindFirstAvailableSlot(remains, Hands().slots);
                    try {
                        remains = Hands().slots.get(f).Add(remains);
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
        for (Item container : containers) {
            if (remains.Value() == 0) break;
            if (container.AllMass() + remains.Item().Mass() > container.MaxMass()) {
                if (container != nullItem)
                    Engine.Println("Ваш " + container.Names(0).toLowerCase() + " не выдержит столько веса");
                break;
            }
            int f = FindFirstAvailableSlot(remains, container.Slots());
            try {
                remains = container.Slots().get(f).Add(remains);
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        if (remains.Value() > 0) {
            if (remains.Value() != slot.Value()) {
                Pickup(remains, got + slot.Value() - remains.Value(), showMessage, false);
            } else
                Engine.Println("Недостаточно места на " + remains.Item().Names(1).toLowerCase() + ((remains.Value() > 1) ? " X" + remains.Value() : "."));
        } else {
            int gotten = slot.Value() + remains.Value();
            if (showMessage)
                Engine.Println("Вы получили " + remains.Item().Names(1).toLowerCase() + (gotten + got > 1 ? " X" + (gotten + got) : ""));
        }
    }

    public boolean Contains(Item item){
        for(int i = 0; i < containers().size(); i++)
            if(containers().get(i).item == item)
                return true;
        return false;
    }

    private int FindFirstAvailableSlot(Slot slot, ArrayList<Slot> slots) {
        int firstFree = -1;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).Item().equals(slot.Item()) && slots.get(i).Value() != slots.get(i).Item().MaxStack())
                return i;
            if (slots.get(i).Item() == nullItem && firstFree == -1)
                firstFree = i;

        }
        return firstFree;
    }

    String percent(float str) {
        return String.format("%.2f", str);
    }

    public String toString(ArrayList<Slot> slots) {
        StringBuilder returns = new StringBuilder();
        int id = 1;
        for (Slot slot : slots) {
            returns.append(id).append(": ").append(slot.toString()).append(((id - 1 == slots.size()) ? "" : "\n"));
            id++;
        }
        return returns.toString();
    }

    public String toString() {
        String result = ("Инвентарь:" + "\nРуки(" + percent(Player().AllHandsMass()) + "/" + percent(Player().MaxHandsMass()) + ")\n" + Player().Inventory().toString(Player().Hands()));
        result += toString(Player().Backpack());
        result += toString(Player().Pouch());
        result += toString(Player().Torso());
        result += toString(Player().Pants());
        return result;
    }

    public String toString(Item item) {
        StringBuilder returns = new StringBuilder();
        if (item != nullItem) {
            returns = new StringBuilder(item.Names(0) + "(" + String.format("%.2f", item.AllMass()) + "/" + String.format("%.2f", item.MaxMass()) + "): \n");
            int id = 1;
            for (Slot slot : item.Slots()) {
                returns.append(id).append(": ").append(slot.Item().Names(0)).append(
                        slot.Value() < 2 ? "" : " X" + slot.Value()).append(((id == item.Slots().size()) ? "" : "\n"));
                id++;
            }
        }
        return returns.toString();
    }
}
