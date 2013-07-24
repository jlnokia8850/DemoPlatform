package iie.ershi.hw.platform.common;



import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 系统全局配置
 * @author JiangLei
 *
 */
public class GlobalConfig {
	/**
	 * 应用会话过期时间，单位秒，缺省为60分钟
	 */
	public static int sessionTimeout = 3600;
	
	public static Locale defaultLocale;
	
	/**
	 * 保存可定制的系统变量，类型不限
	 */
	private static Map<String, Object> ctx = Collections.synchronizedMap(new LinkedHashMap<String, Object>());
	
	public static void setCtx(Map<String, Object> ctx) {
		if (ctx != null)
			GlobalConfig.ctx.putAll(ctx);
	}
	
	public static void addContextValue(String key, Object value) {
		if (value != null)
			ctx.put(key, value);
	}
	
	/**
	 * 获取指定类型的定制系统变量，如果类型不符则返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getContextValueAs(Class<T> classOfT, String key) {
		Object value = ctx.get(key);
		if (value != null && classOfT != null && classOfT.isAssignableFrom(value.getClass()))
			return (T) value;
		return null;
	}
	
	/**
	 * 获取定制系统变量
	 */
	public static Object getContextValue(String key) {
		return ctx.get(key);
	}
	
}