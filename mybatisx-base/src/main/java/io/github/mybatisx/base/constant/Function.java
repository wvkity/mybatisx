package io.github.mybatisx.base.constant;

import io.github.mybatisx.base.fragment.Fragment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 函数枚举
 *
 * @author wvkity
 * @created 2023/1/13
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum Function implements Fragment {

    /**
     * IF函数
     */
    IF("IF");

    /**
     * 函数名
     */
    final String name;

    @Override
    public String getFragment() {
        return this.name;
    }
}
