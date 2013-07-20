package iie.ershi.hw.platform.module;

import iie.ershi.hw.platform.common.GlobalConfig;
import iie.ershi.hw.platform.model.SendPacketsModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean()
@InjectName
@At("/platform")
public class SendpacketsModule {
	private Logger logger = Logger.getLogger(this.getClass());

	private SendPacketsModel curSendPacketsModel = new SendPacketsModel();

	@At
	@Ok("jsp:page.sendpackets")
	// @Ok("redirect:/regex/config?init=true&scanover=false")
	public void init(Ioc ioc, HttpServletRequest request) {
		curSendPacketsModel.setSendTimes("1");
		curSendPacketsModel.setSrcMac("aaa");
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
	}

	@At
	@Ok("jsp:page.sendpackets")
	//@Ok("redirect:/platform/init")
	public void savePacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {
		curSendPacketsModel.setSendTimes(sendPacketsModel.getSendTimes());
		curSendPacketsModel.setSrcMac(sendPacketsModel.getSrcMac());

		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		
		
		// curSendPacketsModel.setSendTimes(packet.getSendTimes());
		// request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		// curSendPacketsModel.setCharsetSize("256");
		// curSendPacketsModel.setRegexAlgor("DFA压缩匹配算法");

		// DecimalFormat df = new DecimalFormat("######0.00");
		// File f = new File(GlobalConfig.getContextValue("upload.dir")
		// + "/regex_Data.txt");
		// double f_size = (double) (f.length() / 1024 / 1024); // MB
		// df.format(f_size);
		// curRegexType.setDataSize(Double.toString(f_size));
		//
		// RuleFileInfo ruleFileInfo = readFileByLines(GlobalConfig
		// .getContextValue("upload.dir") + "/regex_Rule.txt");
		// curRegexType.setRegexNum(String.valueOf(ruleFileInfo.pRulesNum));
		// curRegexType.setRegexLength(String.valueOf(ruleFileInfo.pRulesMinLen)
		// + "-" + String.valueOf(ruleFileInfo.pRulesMaxLen));
		//
		// curRegexModel.setDataFile(GlobalConfig.getContextValue("upload.dir")
		// + "/regex_Data.txt");
		// curRegexModel.setRuleFile(GlobalConfig.getContextValue("upload.dir")
		// + "/regex_Rule.txt");
		// curRegexModel.setK(k);

	}
	
	@At
	@Ok("raw:xml")
	public String getAmchartsData() throws IOException {

		String line; // 用来保存第一行读取的内容

		File f = new File(GlobalConfig.getContextValue("upload.dir")
				+ "/regex_Speed.txt");

		if (!f.exists())
			line = "0;0;0;0;0;0;";
		else {
			InputStream is = new FileInputStream(f);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			line = reader.readLine();

			reader.close();
		}
		String speeds[] = line.split(";");
		System.out.println(speeds.length);

		for (int i = 0; i < speeds.length; i++) {
			System.out.println(speeds[i]);
		}

		Document document = DocumentHelper.createDocument();
		Element chartEle = document.addElement("chart");
		Element seriesEle = chartEle.addElement("series");
		Element graphsEle = chartEle.addElement("graphs");
		Element consumptionGraphEle = graphsEle.addElement("graph")
				.addAttribute("gid", "2").addAttribute("color", "#9FCD95")
				.addAttribute("gradient_fill_colors", "#99CCFF, #000FFF");

		for (int i = 0; i < speeds.length; i++) {
			int k = i + 1;
			seriesEle.addElement("value").addAttribute("xid", "" + i)
					.addText("" + k);

			consumptionGraphEle.addElement("value").addAttribute("xid", "" + i)
					.addAttribute("color", "#318DBD").addText(speeds[i]);
		}

		return document.asXML();

	}

	public static void main(String[] args) throws IOException {

		System.out.println("test");

		String line; // 用来保存第一行读取的内容

		File f = new File("D:/regex_Speed.txt");

		if (!f.exists())
			line = "";

		InputStream is = new FileInputStream(f);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		line = reader.readLine();

		reader.close();

		System.out.println(line);

		String a[] = line.split(";");

		System.out.println(a.length);

		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}

		return;

	}

}
