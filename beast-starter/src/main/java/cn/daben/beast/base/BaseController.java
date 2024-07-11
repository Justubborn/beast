package cn.daben.beast.base;

import cn.daben.beast.model.query.PageQuery;
import cn.daben.beast.model.query.SortQuery;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 基础控制层
 *
 * @author Justubborn
 * @since 2022/5/12
 */
@Getter
public abstract class BaseController< T extends BaseEntity> {

    @Autowired(required = false)
    protected BaseService<T> service;

    /**
     * 新增数据
     * @param entity 参数
     * @return 结果
     */
    @PostMapping(value = "/add")
    public Object add(T entity) {
        if (getService().save(entity)) {
            return Resp.ok(entity.getId());
        } else {
            return Resp.fail(entity.getId());
        }
    }

    /**
     * 删除数据
     * @param ids id列表
     */
    @PostMapping("/remove")
    public void remove(List<Long> ids) {
        getService().delete(ids);
    }

    /**
     * 查询详情
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/get")
    public Object get(Long id) {
        return Resp.ok(getService().getById(id));
    }

    /**
     * 修改数据
     * @param entity 参数
     * @return 结果
     */
    @PostMapping("/update")
    public Object update(T entity) {
        if (getService().updateById(entity)) {
            return Resp.ok(entity.getId());
        } else {
            return Resp.fail(entity.getId());
        }
    }

    /**
     * 查询数据列表
     * @param query 参数
     * @return 结果
     */
    @GetMapping("/list")
    public Object list(SortQuery query) {
        return Resp.ok(getService().list(null, query));
    }
    /**
     * 分页查询列表
     * @param query 参数
     * @return 结果
     */
    @GetMapping("/page")
    public Object page(@Validated T entity, PageQuery query) {
        return Resp.ok(getService().page(entity, query));
    }
}
