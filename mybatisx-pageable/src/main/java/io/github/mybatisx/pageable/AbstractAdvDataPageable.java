/*
 * Copyright (c) 2021-Now, wvkity(wvkity@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.mybatisx.pageable;

import io.github.mybatisx.lang.Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分页
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractAdvDataPageable<T> extends AbstractAdvPageable implements AdvDataPageable<T> {

    /**
     * 数据列表
     */
    protected List<T> data;

    public AbstractAdvDataPageable(String page) {
        super(page);
    }

    public AbstractAdvDataPageable(int page) {
        super(page);
    }

    public AbstractAdvDataPageable(String page, String size) {
        super(page, size);
    }

    public AbstractAdvDataPageable(int page, int size) {
        super(page, size);
    }

    @Override
    public List<T> getData() {
        return Objects.isNull(this.data) ? new ArrayList<>(0) : this.data;
    }

    @Override
    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public void addAll(Collection<T> data) {
        if (Objects.isNotEmpty(data)) {
            if (Objects.isNull(this.data)) {
                this.data = new ArrayList<>(data.size());
            }
            this.data.addAll(data);
        }
    }
}
