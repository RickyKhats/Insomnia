package com.uweeldteam.game;

import com.uweeldteam.Engine;
import com.uweeldteam.GameState;
import com.uweeldteam.game.fight.Fight;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.CraftSystem;
import com.uweeldteam.game.player.inventory.item.Item;
import org.jetbrains.annotations.NotNull;
import uweellibs.*;

import java.util.ArrayList;

public class Game extends MonoBehaviour {
    public static boolean canAction = true;
    public static GameState gameState = GameState.normal;
    public Fight fight;
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
        if (food.Type() != Item.ItemType.food)
            throw new IllegalArgumentException("Item is not food!");
        Player().Inventory().DeleteItems(food);
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

    public void ReadCommand(String message) {
        CountHunger();
        try {
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
                Engine.Println("Message is empty");
            else
                ReadCommand(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReadCommand(ArrayList<String> messages) {
        if (Player() == null)
            throw new IllegalStateException();
        try {
            switch (messages.get(0)) {
                case "охота":
                    fight = new Fight();
                case "использовать":
                    for (int i = 0; i < Player().Inventory().AllItems().length; i++) {
                        StringBuilder item = new StringBuilder();
                        for (int j = 1; j < messages.size(); j++) {
                            item.append(messages.get(j));
                        }
                        Item Item = Player().Inventory().AllItems()[i];
                        if (item.toString().equals(Item.Names(0).replaceAll(" ", "").toLowerCase()) || item.toString().equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                            if (Player().Inventory().Contains(Item)) {
                                Player().Inventory().DeleteItems(Item);
                                Use(Item);
                            } else
                                Engine.Println("У вас нет такого предмета.");
                        }
                    }
                case "получить":
                    for (int i = 0; i < Player().Inventory().AllItems().length; i++) {
                        StringBuilder item = new StringBuilder();
                        int value = 1;
                        for (int j = 1; j < messages.size(); j++) {
                            try {
                                value = Integer.parseInt(messages.get(j));
                            } catch (Exception e) {
                                item.append(messages.get(j));
                            }
                        }
                        Item Item = Player().Inventory().AllItems()[i];
                        if (item.toString().equals(Item.Names(0).replaceAll(" ", "").toLowerCase()) || item.toString().equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                            Get(Item, value);
                            return;
                        }
                    }
                    break;
                case "скрафтить":
                    for (int i = 0; i < Player().Inventory().AllItems().length; i++) {
                        StringBuilder item = new StringBuilder();
                        for (int j = 1; j < messages.size(); j++) {
                            item.append(messages.get(j));
                        }
                        Item Item = Player().Inventory().AllItems()[i];
                        if (item.toString().equals(Item.Names(0).replaceAll(" ", "").toLowerCase()) || item.toString().equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                            CraftSystem.Craft(Item);
                            return;
                        }
                    }
                    break;
                case "инвентарь":
                    Engine.Println(Player().Inventory().toString());
                    break;
                case "профиль":
                    Engine.Println(Player().toString());
                default:
                    Engine.Println(
                            "Такой комманды не существует." +
                            "\nЕсли вы не знаете комманды то их можно прочитать в \"Помощь->Комманды\"");
            }
        } catch (NullPointerException ignored) {
        }

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
        Engine.Println("Saving...");
        PlayerPrefs.SetObject("Game", this);
        Engine.Println(Json.ToJson(Player()));
        Engine.Println("Save completed!");
    }
}
