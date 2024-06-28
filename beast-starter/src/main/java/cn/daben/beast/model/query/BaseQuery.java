package cn.daben.beast.model.query;

import lombok.Data;

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
     * 开始时间
     */
    protected transient Long start_time;
    /**
     * 结束时间
     */
    protected transient Long end_time;

}
