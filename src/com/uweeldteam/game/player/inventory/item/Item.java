//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.player.inventory.item;

import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.Craft;
import uweellibs.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Item {
    nullItem(4.4F, 0, "Пусто", "Отсутствует"),
    bottle(0.1F, 4, "Бутылка", "Бутылку"),
    bottledWater(1.1F, 4, new Range(0, 0), new Range(1, 5), new Range(0, 0), new Range(0, 2), new ArrayList<>(Collections.singletonList(new Craft(bottle))), "Бутылка с водой", "Бутылку с водой"),
    wool(0.1F, 4, "Шерсть", "Шерсть"),
    cotton(0.1F, 4, "Хлопок", "Хлопок"),
    fabric(0.1F, 4, new ArrayList<>(List.of(new Craft(wool), new Craft(cotton, cotton))), "Ткань", "Ткань"),
    wood(4.4F, 4, "Бревно", "Бревно"),
    ironBar(4.4F, 1, "Железный слиток", "Железный слиток"),
    redIronStone(70.0F, 0.5F, 3.0F, ironBar, "Красный железняк", "Красный железняк"),
    brownIronStone(35.0F, 0.035F, 3.0F, ironBar, "Бурый железняк", "Бурый железняк"),
    magnetIronStone(31.03F, 0.05F, 3.0F, ironBar, "Магнетит", "Магнетит"),
    ironPickaxe(4.6F, new Range(10, 50), new ArrayList<>(Collections.singletonList(new Craft(wood, ironBar))), "Железная кирка", "Железную кирку"),
    ironAxe(4.4F, new Range(10, 40), new ArrayList<>(Collections.singletonList(new Craft(wood, ironBar))), "Железный топор", "Железный топор"),
    smallAllowance(2.1F, 2, new Range(10, 40), new Range(10, 40), new Range(0, 2), new Range(0, 1), "Маленький паёк", "Маленький паёк"),
    schoolBackpack(4.0F, new Range(0, 1), (byte) 5, (short) 25, "Школьный рюкзак", "Школьный рюкзак"),
    fabricBackpack(4.0F, new Range(0, 2), (byte) 7, (short) 30, new ArrayList<>(Collections.singletonList(new Craft(fabric))), "Тканевый рюкзак", "Тканевый рюкзак"),
    ironKnife(1.2F, new Range(5, 17), "Нож", "Нож");

    ArrayList<String> names = new ArrayList<>();
    Item.ItemType type;
    float mass = 0.0F;
    short maxStack = 1;
    ArrayList<Craft> craft = new ArrayList<>();
    ArrayList<Slot> slots = new ArrayList<>();
    Item bar;
    Range damage = new Range(1, 6);
    Range defence = new Range(1, 5);
    private float maxMass = 0.0F;
    private Range food = new Range(0, 0);
    private Range satiety = new Range(0, 0);
    private Range water = new Range(0, 0);
    private Range drunkenness = new Range(0, 0);
    private float barPercent;

    Item(float mass, int maxStack, String... names) {
        this.Type(Item.ItemType.item);
        this.Names(names);
        this.Mass(mass);
        this.MaxStack((short) maxStack);
    }

    Item(float mass, int maxStack, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.item);
        this.Names(names);
        this.Mass(mass);
        this.MaxStack((short) maxStack);
        this.Craft(craft);
    }

    Item(float mass, Range damage, String... names) {
        this.Type(Item.ItemType.weapon);
        this.Mass(mass);
        this.Damage(damage);
        this.Names(names);
    }

    Item(float mass, Range damage, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.weapon);
        this.Mass(mass);
        this.Damage(damage);
        this.Names(names);
        this.Craft(craft);
    }

    Item(float mass, Range defence, byte slots, short maxMass, String... names) {
        this.Type(Item.ItemType.backpack);
        this.MaxStack((short) 1);
        this.Mass(mass);
        this.Defence(defence);
        this.Slots(slots);
        this.MaxMass(maxMass);
        this.Names(names);
    }

    Item(float mass, Range defence, byte slots, short maxMass, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.backpack);
        this.MaxStack((short) 1);
        this.Mass(mass);
        this.Defence(defence);
        this.Slots(slots);
        this.MaxMass(maxMass);
        this.Names(names);
        this.Craft(craft);
    }

    Item(float mass, int maxStack, Range food, Range water, Range satiety, Range drunkenness, String... names) {
        this.Type(Item.ItemType.food);
        this.Mass(mass);
        this.Food(food);
        this.Satiety(satiety);
        this.Water(water);
        this.Drunkenness(drunkenness);
        this.MaxStack((short) maxStack);
        this.Names(names);
    }

    Item(float mass, int maxStack, Range food, Range water, Range satiety, Range drunkenness, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.food);
        this.Mass(mass);
        this.Food(food);
        this.Satiety(satiety);
        this.Water(water);
        this.Craft(craft);
        this.Drunkenness(drunkenness);
        this.MaxStack((short) maxStack);
        this.Names(names);
    }

    Item(float barPercent, float mass, float maxMass, Item bar, String... names) {
        this.Type(Item.ItemType.ore);
        this.BarPercent(barPercent);
        this.Mass(mass);
        this.MaxMass(maxMass);
        this.Bar(bar);
        this.Names(names);
    }

    public Item Bar() {
        return this.bar;
    }

    private void Bar(Item bar) {
        this.bar = bar;
    }

    private void Water(Range water) {
        this.water = water;
    }

    public Range Water() {
        return this.water;
    }

    private void Food(Range food) {
        this.food = food;
    }

    public Range Food() {
        return this.food;
    }

    private void Slots(ArrayList<Slot> slots) {
        this.slots.addAll(slots);
    }

    void Slots(byte slots) {
        for (int i = 0; i < slots; ++i) {
            this.slots.add(new Slot());
        }

    }

    public ArrayList<Slot> Slots() {
        return this.slots;
    }

    void Damage(Range damage) {
        this.damage = damage;
    }

    public Range Damage() {
        return this.damage;
    }

    void Mass(float mass) {
        this.mass = mass;
    }

    public float Mass() {
        return this.mass;
    }

    public short MaxStack() {
        return this.Type().equals(Item.ItemType.ore) ? (short) ((int) (this.maxMass / this.mass)) : this.maxStack;
    }

    void MaxStack(short maxStack) {
        this.maxStack = maxStack;
    }

    public Range Defence() {
        return this.defence;
    }

    void Defence(Range defence) {
        this.defence = defence;
    }

    public String Names(int id) {
        return this.names.get(id);
    }

    void Names(String[] names) {
        this.names.addAll(Arrays.asList(names));
    }

    public Item.ItemType Type() {
        return this.type == null ? Item.ItemType.item : this.type;
    }

    void Type(Item.ItemType type) {
        this.type = type;
    }

    public Item[] Craft(int id) {
        return this.craft.get(id).Items();
    }

    public ArrayList<Craft> Craft() {
        return this.craft;
    }

    private void Craft(ArrayList<Craft> craft) {
        this.craft = craft;
    }

    public float AllMass() {
        float result = 0.0F;

        for (int i = 0; i < this.Slots().size(); ++i) {
            result += this.Slots().get(i).getAllMass();
        }

        return result;
    }

    void MaxMass(float maxMass) {
        this.maxMass = maxMass;
    }

    public float MaxMass() {
        return this.maxMass;
    }

    public float BarPercent() {
        return this.barPercent;
    }

    private void BarPercent(float barPercent) {
        this.barPercent = barPercent;
    }

    public Range Drunkenness() {
        return this.drunkenness;
    }

    public void Drunkenness(Range drunkenness) {
        this.drunkenness = drunkenness;
    }

    public Range Satiety() {
        return this.satiety;
    }

    public void Satiety(Range satiety) {
        this.satiety = satiety;
    }

    public enum ItemType {
        backpack,
        weapon,
        food,
        item,
        torso,
        pants,
        armorBackpack,
        pouch,
        firstWeapon,
        secondWeapon,
        ore
    }
}
