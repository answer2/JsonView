package com.answer.library.JsonView.utils;

import com.answer.library.JsonView.manager.VarManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String TAG = "StringToValue";

        public static String var(String original) {
            String result = original;
            List<String> varList = matchVar(original);
            for (String varKey : varList) {
                String replacement = Objects.toString(VarManager.get(varKey), "");
                result = result.replace("{$" + varKey + "}", replacement);
            }
            return result;
        }

        public static Object[] varObject(String original) {
            List<String> varList = matchVar(original);
            Object[] result = new Object[varList.size()];
            for (int i = 0; i < varList.size(); i++) {
                result[i] = VarManager.get(varList.get(i));
            }
            return result;
        }

        public static Object varObjectOne(String original) {
            List<String> varList = matchVar(original);
            return varList.isEmpty() ? null : VarManager.get(varList.get(0));
        }

        public static Object invoke(String original) {
            String type = intercept(original, "(", ")");
            String value = original.replace("(" + type + ")", "");
            Class<?> clazz = ClassUtil.getClassType(type);
            Object object = ClassUtil.getValueClassType(clazz, value);

            return EmptyUtil.isNotNull(object) ? object : VarManager.get(matchVarOne(original));
        }

        public static Object[] invokeArgs(Object instance, List<String> list) {
            Object[] args = new Object[list.size() + 1];
            args[0] = instance;

            for (int i = 1; i < list.size(); i++) {
                args[i] = parseArgument(list.get(i));
            }

            return args;
        }

        public static Object[] invokeArgs(List<String> list) {
            Object[] args = new Object[list.size()];
            for (int i = 0; i < list.size(); i++) {
                args[i] = parseArgument(list.get(i));
            }
            return args;
        }

        private static Object parseArgument(String original) {
            String type = intercept(original, "(", ")");
            String value = original.replace("(" + type + ")", "");
            Class<?> clazz = ClassUtil.getClassType(type);
            Object object = ClassUtil.getValueClassType(clazz, value);

            return EmptyUtil.isNotNull(object) ? object : VarManager.get(matchVarOne(original));
        }

        public static String intercept(String str, String start, String end) {
            int startIndex = str.indexOf(start);
            int endIndex = str.indexOf(end, startIndex + start.length());

            return (startIndex != -1 && endIndex != -1) ? str.substring(startIndex + start.length(), endIndex) : null;
        }

        public static String interceptEnd(String str, String start, String end) {
            int startIndex = str.indexOf(start);
            int endIndex = str.lastIndexOf(end, startIndex + start.length());

            return (startIndex != -1 && endIndex != -1) ? str.substring(startIndex + start.length(), endIndex) : null;
        }

        public static List<String> matchVar(String s) {
            List<String> results = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\{\\$([\\w]+)\\}");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                results.add(matcher.group(1));
            }
            return results;
        }

        public static String matchVarOne(String s) {
            List<String> varList = matchVar(s);
            return varList.isEmpty() ? null : varList.get(0);
        }
   
}
