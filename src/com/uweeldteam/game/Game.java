//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game;

import com.uweeldteam.Engine;
import com.uweeldteam.Engine.GameState;
import com.uweeldteam.game.fight.Fight;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.Player.Stats;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.CraftSystem;
import com.uweeldteam.game.player.inventory.item.Item;
import com.uweeldteam.game.player.inventory.item.Item.ItemType;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import uweellibs.Json;
import uweellibs.MonoBehaviour;
import uweellibs.PlayerPrefs;
import uweellibs.Random;

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
        Stats var10000;
        if (this.Player().Stats().satiety <= 0.0F) {
            var10000 = this.Player().Stats();
            var10000.food += 0.04F;
        } else {
            var10000 = this.Player().Stats();
            var10000.satiety -= 0.04F;
        }

        if (this.Player().Stats().drunkenness <= 0.0F) {
            var10000 = this.Player().Stats();
            var10000.water += 0.07F;
        } else {
            var10000 = this.Player().Stats();
            var10000.drunkenness -= 0.07F;
        }

        if (this.Player().Stats().food > 5.0F) {
            var10000 = this.Player().Stats();
            var10000.health -= 2.0F;
        }

        if (this.Player().Stats().water > 3.0F) {
            var10000 = this.Player().Stats();
            var10000.health -= 2.0F;
        }

    }

    void Eat(@NotNull Item food) {
        if (food == null)
            throw new NullPointerException();

        if (food.Type() != ItemType.food)
            throw new IllegalArgumentException("Item is not food!");
        else {
            this.Player().Inventory().DeleteItems(new Item[]{food});
            Stats var10000 = this.Player().Stats();
            var10000.food += Random.Range(food.Food());
            var10000 = this.Player().Stats();
            var10000.water += Random.Range(food.Water());
            var10000 = this.Player().Stats();
            var10000.satiety += Random.Range(food.Satiety());
            var10000 = this.Player().Stats();
            var10000.drunkenness += Random.Range(food.Drunkenness());
            if (this.Player().Stats().food < 0.0F) {
                this.Player().Stats().food = 0.0F;
            }

            if (this.Player().Stats().water < 0.0F) {
                this.Player().Stats().water = 0.0F;
            }

            if (this.Player().Stats().satiety > 5.0F) {
                this.Player().Stats().satiety = 5.0F;
            }

            if (this.Player().Stats().satiety > 3.0F) {
                this.Player().Stats().satiety = 3.0F;
            }

        }
    }

    public void ReadCommand(String message) {
        this.CountHunger();

        try {
            ArrayList<String> messages = new ArrayList();
            int id = 0;

            for(int i = 0; i < message.length(); ++i) {
                String character = message.substring(i, i + 1);
                if (character.equals(" ")) {
                    ++id;
                } else {
                    try {
                        String var10002 = (String)messages.get(id);
                        messages.set(id, var10002 + character);
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
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    private void ReadCommand(ArrayList<String> messages) {
        if (this.Player() == null) {
            throw new IllegalStateException();
        } else {
            try {
                switch (messages.get(0)){
                    case "получить"
                }
                String var2 = messages.get(0);
                byte var3 = -1;
                switch(var2.hashCode()) {
                    case -202532638:
                        if (var2.equals("получить")) {
                            var3 = 2;
                        }
                        break;
                    case -88677093:
                        if (var2.equals("скрафтить")) {
                            var3 = 3;
                        }
                        break;
                    case 469152994:
                        if (var2.equals("профиль")) {
                            var3 = 5;
                        }
                        break;
                    case 528531311:
                        if (var2.equals("инвентарь")) {
                            var3 = 4;
                        }
                        break;
                    case 1036583877:
                        if (var2.equals("охота")) {
                            var3 = 0;
                        }
                        break;
                    case 1179707848:
                        if (var2.equals("использовать")) {
                            var3 = 1;
                        }
                }

                int i;
                StringBuilder item;
                int value;
                Item Item;
                switch(var3) {
                    case 0:
                        this.fight = new Fight();
                    case 1:
                        for(i = 0; i < this.Player().Inventory().AllItems().length; ++i) {
                            item = new StringBuilder();

                            for(value = 1; value < messages.size(); ++value) {
                                item.append((String)messages.get(value));
                            }

                            Item = this.Player().Inventory().AllItems()[i];
                            if (item.toString().equals(Item.Names(0).replaceAll(" ", "").toLowerCase()) || item.toString().equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                                if (this.Player().Inventory().Contains(Item)) {
                                    this.Player().Inventory().DeleteItems(new Item[]{Item});
                                    this.Use(Item);
                                } else {
                                    Engine.Println("У вас нет такого предмета.");
                                }
                            }
                        }
                    case 2:
                        for(i = 0; i < this.Player().Inventory().AllItems().length; ++i) {
                            item = new StringBuilder();
                            value = 1;

                            for(int j = 1; j < messages.size(); ++j) {
                                try {
                                    value = Integer.parseInt((String)messages.get(j));
                                } catch (Exception var9) {
                                    item.append((String)messages.get(j));
                                }
                            }

                            Item Item = this.Player().Inventory().AllItems()[i];
                            if (item.toString().equals(Item.Names(0).replaceAll(" ", "").toLowerCase()) || item.toString().equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                                this.Get(Item, value);
                                return;
                            }
                        }

                        return;
                    case 3:
                        for(i = 0; i < this.Player().Inventory().AllItems().length; ++i) {
                            item = new StringBuilder();

                            for(value = 1; value < messages.size(); ++value) {
                                item.append((String)messages.get(value));
                            }

                            Item = this.Player().Inventory().AllItems()[i];
                            if (item.toString().equals(Item.Names(0).replaceAll(" ", "").toLowerCase()) || item.toString().equals(Item.Names(1).replaceAll(" ", "").toLowerCase())) {
                                CraftSystem.Craft(Item);
                                return;
                            }
                        }

                        return;
                    case 4:
                        Engine.Println(this.Player().Inventory().toString());
                        break;
                    case 5:
                        Engine.Println(this.Player().toString());
                        break;
                    default:
                        Engine.Println("Такой комманды не существует.\nЕсли вы не знаете комманды то их можно прочитать в \"Помощь -> Комманды\"");
                }
            } catch (NullPointerException var10) {
            }

        }
    }

    private void Use(Item item) {
        switch(item.Type()) {
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
