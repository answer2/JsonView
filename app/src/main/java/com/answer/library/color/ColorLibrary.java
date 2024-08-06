package com.answer.library.color;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ColorLibrary {
    /*
     LibraryName:AnswerLibrary 3.0 Bate 2
     LibraryVersionNumber:3.0 Bate 2
     LibraryType:StringColor BackgroundColor And RoundColor
     Dev:Eternal Answer
     Copyright:Online OS 2021
     **/

    public static String[][] colors =
    new String[][] {
        {" ", " &nbsp;"},
        {"\n", "<br/>"},
        {"§l", "</b><b>"},
        {"§m", "</del><del>"},
        {"§n", "</ins><ins>"},
        {"§o", "</i><i>"},
        {"§r", "</font></b></del></ins></i>"},
        {"§0", "</font><font color=#000000>"},
        {"§1", "</font><font color=#0000AA>"},
        {"§2", "</font><font color=#72EEBC>"},
        {"§3", "</font><font color=#00AAAA>"},
        {"§4", "</font><font color=#FFA39E>"},
        {"§5", "</font><font color=#FB98BF>"},
        {"§6", "</font><font color=#B0E2FF>"},
        {"§7", "</font><font color=#d3cad3>"},
        {"§8", "</font><font color=#555555>"},
        {"§9", "</font><font color=#5555FF>"},
        {"§a", "</font><font color=#55FF55>"},
        {"§b", "</font><font color=#70E3FF>"},
        {"§c", "</font><font color=#FF5555>"},
        {"§d", "</font><font color=#e9a2eb>"},
        {"§e", "</font><font color=#ffcf75>"},
        {"§f", "</font><font color=#FFFFFF>"},
        {"§h", "</font><font color=#FF0000>"}
    };

    public static android.text.Spanned FontColor(String text) {

        for (int e = 0; e < colors.length; e++) {
            text = join(text.split(colors[e][0]), colors[e][1]);
        }
        return android.text.Html.fromHtml(text);
    }

    public static String join(Iterator it, String str) {
        if (!it.hasNext()) {
            return "";
        }
        String start = it.next().toString();
        if (!it.hasNext()) {
            return start;
        }
        StringBuilder sb = new StringBuilder(64).append(start);
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static String join(Collection collection, String str) {
        return join(collection.iterator(), str);
    }

    public static String join(String[] strArr, String str) {
        return join(Arrays.asList(strArr), str);
    }

    public static GradientDrawable Portable(Object hex, Object round) {
        if (isArray(hex)) {
            String[] hex2 = (String[]) hex;
            int[] color = new int[hex2.length - 1];
            for (int a = 1; a < hex2.length; a++) {
                color[a - 1] = hexColor(hex2[a]);
            }
            return new roundRect(color, round, hex2[0]);
        }else{
            
            return new roundRect((hex != null ? hexColor((String)hex) : null), round);
        }
    }

    public static GradientDrawable roundBG(Object hex, Object round, String f, Object s) {
        if (isArray(hex)) {
            String[] hex2 = (String[]) hex;
            return new roundRect(new int[] {Color.parseColor(hex2[0]), Color.parseColor(hex2[1])}, round, hex2[2], 0);
        } else {
            return new roundRect(Color.parseColor((String) (hex)), round, f, s);
        }
    }

    public static GradientDrawable roundBG(Object hex, Object round, String f) {
        if (isArray(hex)) {
            String[] hex2 = (String[]) hex;
            return new roundRect(new int[] {Color.parseColor(hex2[0]), Color.parseColor(hex2[1])}, round, hex2[2], 0);
        } else {
            return new roundRect(Color.parseColor((String) (hex)), round, f, null);
        }
    }

    public static GradientDrawable roundBG(Object hex, Object round) {
        if (isArray(hex)) {
            String[] hex2 = (String[]) hex;
            return new roundRect(new int[] {Color.parseColor(hex2[0]), Color.parseColor(hex2[1])}, round, hex2[2], 0);
        } else {
            return new roundRect(Color.parseColor((String) (hex)), round, null);
        }
    }

    public static GradientDrawable roundBG(Object hex) {
        if (isArray(hex)) {
            String[] hex2 = (String[]) hex;
            return new roundRect(new int[] {Color.parseColor(hex2[0]), Color.parseColor(hex2[1])}, 0, hex2[2], 0);
        } else {
            return new roundRect(Color.parseColor((String) (hex)), 0);
        }
    }

    public static int hexColor(String c) {
        return android.graphics.Color.parseColor(c);
    };

    public static boolean isArray(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.getClass().isArray();
    }
}
