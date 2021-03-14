package com.uweeldteam.game.player.inventory;

import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.Console;

import java.util.ArrayList;
import java.util.Arrays;

import static com.uweeldteam.game.player.inventory.item.Item.nullItem;

public class Inventory {

    static class Container {
        ArrayList<Slot> slots;
        Item item;

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
        Console.Println("Loop");
        ArrayList<Item> craft = new ArrayList<>(Arrays.asList(items));
        ArrayList<ArrayList<Slot>> containers = new ArrayList<>();
        for (int i = 0; i < containers().size(); i++) {
            containers.add(containers().get(i).slots);
        }
        boolean touched = false;
        for (ArrayList<Slot> container : containers) {
            for (int i = 0; i < container.size(); i++) {
                for (int ii = 0; ii < craft.size(); ii++)
                    try {
                        Console.Println(container + " " + i + " " + ii);
                        if (container.get(i).Item() == craft.get(ii)) {
                            container.get(i).Remove(new Slot(craft.get(ii), (short) 0));
                            touched = true;
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
            }
            if (craft.isEmpty()) break;
        }
        if (!craft.isEmpty()) {
            if (!touched) {
                return;
            }
            DeleteItems(craft.toArray(new Item[]{}));
        }
        Console.Println("End.");
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
                    Console.Println("Ваши руки не выдержат столько веса");
                } else {
                    int f = FindFirstAvailableSlot(remains, Hands().slots);
                    try {
                        remains = Hands().slots.get(f).Add(remains);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        for (Item container : containers) {
            if (remains.Value() == 0) break;
            if (container.AllMass() + remains.Item().Mass() > container.MaxMass()) {
                if (container != nullItem)
                    Console.Println("Ваш " + container.Names(0).toLowerCase() + " не выдержит столько веса");
                else
                    break;
            }
            int f = FindFirstAvailableSlot(remains, container.Slots());
            try {
                remains = container.Slots().get(f).Add(remains);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        if (remains.Value() > 0) {
            if (remains.Value() != slot.Value()) {
                Pickup(remains, got + slot.Value() - remains.Value(), showMessage, false);
            } else
                Console.Println("Недостаточно места на " + remains.Item().Names(1).toLowerCase() + ((remains.Value() > 1) ? " X" + remains.Value() : "."));
        } else {
            int gotten = slot.Value() + remains.Value();
            if (showMessage)
                Console.Println("Вы получили " + remains.Item().Names(1).toLowerCase() + (gotten + got > 1 ? " X" + (gotten + got) : ""));
        }
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

    public String toString(ArrayList<Slot> slots) {
        StringBuilder returns = new StringBuilder();
        int id = 1;
        for (Slot slot : slots) {
            returns.append(id).append(": ").append(slot.toString()).append("\n");
            id++;
        }
        return returns.toString();
    }

    public String toString(Item item) {
        StringBuilder returns = new StringBuilder();
        if (item != nullItem) {
            returns = new StringBuilder(item.Names(0) + "(" + String.format("%.2f", item.AllMass()) + "/" + String.format("%.2f", item.MaxMass()) + "): \n");
            int id = 1;
            for (Slot slot : item.Slots()) {
                returns.append(id).append(": ").append(slot.Item().Names(0)).append(slot.Value() < 2 ? "" : " X" + slot.Value()).append("\n");
                id++;
            }
        }
        return returns.toString();
    }
}
