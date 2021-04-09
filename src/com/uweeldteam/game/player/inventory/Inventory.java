package com.uweeldteam.game.player.inventory;

import com.uweeldteam.Engine;
import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.item.Item;
import uweellibs.Console;
import uweellibs.WaitForSeconds;

import java.util.ArrayList;
import java.util.Arrays;

import static com.uweeldteam.game.player.inventory.item.Item.nullItem;

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
        if (Backpack().item != nullItem)
            containers.add(Backpack());
        if (Pants().item != nullItem)
            containers.add(Pants());
        if (Torso().item != nullItem)
            containers.add(Torso());
        if (Pouch().item != nullItem)
            containers.add(Pouch());
        return containers;
    }

    public Item[] AllItems() {
        return Item.values();
    }

    public void DeleteItems(Item... items) {
        ArrayList<Item> craft = new ArrayList<>(Arrays.asList(items));
        ArrayList<ArrayList<Slot>> containers = new ArrayList<>();

        for (int i = 0; i < containers().size(); ++i) {
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

    public void AddItem(Slot slot, String... startMessage) {
        int remains = Pickup(slot);

        if (remains > 0)
            Engine.Println("Недостаточно места на " + slot.Item().Names(1).toLowerCase() + (remains > 1 ? " X" + remains : "."));
        Engine.Println(((startMessage.length == 0) ? "Вы получили " : startMessage[0]) + slot.Item().Names(1).toLowerCase() + (slot.Value() - remains > 1 ? " X" + (slot.Value() - remains) : ""));
    }

    private int Pickup(Slot slot) {
        if (Backpack().item != nullItem && slot.Value() > 0)
            slot.Value((short) Pickup(Hands(), slot));
        if (Backpack().item != nullItem && slot.Value() > 0)
            slot.Value((short) Pickup(Backpack(), slot));
        if (Torso().item != nullItem && slot.Value() > 0)
            slot.Value((short) Pickup(Torso(), slot));
        if (Pants().item != nullItem && slot.Value() > 0)
            slot.Value((short) Pickup(Pants(), slot));
        if (Pouch().item != nullItem && slot.Value() > 0)
            slot.Value((short) Pickup(Pouch(), slot));
        return slot.Value();
    }

    private int Pickup(Container container, Slot slot) {
        Console.Println(container.AllMass());
        if (slot.Value() == 0)
            return 0;
        else {
            short remains = slot.Value();

            while (remains > 0) {
                byte currentSlot = (byte) FindFirstAvailableSlot(slot, container.slots);
                if (currentSlot == -1) {
                    Engine.Println((container.item == nullItem) ? ("Ваши руки не удержат столько вещей") : ("Ваш " + container.item.Names(0).toLowerCase() + " не выдержит столько вещей"));
                    return remains;
                } else if (container.AllMass() + slot.Item().Mass() > container.MaxMass()) {
                    Engine.Println((container.item == nullItem) ? ("Ваши руки не выдержат столько веса") : ("Ваш " + container.item.Names(0).toLowerCase() + " не выдержит столько веса"));
                    return remains;
                } else {
                    if (container.slots.get(currentSlot).Value() + 1 <= slot.Item().MaxStack()) {
                        remains -= 1;
                        container.slots.get(currentSlot).Value((short) (container.slots.get(currentSlot).Value() + 1));
                        container.slots.get(currentSlot).Item(slot.Item());
                    }
                }
                Console.Println(remains, currentSlot, container.slots.get(currentSlot).toString());
                new WaitForSeconds(0.01f);
            }
            return remains;
        }

    }

    public boolean Contains(Item item) {
        for (int i = 0; i < containers().size(); i++) {
            Console.Println(containers().get(i).item.Slots().toString());
            if (containers().get(i).item == item) {
                return true;
            }
        }

        return false;
    }

    private int FindFirstAvailableSlot(Slot slot, ArrayList<Slot> slots) {
        int firstFree = -1;

        for (int i = 0; i < slots.size(); ++i) {
            if (slots.get(i).Item().equals(slot.Item()) && slots.get(i).Value() != slots.get(i).Item().MaxStack()) {
                return i;
            }

            if (slots.get(i).Item() == nullItem && firstFree == -1) {
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

        for (int id = 0; id < slots.size(); id++)
            returns.append(id + 1).append(": ").append(slots.get(id).toString()).append("\n");
        return returns.substring(0, returns.length() - 1);
    }

    public String toString() {
        return "Инвентарь:\nРуки(" + percent(Player().AllHandsMass()) + "/" + percent(Player().MaxHandsMass()) + ")\n"
                + toString(Player().Hands())
                + ((Backpack().item != nullItem
                || Pouch().item != nullItem
                || Torso().item != nullItem
                || Pants().item != nullItem)
                ? ("\n")
                : (""))
                + toString(Player().Backpack())
                + toString(Player().Pouch())
                + toString(Player().Torso())
                + toString(Player().Pants());
    }

    public String toString(Item item) {
        StringBuilder returns = new StringBuilder();
        if (item != nullItem) {
            returns = new StringBuilder(
                    item.Names(0) + "(" + String.format("%.2f", item.AllMass()) + "/" + String.format("%.2f", item.MaxMass()) + ") \n");
            int id = 1;

            for (Slot slot : item.Slots()) {
                returns.append(id).append(": ")
                        .append(slot.Item().Names(0))
                        .append(slot.Value() < 2 ? "" : " X" + slot.Value())
                        .append(id == item.Slots().size() ? "" : "\n");
                id++;
            }
        }

        return returns.toString();
    }

    static class Container {
        public Item item = nullItem;
        //Инициализацию эту не убирать, он кидает исключения когда её нет
        @SuppressWarnings("UnusedAssignment")
        public ArrayList<Slot> slots = new ArrayList<>();

        public Container(Item container) {
            this.slots = container.Slots();
            this.item = container;
        }

        public Container(ArrayList<Slot> slots) {
            this.slots = slots;
        }

        public float AllMass() {
            return ((item != nullItem) ? item.AllMass() : Main.Engine().Game().Player().AllHandsMass());
        }

        public float MaxMass() {
            return ((item != nullItem) ? item.MaxMass() : Main.Engine().Game().Player().MaxHandsMass());
        }
    }
}
