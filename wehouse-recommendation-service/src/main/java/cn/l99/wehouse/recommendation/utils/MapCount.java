package cn.l99.wehouse.recommendation.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 词语工具类，统计词频
 * @param <T>
 */
public class MapCount<T> {
    private HashMap<T, Integer> hm = null;

    public MapCount() {
        this.hm = new HashMap();
    }

    public MapCount(int initialCapacity) {
        this.hm = new HashMap(initialCapacity);
    }

    public void add(T t, int n) {
        Integer integer = null;
        if ((integer = (Integer) this.hm.get(t)) != null) {
            this.hm.put(t, Integer.valueOf(integer.intValue() + n));
        } else {
            this.hm.put(t, Integer.valueOf(n));
        }
    }

    public void add(T t) {
        this.add(t, 1);
    }

    public int size() {
        return this.hm.size();
    }

    public void remove(T t) {
        this.hm.remove(t);
    }

    public HashMap<T, Integer> get() {
        return this.hm;
    }

    public String getDic() {
        Iterator iterator = this.hm.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        Map.Entry next = null;

        while (iterator.hasNext()) {
            next = (Map.Entry) iterator.next();
            sb.append(next.getKey());
            sb.append("\t");
            sb.append(next.getValue());
            sb.append("\n");
        }

        return sb.toString();
    }

}
