package test;

import java.io.Serializable;

/**
 *
 * Created by Administrator on 2016/7/10.
 */
public class Walf extends Animal{
    Walf(String name, String sex) {
        super(name, sex);
        System.out.println("Walf 2");
    }
    Walf(String name) {
        System.out.println("Walf 1");
    }
    public static void main(String[] args) {
        new Walf("abc", "bcd");
        Animal animal1 = new Bird();
        Animal animal = new Walf("abc");
        System.out.println(animal instanceof Creature);
        System.out.println(animal1 instanceof Serializable);
    }
}

class Creature{
    Creature() {
        System.out.println("Creature 0");
    }
}

class Bird extends Animal{

}

class Animal extends Creature {
    Animal() {
        System.out.println("Animal 0");
    }
    Animal(String name) {
        System.out.println("Animal 1");
    }

    Animal(String name, String sex) {
        System.out.println("Animal 2");
    }
}
