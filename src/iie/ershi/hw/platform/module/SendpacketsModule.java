package iie.ershi.hw.platform.module;

import iie.ershi.hw.platform.common.GlobalConfig;
import iie.ershi.hw.platform.model.SendPacketsModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

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
	@Ok("jsp:page.recpackets")
	public void recpackets() {

	}

	@At
	@Ok("jsp:page.sendpackets")
	// @Ok("redirect:/platform/init")
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
	@Ok("jsp:page.recpackets")
	public void getAmchartsData(String[] args) throws DocumentException,IOException {
		File file = new File("d:/regex_Speed.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		File configFile = new File("d:/amcolumn_data1.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		doc = reader.read(configFile);

		try {
			BufferedReader bw = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bw.readLine()) != null) {
				if (line.charAt(0) == '\r' || line.charAt(0) == '\n')
					list.add("0");
				else
					list.add(line);

			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		nums = new int[12];
		for (int i = 0; i < list.size() && i < 12; i++) {
			String s = (String) list.get(i);
			nums[i] = Integer.parseInt(s);
		}

		Element root = doc.getRootElement();
		Element graphs = root.element("graphs");
		Element graph = graphs.element("graph");
		Element val = graph.element("value");
		graph.clearContent();
		for (int i = 0; i < 12; i++) {
			graph.addElement("value").addAttribute("xid", "" + i)
					.addText("" + nums[i]);
		}
		
		XMLWriter out = new XMLWriter(new FileWriter("d:/amcolumn_data1.xml"));
		out.write(doc);
		out.close();
		return;
	}

	public static void main(String[] args) throws DocumentException,
			IOException {
		File file = new File("d:/regex_Speed.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		File configFile = new File("d:/amcolumn_data1.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		doc = reader.read(configFile);

		try {
			BufferedReader bw = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bw.readLine()) != null) {
				if (line.charAt(0) == '\r' || line.charAt(0) == '\n')
					list.add("0");
				else
					list.add(line);

			}
			bw.close();
//			System.out.println((list.get(list.size() - 2) == '\n'));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// for (Iterator i = list.iterator(); i.hasNext();) {
		// String str = (String) i.next();
		// System.out.println(str);
		// }

		nums = new int[12];
		for (int i = 0; i < list.size() && i < 12; i++) {
			String s = (String) list.get(i);
			nums[i] = Integer.parseInt(s);
		}

		for (int i = 0; i < 12; i++) {
			System.out.println(nums[i]);
		}

		Element root = doc.getRootElement();
		Element graphs = root.element("graphs");
		Element graph = graphs.element("graph");
		Element val = graph.element("value");
		graph.clearContent();
		for (int i = 0; i < 12; i++) {
			graph.addElement("value").addAttribute("xid", "" + i)
					.addText("" + nums[i]);
		}
		// System.out.println(val.getTextTrim());
		// val.setText("11");
		XMLWriter out = new XMLWriter(new FileWriter("d:/amcolumn_data1.xml"));
		out.write(doc);
		out.close();
		return;
	}

}
