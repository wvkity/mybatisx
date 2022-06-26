package io.github.mybatisx.core.sql;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.exception.MyBatisException;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.criteria.update.Update;
import io.github.mybatisx.core.fragment.FragmentManager;
import io.github.mybatisx.core.mapping.Scripts;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.util.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 更新SQL语句管理器
 *
 * @author wvkity
 * @created 2022/6/26
 * @since 1.0.0
 */
public class UpdateSqlManager extends AbstractSqlManager {

    private final Update<?> criteria;
    private final ParameterConverter parameterConverter;
    private final Map<Column, Object> columns;

    public UpdateSqlManager(FragmentManager fragmentManager, Update<?> criteria,
                            ParameterConverter parameterConverter, Map<Column, Object> columns) {
        super(fragmentManager);
        this.criteria = criteria;
        this.parameterConverter = parameterConverter;
        this.columns = columns;
    }

    @Override
    protected Criteria<?> getCriteria() {
        return this.criteria;
    }

    @Override
    public String getUpdateFragment() throws MyBatisException {
        if (Maps.isNotEmpty(this.columns)) {
            final List<String> segments = new ArrayList<>(this.columns.size());
            this.columns.forEach((c, v) -> segments.add(Scripts.toConditionArg(null, c, Symbol.EQ, LogicSymbol.NONE, this.parameterConverter.convert(v))));
            return String.join(SqlSymbol.COMMA_SPACE, segments);
        }
        return Constants.EMPTY;
    }

    @Override
    public String getCompleteString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append(SqlSymbol.UPDATE)
                .append(SqlSymbol.SPACE)
                .append(this.criteria.getTableName().trim())
                .append(SqlSymbol.SPACE)
                .append(SqlSymbol.SET)
                .append(SqlSymbol.SPACE)
                .append(this.getUpdateFragment());
        final String condition = this.getWhereString();
        if (Strings.isNotWhitespace(condition)) {
            sb.append(SqlSymbol.SPACE).append(condition.trim());
        }
        return sb.toString();
    }
}
