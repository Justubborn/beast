package cn.daben.beast.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Justubborn
 * @since 2022/5/12
 */
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public final static String ID = "id";

    public final static String CREATOR = "creator";

    public final static String CREATED = "created";

    public final static String MODIFIER = "modifier";

    public final static String MODIFIED = "modified";

    public final static String IS_DELETE = "is_delete";
    /**
     * 主键
     */
    @Id
    protected Long id;
    /**
     * 数据来源
     */
    protected Integer origin;

    /**
     * 数据状态:0-禁用,1-正常
     */
    @TableField(fill = FieldFill.INSERT)
    protected Integer status;
    /**
     * 创建者
     */

    @TableField(fill = FieldFill.INSERT)
    protected Long creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime created;

    /**
     * 修改者
     */
    @TableField(fill = FieldFill.UPDATE)
    protected Long modifier;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    protected LocalDateTime modified;
}
