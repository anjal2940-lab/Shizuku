package moe.shizuku.manager.app;

import android.content.Context;
import android.os.Build;

import androidx.annotation.StyleRes;

import moe.shizuku.manager.R;
import moe.shizuku.manager.ShizukuSettings;
import moe.shizuku.manager.utils.EnvironmentUtils;
import rikka.core.util.ResourceUtils;

public class ThemeHelper {

    private static final String THEME_DEFAULT = "DEFAULT";
    private static final String THEME_BLACK = "BLACK";
    private static final String THEME_PINK = "PINK";
    private static final String THEME_TEAL = "TEAL";
    private static final String THEME_PURPLE = "PURPLE";
    private static final String THEME_AMBER = "AMBER";

    public static final String KEY_LIGHT_THEME = "light_theme";
    public static final String KEY_BLACK_NIGHT_THEME = "black_night_theme";
    public static final String KEY_USE_SYSTEM_COLOR = "use_system_color";

    public static boolean isBlackNightTheme(Context context) {
        return ShizukuSettings.getPreferences().getBoolean(KEY_BLACK_NIGHT_THEME, EnvironmentUtils.isWatch(context));
    }

    public static boolean isUsingSystemColor() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                && ShizukuSettings.getPreferences().getBoolean(KEY_USE_SYSTEM_COLOR, true);
    }

    public static String getTheme(Context context) {
        // Dynamic system colors take priority on Android 12+ if enabled
        if (isUsingSystemColor()) {
            if (isBlackNightTheme(context) && ResourceUtils.isNightMode(context.getResources().getConfiguration())) {
                return THEME_BLACK;
            }
            return THEME_DEFAULT;
        }

        // Return user selected custom theme
        return ShizukuSettings.getPreferences().getString(KEY_LIGHT_THEME, THEME_DEFAULT);
    }

    @StyleRes
    public static int getThemeStyleRes(Context context) {
        switch (getTheme(context)) {
            case THEME_BLACK:
                return R.style.ThemeOverlay_Black;
            case THEME_PINK:
                return R.style.ThemeOverlay_Pink;
            case THEME_TEAL:
                return R.style.ThemeOverlay_Teal;
            case THEME_PURPLE:
                return R.style.ThemeOverlay_Purple;
            case THEME_AMBER:
                return R.style.ThemeOverlay_Amber;
            case THEME_DEFAULT:
            default:
                return R.style.ThemeOverlay;
        }
    }
}

