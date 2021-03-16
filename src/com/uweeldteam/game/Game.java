package com.uweeldteam.game;

import com.uweeldteam.game.fight.Fight;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Inventory;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.CraftSystem;
import com.uweeldteam.game.player.inventory.item.Item;
import com.uweeldteam.game.player.inventory.item.ItemType;
import org.jetbrains.annotations.NotNull;
import uweellibs.*;

import java.util.ArrayList;
import java.util.Locale;

import static com.uweeldteam.game.player.inventory.item.Item.*;

public class Game extends MonoBehaviour {
    public static boolean canAction = true;
    Player player;

    public Game() {
        Player(new Player());
    }

    void CountHunger() {
        if (Player().Stats().satiety < 1) {
            Player().Stats().food += 0.04f;
        } else {
            Player().Stats().satiety -= 0.04f;
        }
        if (Player().Stats().drunkenness < 1) {
            Player().Stats().water += 0.07f;
        } else {
            Player().Stats().drunkenness -= 0.07f;
        }
        if (Player().Stats().food < 1)
            Player().Stats().health -= 2;
        if (Player().Stats().water < 1)
            Player().Stats().health -= 2;
    }

    void Eat(@NotNull Item food) {
        if (food.Type() != ItemType.food)
            throw new IllegalArgumentException("Item is not food!");
        Player().Stats().food += Random.Range(food.Food());
        Player().Stats().water += Random.Range(food.Water());
        Player().Stats().satiety += Random.Range(food.Satiety());
        Player().Stats().drunkenness += Random.Range(food.Drunkenness());
        if (Player().Stats().food < 0) {
            Player().Stats().food = 0;
        }
        if (Player().Stats().water < 0) {
            Player().Stats().water = 0;
        }
        if (Player().Stats().satiety > 5)
            Player().Stats().satiety = 5;
        if (Player().Stats().satiety > 3)
            Player().Stats().satiety = 3;
    }

    public void PostUpdate() {
        if (canAction)
            ReadCommand();
    }

    public void ReadCommand() {
        CountHunger();
        try {
            String message = Console.Read().toLowerCase().replace(" {2}", " ");
            ArrayList<String> messages = new ArrayList<>();
            int id = 0;
            for (int i = 0; i < message.length(); i++) {
                String character = message.substring(i, i + 1);
                if (character.equals(" ")) {
                    id++;
                } else {
                    try {
                        messages.set(id, messages.get(id) + character);
                    } catch (IndexOutOfBoundsException exception) {
                        messages.add(character);
                    }
                }
            }
            if (messages.isEmpty())
                Console.Println("Message is empty");
            else
                ReadCommand(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReadCommand(ArrayList<String> messages) {
        try {
            switch (messages.get(0)) {
                case "использовать":
                    for (int i = 0; i < Player().Inventory().AllItems().length; i++) {
                        String item = "";
                        for (int j = 1; j < messages.size(); j++) {
                            item += messages.get(j);
                        }
                        Item Item = Player().Inventory().AllItems()[i];
                        if (item.equals(Item.Names(0).replaceAll(" ", "").toLowerCase()) || item.equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                            if (Player().Inventory().Contains(Item)) {
                                Player().Inventory().DeleteItems(Item);
                                Use(Item);
                            } else
                                Console.Println("У вас нет такого предмета.");
                        }
                    }
                case "получить":
                    for (int i = 0; i < Player().Inventory().AllItems().length; i++) {
                        String item = "";
                        int value = 1;
                        for (int j = 1; j < messages.size(); j++) {
                            try {
                                value = Integer.parseInt(messages.get(j));
                            } catch (Exception e) {
                                item += (messages.get(j));
                            }
                        }
                        Item Item = Player().Inventory().AllItems()[i];
                        Console.Println(Item);
                        if (
                                item.equals(Item.Names(0).replaceAll(" ", "").toLowerCase())
                                        ||item.equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                            Get(Item, value);
                            return;
                        }
                    }
                    break;
                case "инвентарь":
                    Console.Println("Инвентарь:"
                            + "\nРуки(" + percent(Player().AllHandsMass()) + "/" + percent(Player().MaxHandsMass())
                            + ")\n" + Player().Inventory().toString(Player().Hands()));
                    Console.Println(Player().Inventory().toString(Player().Backpack()));
                    Console.Println(Player().Inventory().toString(Player().Pouch()));
                    Console.Println(Player().Inventory().toString(Player().Torso()));
                    Console.Println(Player().Inventory().toString(Player().Pants()));
                    break;
                case "профиль":
                    Console.Println(Player().toString());
            }
        } catch (NullPointerException ignored) {
        }

    }

    String percent(float str) {
        return String.format("%.2f", str);
    }

    private void Use(Item item) {
        switch (item.Type()) {
            case food:
                Eat(item);
                break;
            case armor:
                break;
        }
    }

    private void Get(Item item, int value) {
        Player().Inventory().AddItem(new Slot(item, value));
    }

    void Player(Player player) {
        this.player = player;
    }

    public Player Player() {
        return player;
    }

    public void Save() {
        Console.Println("Saving...");
        PlayerPrefs.SetObject("Game", this);
        Console.Println(Json.ToJson(Player()));
        Console.Println("Save completed!");
    }
}
