package com.pokewords.framework.learnings;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class SerializationTesing {

    private static abstract class Item implements Serializable {
        private int id;

        public Item(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            this.id = in.readInt();
            onSerializing(in);
        }

        protected abstract void onSerializing(ObjectInputStream in) throws IOException, ClassNotFoundException;

        private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException{
            out.writeInt(id);
            onDeserializing(out);
        }

        protected abstract void onDeserializing(ObjectOutputStream out) throws IOException, ClassNotFoundException;
    }

    private static class ItemA extends Item {
        private String token;

        public ItemA(int id, String token) {
            super(id);
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        @Override
        protected void onSerializing(ObjectInputStream in) throws IOException, ClassNotFoundException {
            System.out.println(ItemA.class.getSimpleName() + ":" + " readObject");
            this.token = (String) in.readObject();
        }

        @Override
        protected void onDeserializing(ObjectOutputStream out) throws IOException {
            System.out.println(ItemA.class.getSimpleName() + ":" + " writeObject");
            out.writeObject(token);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemA itemA = (ItemA) o;
            return Objects.equals(token, itemA.token);
        }

        @Override
        public int hashCode() {
            return Objects.hash(token);
        }
    }

    private static class ItemB extends Item {
        private String firstName;
        private String lastName;
        private String name;

        public ItemB(int id, String firstName, String lastName) {
            super(id);
            this.firstName = firstName;
            this.lastName = lastName;
            this.name = firstName + " " + lastName;
        }

        public String getName() {
            return name;
        }

        @Override
        protected void onSerializing(ObjectInputStream in) throws IOException {
            System.out.println(ItemB.class.getSimpleName() + ":" + " readObject");
            this.firstName = in.readUTF();
            this.lastName = in.readUTF();
            this.name = firstName + " " + lastName;
        }

        @Override
        protected void onDeserializing(ObjectOutputStream out) throws IOException {
            System.out.println(ItemB.class.getSimpleName() + ":" + " writeObject");
            out.writeUTF(firstName);
            out.writeUTF(lastName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemB itemB = (ItemB) o;
            return Objects.equals(firstName, itemB.firstName) &&
                    Objects.equals(lastName, itemB.lastName) &&
                    Objects.equals(name, itemB.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName, name);
        }
    }

    private static class Items implements Serializable {
        private ArrayList<Item> items = new ArrayList<>();

        public Items() {
            items.add(new ItemA(1, "First"));
            items.add(new ItemA(2, "Second"));
            items.add(new ItemA(3, null));
            items.add(new ItemB(4, "Johnny", "Pan"));
            items.add(new ItemB(5, "Joanna", "Zhang"));
            items.add(new ItemA(6, "Six"));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Items items1 = (Items) o;
            return Objects.equals(items, items1.items);
        }

        @Override
        public int hashCode() {
            return Objects.hash(items);
        }
    }

    @Test
    public void test() throws IOException, ClassNotFoundException {
        Items items = new Items();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(items);

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Items readItems = (Items) in.readObject();

        assertEquals(items, readItems);
    }
}
