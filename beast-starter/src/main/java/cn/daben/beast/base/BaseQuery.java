package cn.daben.beast.base;

import cn.daben.beast.exception.BadRequestException;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.micrometer.common.util.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justubborn
 * @since 2023/2/19
 */
@Data
public class BaseQuery {
    /**
     * 搜索参数
     */
    protected transient String params;
    /**
     * 分页页码
     */
    protected transient Integer current = 1;
    /**
     * 分页大小
     */
    protected transient Integer size = 20;

    protected transient Integer total = 20;

    /**
     * 排序方式,asc desc
     */
    protected transient String orders;

    protected transient Long startTime;

    protected transient Long endTime;

    /**
     * description:将orders（json数组字符串）转为List
     */
    public List<OrderItem> buildOrderList() {
        List<OrderItem> orderItemList = new ArrayList<>();
        if (StringUtils.isBlank(orders)) {
            orderItemList.add(OrderItem.desc("id"));
        } else {
            try {
                orderItemList = JSONUtil.toList(orders, OrderItem.class);
            } catch (Exception e) {
                throw new BadRequestException("分页排序参数orders不合法，请传正确的参数格式——['column':'','asc':'true/false']");
            }
        }
        return orderItemList;
    }

    /**
     * description:根据pageVO构建分页查询IPage
     *
     * @return IPage查询条件
     */
    public <K> IPage<K> buildPage() {
        Page<K> page = new Page<>(getCurrent(), getSize());
        page.addOrder(buildOrderList());
        return page;
    }
}
