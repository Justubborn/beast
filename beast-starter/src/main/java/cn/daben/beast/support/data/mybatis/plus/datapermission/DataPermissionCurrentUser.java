package cn.daben.beast.support.data.mybatis.plus.datapermission;

import cn.daben.beast.support.data.mybatis.plus.DataScope;
import lombok.Data;

import java.util.Set;

/**
 * 当前用户信息
 *
 * @author Charles7c
 * @since 1.1.0
 */
@Data
public class DataPermissionCurrentUser {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色列表
     */
    private Set<CurrentUserRole> roles;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 当前用户角色信息
     */
    @Data
    public static class CurrentUserRole {

        /**
         * 角色 ID
         */
        private String roleId;

        /**
         * 数据权限
         */
        private DataScope dataScope;
        
        public CurrentUserRole(String roleId, DataScope dataScope) {
            this.roleId = roleId;
            this.dataScope = dataScope;
        }
    }
}
