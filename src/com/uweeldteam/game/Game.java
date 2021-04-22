package com.uweeldteam.game;

import com.uweeldteam.Engine;
import com.uweeldteam.Engine.GameState;
import com.uweeldteam.ExceptionOccurred;
import com.uweeldteam.game.fight.Fight;
import com.uweeldteam.game.quest.Dialog;
import com.uweeldteam.game.quest.Quest;
import com.uweeldteam.game.quest.QuestPart;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.CraftSystem;
import com.uweeldteam.game.player.inventory.item.Item;
import com.uweeldteam.game.player.inventory.item.Item.ItemType;
import org.jetbrains.annotations.NotNull;
import uweellibs.*;

import java.util.ArrayList;

public class Game extends MonoBehaviour {

    public static boolean canAction = true;

    public GameState gameState;
    public Fight fight;
    Player player;

    public synchronized void Init() {
        Debug.Log("Game start initializing");
        this.gameState = GameState.normal;
        this.Player(new Player());
        canAction = false;
        for(int i = 0; i < Acts.first.quests[0].QuestParts().length; i++)
        {
            if(Engine.ReadEnter()) {
                continue;
            }
            Engine.Println(Acts.first.quests[0].QuestParts()[i].Part().Text());
        }
        Debug.Log("Game initialized");
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
            ArrayList<String> messages = new ArrayList<>();
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
                ReadCommand(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ReadCommand(ArrayList<String> messages) {
        if (this.Player() == null) {
            new ExceptionOccurred(new IllegalStateException("Player is null"));
        } else {
            try {
                switch (messages.get(0)) {
                    case "получить":
                        Slot got = StringToItem(messages);
                        if (got == null) {
                            Engine.Println("Такого предмета нет.");
                            break;
                        }
                        Get(got);
                        break;
                    case "скрафтить":
                        for (int i = 0; i < Player().Inventory().AllItems().length; ++i) {
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
                    case "использовать": {
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
                    }
                    case "экипировать": {
                        StringBuilder itemName = new StringBuilder();
                        for (int messageID = 1; messageID < messages.size(); messageID++)
                            itemName.append(messages.get(messageID));
                        for (int i = 0; i < Player().Inventory().AllItems().length; i++) {
                            Item item = Player().Inventory().AllItems()[i];
                            if (itemName.toString().equals(item.Names(0).replaceAll(" ", "").toLowerCase()) || itemName.toString().equals(item.Names(1).replaceAll(" ", "").toLowerCase())) {
                                if (Player().Inventory().Contains(item)) {
                                    Equip(item);
                                } else {
                                    Engine.Println("У вас нет такого предмета.");
                                }
                                Console.Println(item, itemName, Player().Inventory().Contains(item));
                                return;
                            }
                        }
                    }
                    break;
                    default:
                        Engine.Println("Такой комманды не существует.");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                new ExceptionOccurred(new Exception("Ошибка при чтении команды: " + messages.toString()));
            }

        }
    }

    private void Get(Item item, int value) {
        Get(new Slot(item, value));
    }

    private void Get(Slot slot) {
        Player().Inventory().AddItem(slot, "Вы получили ");
    }

    private void Use(Item item) {
        if (item.Type() == ItemType.food)
            this.Eat(item);
        else
            Engine.Println("Этот предмет нельзя использовать");
    }

    private void Equip(Item item) {
        Item returned = Player().Equip(item);
        Player().Inventory().AddItem(new Slot(returned, 1), "Вы скрафтили ");
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

    public Slot StringToItem(ArrayList<String> messages) {
        for (int itemId = 0, value = 1; itemId < this.Player().Inventory().AllItems().length; itemId++) {
            StringBuilder itemName = new StringBuilder();

            for (int messageId = 1; messageId < messages.size(); messageId++) {
                try {
                    value = Integer.parseInt(messages.get(messageId));
                } catch (Exception objectIsString) {
                    itemName.append(messages.get(messageId));
                }
            }
            Item item = this.Player().Inventory().AllItems()[itemId];
            if (itemName.toString().equals(item.Names(0).replaceAll(" ", "").toLowerCase()) || itemName.toString().equals(item.Names(1).replaceAll(" ", "").toLowerCase())) {
                return new Slot(item, value);
            }
            Console.Println(item, itemName);
        }
        return null;
    }

    public static class Acts {

        public static Act first = new Act(new Quest[]{
                new Quest(new QuestPart[]{
                        new QuestPart(new Dialog() {
                            public final String text = "Сознание постепенно возвращалось ко мне, голова болела настолько сильно, что казалось будто она сейчас взорвётся.";
                            public String Text() {
                                return text;
                            } }) { },
                        new QuestPart(new Dialog() {
                            public final String text = "Губы были сухие, казалось что я очень много выпил вчера.";
                            public String Text() {
                                return text;
                            } }) { } }),
                new Quest(new QuestPart[]{
                        new QuestPart(new Dialog() {
                            public final String text = "Начало";
                            public String Text() {
                                return text;
                            } }) { } })
        });
        public static Act second = new Act(new Quest[]{new Quest(new QuestPart[]{new QuestPart(new Dialog() {public final String text = "Начало";public String Text() { return text; }}) { }})}) {
        };
    }

    static class Act {
        public Quest[] quests;

        public Act(Quest[] quests) {
            this.quests = quests;
        }
    }
}
