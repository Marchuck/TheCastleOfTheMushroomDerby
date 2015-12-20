package pl.lukmarr.thecastleofthemushroomderby.xmlParser;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Łukasz Marczak
 *
 * @since 20.12.15
 */
public class ClassReflector {
    public static final String TAG = ClassReflector.class.getSimpleName();

    public static <T> void review(Class<T> tClass) {
        String thisClassName = tClass.getSimpleName();
        Log.d(TAG, "review class " + thisClassName);
        try {
            Field[] fields = tClass.getDeclaredFields();
            for (Field f : fields) {
                if (!f.getType().getName().equals("java.util.List")) {
                    Log.d(TAG, "new Field " + f.getName() + " of type " + shorten(f.getType().toString()));
                } else {
                    Type type = f.getGenericType();
                    if (type instanceof ParameterizedType) {
                        ParameterizedType pType = (ParameterizedType) type;
                        Type[] arr = pType.getActualTypeArguments();
                        for (Type tp : arr) {
                            Class<?> clzz = (Class<?>) tp;
                            String listElementType = shorten(clzz.getName());
                            Log.d(TAG, "new Field " + f.getName() + " of type List<" + listElementType + ">");
                            if (!((Class<?>) tp).getSimpleName().equals(thisClassName) && !isPrimitive(listElementType)) {
                                review(clzz);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean isPrimitive(String let) {
        return (let.equals("String") || let.equals("Integer") || let.equals("Double") || let.equals("Float") ||
                let.equals("Long") || let.equals("Boolean") || let.equals("Character"));
    }

    private static String shorten(String str) {
        String[] ss = str.split("\\.");
        if (ss.length == 0)
            return str;
        return ss[ss.length - 1];
    }
}
