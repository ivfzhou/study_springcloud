package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_coupon_issue")
public class CouponIssue implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer cid;

    // 0-领劵中心 1-定时抢购
    private Integer method;

    // 0-时间范围有效 1-领取天数有效
    private Integer type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    // 领取多少天之后有效。
    private Integer days;

    //发行的数量 -1 无上限。
    private Integer number;

    // 定时抢购开始的时间。
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goStartTime;

    // 定时抢购结束的时间。
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goStopTime;

}
