package com.uweeldteam.game;

import com.uweeldteam.Engine;
import com.uweeldteam.Engine.GameState;
import com.uweeldteam.ExceptionOccurred;
import com.uweeldteam.Main;
import com.uweeldteam.game.fight.Fight;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.CraftSystem;
import com.uweeldteam.game.player.inventory.item.Item;
import com.uweeldteam.game.player.inventory.item.Item.ItemType;
import org.jetbrains.annotations.NotNull;
import uweellibs.Json;
import uweellibs.MonoBehaviour;
import uweellibs.PlayerPrefs;
import uweellibs.Random;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Game extends MonoBehaviour {
    public static boolean canAction = true;
    public GameState gameState;
    public Fight fight;
    Player player;

    public Game() {
        this.gameState = GameState.normal;
        this.Player(new Player());
    }

    void CountHunger() {
        if (Player().Stats().satiety <= 0.0F) {
            Player().Stats().food += 0.04F;
        } else {
            Player().Stats().satiety -= 0.04F;
        }
        if (Player().Stats().drunkenness <= 0.0F) {
            Player().Stats().water += 0.07F;
        } else {
            Player().Stats().drunkenness -= 0.07F;
        }
        if (Player().Stats().food > 5.0F) {
            Player().Stats().health -= 2.0F;
        }
        if (Player().Stats().water > 3.0F) {
            Player().Stats().health -= 2.0F;
        }

    }

    void Eat(@NotNull Item food) {
        if (food.Type() != ItemType.food)
            throw new IllegalArgumentException("Item is not food!");
        else {
            Player().Inventory().DeleteItems(food);
            Player().Stats().food += Random.Range(food.Food());
            Player().Stats().water += Random.Range(food.Water());
            Player().Stats().satiety += Random.Range(food.Satiety());
            Player().Stats().drunkenness += Random.Range(food.Drunkenness());
            if (Player().Stats().food < 0.0F) {
                Player().Stats().food = 0.0F;
            }
            if (Player().Stats().water < 0.0F) {
                Player().Stats().water = 0.0F;
            }

            if (Player().Stats().satiety > 5.0F) {
                Player().Stats().satiety = 5.0F;
            }
            if (Player().Stats().satiety > 3.0F) {
                Player().Stats().satiety = 3.0F;
            }

        }
    }

    public void ReadCommand(String message) {
        CountHunger();

        try {
            ArrayList<String> messages = new ArrayList();
            int id = 0;

            for (int i = 0; i < message.length(); ++i) {
                String character = message.substring(i, i + 1);
                if (character.equals(" ")) {
                    id++;
                } else {
                    try {
                        String text = messages.get(id);
                        messages.set(id, text + character);
                    } catch (IndexOutOfBoundsException var7) {
                        messages.add(character);
                    }
                }
            }

            if (messages.isEmpty()) {
                Engine.Println("Message is empty");
            } else {
                this.ReadCommand(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ReadCommand(ArrayList<String> messages) throws Exception {
        if (this.Player() == null) {
            throw new IllegalStateException();
        } else {
            try {
                switch (messages.get(0)) {
                    case "получить":
                        for (int itemId = 0, value; itemId < this.Player().Inventory().AllItems().length; itemId++) {
                            StringBuilder itemName = new StringBuilder();
                            value = 1;

                            for (int j = 1; j < messages.size(); ++j) {
                                try {
                                    value = Integer.parseInt(messages.get(j));
                                } catch (Exception var9) {
                                    itemName.append(messages.get(j));
                                }
                            }
                            Item item = this.Player().Inventory().AllItems()[itemId];
                            if (itemName.toString().equals(item.Names(0).replaceAll(" ", "").toLowerCase()) || itemName.toString().equals(item.Names(1).replaceAll(" ", "").toLowerCase())) {
                                this.Get(item, value);
                                return;
                            }
                        }
                        break;
                    case "скрафтить":
                        for (int i = 0; i < this.Player().Inventory().AllItems().length; ++i) {
                            StringBuilder itemName = new StringBuilder();

                            for (int messageId = 1; messageId < messages.size(); messageId++) {
                                itemName.append(messages.get(messageId));
                            }

                            Item item = this.Player().Inventory().AllItems()[i];
                            if (itemName.toString().equals(item.Names(0).replaceAll(" ", "").toLowerCase()) || itemName.toString().equals(item.Names(1).replaceAll(" ", "").toLowerCase())) {
                                CraftSystem.Craft(item);
                                return;
                            }
                        }
                        break;
                    case "профиль":
                        Engine.Println(this.Player().toString());
                        break;
                    case "инвентарь":
                        Engine.Println(this.Player().Inventory().toString());
                        break;
                    case "охота":
                        fight = new Fight();
                        break;
                    case "использовать":
                        StringBuilder itemName = new StringBuilder();
                        for (int messageID = 1; messageID < messages.size(); messageID++)
                            itemName.append(messages.get(messageID));

                        for (int i = 0; i < Player().Inventory().AllItems().length; ++i) {
                            Item item = Player().Inventory().AllItems()[i];
                            if (itemName.toString().equals(item.Names(0).replaceAll(" ", "").toLowerCase()) || itemName.toString().equals(item.Names(1).replaceAll(" ", "").toLowerCase())) {
                                if (Player().Inventory().Contains(item)) {
                                    Player().Inventory().DeleteItems(item);
                                    Use(item);
                                } else {
                                    Engine.Println("У вас нет такого предмета.");
                                }
                            }
                        }
                        break;
                    default:
                        Engine.Println("Такой комманды не существует.");
                }
            } catch (NullPointerException ignored) {
                new ExceptionOccurred(new Exception("Ошибка при чтении команды"));
            }

        }
    }

    private void Use(Item item) {
        switch (item.Type()) {
            case food:
                this.Eat(item);
            case armor:
            default:
        }
    }

    private void Get(Item item, int value) {
        this.Player().Inventory().AddItem(new Slot(item, value));
    }

    void Player(Player player) {
        this.player = player;
    }

    public Player Player() {
        return this.player;
    }

    public void Save() {
        Engine.Println("Saving...");
        PlayerPrefs.SetObject("Game", this);
        Engine.Println(Json.ToJson(this.Player()));
        Engine.Println("Save completed!");
    }
}
