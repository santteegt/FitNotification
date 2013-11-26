package com.fitnotif.dto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que clona objetos a profundidad. Clona objetos, no sus referencias.
 * 
 * @author FitBank 2.0
 * 
 * @param <T>
 *            Tipo de objeto a clonar
 */
public final class Clonador<T> {

    private Clonador() {
    }

    /**
     * Metodo para clonar objetos de cualquier Tipo.
     * 
     * @param t
     *            Objeto a ser clonado
     * 
     * @return Objeto clonado
     */
    public static <T> T clonar(T t) {
        if (t == null || t.getClass().isPrimitive() || t.getClass().isEnum()
                || t instanceof String || t instanceof Number
                || t instanceof Boolean) {
            return t;
        }

        if (t instanceof List && t.getClass().getPackage().getName().startsWith("java")) {
            return (T) clonarList((List) t);
        }

        return clonarSerializando(t);
    }

    private static <T> T clonarSerializando(T t) {
        T obj = null;

        try {
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(t);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
            obj = (T) in.readObject();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }

        return obj;
    }

    private static List clonarList(List t) {
        final List res;

        if (t.getClass() == ArrayList.class) {
            res = new ArrayList(t.size());
        } else {
            try {
                res = t.getClass().newInstance();
            } catch (InstantiationException e) {

                return clonarSerializando(t);
            } catch (IllegalAccessException e) {

                return clonarSerializando(t);
            }
        }

        for (Object o : t) {
            res.add(clonar(o));
        }

        return res;
    }

}
