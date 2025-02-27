package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import cn.ivfzhou.reserve_platform.entity.coupon.CouponUtil;
import cn.ivfzhou.reserve_platform.entity.coupon.limit.ILimit;
import cn.ivfzhou.reserve_platform.entity.coupon.rule.IRule;

@Data
@Accessors(chain = true)
@TableName("t_coupon")
public class Coupon implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String subject;

    private String subTitle;

    private Integer utype;

    private Integer priority;

    private String content;

    // 优惠规则。
    private String ruleInfo;

    // 优惠限制。
    private String limitInfo;

    private Date createTime = new Date();

    private Integer status = 0;

    @TableField(exist = false)
    private Integer reccount;

    // 优惠的规则实现对象。
    @TableField(exist = false)
    @JsonIgnore
    private IRule rule;

    // 优惠的限制实现对象。
    @TableField(exist = false)
    @JsonIgnore
    private ILimit limit;

    @TableField(exist = false)
    private CouponIssue issue;

    public void setRuleInfo(String ruleInfo) {
        this.ruleInfo = ruleInfo;
        // 规则的json -> 实际的规则对象
        rule = CouponUtil.dynamicField2Obj(ruleInfo);
    }

    public void setLimitInfo(String limitInfo) {
        this.limitInfo = limitInfo;
        limit = CouponUtil.dynamicField2Obj(limitInfo);
    }

}
