package internal.util.key;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyFormatter {

    static public final Pattern KEY_PATTERN = Pattern.compile("(\\d+):(\\d+)");

    static public String getUnitKey(Integer nodeId, Integer unitId) {
        return String.format("%s:%s", nodeId, unitId);
    }

    static public UnitId getUnitId(String key) {
        Matcher m = KEY_PATTERN.matcher(key);
        if(m.matches()) {
            return UnitId.builder().nodeId(Integer.parseInt(m.group(1))).unitId(Integer.parseInt(m.group(2))).build();
        }
        return UnitId.builder().build();
    }
}
