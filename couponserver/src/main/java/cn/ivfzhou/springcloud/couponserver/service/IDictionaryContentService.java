package cn.ivfzhou.springcloud.couponserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.springcloud.entity.db.DictionaryContent;

/**
 * 字典内容服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供字典内容表（t_dictionary_content）的基础业务操作。
 * </p>
 */
public interface IDictionaryContentService extends IService<DictionaryContent> {
}
