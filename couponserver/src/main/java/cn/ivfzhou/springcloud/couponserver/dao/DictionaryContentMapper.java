package cn.ivfzhou.springcloud.couponserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.ivfzhou.springcloud.entity.db.DictionaryContent;

/**
 * 字典内容数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供字典内容表（t_dictionary_content）的基础 CRUD 操作。
 * </p>
 */
public interface DictionaryContentMapper extends BaseMapper<DictionaryContent> {
}
