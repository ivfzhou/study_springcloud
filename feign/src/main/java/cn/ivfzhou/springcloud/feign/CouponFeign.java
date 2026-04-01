package cn.ivfzhou.springcloud.feign;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 优惠券服务 Feign 远程调用接口。
 * <p>
 * 用于其他微服务通过 OpenFeign 调用优惠券服务（micro-coupon）的内部接口，
 * 实现优惠券、模板、字典的增删改查操作。
 * </p>
 */
@FeignClient("micro-coupon")
public interface CouponFeign {

    /**
     * 优惠券列表。
     */
    @RequestMapping("/coupon/list")
    ResultData<List<Coupon>> couponList();

    /**
     * 新增优惠券。
     */
    @RequestMapping("/coupon/insert")
    ResultData<Boolean> insert(@RequestBody Coupon coupon);

    /**
     * 发布优惠券。
     */
    @RequestMapping("/coupon/issue")
    ResultData<Boolean> issue(@RequestBody CouponIssue couponIssue);

    /**
     * 通过id查询优惠券的信息。
     */
    @RequestMapping("/coupon/couponById")
    ResultData<Coupon> getCouponById(@RequestParam("cid") Integer cid);

    /**
     * 模板列表。
     */
    @RequestMapping("/template/list")
    ResultData<List<CouponTemplate>> getTemplates();

    /**
     * 保存模板对象。
     */
    @RequestMapping("/template/insert")
    ResultData<Boolean> insertTemplate(@RequestBody CouponTemplate couponTemplate);

    /**
     * 获得指定类型的模板集合。
     */
    @RequestMapping("/template/listByType")
    ResultData<List<CouponTemplate>> getTemplatesByType(@RequestParam("type") Integer type);

    /** 查询所有字典列表 */
    @RequestMapping("/dic/list")
    ResultData<List<Dictionary>> list();

    /** 根据字典ID查询字典内容列表 */
    @RequestMapping("/dic/getContentByDid")
    ResultData<List<DictionaryContent>> getContentByDid(@RequestParam("did") Integer did);

    /** 新增字典 */
    @RequestMapping("/dic/insert")
    ResultData<Boolean> insertDic(@RequestBody Dictionary dictionary);

    /** 新增字典内容 */
    @RequestMapping("/dic/insertContent")
    ResultData<Boolean> insertDicContent(@RequestBody DictionaryContent content);

}
