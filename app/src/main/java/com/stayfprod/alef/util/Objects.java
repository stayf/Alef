package com.stayfprod.alef.util;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;

public class Objects {
    public static boolean isNotBlank(final Object cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(final Object obj) {
        if (obj == null)
            return true;

        if (obj.getClass().isArray() && Array.getLength(obj) == 0)
            return true;

        if (obj instanceof CharSequence)
            return isBlank((CharSequence) obj);

        if (obj instanceof Collection && ((Collection) obj).isEmpty())
            return true;

        if (obj instanceof Map && ((Map) obj).isEmpty())
            return true;

        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty())
            return true;

        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0)
            return true;

        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0)
            return true;

        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0)
            return true;

        if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0)
            return true;

        return false;
    }

    private static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0)
            return true;
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(cs.charAt(i))))
                return false;
        }
        return true;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }
}
