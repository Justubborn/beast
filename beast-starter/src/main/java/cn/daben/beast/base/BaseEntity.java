package cn.daben.beast.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Justubborn
 * @since 2022/5/12
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static String ID = "id";

    public static String CREATOR = "creator";

    public static String CREATED = "created";

    public static String MODIFIER = "modifier";

    public static String MODIFIED = "modified";

    public static String IS_DELETE = "is_delete";
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;
    /**
     * 数据来源
     */
    @Schema(hidden = true)
    protected Integer origin;
    
    /**
     * 数据状态:0-禁用,1-正常
     */
    @Schema(description = "描述")
    protected Integer status;
    /**
     * 创建者
     */

    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    protected Long creator;

    /**
     * 创建时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime created;

    /**
     * 修改者
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    protected Long modifier;

    /**
     * 修改时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    protected LocalDateTime modified;
}
