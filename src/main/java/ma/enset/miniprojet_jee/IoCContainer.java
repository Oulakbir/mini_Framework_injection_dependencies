package ma.enset.miniprojet_jee;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class IoCContainer {
    private Map<Class<?>, Object> objectMap = new HashMap<>();

    public void addObject(Class<?> clazz, Object obj) {
        objectMap.put(clazz, obj);
    }

    public <T> T getObject(Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (!objectMap.containsKey(clazz)) {
            // Si la classe n'est pas encore instanciée, on l'instancie maintenant
            T object = createObject(clazz);
            objectMap.put(clazz, object);
        }
        return (T) objectMap.get(clazz);
    }

    private <T> T createObject(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                int i = 0;
                for (Class<?> parameterType : parameterTypes) {
                    parameters[i++] = getObject(parameterType);
                }
                return (T) constructor.newInstance(parameters);
            }
        }
        // Si aucun constructeur n'a l'annotation @Inject, on essaie le constructeur par défaut
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void doInjection(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object fieldObj = null;
                try {
                    fieldObj = getObject(field.getType());
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                field.set(obj, fieldObj);
            }
        }
        // Injecter les méthodes annotées si nécessaire
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Inject.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                int i = 0;
                for (Class<?> parameterType : parameterTypes) {
                    try {
                        parameters[i++] = getObject(parameterType);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    method.invoke(obj, parameters);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

