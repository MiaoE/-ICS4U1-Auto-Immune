package com.summative.game.list;

import java.util.Iterator;

/**
 * [List.java]
 * This is a dynamic linked list data structure.
 *
 * @param <E> type
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class List<E> implements Iterable<E> {
    private Node<E> head;

    int size;

    /**
     * add
     * Adds an item to the list
     *
     * @param item the item
     */
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

    /**
     * get
     * Gets the item from a specified index
     *
     * @param index the index
     * @return the item
     */
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

    /**
     * indexOf
     * Gets the index of an item
     *
     * @param item the item
     * @return -1 if the item is not in the list, otherwise the index of the item
     */
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

    /**
     * remove
     * Removes an item from specified index
     *
     * @param index the index
     */
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

    /**
     * remove
     * Removes an item from the list
     *
     * @param item the item
     * @return true if the item is found and removed, false otherwise
     */
    public boolean remove(E item) {
        int index = indexOf(item);
        if (index == -1) {
            return false;
        }

        remove(index);
        return true;
    }

    /**
     * clear
     * Clears the list
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * size
     * Gets the size of the list
     *
     * @return the size
     */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0 || head == null);
    }

    /**
     * iterator
     * This method is used to have a functional for-each loop
     *
     * @return an Iterator object
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * ListIterator subclass
     * This is a subclass used by {@link List#iterator()}
     */
    class ListIterator implements Iterator<E> {

        private int index = 0;

        /**
         * hasNext
         * Checks if there is an item after the current item
         *
         * @return true if there is an item after, false otherwise
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * next
         * Gets the next item
         *
         * @return the next item
         */
        @Override
        public E next() {
            return get(index++);
        }

        /**
         * remove
         * Must be overridden to build
         */
        @Override
        public void remove(){}
    }

    /**
     * Node subclass
     * A Node is only used by {@code List} class, so it is an inner class.
     * @param <T> the type
     */
    class Node<T> {

        private T item;
        private Node<T> next;

        /**
         * Node Constructor
         * A constructor for a node of the list, only contains the item
         *
         * @param item the item
         */
        public Node(T item) {
            this.item = item;
            next = null;
        }

        /**
         * getNext
         * Gets the next Node
         *
         * @return next Node object
         */
        public Node<T> getNext() {
            return next;
        }

        /**
         * setNext
         * Sets the next Node
         *
         * @param next the next Node object
         */
        public void setNext(Node<T> next) {
            this.next = next;
        }

        /**
         * getItem
         * Gets the item from this node
         *
         * @return the item
         */
        public T getItem() {
            return item;
        }
    }
}