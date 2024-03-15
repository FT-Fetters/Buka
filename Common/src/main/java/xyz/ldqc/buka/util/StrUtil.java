package xyz.ldqc.buka.util;

/**
 * @author Fetters
 */
public class StrUtil {

    private StrUtil(){}

    public static String format(final String format, final Object... args) {
        String formatStr = format;
        for (int i = 0; i < args.length; i++) {
            formatStr = formatStr.replaceFirst("\\{"+ i + "}", args[i].toString());
        }
        return formatStr;
    }

}
