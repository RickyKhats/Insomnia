package com.uweeldteam.game.player.inventory.item;

import com.uweeldteam.game.player.inventory.*;
import com.uweeldteam.game.player.inventory.craftsystem.Craft;
import uweellibs.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Item {
    nullItem(4.4f, 0, "Пусто", "Отсутствует"),
    bottle(0.1f, 4, "Бутылка", "Бутылку"),
    bottledWater(1.1f,  4, new Range(0, 0), new Range(1,5), new Range(0,0), new Range(0, 2), new ArrayList<>(Collections.singletonList(new Craft(bottle))), "Бутылка с водой", "Бутылку с водой"),
    wool(0.1f, 4, "Шерсть", "Шерсть"),
    cotton(0.1f, 4, "Хлопок", "Хлопок"),
    fabric(0.1f, 4, new ArrayList<>(List.of(new Craft(wool), new Craft(cotton, cotton))), "Ткань", "Ткань"),
    wood(4.4f, 4, "Бревно", "Бревно"),
    ironBar(4.4f, 1, "Железный слиток", "Железный слиток"),
    redIronStone(70, 0.5f, 3, ironBar, "Красный железняк", "Красный железняк"),
    brownIronStone(35, 0.035f, 3, ironBar, "Бурый железняк", "Бурый железняк"),
    magnetIronStone(31.03f, 0.050f, 3, ironBar, "Магнетит", "Магнетит"),
    ironPickaxe(4.6f, new Range(10, 50), new ArrayList<>(Collections.singletonList(new Craft(wood, ironBar))), "Железная кирка", "Железную кирку"),
    ironAxe(4.4f, new Range(10, 40), new ArrayList<>(Collections.singletonList(new Craft(wood, ironBar))), "Железный топор", "Железный топор"),
    smallAllowance(2.1f, 2, new Range(10, 40), new Range(10, 40), new Range(0, 2), new Range(0, 1), "Маленький паёк", "Маленький паёк"),
    schoolBackpack(4f, new Range(0, 1), (byte) 5, (short) 25, "Школьный рюкзак", "Школьный рюкзак"),
    fabricBackpack(4f, new Range(0, 2), (byte) 7, (short) 30, new ArrayList<>(Collections.singletonList(new Craft(fabric))), "Тканевый рюкзак", "Тканевый рюкзак"),
    ironKnife(1.2f, new Range(5, 17), "Нож", "Нож");

    ArrayList<String> names = new ArrayList<>();
    ItemType type;
    float mass = 0;
    private float maxMass = 0;
    short maxStack = 1;
    ArrayList<Craft> craft = new ArrayList<>();
    private Range food = new Range(0, 0);
    private Range satiety = new Range(0, 0);
    private Range water = new Range(0, 0);
    private Range drunkenness = new Range(0, 0);
    ArrayList<Slot> slots = new ArrayList<>();
    Item bar;
    Range damage = new Range(1, 6);
    Range defence = new Range(1, 5);
    private float barPercent;

    //standard item
    Item(float mass, int maxStack, String... names) {
        Type(ItemType.item);
        Names(names);
        Mass(mass);
        MaxStack((short) maxStack);
    }
    //standard item with craft
    Item(float mass, int maxStack, ArrayList<Craft> craft, String... names) {
        Type(ItemType.item);
        Names(names);
        Mass(mass);
        MaxStack((short) maxStack);
        Craft(craft);
    }
    //weapon
    Item(float mass, Range damage, String... names) {
        Type(ItemType.weapon);
        Mass(mass);
        Damage(damage);
        Names(names);
    }
    //weapon with craft
    Item(float mass, Range damage, ArrayList<Craft> craft, String... names) {
        Type(ItemType.weapon);
        Mass(mass);
        Damage(damage);
        Names(names);
        Craft(craft);
    }
    //backpack
    Item(float mass, Range defence, byte slots, short maxMass, String... names) {
        Type(ItemType.backpack);
        MaxStack((short) 1);
        Mass(mass);
        Defence(defence);
        Slots(slots);
        MaxMass(maxMass);
        Names(names);
    }
    //backpack with craft
    Item(float mass, Range defence, byte slots, short maxMass, ArrayList<Craft> craft, String... names) {
        Type(ItemType.backpack);
        MaxStack((short) 1);
        Mass(mass);
        Defence(defence);
        Slots(slots);
        MaxMass(maxMass);
        Names(names);
        Craft(craft);
    }
    //food
    Item(float mass, int maxStack, Range food, Range water, Range satiety, Range drunkenness, String... names) {
        Type(ItemType.food);
        Mass(mass);
        Food(food);
        Satiety(satiety);
        Water(water);
        Drunkenness(drunkenness);
        MaxStack((short) maxStack);
        Names(names);
    }
    //food with craft
    Item(float mass, int maxStack, Range food, Range water, Range satiety, Range drunkenness, ArrayList<Craft> craft, String... names) {
        Type(ItemType.food);
        Mass(mass);
        Food(food);
        Satiety(satiety);
        Water(water);
        Craft(craft);
        Drunkenness(drunkenness);
        MaxStack((short) maxStack);
        Names(names);
    }
    //ore
    Item(float barPercent, float mass, float maxMass, Item bar, String... names) {
        Type(ItemType.ore);
        BarPercent(barPercent);
        Mass(mass);
        MaxMass(maxMass);
        Bar(bar);
        Names(names);
    }

    public Item Bar() {
        return bar;
    }

    private void Bar(Item bar) {
        this.bar = bar;
    }
    private void Water(Range water) {
        this.water = water;
    }

    public Range Water() {
        return water;
    }
    private void Food(Range food) {
        this.food = food;
    }

    public Range Food() {
        return food;
    }
    private void Slots(ArrayList<Slot> slots) {
        this.slots.addAll(slots);
    }

    void Slots(byte slots) {
        for (int i = 0; i < slots; i++)
            this.slots.add(new Slot());
    }
    public ArrayList<Slot> Slots() {
        return slots;
    }

    void Damage(Range damage) {
        this.damage = damage;
    }
    public Range Damage() {
        return damage;
    }

    void Mass(float mass) {
        this.mass = mass;
    }
    public float Mass() {
        return mass;
    }

    public short MaxStack() {
        if(Type().equals(ItemType.ore))
            return (short) (maxMass / mass);
        return maxStack;
    }
    void MaxStack(short maxStack) {
        this.maxStack = maxStack;
    }

    public Range Defence() {
        return defence;
    }
    void Defence(Range defence) {
        this.defence = defence;
    }

    public String Names(int id) {
        return names.get(id);
    }
    void Names(String[] names) {
        this.names.addAll(Arrays.asList(names));
    }

    public ItemType Type() {
        if (type == null)
            return ItemType.item;
        return type;
    }
    void Type(ItemType type) {
        this.type = type;
    }

    public Item[] Craft(int id) {
        return craft.get(id).Items();
    }
    public ArrayList<Craft> Craft() {
        return craft;
    }
    private void Craft(ArrayList<Craft> craft) {
        this.craft = craft;
    }

    public float AllMass() {
        float result = 0;
        for (int i = 0; i < Slots().size(); i++) {
            result += Slots().get(i).getAllMass();
        }
        return result;
    }
    void MaxMass(float maxMass) {
        this.maxMass = maxMass;
    }
    public float MaxMass() {
        return maxMass;
    }

    public float BarPercent() {
        return barPercent;
    }
    private void BarPercent(float barPercent) {
        this.barPercent = barPercent;
    }

    public Range Drunkenness() {
        return drunkenness;
    }
    public void Drunkenness(Range drunkenness) {
        this.drunkenness = drunkenness;
    }

    public Range Satiety() {
        return satiety;
    }
    public void Satiety(Range satiety) {
        this.satiety = satiety;
    }

    public enum ItemType {
        backpack, weapon, food, item, armorBackpack, armor, pouch, firstWeapon, secondWeapon, ore
    }
}
