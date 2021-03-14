package com.uweeldteam.game.player.inventory;

import com.uweeldteam.game.player.inventory.item.Item;

public class Slot {
    private Item item = Item.nullItem;
    private short value = 0;

    public Slot(Item item, int value) {
        Item(item);
        Value((short) value);
    }

    public Slot() {

    }

    public float getAllMass(){
        return item.Mass() * value;
    }

    public void Value(short value){
        this.value = value;
    }
    public short Value(){
        return value;
    }
    public Item Item(){
        return item;
    }
    public void Item(Item item){
        this.item = item;
    }
    public Slot Add(Slot slot){
        if(slot.Value() < 0)
            throw new IllegalArgumentException();

        Slot result = new Slot(slot.Item(), (short) 0);
        if(Item() != slot.Item() && Item() != Item.nullItem)
            throw new IllegalArgumentException();
        else
            Item(slot.Item());
        if(Value() + slot.Value() > Item().MaxStack()) {
            result.Value((short) (Value() + slot.Value() - Item().MaxStack()));
            Value(Item().MaxStack());
        }
        else {
            Value((short) (Value() + slot.Value()));
            result.Value((short) 0
            );
        }
        return result;
    }
    public void Remove(Slot slot){
        value -= slot.Value();
        if(value < 1)
            item = Item.nullItem;
        if (value < 0)
            throw new IllegalStateException("Value is not correct!");
    }
    public String toString(){
        if(item != null)
        return Item().Names(0) + (Value() > 1 ? " X" + Value() : "");
        return "Пусто";
    }
}
