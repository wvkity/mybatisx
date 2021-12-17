/*
 * Copyright (c) 2021-2023, wvkity(wvkity@gmail.com).
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

/**
 * 抽象分页
 *
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractAdvPageable extends AbstractPageable implements AdvPageable {

    /**
     * 默认显示页码数
     */
    public static final int DEFAULT_DISPLAY_SIZE = 8;
    /**
     * 最小显示页码数
     */
    public static final int DEFAULT_MIN_DISPLAY_SIZE = 2;
    /**
     * 最大显示页码数
     */
    public static final int DEFAULT_MAX_DISPLAY_SIZE = 40;
    /**
     * 显示页码数
     */
    protected int display = DEFAULT_DISPLAY_SIZE;
    /**
     * 起始页码
     */
    protected int start;
    /**
     * 结束页码
     */
    protected int end;

    public AbstractAdvPageable(String page) {
        super(page);
    }

    public AbstractAdvPageable(int page) {
        super(page);
    }

    public AbstractAdvPageable(String page, String size) {
        super(page, size);
    }

    public AbstractAdvPageable(int page, int size) {
        super(page, size);
    }

    @Override
    public int getDisplay() {
        return this.display;
    }

    @Override
    public void setDisplay(int display) {
        this.display = Math.min(DEFAULT_MAX_DISPLAY_SIZE, display < DEFAULT_MIN_DISPLAY_SIZE ? DEFAULT_DISPLAY_SIZE :
                display);
        if (this.records > 0) {
            this.calculateStartAndEnd();
        }
    }

    @Override
    public int getStart() {
        return this.start;
    }

    @Override
    public int getEnd() {
        return this.end;
    }

    @Override
    protected void calculateTotals() {
        super.calculateTotals();
        this.calculateStartAndEnd();
    }

    protected void calculateStartAndEnd() {
        int _$start;
        int _$end;
        int _$page = this.page;
        int _$totals = this.totals;
        int _$display = this.display;
        if (_$totals <= _$display) {
            _$start = ONE;
            _$end = _$totals;
        } else {
            if (_$display <= DEFAULT_MIN_DISPLAY_SIZE) {
                _$start = _$page;
                _$end = _$page + ONE;
                if (_$end > _$totals) {
                    _$end = _$totals;
                }
            } else {
                final int avg = _$display / 2;
                final int num = avg + ONE;
                if (_$page <= num) {
                    _$start = ONE;
                    _$end = _$display;
                } else {
                    if ((_$totals - _$page) >= num) {
                        _$start = _$page - avg;
                        _$end = _$page - avg + _$display - ONE;
                    } else {
                        _$start = _$page - (_$display - ONE - (_$totals - _$page));
                        _$end = _$totals;
                    }
                }
            }
        }
        this.start = _$start;
        this.end = _$end;
    }
}
