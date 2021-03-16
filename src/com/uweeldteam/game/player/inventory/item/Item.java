package com.uweeldteam.game.player.inventory.item;

import com.uweeldteam.game.player.inventory.*;
import com.uweeldteam.game.player.inventory.craftsystem.Craft;
import uweellibs.Range;

import java.util.ArrayList;
import java.util.Arrays;

public enum Item {
    nullItem(4.4f, 0, "Пусто", "Отсутствует"),
    bottle(0.1f, 4, "Бутылка", "Бутылку"),
    bottledWater(1.1f,  4, new Range(0, 0), new Range(1,5), new Range(0,0), new Range(0, 2), new Craft(bottle), "Бутылка с водой", "Бутылку с водой"),
    wood(4.4f, 4, "Бревно", "Бревно"),
    ironBar(4.4f, 1, "Железный слиток", "Железный слиток"),
    redIronStone(0.1f, 1, ironBar, "Красный железняк", "Красный железняк"),
    ironOre(1, 12, ironBar, "Железная руда", "Железную руду"),
    ironPickaxe(4.6f, new Range(10, 50), new Craft(wood, ironBar), "Железная кирка", "Железную кирку"),
    ironAxe(4.4f, new Range(10, 40), new Craft(wood, ironBar), "Железный топор", "Железный топор"),
    smallAllowance(2.1f, 2, new Range(10, 40), new Range(10, 40), new Range(0, 2), new Range(0, 1), "Маленький паёк", "Маленький паёк"),
    backpack(4f, 1f, (byte) 5, (short) 25, "Рюкзак", "Рюкзак"),
    ironKnife(1.2f, new Range(5, 17), "Нож", "Нож");

    ArrayList<String> names = new ArrayList<>();
    ItemType type;
    float mass = 0;
    private float maxMass = 0;
    short maxStack = 1;
    private Craft craft = new Craft();
    private Range food = new Range(0, 0);
    private Range satiety = new Range(0, 0);
    private Range water = new Range(0, 0);
    private Range drunkenness = new Range(0, 0);
    ArrayList<Slot> slots = new ArrayList<>();
    OreType oreType;
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
    Item(float mass, int maxStack, Craft craft, String... names) {
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
    Item(float mass, Range damage, Craft craft, String... names) {
        Type(ItemType.weapon);
        Mass(mass);
        Damage(damage);
        Names(names);
        Craft(craft);
    }
    //backpack
    Item(float mass, float Range, byte slots, short maxMass, String... names) {
        Type(ItemType.backpack);
        MaxStack((short) 1);
        Mass(mass);
        Defence(defence);
        Slots(slots);
        MaxMass(maxMass);
        Names(names);
    }
    //backpack with craft
    Item(float mass, Range defence, byte slots, short maxMass, Craft craft, String... names) {
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
    Item(float mass, int maxStack, Range food, Range water, Range satiety, Range drunkenness, Craft craft, String... names) {
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
    Item(float barPercent, int maxMass, Item bar, String... names) {
        Type(ItemType.ore);
        BarPercent(barPercent);
        MaxMass((short) maxMass);
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

    public OreType OreType() {
        return this.oreType;
    }
    public void OreType(OreType oreType) {
        this.oreType = oreType;
    }

    public Item[] Craft() {
        return craft.Items();
    }
    private void Craft(Craft craft) {
        this.craft = craft;
    }

    public float AllMass() {
        float result = 0;
        for (int i = 0; i < Slots().size(); i++) {
            result += Slots().get(i).getAllMass();
        }
        return result;
    }
    void MaxMass(int maxMass) {
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
}
