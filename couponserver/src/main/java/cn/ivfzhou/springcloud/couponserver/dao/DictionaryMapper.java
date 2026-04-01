package cn.ivfzhou.springcloud.couponserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.ivfzhou.springcloud.entity.db.Dictionary;

/**
 * 字典数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供字典表（t_dictionary）的基础 CRUD 操作。
 * </p>
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {
}
