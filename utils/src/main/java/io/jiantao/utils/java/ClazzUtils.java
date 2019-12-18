package io.jiantao.utils.java;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by jiantaoyang on 2018/11/7.
 */
public class ClazzUtils {
    /**
     * 处理泛型类Type，配合Gson类库解析泛型实体。
     * @param raw 原class类型
     * @param args 原class的泛型类型
     * @return
     */
    public static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
