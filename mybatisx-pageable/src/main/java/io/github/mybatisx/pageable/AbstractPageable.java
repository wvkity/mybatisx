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

import io.github.mybatisx.lang.Strings;

/**
 * 抽象分页
 *
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractPageable implements Pageable {

    /**
     * 0
     */
    public static final int ZERO = 0;
    /**
     * 1
     */
    public static final int ONE = 1;
    /**
     * 默认每页20条数据
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 每页最大数目
     */
    public static final int DEFAULT_MAX_PAGE_SIZE = 2000;
    /**
     * 当前页
     */
    protected final int page;
    /**
     * 每页数目
     */
    protected final int size;
    /**
     * 总记录数
     */
    protected long records;
    /**
     * 总页数
     */
    protected int totals;

    public AbstractPageable(String page) {
        this(Strings.parseInt(page));
    }

    public AbstractPageable(int page) {
        this(page, DEFAULT_PAGE_SIZE);
    }

    public AbstractPageable(String page, String size) {
        this(Strings.parsePositiveInt(page), Strings.parseInt(size, DEFAULT_PAGE_SIZE));
    }

    public AbstractPageable(int page, int size) {
        if (page < ONE) {
            throw new IllegalArgumentException("Page index must not be less than one!");
        }
        if (size < ONE) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        if (size > DEFAULT_MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size must not be greater than " + DEFAULT_MAX_PAGE_SIZE + "!");
        }
        this.page = page;
        this.size = size;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getTotals() {
        return this.totals;
    }

    @Override
    public long getRecords() {
        return this.records;
    }

    @Override
    public void setRecords(long records) {
        this.records = Math.max(records, ZERO);
        this.calculateTotals();
    }

    @Override
    public int getOffset() {
        return Math.max(ZERO, (this.page - ONE) * this.size);
    }

    @Override
    public boolean isHasPrev() {
        return this.records > ZERO && this.page > ONE && this.page <= this.totals;
    }

    @Override
    public boolean isHasNext() {
        return this.records > ZERO && this.page >= ONE && this.page < this.totals;
    }

    protected void calculateTotals() {
        if (this.records > ZERO) {
            final int pages = (int) (this.records / this.size);
            if (this.records % this.size == ZERO) {
                this.totals = pages;
            } else {
                this.totals = pages + ONE;
            }
        }
    }
}
