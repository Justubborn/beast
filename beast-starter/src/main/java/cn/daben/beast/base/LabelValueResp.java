/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.daben.beast.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 键值对信息
 *
 * @author Charles7c
 * @since 2.1.0
 */
@Data
@Schema(description = "键值对信息")
public class LabelValueResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    @Schema(description = "标签", example = "男")
    private String label;

    /**
     * 值
     */
    @Schema(description = "值", example = "1")
    private Object value;

    /**
     * 是否禁用
     */
    @Schema(description = "是否禁用", example = "false")
    private Boolean disabled;

    /**
     * 扩展
     */
    @Schema(description = "扩展")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object extend;

    public LabelValueResp() {
    }

    public LabelValueResp(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public LabelValueResp(String label, Object value, Object extend) {
        this.label = label;
        this.value = value;
        this.extend = extend;
    }

    public LabelValueResp(String label, Object value, Boolean disabled) {
        this.label = label;
        this.value = value;
        this.disabled = disabled;
    }

 
}
