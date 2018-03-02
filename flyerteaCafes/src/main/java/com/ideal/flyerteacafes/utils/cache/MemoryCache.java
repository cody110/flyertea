package com.ideal.flyerteacafes.utils.cache;

/**
 * Created by fly on 2018/2/6.
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by daiyiming on 2016/12/10.
 * 本地内存缓存
 */
public final class MemoryCache<KO, VO> {

    public static final int CAPACITY_DEFAULT = 10; // 默认缓存
    public static final int CAPACITY_INFINITY = -1; // 无限缓存
    public static final int MAX_CAPACITY_DEFAULT = 20; // 默认最大缓存
    public static final int MAX_CAPACITY_INFINITY = -1; // 无限最大缓存

    private final LinkedList<ValueHolder<KO, VO>> mCacheList; // 缓存
    private final HashSet<KO> mKeySet; // 键缓存
    private volatile int mCapacity; // 容量
    private volatile int mMaxCapacity; // 最大容量
    private volatile boolean mAllowUpdate; // 键值冲突时是否准许更新

    /**
     * 键值对组合类
     *
     * @param <KI> 键类型
     * @param <VI> 值类型
     */
    private static final class ValueHolder<KI, VI> {
        private final KI mKey; // 键不准许修改，若修改则用添加新的对象
        private VI mValue; // 值可以修改，用于更新

        private ValueHolder(KI key, VI value) {
            mKey = key;
            mValue = value;
        }

        /**
         * 更新值对象
         *
         * @param value 新的值对象
         */
        private void update(VI value) {
            recycleValue();
            mValue = value;
        }

        /**
         * 回收Holder
         */
        private void recycle() {
            recycleKey();
            recycleValue();
        }

        private void recycleKey() {
            if (mKey instanceof IRecycleInterface) {
                ((IRecycleInterface) mKey).recycle();
            }
        }

        private void recycleValue() {
            if (mValue instanceof IRecycleInterface) {
                ((IRecycleInterface) mValue).recycle();
            }
        }

    }

    /**
     * 值接口，实现帮助对象键值内存回收
     */
    public interface IRecycleInterface {
        void recycle();
    }

    public MemoryCache() {
        this(CAPACITY_DEFAULT, MAX_CAPACITY_DEFAULT);
    }

    public MemoryCache(int capacity, int maxCapacity) {
        if (capacity < CAPACITY_INFINITY
                || maxCapacity < MAX_CAPACITY_INFINITY) {
            throw new IllegalArgumentException("MemoryCache：构造函数参数错误");
        }
        mCacheList = new LinkedList<>();
        mKeySet = new HashSet<>();
        mCapacity = capacity;
        mMaxCapacity = maxCapacity;
        mAllowUpdate = false;
    }

    public synchronized void put(KO key, VO value) {
        if (key == null || value == null) {
            return;
        }
        if (mKeySet.contains(key)) { // 如果已经存在则复用对象
            ListIterator<ValueHolder<KO, VO>> iterator = mCacheList.listIterator();
            while (iterator.hasNext()) {
                ValueHolder<KO, VO> holder = iterator.next();
                if (holder.mKey.equals(key)) {
                    holder.update(value);
                    // 如果不准许更新则删除重新添加
                    if (!mAllowUpdate && iterator.previousIndex() != 0) {
                        iterator.remove();
                        mCacheList.addFirst(holder);
                    }
                    break;
                }
            }
        } else { // 不存在则添加
            mKeySet.add(key);
            mCacheList.addFirst(new ValueHolder<>(key, value));
        }
        // 如果大于最大容量且不是无限容量则清除末尾一个
        if (mMaxCapacity != MAX_CAPACITY_INFINITY
                && mCacheList.size() > mMaxCapacity) {
            remove(mCacheList.size() - 1);
        }
    }

    public synchronized VO get(KO key) {
        if (mKeySet.contains(key)) {
            ListIterator<ValueHolder<KO, VO>> iterator = mCacheList.listIterator();
            while (iterator.hasNext()) {
                ValueHolder<KO, VO> holder = iterator.next();
                if (holder.mKey.equals(key)) { // 找到
                    if (iterator.previousIndex() != 0) { // 如果不是在头部就移动到头部
                        iterator.remove();
                        mCacheList.addFirst(holder);
                    }
                    // 删除一个超出容量的数据
                    if (mCapacity != CAPACITY_INFINITY
                            && mCacheList.size() > mCapacity) {
                        remove(mCacheList.size() - 1);
                    }
                    return holder.mValue;
                }
            }
        }
        return null;
    }

    public synchronized boolean contains(KO k) {
        return mKeySet.contains(k);
    }

    public synchronized void clear() {
        mKeySet.clear();
        for (ValueHolder<KO, VO> holder : mCacheList) {
            holder.recycle();
        }
        mCacheList.clear();
    }

    public synchronized int size() {
        return mCacheList.size();
    }

    public synchronized void remove(KO key) {
        if (mKeySet.contains(key)) {
            ListIterator<ValueHolder<KO, VO>> iterator = mCacheList.listIterator();
            while (iterator.hasNext()) {
                ValueHolder<KO, VO> holder = iterator.next();
                if (holder.mKey.equals(key)) {
                    iterator.remove();
                    mKeySet.remove(holder.mKey);
                    holder.recycle();
                    break;
                }
            }
        }
    }

    public synchronized void remove(int position) {
        if (position >= 0 && position < size()) {
            ValueHolder<KO, VO> removedHolder = mCacheList.remove(position);
            mKeySet.remove(removedHolder.mKey);
            removedHolder.recycle();
        }
    }

    public void setCapacity(int capacity) {
        mCapacity = capacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        mMaxCapacity = maxCapacity;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public int getMaxCapacity() {
        return mMaxCapacity;
    }

    /**
     * 准许更新
     * 决定添加过程中键值重复时直接原位置更新还是删除重新添加
     *
     * @param allowUpdate 是否准许更新
     */
    public void allowUpdate(boolean allowUpdate) {
        mAllowUpdate = allowUpdate;
    }

}
