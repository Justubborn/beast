/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.daben.beast.support.data.mybatis;

import cn.daben.beast.base.BaseEntity;
import cn.daben.beast.exception.BusinessException;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 元对象处理器配置（插入或修改时自动填充）
 *
 * @author Charles7c
 * @since 2022/12/22 19:52
 */
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入数据时填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (null == metaObject) {
                return;
            }
            Long createUser = -1L;
            LocalDateTime createTime = LocalDateTime.now();
            if (metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
                // 继承了 BaseEntity 的类，填充创建信息字段
                baseEntity.setCreator(ObjectUtil.defaultIfNull(baseEntity.getCreator(), createUser));
                baseEntity.setCreated(ObjectUtil.defaultIfNull(baseEntity.getCreated(), createTime));
            } else {
                // 未继承 BaseEntity 的类，如存在创建信息字段则进行填充
                this.fillFieldValue(metaObject, BaseEntity.CREATOR, createUser, false);
                this.fillFieldValue(metaObject, BaseEntity.CREATED, createTime, false);
            }
        } catch (Exception e) {
            throw new BusinessException("插入数据时自动填充异常：" + e.getMessage());
        }
    }

    /**
     * 修改数据时填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (null == metaObject) {
                return;
            }

            Long updateUser = -1L;
            LocalDateTime updateTime = LocalDateTime.now();
            if (metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
                // 继承了 BaseEntity 的类，填充修改信息
                baseEntity.setModifier(updateUser);
                baseEntity.setModified(updateTime);
            } else {
                // 未继承 BaseEntity 的类，根据类中拥有的修改信息字段进行填充，不存在修改信息字段不进行填充
                this.fillFieldValue(metaObject, BaseEntity.CREATOR, updateUser, true);
                this.fillFieldValue(metaObject, BaseEntity.CREATED, updateTime, true);
            }
        } catch (Exception e) {
            throw new BusinessException("修改数据时自动填充异常：" + e.getMessage());
        }
    }

    /**
     * 填充字段值
     *
     * @param metaObject     元数据对象
     * @param fieldName      要填充的字段名
     * @param fillFieldValue 要填充的字段值
     * @param isOverride     如果字段值不为空，是否覆盖（true：覆盖；false：不覆盖）
     */
    private void fillFieldValue(MetaObject metaObject, String fieldName, Object fillFieldValue, boolean isOverride) {
        if (metaObject.hasSetter(fieldName)) {
            Object fieldValue = metaObject.getValue(fieldName);
            setFieldValByName(fieldName, null != fieldValue && !isOverride ? fieldValue : fillFieldValue, metaObject);
        }
    }
}
