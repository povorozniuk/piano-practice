package com.povorozniuk.backend.model.games.util;

import com.povorozniuk.backend.util.SqlUtil;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class SqlUtilTest {

    @Test
    public void findAndReplace(){
        MatcherAssert.assertThat(SqlUtil.splitSqlStatements("statement 1; statement 2;"), Matchers.hasSize(2));
        MatcherAssert.assertThat(SqlUtil.splitSqlStatements("     statement      1      ; statement 2 \n;"), Matchers.contains("statement 1;", "statement 2;"));

    }
}
