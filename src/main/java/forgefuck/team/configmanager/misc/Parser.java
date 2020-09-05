package forgefuck.team.configmanager.misc;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import forgefuck.team.configmanager.objects.ModuleField;

public class Parser {
    
    public static Object parseFieldValue(ModuleField field) {
        return parseFieldValue(field.getName(), field.getType(), field.getValue());
    }
    
    public static Object parseFieldValue(ModuleField field, String newVal) {
        return parseFieldValue(field.getName(), field.getType(), newVal);
    }
    
    public static Object parseFieldValue(String name, String type, String val) {
        switch (type) {
        case "char":
            try {
                return Charset.forName(val);
            } catch(UnsupportedCharsetException e) {
                return null;
            }
        case "boolean":
            return "true".equals(val.toLowerCase()) || "false".equals(val.toLowerCase()) ? Boolean.parseBoolean(val) : null;
        case "byte":
            try {
                return Byte.parseByte(val);
            } catch(NumberFormatException e) {
                return null;
            }
        case "double":
            try {
                return Double.parseDouble(val);
            } catch(NumberFormatException e) {
                return null;
            }
        case "float":
            try {
                return Float.parseFloat(val);
            } catch(NumberFormatException e) {
                return null;
            }
        case "int":
            try {
                return Integer.parseInt(val);
            } catch(NumberFormatException e) {
                return null;
            }
        case "long":
            try {
                return Long.parseLong(val);
            } catch(NumberFormatException e) {
                return null;
            }
        case "short":
            try {
                return Short.parseShort(val);
            } catch(NumberFormatException e) {
                return null;
            }
        case "java.lang.String":
            return val;
        case "java.util.List":
            try {
                return new GsonBuilder().create().fromJson(val, List.class);
            } catch(JsonSyntaxException e) {
                return null;
            }
        case "java.util.Map":
            try {
                return new GsonBuilder().create().fromJson(val, Map.class);
            } catch(JsonSyntaxException e) {
                return null;
            }
        }
        return null;
    }

}
