//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam.game.player.inventory.item;

import com.uweeldteam.game.player.inventory.Slot;
import com.uweeldteam.game.player.inventory.craftsystem.Craft;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import uweellibs.Range;

public enum Item {
    nullItem(4.4F, 0, new String[]{"Пусто", "Отсутствует"}),
    bottle(0.1F, 4, new String[]{"Бутылка", "Бутылку"}),
    bottledWater(1.1F, 4, new Range(0, 0), new Range(1, 5), new Range(0, 0), new Range(0, 2), new ArrayList(Collections.singletonList(new Craft(new Item[]{bottle}))), new String[]{"Бутылка с водой", "Бутылку с водой"}),
    wool(0.1F, 4, new String[]{"Шерсть", "Шерсть"}),
    cotton(0.1F, 4, new String[]{"Хлопок", "Хлопок"}),
    fabric(0.1F, 4, new ArrayList(List.of(new Craft(new Item[]{wool}), new Craft(new Item[]{cotton, cotton}))), new String[]{"Ткань", "Ткань"}),
    wood(4.4F, 4, new String[]{"Бревно", "Бревно"}),
    ironBar(4.4F, 1, new String[]{"Железный слиток", "Железный слиток"}),
    redIronStone(70.0F, 0.5F, 3.0F, ironBar, new String[]{"Красный железняк", "Красный железняк"}),
    brownIronStone(35.0F, 0.035F, 3.0F, ironBar, new String[]{"Бурый железняк", "Бурый железняк"}),
    magnetIronStone(31.03F, 0.05F, 3.0F, ironBar, new String[]{"Магнетит", "Магнетит"}),
    ironPickaxe(4.6F, new Range(10, 50), new ArrayList(Collections.singletonList(new Craft(new Item[]{wood, ironBar}))), new String[]{"Железная кирка", "Железную кирку"}),
    ironAxe(4.4F, new Range(10, 40), new ArrayList(Collections.singletonList(new Craft(new Item[]{wood, ironBar}))), new String[]{"Железный топор", "Железный топор"}),
    smallAllowance(2.1F, 2, new Range(10, 40), new Range(10, 40), new Range(0, 2), new Range(0, 1), new String[]{"Маленький паёк", "Маленький паёк"}),
    schoolBackpack(4.0F, new Range(0, 1), (byte)5, (short)25, new String[]{"Школьный рюкзак", "Школьный рюкзак"}),
    fabricBackpack(4.0F, new Range(0, 2), (byte)7, (short)30, new ArrayList(Collections.singletonList(new Craft(new Item[]{fabric}))), new String[]{"Тканевый рюкзак", "Тканевый рюкзак"}),
    ironKnife(1.2F, new Range(5, 17), new String[]{"Нож", "Нож"});

    ArrayList<String> names = new ArrayList();
    Item.ItemType type;
    float mass = 0.0F;
    private float maxMass = 0.0F;
    short maxStack = 1;
    ArrayList<Craft> craft = new ArrayList();
    private Range food = new Range(0, 0);
    private Range satiety = new Range(0, 0);
    private Range water = new Range(0, 0);
    private Range drunkenness = new Range(0, 0);
    ArrayList<Slot> slots = new ArrayList();
    Item bar;
    Range damage = new Range(1, 6);
    Range defence = new Range(1, 5);
    private float barPercent;

    private Item(float mass, int maxStack, String... names) {
        this.Type(Item.ItemType.item);
        this.Names(names);
        this.Mass(mass);
        this.MaxStack((short)maxStack);
    }

    private Item(float mass, int maxStack, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.item);
        this.Names(names);
        this.Mass(mass);
        this.MaxStack((short)maxStack);
        this.Craft(craft);
    }

    private Item(float mass, Range damage, String... names) {
        this.Type(Item.ItemType.weapon);
        this.Mass(mass);
        this.Damage(damage);
        this.Names(names);
    }

    private Item(float mass, Range damage, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.weapon);
        this.Mass(mass);
        this.Damage(damage);
        this.Names(names);
        this.Craft(craft);
    }

    private Item(float mass, Range defence, byte slots, short maxMass, String... names) {
        this.Type(Item.ItemType.backpack);
        this.MaxStack((short)1);
        this.Mass(mass);
        this.Defence(defence);
        this.Slots(slots);
        this.MaxMass((float)maxMass);
        this.Names(names);
    }

    private Item(float mass, Range defence, byte slots, short maxMass, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.backpack);
        this.MaxStack((short)1);
        this.Mass(mass);
        this.Defence(defence);
        this.Slots(slots);
        this.MaxMass((float)maxMass);
        this.Names(names);
        this.Craft(craft);
    }

    private Item(float mass, int maxStack, Range food, Range water, Range satiety, Range drunkenness, String... names) {
        this.Type(Item.ItemType.food);
        this.Mass(mass);
        this.Food(food);
        this.Satiety(satiety);
        this.Water(water);
        this.Drunkenness(drunkenness);
        this.MaxStack((short)maxStack);
        this.Names(names);
    }

    private Item(float mass, int maxStack, Range food, Range water, Range satiety, Range drunkenness, ArrayList<Craft> craft, String... names) {
        this.Type(Item.ItemType.food);
        this.Mass(mass);
        this.Food(food);
        this.Satiety(satiety);
        this.Water(water);
        this.Craft(craft);
        this.Drunkenness(drunkenness);
        this.MaxStack((short)maxStack);
        this.Names(names);
    }

    private Item(float barPercent, float mass, float maxMass, Item bar, String... names) {
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
        for(int i = 0; i < slots; ++i) {
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
        return this.Type().equals(Item.ItemType.ore) ? (short)((int)(this.maxMass / this.mass)) : this.maxStack;
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
        return (String)this.names.get(id);
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
        return ((Craft)this.craft.get(id)).Items();
    }

    public ArrayList<Craft> Craft() {
        return this.craft;
    }

    private void Craft(ArrayList<Craft> craft) {
        this.craft = craft;
    }

    public float AllMass() {
        float result = 0.0F;

        for(int i = 0; i < this.Slots().size(); ++i) {
            result += ((Slot)this.Slots().get(i)).getAllMass();
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

    public static enum ItemType {
        backpack,
        weapon,
        food,
        item,
        armorBackpack,
        armor,
        pouch,
        firstWeapon,
        secondWeapon,
        ore;

        private ItemType() {
        }
    }
}
