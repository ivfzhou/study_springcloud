package cn.ivfzhou.reserve_platform.common.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Slf4j
public class PinyinUtil {

    private static final HanyuPinyinOutputFormat hanyuPinyinOutputFormat;

    static {
        hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 将字符串转换成拼音。
     */
    public static String str2Pinyin(String content) {
        if (content == null) return null;
        StringBuilder sb = new StringBuilder();
        char[] chars = content.toCharArray();
        for (char c : chars) {
            // 逐个字符转换。
            try {
                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, hanyuPinyinOutputFormat);
                if (pinyin != null) {
                    sb.append(pinyin[0]);
                } else {
                    // 没办法正常转换成拼音的字符。
                    sb.append(c);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                log.error("转拼音失败", e);
            }
        }
        return sb.toString();
    }

}
