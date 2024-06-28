package cn.daben.beast.model.resp;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justubborn
 * @since 2022/12/16
 */
@Data
public class PageResp<T> {

    /**
     * 总记录数
     */
    long total;

    protected List<T> list;

    /**
     * 基于 MyBatis Plus 分页数据构建分页信息
     *
     * @param page MyBatis Plus 分页数据
     * @param <T>  列表数据类型
     * @return 分页信息
     */
    public static <T> PageResp<T> build(IPage<T> page) {
        if (null == page) {
            return empty();
        }
        PageResp<T> pageResp = new PageResp<>();
        pageResp.setList(page.getRecords());
        pageResp.setTotal(page.getTotal());
        return pageResp;
    }

    /**
     * 基于列表数据构建分页信息
     *
     * @param page 页码
     * @param size 每页条数
     * @param list 列表数据
     * @param <T>  列表数据类型
     * @return 分页信息
     */
    public static <T> PageResp<T> build(int page, int size, List<T> list) {
        if (CollUtil.isEmpty(list)) {
            return empty();
        }
        PageResp<T> pageResp = new PageResp<>();
        pageResp.setTotal(list.size());
        // 对列表数据进行分页
        int fromIndex = (page - 1) * size;
        int toIndex = page * size + fromIndex;
        if (fromIndex > list.size()) {
            pageResp.setList(new ArrayList<>(0));
        } else if (toIndex >= list.size()) {
            pageResp.setList(list.subList(fromIndex, list.size()));
        } else {
            pageResp.setList(list.subList(fromIndex, toIndex));
        }
        return pageResp;
    }

    /**
     * 空分页信息
     *
     * @param <T> 列表数据类型
     * @return 分页信息
     */
    private static <T> PageResp<T> empty() {
        PageResp<T> pageResp = new PageResp<>();
        pageResp.setList(new ArrayList<>(0));
        return pageResp;
    }
}
