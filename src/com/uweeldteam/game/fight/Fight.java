package com.uweeldteam.game.fight;

import com.uweeldteam.Main;
import com.uweeldteam.game.Game;
import com.uweeldteam.game.mob.Mob;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.Slot;
import uweellibs.Console;
import uweellibs.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Fight {
    static Mob enemy;
    static int energy;

    public static ArrayList<Mob> AllMobs() {
        return new ArrayList<>(Arrays.asList(Mob.values()));
    }

    private static void EnemyAttack() {
        if (enemy == null)
            throw new IllegalStateException("Enemy not be null!");
        float damage = 0;
        if (enemy.damage != null) {
            damage = BlockPlayer(Random.Range(Player().FirstWeapon().Damage()), enemy);

        }

        Player().Stats().health -= damage;

        if (damage == 0) {
            Console.Println(enemy.names[0] + " не атаковал вас.");
        } else if (damage < 5) {
            Console.Println(enemy.names[0] + " почти не нанёс вам увечий.");
        } else if (damage < 10) {
            Console.Println(enemy.names[0] + " нанёс вам лёгкие увечия.");
        } else if (damage < 25) {
            Console.Println(enemy.names[0] + " нанёс вам увечия.");
        } else {
            Console.Println(enemy.names[0] + " нанёс вам сильные увечия.");
        }
        if (Player().Stats().health <= 0) {
            Player().Death();
            return;
        }
        Console.Println("Здоровье " + Player().Stats().name
                + String.format(" %.2f", Player().Stats().health));
        ReadCommand();
    }

    private static void PlayerAttack() {
        energy -= 1;
        float damage = BlockEnemy(Random.Range(enemy.damage));
        enemy.health -= damage;
        if (damage < 5)
            Console.Println("Вы не нанесли " + enemy.names[2].toLowerCase() + " особых увечий.");
        else if (damage < 10)
            Console.Println("Вы нанесли " + enemy.names[2].toLowerCase() + " лёгкие увечия.");
        else if (damage < 25)
            Console.Println("Вы нанесли " + enemy.names[2].toLowerCase() + " увечия.");
        else
            Console.Println("Вы нанесли " + enemy.names[2].toLowerCase() + " сильные увечия.");
        if (enemy.health <= 0) {
            Console.Println("Поздравляем, вы победили " + enemy.names[1].toLowerCase());
            for (int i = 0; i < enemy.drop.length; i++)
                Player().Inventory().AddItem(new Slot(enemy.drop[i], 1));
            Game.canAction = true;
            Main.Engine().Game().ReadCommand();
            return;
        }
        Console.Println("Здоровье противника: " +
                String.format("%.2f", enemy.health) +
                "Энергия: " + energy);
        EnemyAttack();
    }

    public static Player Player() {
        return Main.Engine().Game().Player();
    }

    public void Fight() {
        try {
            Fight(AllMobs().get(Random.Range(0, AllMobs().size())));
        } catch (IndexOutOfBoundsException ignored) {
            Fight();
        }
    }

    public void Fight(Mob mob) {
        Game.canAction = false;
        energy = 15;
        enemy = mob;
        if (enemy == null)
            throw new IllegalStateException("Enemy is not be null!");
        enemy.health = mob.standardHealth;
        Console.Println("Противник: " + enemy.names[0].toLowerCase()
                + "\nЗдоровье - " + String.format("%.2f", enemy.health));
        Help();
        ReadCommand();
    }

    private static void ReadCommand() {
        switch (Console.Read().toLowerCase()) {
            case "атаковать":
                if (energy >= 1)
                    PlayerAttack();
                else {
                    Console.Println("У вас недостаточно энергии.");
                    ReadCommand();
                }
                break;
            case "прикрыться":
                Block();
            default:
                Console.Println("Такой команды не существует");
                ReadCommand();
        }
    }

    private static void Block() {
        if (Random.Percent(36)) {
            Console.Println("Вы успешно прикрылись от удара противника.");
            energy += 4;
            ReadCommand();
        } else {
            Console.Println("Вам не удалось прикрыться от удара противника.");
            energy += 3;
            EnemyAttack();
        }
    }

    private void Help() {
        Console.Println("Команды:" +
                "\nАтаковать - атака противника основным/запасным оружием на выбор." +
                "\nБлокировать - заблокировать удар противника и восстановить немного сил." +
                "\nЛечиться - использовать один из расходников в руках или подсумке.");
    }

    private static float BlockPlayer(float damage, Mob mob) {
        float block = Random.Range(mob.defence);
        return ((damage - block > 0) ? damage - block : 0);
    }

    private static float BlockEnemy(float damage) {
        float block = damage - (Random.Range(Player().Pants().Defence()) + Random.Range(Player().Torso().Defence()));
        return (damage - block > 0) ? damage - block : 0;
    }
}
