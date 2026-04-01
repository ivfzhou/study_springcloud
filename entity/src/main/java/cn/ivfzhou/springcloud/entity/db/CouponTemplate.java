package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 优惠券模板实体类。
 * <p>
 * 对应数据库表 t_coupon_template，存储优惠券的模板定义信息。
 * 通过 templateClass 字段关联具体的规则/限制实现类，dynamic 字段存储动态属性配置。
 * 用于后台管理系统创建优惠券规则模板。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_coupon_template")
public class CouponTemplate implements Serializable {

    /** 模板ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 模板名称 */
    private String name;

    /** 创建时间，默认为当前时间 */
    private Date createTime = new Date();

    /** 状态标记 */
    private Integer status;

    /** 模板实现类的全限定类名 */
    @TableField("class")
    private String templateClass;

    /** 动态属性数据（JSON格式），用于存储实现类的字段配置 */
    private String dynamic;

    /** 模板类型 */
    private Integer type;

}
