package iie.ershi.hw.platform;

import iie.ershi.hw.platform.common.GlobalConfig;

import java.io.File;

import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class HardwarePlatformSetup implements Setup{
	@Override
	public void destroy(NutConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(NutConfig config) {
		// 首先设置系统环境变量
		String webdir = new File(config.getAppRoot()).toPath().normalize()
				.toString(); // 未使用getCanonicalPath，避免需要捕获异常

		// 初始化系统环境变量
		GlobalConfig.addContextValue("context.path", config.getServletContext()
				.getContextPath());
		GlobalConfig.addContextValue("web.dir", webdir);
		GlobalConfig.addContextValue("webinf.dir", webdir + "/WEB-INF");
		GlobalConfig.addContextValue("upload.dir", webdir
				+ "/WEB-INF/uploadTmp");
		GlobalConfig.addContextValue("conf.dir", webdir
				+ "/conf");
		GlobalConfig.addContextValue("amchartData.dir", webdir
				+ "/amchartData");
	}
}
