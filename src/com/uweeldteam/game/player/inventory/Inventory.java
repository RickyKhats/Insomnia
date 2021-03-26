package com.uweeldteam.game.player.inventory;

import com.uweeldteam.Engine;
import com.uweeldteam.ExceptionOccurred;
import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.Console;

import java.util.ArrayList;
import java.util.Arrays;

public class Inventory {

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
        ArrayList<Inventory.Container> containers = new ArrayList<>();
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
        ArrayList<Item> craft = new ArrayList<>(Arrays.asList(items));
        ArrayList<ArrayList<Slot>> containers = new ArrayList<>();

        for(int i = 0; i < containers().size(); ++i) {
            containers.add(containers().get(i).slots);
        }

        boolean touched = false;

        for (ArrayList<Slot> slots : containers) {

            for (Slot slot : slots) {
                for (Item item : craft) {
                    try {
                        if (slot.Item() == item) {
                            slot.Remove(new Slot(item, 0));
                            touched = true;
                        }
                    } catch (IndexOutOfBoundsException ignored) {
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
        ArrayList<Item> containers = new ArrayList<>();

        int gotten = 0;
        while (gotten < containers().size()) {
            if (containers().get(gotten).item != null) {
                containers.add(containers().get(gotten).item);
            }
            gotten++;
        }

        if (checkHands) {
            for(gotten = 0; gotten < Hands().slots.size() && remains.Value() != 0; ++gotten) {
                if (Player().AllHandsMass() + remains.Item().Mass() > Player().MaxHandsMass()) {
                    Engine.Println("Ваши руки не выдержат столько веса");
                    break;
                }

                int f = this.FindFirstAvailableSlot(remains, Hands().slots);

                try {
                    remains = Hands().slots.get(f).Add(remains);
                } catch (Exception e) {
                    new ExceptionOccurred(e);
                }
            }
        }

        for (Item container : containers) {
            if (remains.Value() == 0)
                break;

            if (container.AllMass() + remains.Item().Mass() > container.MaxMass()) {
                if (container != Item.nullItem) {
                    Engine.Println("Ваш " + container.Names(0).toLowerCase() + " не выдержит столько веса");
                }
                break;
            }
            try {
                remains = container.Slots().get(FindFirstAvailableSlot(remains, container.Slots())).Add(remains);
            } catch (IndexOutOfBoundsException ignored) {
            }
        }

        if (remains.Value() > 0) {
            if (remains.Value() != slot.Value()) {
                this.Pickup(remains, got + slot.Value() - remains.Value(), showMessage, false);
            } else {
                Engine.Println("Недостаточно места на " + remains.Item().Names(1).toLowerCase() + (remains.Value() > 1 ? " X" + remains.Value() : "."));
            }
        } else {
            gotten = slot.Value() + remains.Value();
            if (showMessage) {
                Engine.Println("Вы получили " + remains.Item().Names(1).toLowerCase() + (gotten + got > 1 ? " X" + (gotten + got) : ""));
            }
        }

    }

    public boolean Contains(Item item) {
        for(int i = 0; i < containers().size(); i++) {
            Console.Println(containers().get(i).item.Slots().toString());
            if (containers().get(i).item == item) {
                return true;
            }
        }

        return false;
    }

    private int FindFirstAvailableSlot(Slot slot, ArrayList<Slot> slots) {
        int firstFree = -1;

        for(int i = 0; i < slots.size(); ++i) {
            if (slots.get(i).Item().equals(slot.Item()) && slots.get(i).Value() != slots.get(i).Item().MaxStack()) {
                return i;
            }

            if (slots.get(i).Item() == Item.nullItem && firstFree == -1) {
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

        int id = 0;
        for (Slot slot : slots) {
            returns.append(id + 1).append(": ").append(slot.toString()).append(id == slots.size() ? "" : "\n");
            id++;
        }

        return returns.toString();
    }

    public String toString() {
        return "Инвентарь:\nРуки(" + percent(Player().AllHandsMass()) + "/" + percent(Player().MaxHandsMass()) + ")\n"
                + Player().Inventory().toString(Player().Hands())
                + toString(Player().Backpack())
                + toString(Player().Pouch())
                + toString(Player().Torso())
                + toString(Player().Pants());
    }

    public String toString(Item item) {
        StringBuilder returns = new StringBuilder();
        if (item != Item.nullItem) {
            returns = new StringBuilder(item.Names(0) + "(" + String.format("%.2f", item.AllMass()) + "/" + String.format("%.2f", item.MaxMass()) + "): \n");
            int id = 1;

            for (Slot slot : item.Slots()) {
                returns.append(id).append(": ").append(slot.Item().Names(0)).append(slot.Value() < 2 ? "" : " X" + slot.Value()).append(id == item.Slots().size() ? "" : "\n");
                id++;
            }
        }

        return returns.toString();
    }

    static class Container {
        public ArrayList<Slot> slots = new ArrayList<>();
        public Item item = Item.nullItem;

        public Container(Item container) {
            this.slots = container.Slots();
            this.item = container;
        }

        public Container(ArrayList<Slot> slots) {
            this.slots = slots;
        }
    }
}
