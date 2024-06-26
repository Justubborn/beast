package cn.daben.beast.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Justubborn
 * @since 2022/12/16
 */
@Getter
@Setter
@AllArgsConstructor
public class Page<T> {
    /**
     * 总数
     */
    long total;
    /**
     * 返回数据
     */
    protected List<T> rows;
}
