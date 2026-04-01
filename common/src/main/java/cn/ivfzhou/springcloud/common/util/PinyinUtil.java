package cn.ivfzhou.springcloud.common.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音转换工具类。
 * <p>
 * 使用 pinyin4j 库将中文字符串转换为不带声调的拼音字符串。
 * 无法转换的字符（如数字、英文、标点）保持原样输出。
 * </p>
 */

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
