package cn.ivfzhou.springcloud.orderserver.controller;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.Order;
import cn.ivfzhou.springcloud.entity.db.Room;
import cn.ivfzhou.springcloud.feign.HotelFeign;
import cn.ivfzhou.springcloud.orderserver.service.IOrderService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private HotelFeign hotalFeign;

    /**
     * 支付宝支付
     */
    @RequestMapping("/alipay")
    public String aliPay(String oid) {

        //订单查询
        Order order = orderService.getById(oid);

        //获得房型信息
        ResultData<Room> rooms = hotalFeign.getRoomById(order.getRid());

        //支付
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016073000127352",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRkTzQUmW1fclRkqpvUS8aKm4CjwvVlX9djvnmNkT0THY4+dKvQYIhG+nXhzmsa5KpKgx9Bq+fd0SeVTa8ZBf7frbmKDCK9Gi84v9s1ifgOACl+EVS/vBDaXvPG6eS3RdoApTg+HsrmvsgztV2RVgwCzgnECBRbAy1QPbKLauNBntuT/TIlrZA9ymQRYX1/BHanD9ISKFobAHhaFcQGv4tpVQT3BiethisT9WX/DT31fTs0SxrVs4kh38GmOveLRb1SFgLujZA0z0umz85CeugA7DBeXac3RRUyt+rHnYL2knZcc3izR5+8tR2GzbMyD60t22A1pcn0RhEPjE9EknZAgMBAAECggEBAIV3/6SbCDrCnKY4riDm9SMuqDOcaSNqsZ9dCvDz7YF54iQXm/+pNuGYsG43xL+82npU3aJ9UOYL+1SZmVhfBkZIRvb8/pVJyAb4s25dZa6GasHCw/13V2DYBPc+ygU0xtNNGqiz7Gts7LmGK98HcfaLMu9OL/O3+Gbwq9dwzIy+dC7JhPAhUTSwPSXb8a0tzt9nwtifOfnvr1tIegWQW9AR/QupcEvyMF23i5UjWd71fez023VgpszCqQSxbXZBl2nK8nc9XlaAH1b85zEwvL+MbOpnOFCLNfSfWRmz5/ujFBRkSHXlj4qdyxZm/iX/NXGyOjBwKg8+1jadh2en2QECgYEAzKuYafVILZLN8rBCteHzAU4avnVlH4KEHVxN5cjGy8Eji87Me2G1DRU55cJ2cCJFB1x8ShRQ1zbZO1HLJKfo9XnRYtfVY2Gwl4P/qFH+JaquZHRzkt+V1/kVbj8aWzvXFPILdLhp52O/FUoWgvbIOTSDSRieREdo/FEZPDZI79ECgYEAthMRKaIKM7ASbC3gK5NglDqT+7SflmkdgcnFaQwvpX35lcUbFULsfgwSwM5lN1CoICyJGKhPFCOvsDffoveGWptQHrsoh6K9XS+WRZlY7Vj+O6pMArz3C/ZTX2eu7RWuOU+wl7+OiUCM8PCNl2GwUpdFLN+rCtZ8JK+I7b6yg4kCgYAgqPlHX0bHTvfiVFIw5/95I9eMsPsUDiW68bHUizd96DId60AiKDNWBR8aFPAFcNKHHDEROTC1RyFfH+xEyy6XjXrmRP7ePwaJBx1S5NKjfBscGKEgvOESd8L9tsAj8uadhwpg89Sigf5KDWqON4c36bNRhMYsqyCLsv1N6HO3IQKBgQCEwNI6sNMPbMmB5tET9Jjueud7Fu9lE69X+m3dce0rlPZtpRFiJzGXleX2foZ+Fmj/THmJaO5/mKV4rsR4ZlGnrqZ0QbIDczzuvMusY2rbY4+6oShatrzKFAgl/8sjQjONxfTUL+8VU+NvqXUTcz7EeVcOqOZlOI27keEct3YhQQKBgGNcicqpMWXyj9nnq4dDIG8JBhzxJCUs1HpkrtkfvMCRQQizbxLZ7W642z5qC3hjcCcgQ/1gEwGOQT1l4I3O1LkxaeWtGFSk4wUvR2XLLw+2pe9UOIv7XifigcsLbJyqHDIsaaFFiipJDXWTVmiScXM29U9LcLRSXEAGt8uiYPzB",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+n+pEqTVi27mtj3CuUQXi2ixqeeTwE/0tGrW+sg6xtfajvJV67GYf2zzNxxBV0TYfhdbi70VI3DftEijg7GSNKoOilAu2DKQFqidnSxmN1Es1oRTaiaehqm1Uzs2uswpzBVR21iygLHujwthC8kNkMgxVFkjbE/qTn7z5wlsailtg6wF+hY3BcDCiaLyVLjEDngmrLyLXPLenjAuvXq20h9zV7CW9HXuhpPBDfsn4fv5TjgEl1smjJNr4O/VxICKDNPsvrCyNXhfroK9PEFFwH+4IWGeBUJAP2cSufNU0OA+UH+2xQnaR8Cz30QIgIslckBGuXQZvxqaY2mMMz14QIDAQAB",
                "RSA2"); //获得初始化的AlipayClient

        //手机网页的请求对象
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        //电脑网页的请求对象
        //AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        //支付完成后，同步跳转到什么请求 - 不能作为支付结果凭证
        alipayRequest.setReturnUrl("http://www.baidu.com");
        //支付完成后，异步通知的请求 - 判断是否支付成功，写商户Controller的地址
        alipayRequest.setNotifyUrl("http://verygoodwlk.xicp.net/pay/alipayresult");//在公共参数中设置回跳和通知地址
        //支付的请求体
        //out_trade_no - 商户订单号 ，商户端唯一
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + order.getOid() + "\"," +
                " \"total_amount\":\"" + order.getAllPrice() + "\"," +
                " \"subject\":\"" + rooms.getData().getTitle() + "\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");//填充业务参数
        String form = "";
        try {
            //发送请求给支付宝服务器，获得支付宝服务器的返回表单
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    /**
     * 支付宝的支付异步通知请求方法
     */
    @PostMapping("/alipayresult")
    public String aliPayResult(HttpServletRequest request) {
        //1、接收支付宝的结果

        //从请求中获取所有的参数
        Map<String, String> paramsMap = new HashMap<>(); //将异步通知中收到的所有参数都存放到map中
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, values) -> {
            System.out.println(key + " " + Arrays.toString(values));
            paramsMap.put(key, values[0]);
        });

        //做支付宝的结果验签 - 验证是否由支付宝发出
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(paramsMap, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+n+pEqTVi27mtj3CuUQXi2ixqeeTwE/0tGrW+sg6xtfajvJV67GYf2zzNxxBV0TYfhdbi70VI3DftEijg7GSNKoOilAu2DKQFqidnSxmN1Es1oRTaiaehqm1Uzs2uswpzBVR21iygLHujwthC8kNkMgxVFkjbE/qTn7z5wlsailtg6wF+hY3BcDCiaLyVLjEDngmrLyLXPLenjAuvXq20h9zV7CW9HXuhpPBDfsn4fv5TjgEl1smjJNr4O/VxICKDNPsvrCyNXhfroK9PEFFwH+4IWGeBUJAP2cSufNU0OA+UH+2xQnaR8Cz30QIgIslckBGuXQZvxqaY2mMMz14QIDAQAB", "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (signVerified) {
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            System.out.println("支付验签成功！");
            //2、根据结果修改订单状态
            String orderid = paramsMap.get("out_trade_no");
            orderService.updateOrderStatus(orderid, 1);

            return "success";
        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            System.out.println("支付验签失败！");
            return "failure";
        }
    }

}
