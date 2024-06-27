package cn.daben.beast.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "搜索参数")
    protected transient String params;
    @Schema(description = "开始时间")
    protected transient Long start_time;
    @Schema(description = "结束时间")
    protected transient Long end_time;

}
