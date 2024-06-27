package cn.daben.beast.support.data.mybatis.plus.datapermission;

/**
 * 数据权限过滤器接口
 *
 * @author Charles7c
 * @since 1.1.0
 */
public interface DataPermissionFilter {

    /**
     * 是否过滤
     *
     * @return true：过滤；false：不过滤
     */
    boolean isFilter();

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     */
    DataPermissionCurrentUser getCurrentUser();
}
