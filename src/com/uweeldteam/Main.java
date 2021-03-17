package com.uweeldteam;

import uweellibs.*;

//TODO: 14.02.2021 {
//  Система инвентаря (Лут в вещах, вес, спряжения) #12.03.21
//  Система крафтов; #11.03.21
//  Система предметов; #11.03.21
//  Система обновления и инициализации; #14.02.21
//  Игрок #11.03.21;
//  Мобы;
//  Система боёв;
//  NPC;
//  Сюжетная линия;
//  Игровые ивенты;
//}

public class Main extends MonoBehaviour {
    public static Engine engine;

    public Main(String[] args) {
        try {
            if(args[0] == "newGame")
                engine = new Engine(true);
        } catch (IndexOutOfBoundsException ignored) {}
        engine = new Engine(false);
    }

    public static void main(String[] args) {
        new Main(args);
    }
    public static Engine Engine(){
        return engine;
    }
}
