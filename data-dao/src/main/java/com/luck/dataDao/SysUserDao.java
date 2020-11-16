package com.luck.dataDao;

import com.luck.dataEntity.SysUser;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;
public interface SysUserDao extends BaseMapper<SysUser> {
    @SqlStatement(params = "condition")
    SysUser test(String condition);
}
