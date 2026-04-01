package cn.ivfzhou.springcloud.couponserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.springcloud.entity.db.Dictionary;

/**
 * 字典服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供字典表（t_dictionary）的基础业务操作。
 * </p>
 */
public interface IDictionaryService extends IService<Dictionary> {
}
