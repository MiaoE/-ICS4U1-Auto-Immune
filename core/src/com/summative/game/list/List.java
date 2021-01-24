package com.summative.game.list;

import java.util.Iterator;

public class List<E> implements Iterable<E> {
    private Node<E> head;

    int size;

    public void add(E item) {
        size++;
        if (head == null) {
            head = new Node<>(item);
            return;
        }

        Node<E> tempNode = head;
        while (tempNode.getNext() != null) {
            tempNode = tempNode.getNext();
        }
        tempNode.setNext(new Node<>(item));//, tempNode));
    }

    public E get(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        Node<E> tempNode = head;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.getNext();
        }
        return tempNode.getItem();
    }

    public int indexOf(E item) {
        Node<E> tempNode = head;
        for (int i = 0; i < size; i++) {
            if (!tempNode.getItem().equals(item)) {
                tempNode = tempNode.getNext();
            } else {
                return i;
            }
        }
        return -1;
    }

    public void remove(int index) {
        if (index >= size || index < 0) {
            return;
        }

        size--;
        if (index == 0) {
            head = head.getNext();
            return;
        }

        Node<E> tempNode = head;
        for (int i = 0; i < index - 1; i++) {
            tempNode = tempNode.getNext();
        }
        tempNode.setNext(tempNode.getNext().getNext());
    }

    public boolean remove(E item) {
        int index = indexOf(item);
        if (index == -1) {
            return false;
        }

        remove(index);
        return true;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0 || head == null);
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    class ListIterator implements Iterator<E> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            return get(index++);
        }
    }

    class Node<T> {
        private T item;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
            next = null;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getItem() {
            return item;
        }
    }
}