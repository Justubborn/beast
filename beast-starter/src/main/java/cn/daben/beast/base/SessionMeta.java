package cn.daben.beast.base;

import lombok.Data;

import java.util.List;

/**
 * @author Justubborn
 * @since 2023/5/26
 */
@Data
public class SessionMeta {
    private Long userId;

    private String userName;

    private List<String> roleId;

    private String remoteIp;

    private Integer channel;

    private Integer terminal;
}
