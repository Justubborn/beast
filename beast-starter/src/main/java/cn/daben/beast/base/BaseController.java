package cn.daben.beast.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础控制层
 *
 * @author Justubborn
 * @since 2022/5/12
 */
public class BaseController<Service extends BaseService<T>, T extends BaseEntity> {

    @Autowired(required = false)
    protected Service service;

    public BaseService<T> getService() {
        return service;
    }

    @ResponseBody
    @PostMapping(value = "create")
    public Object add(T entity) {
        return getService().save(entity);
    }

    @PostMapping("remove")
    public void remove(String id) {

        getService().removeById(id);


    }

    @GetMapping("get")
    public Object get(Long id) {
        return RespEntity.ok(getService().getById(id));
    }

    @PostMapping("update")
    public Object update(T entity) {
        getService().updateById(entity);
        return entity.getId();
    }


    @GetMapping("list")
    public Object list(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        return getService().list(wrapper);
    }

    @GetMapping("page")
    public Object page(@Validated T entity, BaseQuery baseQuery) {
        IPage<T> page = baseQuery.buildPage();
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        return getService().page(page, wrapper);
    }
}
