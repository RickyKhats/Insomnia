package com.uweeldteam.game;

import com.uweeldteam.game.fight.Fight;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Inventory;
import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.CraftSystem;
import com.uweeldteam.game.player.inventory.item.Item;
import com.uweeldteam.game.player.inventory.item.ItemType;
import uweellibs.*;

import java.util.ArrayList;

import static com.uweeldteam.game.player.inventory.item.Item.*;

public class Game extends MonoBehaviour {
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

    void Eat(Item food) {
        if (food.Type() != ItemType.food)
            throw new IllegalArgumentException("Item is not food!");
        Player().Stats().food -= Random.Range(food.Food());
        Player().Stats().water -= Random.Range(food.Water());
        Player().Stats().satiety -= Random.Range(food.Satiety());
        Player().Stats().drunkenness -= Random.Range(food.Drunkenness());
        if (Player().Stats().food < 0)
            Player().Stats().food = 0;
        if (Player().Stats().water < 0)
            Player().Stats().water = 0;
        if (Player().Stats().satiety > 5)
            Player().Stats().satiety = 5;
        if (Player().Stats().satiety > 3)
            Player().Stats().satiety = 3;
    }

    public void PostUpdate() {
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
        switch (messages.get(0)) {
            case "охота":
                new Fight();
                break;
            case "профиль":
                Console.Println(Player().toString());
                break;
            case "инвентарь":
                Console.Println(
                        "Инвентарь:",
                        "Руки(" + Player().AllHandsMass() + "/" + Player().MaxHandsMass() + "):",
                        Player().Inventory().toString(Player().Hands()),
                        Player().Inventory().toString(Player().Backpack()),
                        Player().Inventory().toString(Player().Pouch()),
                        Player().Inventory().toString(Player().Pouch()),
                        Player().Inventory().toString(Player().Pouch()));
                break;
            case "получить":
                switch (messages.get(1)) {
                    case "бутылку":
                        Get(bottle, messages, 2);
                        break;
                    case "древесину":
                        Get(wood, messages, 2);
                        break;
                    default:
                        try {
                            switch (messages.get(1) + " " + messages.get(2)) {
                                case "железный топор":
                                    Get(ironAxe, messages, 3);
                                    break;
                                case "маленький паёк":
                                    Get(smallAllowance, messages, 3);
                                    break;
                                case "железную кирку":
                                    Get(ironPickaxe, messages, 3);
                                    break;
                                case "железный слиток":
                                    Get(ironBar, messages, 3);
                                    break;
                                default:
                                    Console.Println("Такого предмета не существует.");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            Console.Println("Такого предмета не существует.");
                        }
                }
                break;
            case "скрафтить":

                try {
                    switch (messages.get(1)) {
                        case "":
                            break;
                        default:
                            switch (messages.get(1) + " " + messages.get(2)) {
                                case "железный топор":
                                    CraftSystem.Craft(ironAxe);
                                    break;
                                case "железную кирку":
                                    CraftSystem.Craft(ironPickaxe);
                                    break;
                                case "бутелированную воду":
                                    CraftSystem.Craft(bottledWater);
                                    break;
                                default:
                                    Console.Println("Такого предмета не существует");
                            }
                    }
                } catch (IndexOutOfBoundsException ignored) {
                    Console.Println("Такого предмета не существует");
                }
                break;
            case "использовать":
                switch (messages.get(1)) {
                    default:
                        switch (messages.get(1) + " " + messages.get(1)) {
                            case "маленький паёк":
                                if (Player().Inventory().Contains(smallAllowance)) {
                                    Player().Inventory().DeleteItems(smallAllowance);
                                    Eat(smallAllowance);
                                    Console.Println("Вы съели маленький паёк");
                                }
                                else
                                    Console.Println("У вас нет маленького пайка");
                                break;
                            case "бутелированную воду":
                                if (Player().Inventory().Contains(bottledWater)) {
                                    Player().Inventory().DeleteItems(bottledWater);
                                    Eat(bottledWater);
                                    Console.Println("Вы выпили бутылку воды");
                                }
                                else
                                    Console.Println("У вас нет бутылки воды");
                                break;
                        }
                }
            default:
                throw new IllegalStateException("Unexpected value: " + messages.get(0));
        }
        Save();
    }

    private void Get(Item item, ArrayList<String> messages, int id) {
        try {
            Player().Inventory().AddItem(new Slot(item, Integer.parseInt(messages.get(id))));
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            Player().Inventory().AddItem(new Slot(item, 1));
        }
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
