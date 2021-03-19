//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.fight;

import com.uweeldteam.Engine;
import com.uweeldteam.Main;
import com.uweeldteam.Engine.GameState;
import com.uweeldteam.game.Game;
import com.uweeldteam.game.mob.Mob;
import com.uweeldteam.game.mob.Mob.MobType;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.Player.Stats;
import com.uweeldteam.game.player.inventory.Slot;
import java.util.ArrayList;
import java.util.Arrays;
import uweellibs.Random;

public class Fight {
    static Mob enemy;
    static int energy;

    public static ArrayList<Mob> AllMobs() {
        return new ArrayList(Arrays.asList(Mob.values()));
    }

    private static void EnemyAttack() {
        if (enemy == null) {
            throw new IllegalStateException("Enemy not be null!");
        } else {
            float damage = 0.0F;
            if (enemy.getType() == MobType.enemy) {
                damage = BlockPlayer(Random.Range(Player().FirstWeapon().Damage()), enemy);
            }

            Stats var10000 = Player().Stats();
            var10000.health -= damage;
            if (damage == 0.0F) {
                Engine.Println(enemy.Name(0) + " не атаковал вас.");
            } else if (damage < 5.0F) {
                Engine.Println(enemy.Name(0) + " почти не нанёс вам увечий.");
            } else if (damage < 10.0F) {
                Engine.Println(enemy.Name(0) + " нанёс вам лёгкие увечия.");
            } else if (damage < 25.0F) {
                Engine.Println(enemy.Name(0) + " нанёс вам увечия.");
            } else {
                Engine.Println(enemy.Name(0) + " нанёс вам сильные увечия.");
            }

            if (Player().Stats().health <= 0.0F) {
                Main.Engine().Game().gameState = GameState.death;
                Player().Death();
            } else {
                String var1 = Player().Stats().name;
                Engine.Println("Здоровье " + var1 + String.format(" %.2f", Player().Stats().health));
            }
        }
    }

    private static void PlayerAttack() {
        --energy;
        float damage = BlockEnemy(Random.Range(enemy.Damage()));
        enemy.Health(enemy.Health() - damage);
        if (damage < 5.0F) {
            Engine.Println("Вы не нанесли " + enemy.Name(2).toLowerCase() + " особых увечий.");
        } else if (damage < 10.0F) {
            Engine.Println("Вы нанесли " + enemy.Name(2).toLowerCase() + " лёгкие увечия.");
        } else if (damage < 25.0F) {
            Engine.Println("Вы нанесли " + enemy.Name(2).toLowerCase() + " увечия.");
        } else {
            Engine.Println("Вы нанесли " + enemy.Name(2).toLowerCase() + " сильные увечия.");
        }

        if (!(enemy.Health() <= 0.0F)) {
            String var10000 = String.format("%.2f", enemy.Health());
            Engine.Println("Здоровье противника: " + var10000 + "Энергия: " + energy);
            EnemyAttack();
        } else {
            Engine.Println("Поздравляем, вы победили " + enemy.Name(1).toLowerCase());

            for(int i = 0; i < enemy.Drop().length; ++i) {
                Player().Inventory().AddItem(new Slot(enemy.Drop()[i], 1));
            }

            Main.Engine().Game().gameState = GameState.normal;
        }
    }

    public static Player Player() {
        return Main.Engine().Game().Player();
    }

    public Fight() {
        try {
            new Fight((Mob)AllMobs().get(Random.Range(0, AllMobs().size())));
        } catch (IndexOutOfBoundsException var2) {
            new Fight();
        }

    }

    public Fight(Mob mob) {
        Game.canAction = false;
        energy = 15;
        enemy = mob;
        if (enemy == null) {
            throw new IllegalStateException("Enemy is not be null!");
        } else {
            enemy.Health(mob.StandardHealth());
            String var10000 = enemy.Name(0).toLowerCase();
            Engine.Println("Противник: " + var10000 + "\nЗдоровье - " + String.format("%.2f", enemy.Health()));
            this.Help();
            Main.Engine().Game().gameState = GameState.fight;
        }
    }

    public static void ReadCommand(String text) {
        byte var2 = -1;
        switch(text.hashCode()) {
            case -1194162970:
                if (text.equals("прикрыться")) {
                    var2 = 1;
                }
                break;
            case 595967530:
                if (text.equals("атаковать")) {
                    var2 = 0;
                }
        }

        switch(var2) {
            case 0:
                if (energy >= 1) {
                    PlayerAttack();
                } else {
                    Engine.Println("У вас недостаточно энергии.");
                }
                break;
            case 1:
                Block();
            default:
                Engine.Println("Такой команды не существует");
        }

    }

    private static void Block() {
        if (Random.Percent(36)) {
            Engine.Println("Вы успешно прикрылись от удара противника.");
            energy += 4;
        } else {
            Engine.Println("Вам не удалось прикрыться от удара противника.");
            energy += 3;
            EnemyAttack();
        }

    }

    private void Help() {
        Engine.Println("Команды:\nАтаковать - атака противника основным/запасным оружием на выбор.\nБлокировать - заблокировать удар противника и восстановить немного сил.\nЛечиться - использовать один из расходников в руках или подсумке.");
    }

    private static float BlockPlayer(float damage, Mob mob) {
        float block = Random.Range(mob.Defence());
        return damage - block > 0.0F ? damage - block : 0.0F;
    }

    private static float BlockEnemy(float damage) {
        float block = damage - (Random.Range(Player().Pants().Defence()) + Random.Range(Player().Torso().Defence()));
        return damage - block > 0.0F ? damage - block : 0.0F;
    }
}
