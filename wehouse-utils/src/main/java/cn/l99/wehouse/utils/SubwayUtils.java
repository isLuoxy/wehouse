package cn.l99.wehouse.utils;

/**
 * 地铁工具类
 *
 * @author L99
 */
public class SubwayUtils {

    /**
     * 地铁名格式化
     * <code>
     * String name = 地铁1号线(堰桥-长广溪);
     * String newName = SubwayUtils.SubwayNameFormat(name);
     * </code>
     * 这个时候 newName = 地铁1号线
     *
     * @param oldName 原本名称
     * @return 格式化后的名称
     */
    public static String SubwayNameFormat(String oldName) {
        String prefix = "(";
        int prefixIndex = oldName.indexOf(prefix);
        String substring = oldName.substring(0, prefixIndex);
        return substring;
    }
}
