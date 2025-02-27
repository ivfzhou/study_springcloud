package cn.ivfzhou.reserve_platform.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.*;

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

    @RequestMapping("/dic/list")
    ResultData<List<Dictionary>> list();

    @RequestMapping("/dic/getContentByDid")
    ResultData<List<DictionariesContent>> getContentByDid(@RequestParam("did") Integer did);

    @RequestMapping("/dic/insert")
    ResultData<Boolean> insertDic(@RequestBody Dictionary dictionary);

    @RequestMapping("/dic/insertContent")
    ResultData<Boolean> insertDicContent(@RequestBody DictionariesContent content);

}
