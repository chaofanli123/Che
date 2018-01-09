package com.victor.che.util;

import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * public String[] split(String regex) Splits this string around matches of
     * the given regular expression. 参数regex是一个
     * regular-expression的匹配模式而不是一个简单的String，他对一些特殊的字符可能会出现你预想不到的结果： 1.用竖线 |
     * 分隔字符串，你将得不到预期的结果。 2.用 *
     * 分隔字符串运行将抛出java.util.regex.PatternSyntaxException异常，用加号 + 也是如此。 显然，| + *
     * 不是有效的模式匹配规则表达式，用"\\*" "\\+"转义后即可得到正确的字符串结果。
     */
    // "|"分隔串时虽然能够执行，但是却不是预期的目的，得到的是每个字符的分割，而不是字符串，"\\|"转义后即可得到正确的字符串结果。
    public static final String CHAR_VERTICAL_LINE = "\\|";// 竖线分隔符
    public static final String CHAR_PLUS = "\\+";// +号分隔符
    public static final String CHAR_STAR = "\\*";// *号分隔符
    /**
     * “yyyy-mm-dd“ 格式的日期校验，已考虑平闰年。
     **/
    public static final String DATE_REGEX = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * 金额校验，精确到2位小数。
     **/
    public static final String MONEY_REGEX = "^[0-9]+(.[0-9]{2})?$";
    /**
     * IE目前还没被完全取代，很多页面还是需要做版本兼容，下面是IE版本检查的表达式。
     **/
    public static final String IE_VERSION_REGEX = "^.*MSIE [5-8](?:\\.[0-9]+)?(?!.*Trident\\/[5-9]\\.0).*$";
    /**
     * 校验IP-v4地址
     **/
    public static final String IPV4_REGEX = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";

    /**
     * 校验IP-v6地址
     **/
    public static final String IPV6_REGEX = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";

    /**
     * 提取URL链接
     **/
    public static final String URL_PATTERN_REGEX = "^(f|ht){1}(tp|tps):\\/\\/([\\w-]+\\.)+[\\w-]+(\\/[\\w- ./?%&=]*)?";

    /**
     * 提取URL链接
     **/
    public static final String FILE_PATH_REGEX = "^([a-zA-Z]\\:|\\\\)\\\\([^\\\\]+\\\\)*[^\\/:*?\"<>|]+\\.txt(l)?$";

    /**
     * 提取页面超链接
     **/
    public static final String A_PATTERN_REGEX = "(<a\\s*(?!.*\\brel=)[^>]*)(href=\"https?://)((?!(?:(?:www\\.)?'.implode('|(?:www\\.)?', $follow_list).'))[^\"]+)\"((?!.*\\brel=)[^>]*)(?:[^>]*)>";

    /**
     * 格式化成货币格式0.00， 10.00, 15.12等
     *
     * @param args
     * @return
     */
    public static String formatCurrency(Object args) {
        return String.format("%.2f", args);
    }

    /**
     * 是否是金额数值
     *
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d+(.\\d{1,2})?$");
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.matches();
        return result;
    }

    /**
     * 是否是空的json数组
     *
     * @param str
     * @return
     */
    public static boolean isEmptyJSONArray(String str) {
        return isEmpty(str) || "[]".equals(str);
    }

    /**
     * 为TextView设置html文本
     *
     * @param textView
     * @param htmlText
     */
    public static void setHtmlText(TextView textView, String htmlText) {
        textView.setText(Html.fromHtml(htmlText));
    }

    /**
     * 手机号格式是否正确
     *
     * @param str
     * @return
     */
    public static boolean isPhoneNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        /**
         * 手机号码: 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[6, 7, 8],
         * 18[0-9], 170[0-9]
         *
         * 移动号段:134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,
         * 188, 147,178,1705
         *
         * 联通号段: 130,131,132,155,156,185,186,145,176,1709
         *
         * 电信号段: 133,153,180,181,189,177,1700
         */
        String mobileRegex = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";
        /**
         * 中国移动：China Mobile
         * 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,
         * 147,178,1705
         */
        String CM = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";
        /**
         * 中国联通：China Unicom 130,131,132,155,156,185,186,145,176,1709
         */
        String CU = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";
        /**
         * 中国电信：China Telecom 133,153,180,181,189,177,1700
         */
        String CT = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

        return str.matches(mobileRegex) || str.matches(CM) || str.matches(CU) || str.matches(CT);
    }

    public static String removeSpace(Object str) {
        if (str instanceof String) {
            String s = (String) str;
            if (isEmpty(s)) {
                return "";
            }
            return s.replace(" ", "");
        } else {
            return "";
        }
    }

    public static boolean isEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return email.matches(
                "\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx|tech)$\\b");
    }

    /**
     * 是否是身份证号
     *
     * @param str
     * @return
     */
    public static boolean isIdCard(String str) {
        if (isEmpty(str)) {
            return false;
        }
        // 18位身份证号
        String regex1 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
        // 15位身份证号
        String regex2 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        return Pattern.matches(regex1, str) || Pattern.matches(regex2, str);
    }

    /**
     * 保留小数点后2位（舍弃第三位之后，非四舍五入）
     *
     * @return
     */
    public static double roundFloor2(double value) {
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    /**
     * 字符串不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return (str != null) && (str.trim().length() > 0);
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    /**
     * 判断两个字符串是否相等（null==null, ""==null）
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsEmptyNull(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null) {
            return str2.length() == 0;
        } else if (str2 == null) {
            return str1.length() == 0;
        } else {
            return str1.equalsIgnoreCase(str2);
        }
    }

    /**
     * 防止空指针的异常
     *
     * @param str
     * @return
     */
    public static String convertNull(String str) {
        return str == null ? "" : str;
    }

    /**
     * 替换，防止空指针异常
     *
     * @param srcStr
     * @param target
     * @param replacement
     * @return
     */
    public static String replace(String srcStr, String target, String replacement) {
        if (isEmpty(srcStr)) {
            return "";
        }
        if (target == null || replacement == null) {
            return srcStr;
        }
        return srcStr.replace(target, replacement);
    }

    /**
     * 替换字符串
     *
     * @param oldStr
     * @param newChar
     * @return
     */
    public static String replace(String oldStr, String newChar) {
        if (isEmpty(oldStr)) {
            return "";
        }
        return oldStr.replace(oldStr, newChar);
    }

    /**
     * 获取字符串最后几位
     *
     * @param str 原始字符串
     * @param n   位数
     * @return
     */
    public static String getLastNChar(String str, int n) {
        if (isEmpty(str)) {
            return "";
        }
        if (str.length() <= n || n <= 0) {
            return str;
        }
        return str.substring(str.length() - n);
    }

    /**
     * 获取字符串前几位
     *
     * @param str 原始字符串
     * @param n   位数
     * @return
     */
    public static String getBeginNChar(String str, int n) {
        if (isEmpty(str)) {
            return "";
        }
        if (str.length() <= n || n <= 0) {
            return str;
        }
        return str.substring(0, n);
    }

    public static String trimToLength(String str, int len) {
        if (isEmpty(str)) {
            return "";
        }
        if (len <= 0) {
            return str;
        }
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len);
        }
    }

    public static String trimToLength2(String str, int len) {
        if (isEmpty(str)) {
            return "";
        }
        if (len <= 0) {
            return str;
        }
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len) + "……";
        }
    }

    /**
     * 删除最后N个字符
     *
     * @param str
     * @return
     */
    public static String removeLastNChar(String str, int len) {
        if (len <= 0) {
            return str;
        }
        if (isEmpty(str) || str.length() <= len) {
            return "";
        } else {
            return str.substring(0, str.length() - len);
        }
    }

    /**
     * 隐藏手机号或邮箱 132****8888或468****@qq.com
     *
     * @param str
     * @return
     */
    public static String hideString(String str) {
        if (isEmpty(str) || str.length() < 7) {
            return "";
        }
        char[] chars = str.toCharArray();
        for (int i = 3; i <= 6; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }

    /**
     * 是否是url路径
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        String regex = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
                + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
                + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
                + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
                + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
                + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
                + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
                + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
        return Pattern.matches(regex, url);
    }

    /**
     * 是否是车牌号
     *
     * @param text
     * @return
     */
    public static boolean isPln(String text) {
        if (text == null || text.trim().length() == 0) {
            return false;
        }
        String regex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Za-z]{1}[A-Za-z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Za-z0-9]{4}[A-Za-z0-9挂学警港澳]{1}$";
        return Pattern.matches(regex, text);
    }

    /**
     * 判断是否是身份证号
     * @param text
     * @return
     */
    public static boolean isSfz(String text){
        if(TextUtils.isEmpty(text)){
            return false;
        }
//        验证15/18位身份证号码的正则表达式为：
        String isIDCard1="/^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        return Pattern.matches(isIDCard1,text);
    }
}
