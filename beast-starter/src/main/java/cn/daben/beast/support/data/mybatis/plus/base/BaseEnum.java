package cn.daben.beast.support.data.mybatis.plus.base;

/**
 * @author Justubborn
 * @since 2023/2/24
 */
public interface BaseEnum<T> {
    T getValue();

    /**
     * 枚举描述
     *
     * @return 枚举描述
     */
    String getDescription();

    /**
     * 颜色
     *
     * @return 颜色
     */
    default String getColor() {
        return null;
    }
}
