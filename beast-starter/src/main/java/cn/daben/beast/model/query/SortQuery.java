package cn.daben.beast.model.query;

import cn.daben.beast.constant.StringConst;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序查询条件
 *
 * @author Charles7c
 * @since 1.0.0
 */
@Data
public class SortQuery extends BaseQuery {

    /**
     * 排序条件
     */
    private String[] sort;

    /**
     * 解析排序条件为 Spring 分页排序实体
     *
     * @return Spring 分页排序实体
     */
    public Sort buildSort() {
        if (ArrayUtil.isEmpty(sort)) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>(sort.length);
        if (CharSequenceUtil.contains(sort[0], StringConst.COMMA)) {
            // e.g "sort=createTime,desc&sort=name,asc"
            for (String s : sort) {
                List<String> sortList = CharSequenceUtil.splitTrim(s, StringConst.COMMA);
                Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sortList.get(1).toUpperCase()), sortList
                        .get(0));
                orders.add(order);
            }
        } else {
            // e.g "sort=createTime,desc"
            Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sort[1].toUpperCase()), sort[0]);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
