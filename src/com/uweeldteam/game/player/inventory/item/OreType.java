package com.uweeldteam.game.player.inventory.item;

import com.uweeldteam.Main;
import com.uweeldteam.game.player.Player;
import com.uweeldteam.game.player.inventory.craftsystem.CraftSystem;
import uweellibs.Console;

public enum OreType {
    redIronStone,
    brownIronStone,
    magnetIronStone,
    ironSpar;

    public void OreToBar(Item ore) {
        if (ore.OreType() == null) {
            throw new IllegalArgumentException("Item is not ore!");
        }
        else{
            if(CraftSystem.canCraft(ore)){
                Console.Println("Craft");
            } else {
                Console.Println("Not enough resources!");
            }
        }
    }

    public Player Player(){
        return Main.Engine().Game().Player();
    }

}
