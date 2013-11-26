package com.fitnotif.util;

import java.util.Enumeration;
import java.util.Iterator;

public final class IterableEnumeration<E> implements Iterable<E> {

    private Enumeration<E> enumeration;

    public static <E> IterableEnumeration<E> get(
            final Enumeration<E> enumeration) {
        return new IterableEnumeration<E>(enumeration);
    }

    private IterableEnumeration(final Enumeration<E> enumeration) {
        this.enumeration = enumeration;
    }

    public Iterator<E> iterator() {
        if (this.enumeration == null) {
            throw new Error("Se puede iterar una sola vez");
        }

        final Enumeration<E> enumeration = this.enumeration;
        this.enumeration = null;

        return new Iterator<E>() {
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            public E next() {
                return enumeration.nextElement();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}
