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

@Data
@Accessors(chain = true)
@TableName("t_coupon")
public class Coupon implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String subject;

    private String subTitle;

    private Integer type;

    private Integer priority;

    private String content;

    private String ruleInfo;

    private String limitInfo;

    private Date createTime = new Date();

    private Integer status;

    @TableField(exist = false)
    private Integer receiveCount;

    @TableField(exist = false)
    @JsonIgnore
    private IRule rule;

    @TableField(exist = false)
    @JsonIgnore
    private ILimit limit;

    @TableField(exist = false)
    private CouponIssue issue;

    public void setRuleInfo(String ruleInfo) {
        this.ruleInfo = ruleInfo;
        rule = CouponUtil.dynamicField2Obj(ruleInfo);
    }

    public void setLimitInfo(String limitInfo) {
        this.limitInfo = limitInfo;
        limit = CouponUtil.dynamicField2Obj(limitInfo);
    }

}
