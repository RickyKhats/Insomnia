//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.player.inventory.craftsystem;

import com.uweeldteam.game.player.inventory.item.Item;

public class Craft {
    Item[] items;

    public Craft(Item... items) {
        this.items = items;
    }

    public Item[] Items() {
        return this.items;
    }
}
