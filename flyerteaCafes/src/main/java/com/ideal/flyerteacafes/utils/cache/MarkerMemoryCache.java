package com.ideal.flyerteacafes.utils.cache;

import android.graphics.Bitmap;

import com.amap.api.maps2d.model.BitmapDescriptor;

/**
 * Created by daiyiming on 2016/12/11.
 * 缓存执行类
 */
public final class MarkerMemoryCache {

    private static MemoryCache<Key, Value> sLocalCache = new MemoryCache<>();

    private static void confirmEnable() {
        if (sLocalCache == null) {
            sLocalCache = new MemoryCache<>();
        }
    }

    public static void put(Key key, Value value) {
        confirmEnable();
        sLocalCache.put(key, value);
    }

    public static BitmapDescriptor get(Key key) {
        confirmEnable();
        Value value = sLocalCache.get(key);
        if (value != null) {
            return value.mBitmapDescriptor;
        }
        return null;
    }

    public static Key generateKey(int kind, String styleDetail, String styleId) {
        return new Key(kind, styleDetail == null ? "" : styleDetail, styleId == null ? "" : styleId);
    }

    public static Value generateValue(BitmapDescriptor bitmapDescriptor) {
        return new Value(bitmapDescriptor);
    }

    public static void clear() {
        sLocalCache.clear();
    }

    public static final class Key {

        private final int mKind;
        private final String mStyleDetail;
        private final String mStyleId;
        private final int mHashCode;

        private Key(int kind, String styleDetail, String styleId) {
            mKind = kind;
            mStyleDetail = styleDetail;
            mStyleId = styleId;
            mHashCode = generateHashCode();
        }

        private int generateHashCode() {
            int result = 17;
            result = 31 * result + mKind;
            result = 31 * result + mStyleDetail.hashCode();
            result = 31 * result + mStyleId.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Key) {
                if (object == this) {
                    return true;
                }
                Key key = (Key) object;
                return mKind == key.mKind
                        && mStyleDetail.equals(key.mStyleDetail)
                        && mStyleId.equals(key.mStyleId);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return mHashCode;
        }

    }

    public static final class Value implements MemoryCache.IRecycleInterface {

        private BitmapDescriptor mBitmapDescriptor = null;

        private Value(BitmapDescriptor bitmapDescriptor) {
            mBitmapDescriptor = bitmapDescriptor;
        }

        @Override
        public void recycle() {
            if (mBitmapDescriptor != null) {
                Bitmap bitmap = mBitmapDescriptor.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

}

