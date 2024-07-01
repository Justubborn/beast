package cn.daben.beast.base;

import cn.daben.beast.constant.StringConst;
import cn.daben.beast.model.query.PageQuery;
import cn.daben.beast.model.query.SortQuery;
import cn.daben.beast.model.resp.PageResp;
import cn.daben.beast.toolkit.ReflectKit;
import cn.daben.beast.toolkit.StringKit;
import cn.daben.beast.toolkit.ValidationKit;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * 基础服务抽象类
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
    protected final List<Field> entityFields = ReflectKit.getNonStaticFields(this.entityClass);

    protected String[] getSearchParam() {
        return new String[0];
    }

    @Override
    public <Q extends PageQuery> PageResp<T> page(T entity, Q query) {
        QueryWrapper<T> wrapper = new QueryWrapper<T>().checkSqlInjection();
        paging(wrapper, query);
        sort(wrapper, query);
        IPage<T> page = super.page(query.toPage(), wrapper);
        return PageResp.build(page);
    }


    @Override
    public <Q extends SortQuery> List<T> list(T entity, Q query) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        // 设置排序
        sort(queryWrapper, query);
       List<T> list = baseMapper.selectList(queryWrapper);
        System.out.println(JSONUtil.toJsonStr(list));
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public T get(Long id) {
        return null;
    }

    @Override
    public <Q extends SortQuery> List<LabelValueResp> listDict(Q query) {
        return List.of();
    }

    @Override
    public boolean save(T entity) {
        beforeAdd(entity);
        boolean result = super.save(entity);
        afterAdd(entity);
        return result;
    }

    @Override
    public void update(T entity) {
        beforeUpdate(entity);
        baseMapper.updateById(entity);
        afterUpdate(entity);
    }

    @Override
    public void delete(List<Long> ids) {
        beforeDelete(ids);
        baseMapper.deleteBatchIds(ids);
        afterDelete(ids);
    }


    protected <Q extends PageQuery> void paging(QueryWrapper<T> queryWrapper, Q query) {
        if (StringKit.isNotEmpty(query.getParams()) && getSearchParam().length != 0) {
            queryWrapper.and(p -> {
                for (String string : getSearchParam()) {
                    p.or(a -> a.like(string, query.getParams()));
                }
            });
        }
    }

    /**
     * 设置排序
     *
     * @param queryWrapper 查询条件封装对象
     * @param sortQuery    排序查询条件
     */
    protected void sort(QueryWrapper<T> queryWrapper, SortQuery sortQuery) {
        Sort sort = sortQuery.buildSort();
        if (null == sort) {
            return;
        }
        for (Sort.Order order : sort) {
            if (null != order) {
                String property = order.getProperty();
                String checkProperty;
                // 携带表别名则获取 . 后面的字段名
                if (property.contains(StringConst.DOT)) {
                    checkProperty = CollUtil.getLast(List.of(property.split("\\.")));
                } else {
                    checkProperty = property;
                }
                Optional<Field> optional = entityFields.stream()
                        .filter(field -> checkProperty.equals(field.getName()))
                        .findFirst();
                ValidationKit.throwIf(optional.isEmpty(), "无效的排序字段 [{}]", property);
                queryWrapper.orderBy(true, order.isAscending(), property);
            }
        }
    }

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
     * @param entity 实体信息
     */
    protected void afterAdd(T entity) {
        /* 新增后置处理 */
    }

    /**
     * 修改后置处理
     *
     * @param entity 修改信息
     */
    protected void afterUpdate(T entity) {
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
