package cn.ivfzhou.springcloud.entity.coupon;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CouponUtil {

    /**
     * 将动态的 JSON 转实现类。
     * 形如：
     * {
     *   "myclass":"cn.ivfzhou.springcloud.entity.coupon.rule.ManJianRule",
     *   "fields":[
     *     {
     *       "name":"a",
     *       "value":"100"
     *     },
     *     {
     *       "name":"b",
     *       "value":"20"
     *     },
     *   ]
     * }
     */
    public static <T> T dynamicField2Obj(String dynamicJson) {
        try {
            // 解析 JSON 字符串。
            JSONObject jsonObject = JSON.parseObject(dynamicJson);
            // 当前实现类的类路径。
            String myclass = jsonObject.getString("myclass");
            // 反射动态创建实现类。通过类的全路径限定名获得该类的反射对象。
            Class<?> c = Class.forName(myclass);
            // 通过反射对象创建对象。
            Object obj = c.getDeclaredConstructor().newInstance();

            // 解析类中的动态属性。
            JSONArray fieldsJson = jsonObject.getJSONArray("fields");
            for (int i = 0; i < fieldsJson.size(); i++) {
                // 从数组中解析出属性的 JSON。
                JSONObject fieldJson = fieldsJson.getJSONObject(i);
                // 获得属性的 name。
                String name = fieldJson.getString("name");
                // 获得属性的 value。
                String value = fieldJson.getString("value");

                // 通过反射将动态属性设置给指定的对象。
                Field field = c.getDeclaredField(name);
                // 授权操作私有属性。
                field.setAccessible(true);

                // 判断属性类型。获得属性类型的 Class 对象。
                Class<?> typeClass = field.getType();
                if (typeClass == int.class || typeClass == Integer.class) {
                    field.setInt(obj, Integer.parseInt(value));
                } else if (typeClass == float.class || typeClass == Float.class ||
                        typeClass == double.class || typeClass == Double.class) {
                    field.setFloat(obj, Float.parseFloat(value));
                } else if (typeClass == Date.class) {
                    field.set(obj, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
                } else {
                    field.set(obj, value);
                }
            }

            return (T) obj;
        } catch (Exception e) {
            log.error("字符串解析对象出错", e);
        }

        return null;
    }

}
