package com.fitnotif.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.WordUtils;

public final class Servicios {

    private static final Map<Class<?>, Collection<Class<?>>> SERVICES_CACHE =
            new HashMap<Class<?>, Collection<Class<?>>>();

    private Servicios() {
    }

    /**
     * Obtiene el nombre real del archivo referenciado.
     * 
     * @param file
     *            Path del archivo que se está buscando.
     * 
     * @return String con el path real.
     */
    public static String getResource(String path) {
        path = Thread.currentThread().getContextClassLoader().getResource(path).
                toString();
        path = path.replaceFirst("file:", "");
        path = path.replaceFirst("target/[^/]+/", "src/main/resources/");

        return path;
    }

    /**
     * Abre el navegador por defecto en varias plataformas. Usa:
     * 
     * <ul>
     * <li>MacOS X: <code>open URL</code></li>
     * <li>Windows: <code>rundll32 url.dll,FileProtocolHandler URL</code></li>
     * <li>Otros: <code>xdg-open URL</code></li>
     * </ul>
     * 
     * @param url
     *            Url a ser abierto
     */
    public static void abrirNavegador(String url) {
        String osName = System.getProperty("os.name");
        try {
            if (osName.startsWith("Mac OS")) {
                Runtime.getRuntime().exec(new String[] { "open", url });
            } else if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec(
                        new String[] { "rundll32",
                            "url.dll,FileProtocolHandler", url });
            } else {
                Runtime.getRuntime().exec(new String[] { "xdg-open", url });
            }
        } catch (IOException e) {
            //Debug.error(e);
        }
    }

    /**
     * Método que carga clases que extienden o implementan &lt;S&gt; imitando
     * los Services de java 6.
     * 
     * @param <S>
     *            Tipo de clases
     * @param clase
     *            Clase base de la que se buscan instancias.
     * 
     * @return Un Iterable de &lt;S&gt;
     */
    public static synchronized <S> Collection<Class<?>> loadClasses(
            Class<S> clase) {
        Collection<Class<?>> services = SERVICES_CACHE.get(clase);

        if (services != null && !services.isEmpty()) {
            return services;
        }

        services = new HashSet<Class<?>>();
        SERVICES_CACHE.put(clase, services);

        try {
            ClassLoader classLoader = Thread.currentThread().
                    getContextClassLoader();
            Iterable<URL> serviceURLs = IterableEnumeration.get(classLoader.
                    getResources("META-INF/services/" + clase.getName()));            

            for (URL url : serviceURLs) {
                InputStream is = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is,
                        "UTF-8"));

                String line = null;
                while ((line = r.readLine()) != null) {
                    int comment = line.indexOf('#');
                    if (comment >= 0) {
                        line = line.substring(0, comment);
                    }
                    String className = line.trim();
                    if (className.length() == 0) {
                        continue;
                    }
                    try {
                        Class<?> subClass = Class.forName(className, true,
                                classLoader);

                        if (!subClass.isInterface() && !Modifier.isAbstract(
                                subClass.getModifiers())) {
                            services.add(subClass.asSubclass(clase));
                        }
                    } catch (ClassNotFoundException e) {
                        //Debug.error(e);
                    }
                }
                is.close();
            }
        } catch (IOException e) {
            //Debug.error(e);
        }

        return services;
    }

    /**
     * Método que carga objetos del tipo &lt;S&gt; imitando los Services de java
     * 6.
     * 
     * @param <S>
     *            Tipo de clases
     * @param ifc
     *            Clase base de la que se buscan instancias.
     * 
     * @return Un Iterable de &lt;S&gt;
     */
    @SuppressWarnings("unchecked")
    public static <S> Collection<S> load(Class<S> ifc) {
        Collection<S> services = new ArrayList<S>();
        for (Class<?> aClass : loadClasses(ifc)) {
            try {
                Constructor<?> ctor = aClass.getConstructor();
                services.add((S) ctor.newInstance());
            } catch (Exception ex) {
                /*Debug.error("No se pudo crear un elemento de la clase " + aClass.
                        getName(), ex);*/
            }
        }
        return services;
    }

    /**
     * Obtiene un field publico, protegido o privado de esta clase o de una
     * superclase.
     * 
     * @param clase
     *            Clase de la que se quiere obtener el field.
     * @param nombre
     *            Nombre del field.
     * 
     * @return El Field encontrado.
     * 
     * @throws NoSuchFieldException
     *             En caso de que no haya un field con ese nombre.
     */
    public static Field getField(Class<?> clase, String nombre)
            throws NoSuchFieldException {
        try {
            return clase.getDeclaredField(nombre);
        } catch (NoSuchFieldException e) {
            if (clase.getSuperclass() != null) {
                return getField(clase.getSuperclass(), nombre);
            } else {
                throw e;
            }
        } catch (SecurityException e) {
            throw new Error(e);
        }
    }

    /**
     * Obtiene una lista de fields publicos, protegidos o privados de la clase
     * especificada o superclases.
     * 
     * @param clase
     *            Clase de la que se quiere obtener los fields.
     * 
     * @return Collection de fields.
     */
    public static Collection<Field> getFields(Class<?> clase) {
        List<Field> fields = new LinkedList<Field>();

        fields.addAll(Arrays.asList(clase.getDeclaredFields()));

        if (clase.getSuperclass() != null) {
            fields.addAll(getFields(clase.getSuperclass()));
        }

        return fields;
    }

    /**
     * Obtiene el Class más adecuado tomandolo del Type.
     * 
     * @param type
     *            Type que puede ser genérico, con comodines, etc.
     * 
     * @return Class
     */
    public static Class<?> getClassFromType(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getClassFromType(((ParameterizedType) type).getRawType());
        } else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;

            if (wildcardType.getLowerBounds().length > 0) {
                return getClassFromType(wildcardType.getLowerBounds()[0]);
            } else if (wildcardType.getUpperBounds().length > 0) {
                return getClassFromType(wildcardType.getUpperBounds()[0]);
            }
        }

        return null;
    }

    /**
     * Determina si el type referencia a una clase simple (int, String, etc)
     * 
     * @param type
     *            Type a ser probado
     * 
     * @return true si el Type referencia a un tipo simple
     */
    public static boolean isSimpleType(Type type) {
        Class<?> clase = getClassFromType(type);

        return clase.isPrimitive() || String.class.isAssignableFrom(clase)
                || Enum.class.isAssignableFrom(clase)
                || Number.class.isAssignableFrom(clase)
                || Boolean.class.isAssignableFrom(clase);
    }

    /**
     * Obtiene el Class del Type genérico. Por ejemplo si el type es
     * <code>List&lt;? extends String&gt;</code> devuelve String.class.
     *
     * @param type
     *            Type a ser probado
     *
     * @return Class genérica
     */
    public static Class<?> getGenericType(Type type) {
        return getGenericType(type, 0);
    }

    /**
     * Obtiene el Class del Type genérico. Por ejemplo si el type es
     * <code>List&lt;? extends String&gt;</code> devuelve String.class.
     *
     * @param type
     *            Type a ser probado
     * @param pos
     *            Posicion del tipo
     *
     * @return Class genérica
     */
    public static Class<?> getGenericType(Type type, int pos) {
        if (type instanceof ParameterizedType) {
            return getClassFromType(((ParameterizedType) type).
                    getActualTypeArguments()[pos]);
        }

        return null;
    }

    /**
     * Obtiene el tipo de la propiedad del objeto.
     * 
     * @param o
     *            Objeto a ser probado
     * @param propiedad
     *            Propiedad del objeto
     * 
     * @return Type de esa propiedad
     */
    public static Type getType(Object o, String propiedad) {
        try {
            Method method = PropertyUtils.getPropertyDescriptor(o, propiedad).
                    getReadMethod();

            return method.getGenericReturnType();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * Convierte un string a un string con guiones. Ej:
     *
     * <pre>
     * toDashedString(&quot;A BB CCC&quot;).equals(&quot;a-bb-ccc&quot;)
     * toDashedString(&quot;String Largo&quot;).equals(&quot;string-largo&quot;)
     * toDashedString(&quot;CamelCase&quot;).equals(&quot;camel-case&quot;)
     * </pre>
     *
     * @param string
     *            String a ser convertido
     *
     * @return String con guiones
     */
    public static String toDashedString(String string) {
        return WordUtils.uncapitalize(string.replaceAll(" ", "")).replaceAll(
                "([A-Z0-9])", "-$1").toLowerCase();
    }

    /**
     * Convierte un string a un string con guiones. Ej:
     *
     * <pre>
     * toDashedString(&quot;A BB CCC&quot;).equals(&quot;A_BB_CCC&quot;)
     * toDashedString(&quot;String Largo&quot;).equals(&quot;STRING_LARGO&quot;)
     * toDashedString(&quot;CamelCase&quot;).equals(&quot;CAMEL_CASE&quot;)
     * </pre>
     *
     * @param string
     *            String a ser convertido
     *
     * @return String con guiones
     */
    public static String toUnderscoreString(String string) {
        return WordUtils.uncapitalize(string.replaceAll(" ", "")).replaceAll(
                "([A-Z0-9])", "_$1").toUpperCase();
    }

    /**
     * Convierte un string que tiene subguiones a un string normal. Ej:
     * 
     * <pre>
     * toDashedString(&quot;A_BB_CCC&quot;).equals(&quot;A Bb Ccc&quot;)
     * toDashedString(&quot;String_Largo&quot;).equals(&quot;String Largo&quot;)
     * toDashedString(&quot;CamelCase&quot;).equals(&quot;Camelcase&quot;)
     * </pre>
     *
     * @param string
     *            String a ser convertido
     * 
     * @return String con sin subguiones
     */
    public static String fromUnderscoreString(String string) {
        return WordUtils.capitalizeFully(string.replaceAll("_", " "));
    }

    /**
     * Genera un id único.
     *
     * @return String con el id
     */
    public static String generarIdUnicoTemporal() {
        // IMPORTANTE: Mantener sincronizado con Util.js
        String id = "_id_";

        id += (int) (Math.random() * 999);
        id += "_";
        id += (int) (Math.random() * 999);
        id += "_";
        id += (int) (Math.random() * 999);

        return id;
    }

    /**
     * Genera un id único.
     *
     * @return String con el id
     */
    public static String generarIdUnicoPermanente() {
        // IMPORTANTE: Mantener sincronizado con Util.js
        return "ID_" + generarIdUnicoTemporal();
    }

    public static int inside(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }

}
