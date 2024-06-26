package cn.daben.beast.base;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 基础服务抽象类
 *
 * @param <T>
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
    /**
     * 新增前置处理
     *
     * @param entity 创建信息
     */
    protected void beforeAdd(T entity) {
        /* 新增前置处理 */
    }

    /**
     * 修改前置处理
     *
     * @param entity 修改信息
     */
    protected void beforeUpdate(T entity) {
        /* 修改前置处理 */
    }

    /**
     * 删除前置处理
     *
     * @param ids ID 列表
     */
    protected void beforeDelete(List<Long> ids) {
        /* 删除前置处理 */
    }

    /**
     * 新增后置处理
     *
     * @param param    创建信息
     * @param entity 实体信息
     */
    protected void afterAdd(T param, T entity) {
        /* 新增后置处理 */
    }

    /**
     * 修改后置处理
     *
     * @param param    修改信息
     * @param entity 实体信息
     */
    protected void afterUpdate(T param, T entity) {
        /* 修改后置处理 */
    }


    /**
     * 删除后置处理
     *
     * @param ids ID 列表
     */
    protected void afterDelete(List<Long> ids) {
        /* 删除后置处理 */
    }

}
