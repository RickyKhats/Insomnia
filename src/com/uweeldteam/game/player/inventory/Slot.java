//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.player.inventory;

import com.uweeldteam.game.player.inventory.item.Item;

public class Slot {
    private Item item;
    private short value;

    public Slot(Item item, int value) {
        this.item = Item.nullItem;
        this.value = 0;
        this.Item(item);
        this.Value((short)value);
    }

    public Slot() {
        this.item = Item.nullItem;
        this.value = 0;
    }

    public float getAllMass() {
        return this.item.Mass() * (float)this.value;
    }

    public void Value(short value) {
        this.value = value;
    }

    public short Value() {
        return this.value;
    }

    public Item Item() {
        return this.item;
    }

    public void Item(Item item) {
        this.item = item;
    }

    public Slot Add(Slot slot) {
        if (slot.Value() < 0) {
            throw new IllegalArgumentException();
        } else {
            Slot result = new Slot(slot.Item(), 0);
            if (this.Item() != slot.Item() && this.Item() != Item.nullItem) {
                throw new IllegalArgumentException();
            } else {
                this.Item(slot.Item());
                if (this.Value() + slot.Value() > this.Item().MaxStack()) {
                    result.Value((short)(this.Value() + slot.Value() - this.Item().MaxStack()));
                    this.Value(this.Item().MaxStack());
                } else {
                    this.Value((short)(this.Value() + slot.Value()));
                    result.Value((short)0);
                }

                return result;
            }
        }
    }

    public void Remove(Slot slot) {
        this.value -= slot.Value();
        if (this.value < 1) {
            this.item = Item.nullItem;
        }

        if (this.value < 0) {
            throw new IllegalStateException("Value is not correct!");
        }
    }

    public String toString() {
        return Item().Names(0) + (this.Value() > 1 ? " X" + this.Value() : "");
    }
}
