package cn.daben.beast.base;


import cn.daben.beast.model.query.PageQuery;
import cn.daben.beast.model.query.SortQuery;
import cn.daben.beast.model.resp.PageResp;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 基础封装-业务数据操作
 *
 * @author Justubborn
 * @since 2022/5/12
 */
public interface BaseService<T> extends IService<T> {
    /**
     * 分页查询列表
     *
     * @param entity 实体类
     * @param query  查询条件
     * @return 分页列表信息
     */
    <Q extends PageQuery> PageResp<T> page(T entity, Q query);


    /**
     * 查询列表
     *
     * @param query 查询条件
     * @return 列表信息
     */
    <Q extends SortQuery> List<T> list(T entity, Q query);

    /**
     * 查询详情
     *
     * @param id ID
     * @return 详情信息
     */
    T get(Long id);

    /**
     * 查询字典列表
     *
     * @param query 查询条件
     * @return 字典列表信息
     * @since 2.1.0
     */
    <Q extends SortQuery> List<LabelValueResp> listDict(Q query);

    /**
     * 修改
     *
     * @param req 修改信息
     */
    void update(T req);

    /**
     * 删除
     *
     * @param ids ID 列表
     */
    void delete(List<Long> ids);


    /**
     * 查询树列表
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @param isSimple  是否为简单树结构（不包含基本树结构之外的扩展字段）
     * @return 树列表信息
     */
    List<Tree<Long>> tree(T query, SortQuery sortQuery, boolean isSimple);
}
