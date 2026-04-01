package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import cn.ivfzhou.springcloud.entity.coupon.CouponUtil;
import cn.ivfzhou.springcloud.entity.coupon.limit.ILimit;
import cn.ivfzhou.springcloud.entity.coupon.rule.IRule;

/**
 * 优惠券实体类。
 * <p>
 * 对应数据库表 t_coupon，存储优惠券的基本信息，包括标题、类型、优惠规则和限制条件等。
 * 规则（ruleInfo）和限制（limitInfo）以 JSON 字符串形式存储在数据库中，
 * 通过 CouponUtil 动态解析为对应的实现类对象（IRule / ILimit）。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_coupon")
public class Coupon implements Serializable {

    /** 优惠券ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 优惠券主标题 */
    private String subject;

    /** 优惠券副标题 */
    private String subTitle;

    /** 优惠券类型 */
    private Integer type;

    /** 优惠券优先级，数值越大优先级越高 */
    private Integer priority;

    /** 优惠券内容描述 */
    private String content;

    /** 优惠规则信息（JSON格式），设置时自动解析为 IRule 实现类对象 */
    private String ruleInfo;

    /** 优惠限制信息（JSON格式），设置时自动解析为 ILimit 实现类对象 */
    private String limitInfo;

    /** 优惠券创建时间，默认为当前时间 */
    private Date createTime = new Date();

    /** 优惠券状态，0-正常，1-禁用 */
    private Integer status;

    /** 优惠券已领取数量（非数据库字段） */
    @TableField(exist = false)
    private Integer receiveCount;

    /** 优惠规则对象（非数据库字段），由 ruleInfo 动态解析而来 */
    @TableField(exist = false)
    @JsonIgnore
    private IRule rule;

    /** 优惠限制对象（非数据库字段），由 limitInfo 动态解析而来 */
    @TableField(exist = false)
    @JsonIgnore
    private ILimit limit;

    /** 关联的优惠券发放信息（非数据库字段） */
    @TableField(exist = false)
    private CouponIssue issue;

    /** 设置优惠规则信息，同时将 JSON 解析为 IRule 实现类对象 */
    public void setRuleInfo(String ruleInfo) {
        this.ruleInfo = ruleInfo;
        rule = CouponUtil.dynamicField2Obj(ruleInfo);
    }

    /** 设置优惠限制信息，同时将 JSON 解析为 ILimit 实现类对象 */
    public void setLimitInfo(String limitInfo) {
        this.limitInfo = limitInfo;
        limit = CouponUtil.dynamicField2Obj(limitInfo);
    }

}
