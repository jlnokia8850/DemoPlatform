package iie.ershi.hw.platform.module;

import iie.ershi.hw.platform.common.GlobalConfig;
import iie.ershi.hw.platform.model.SendPacketsModel;
import iie.ershi.hw.platform.model.PreventPacketsModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.lang.Runtime;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
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
	private PreventPacketsModel curPreventPacketsModel = new PreventPacketsModel();

	private List<String> curFileList = new ArrayList<String>();
	List<String> sendTimes = new ArrayList<String>();
	private List<String> sendTimesList = new ArrayList<String>();
	private int defaulted;
	
	private static final  String sendConfpath="/home/fedora/baixu/workplace/ExchangeData/send.txt";
	private static final  String revDatapath = "/home/fedora/baixu/workplace/ExchangeData/receiveData.txt";
	private static final  String dropDatapath = "/home/fedora/baixu/workplace/ExchangeData/dropData.txt";
	private static final  String tcpDatapath = "/home/fedora/baixu/workplace/ExchangeData/tcpData.txt";
	private static final  String udpDatapath = "/home/fedora/baixu/workplace/ExchangeData/udpData.txt";
	private static final  String otherDatapath = "/home/fedora/baixu/workplace/ExchangeData/udpData.txt";
	private static final  String noneDatapath = "/home/fedora/baixu/workplace/ExchangeData/udpData.txt";
	private static final  String preventPath="/home/fedora/baixu/workplace/ExchangeData/prevent.txt";

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////sendpackets//////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@At
	@Ok("jsp:page.sendpackets")
	// @Ok("redirect:/regex/config?init=true&scanover=false")
	public void init(Ioc ioc, HttpServletRequest request) {
		// curSendPacketsModel.setSendTimes("1");
		// curSendPacketsModel.setSrcMac("FF-FF-FF-FF-FF");
		// curSendPacketsModel.setDstMac("FF-FF-FF-FF-FF");
		// curSendPacketsModel.setSrcIP("172.0.0.1");

		defaulted = 0;

		initList();

		request.setAttribute("curFileList", curFileList);
		// request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
	}

	@At
	@Ok("jsp:page.preventpackets")
	public void init_pre(Ioc ioc, HttpServletRequest request) {
		defaulted = 0;
		request.setAttribute("curPreventPacketsModel", curPreventPacketsModel);
	}

	@At
	@Ok("jsp:page.sendpackets")
	public void savePacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {

		curSendPacketsModel.setName(sendPacketsModel.getName());
		if (sendPacketsModel.getName() == "")
			curSendPacketsModel.setName("packet" + (++defaulted));
		curSendPacketsModel.setSendTimes(sendPacketsModel.getSendTimes());
		curSendPacketsModel.setSrcMac(sendPacketsModel.getSrcMac());
		curSendPacketsModel.setDstMac(sendPacketsModel.getDstMac());
		curSendPacketsModel.setSrcIP(sendPacketsModel.getSrcIP());
		curSendPacketsModel.setDstIP(sendPacketsModel.getDstIP());
		curSendPacketsModel.setIpPro(sendPacketsModel.getIpPro());
		curSendPacketsModel.setSrcPort(sendPacketsModel.getSrcPort());
		curSendPacketsModel.setDstPort(sendPacketsModel.getDstPort());
		curSendPacketsModel.setContent(sendPacketsModel.getContent());

		String file_name = GlobalConfig.getContextValue("conf.dir") + "/"
				+ curSendPacketsModel.getName() + ".conf";

		savetoFile(file_name, curSendPacketsModel);

		if (!curFileList.contains(curSendPacketsModel.getName()))
			curFileList.add(curSendPacketsModel.getName());
		// delFileList.add(curSendPacketsModel.getName());

		request.setAttribute("curFileList", curFileList);
		// request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		// System.out.println("the last test:" + file_name);
		// initList();
		// curSendPacketsModel.setSendTimes(packet.getSendTimes());
		// request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		// curSendPacketsModel.setCharsetSize("256");
		// curSendPacketsModel.setRegexAlgor("DFA压缩匹配算法");

		// DecimalFormat df = new DecimalFormat("######0.00");
		// File f = new File(GlobalConfig.getContextValue("upload.dir") +
		// "/regex_Data.txt");
		// double f_size = (double) (f.length() / 1024 / 1024); // MB
		// df.format(f_size);
		// curRegexType.setDataSize(Double.toString(f_size));
		//
		// RuleFileInfo ruleFileInfo =
		// readFileByLines(GlobalConfig.getContextValue("upload.dir") +
		// "/regex_Rule.txt");
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
	@Ok("jsp:page.sendpackets")
	public void loadpacket(Ioc ioc, HttpServletRequest request,
			@Param("filename") String petName) {// 函数用于处理选择包名，显示对应的协议内容。主要是读文件操作

		// System.out.print(petName);

		curSendPacketsModel.setName(petName);

		// 结下来是读文件操作
		String file_name = GlobalConfig.getContextValue("conf.dir") + "/"
				+ petName + ".conf";
		File file = null;
		BufferedReader br = null;
		String strXmlLine = "";
		try {
			file = new File(file_name);// 文件路径
			br = new BufferedReader(new FileReader(file));
			int indx = 0;
			String content = null;
			while ((strXmlLine = br.readLine()) != null) {
				String element = null;
				int bngIndex = strXmlLine.lastIndexOf("="); // 得到最后一个=号
				if (bngIndex >= strXmlLine.length())
					bngIndex = strXmlLine.length() - 1;
				if (bngIndex < 0)
					bngIndex = -1;
				element = strXmlLine.substring(bngIndex + 1,
						strXmlLine.length());
				if (indx < 8) {
					SetElementVale(element, indx);
				} else {
					content = content == null ? element : content + "\n"
							+ element;
				}
				indx++;
			}
			SetElementVale(content, indx);
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		request.setAttribute("curFileList", curFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		return;
	}

	@At
	@Ok("jsp:page.sendpackets")
	public void deltpacket(Ioc ioc, HttpServletRequest request,
			@Param("filename") String petName) {// 函数用于处理选择包名，显示对应的协议内容。主要是读文件操作
		// 结下来是删除文件操作
		String file_name = GlobalConfig.getContextValue("conf.dir") + "/"
				+ petName + ".conf";
		File file = null;
		file = new File(file_name);// 文件路径
		if (file.isFile() && file.exists()) {
			System.gc();// 启动jvm垃圾回收
			file.delete();
		}

		String file_name_pkt = GlobalConfig.getContextValue("sendData.dir")
				+ "/" + petName + ".txt";
		File file_pkt = null;
		file_pkt = new File(file_name_pkt);// 文件路径
		if (file_pkt.isFile() && file_pkt.exists()) {
			System.gc();// 启动jvm垃圾回收
			file_pkt.delete();
		}
		curSendPacketsModel.setName("");
		curSendPacketsModel.setSendTimes("");
		curSendPacketsModel.setSrcMac("");
		curSendPacketsModel.setDstMac("");
		curSendPacketsModel.setSrcIP("");
		curSendPacketsModel.setDstIP("");
		curSendPacketsModel.setIpPro("");
		curSendPacketsModel.setSrcPort("");
		curSendPacketsModel.setDstPort("");
		curSendPacketsModel.setContent("");

		curFileList.remove(petName);
		sendTimesList.remove(petName);
		// delFileList.remove(petName);
		request.setAttribute("curFileList", curFileList);
		// request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
	}

	@At
	@Ok("jsp:page.sendpackets")
	public void sendPacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {

		String file_name = sendConfpath;
		savetoConf(file_name, curSendPacketsModel);

		String pktnum_file = GlobalConfig.getContextValue("sendData.dir") + "/"
				+ curSendPacketsModel.getName() + ".txt";
		savetoPkt(pktnum_file, curSendPacketsModel);
//		System.out.println(System.getProperty("user.dir"));

		try {
			Process processrec = Runtime.getRuntime().exec( "./opt/receiveData");
			Process processblock = Runtime.getRuntime().exec( "./opt/block_packet");
			Process processudp = Runtime.getRuntime().exec( "./opt/div1_udp");
			Process processtcp = Runtime.getRuntime().exec( "./opt/div2_tcp");
			Process processother = Runtime.getRuntime().exec( "./opt/div3_other");
			Process processsend = Runtime.getRuntime().exec( "./opt/send");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("curFileList", curFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
	}

	@At
	@Ok("jsp:page.sendpackets")
	// @Ok("redirect:/platform/init")
	public void resetPacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {//

		curSendPacketsModel.setName("");
		curSendPacketsModel.setSendTimes("");
		curSendPacketsModel.setSrcMac("");
		curSendPacketsModel.setDstMac("");
		curSendPacketsModel.setSrcIP("");
		curSendPacketsModel.setDstIP("");
		curSendPacketsModel.setIpPro("");
		curSendPacketsModel.setSrcPort("");
		curSendPacketsModel.setDstPort("");
		curSendPacketsModel.setContent("");

		request.setAttribute("curFileList", curFileList);
		// request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		// add your code about send packets here...
	}

	public void savetoFile(String file_name, SendPacketsModel send_bean)// 把数据包保存到文件
	{
		try {
			String pkt_num = "";
			File file = new File(file_name);
			if (!file.exists()) {
				file.createNewFile();// 创建文件
				file.mkdir();// 创建目录
				// file.delete();//调用完马上就删除了
				// file.deleteOnExit();//程序退出时，可以作为临时文件。
				pkt_num = send_bean.getSendTimes();
			}
			// else {
			// BufferedReader br = null;
			// String strXmlLine = "";
			// String content = null;
			// List<String> list = new ArrayList<String>();
			// try {
			// br = new BufferedReader(new FileReader(file));
			// while ((strXmlLine = br.readLine()) != null) {
			// String element = null;
			// int bngIndex = strXmlLine.lastIndexOf("="); // 得到最后一个=号
			// if (bngIndex >= strXmlLine.length())
			// bngIndex = strXmlLine.length() - 1;
			// if (bngIndex < 0)
			// bngIndex = -1;
			// element = strXmlLine.substring(bngIndex + 1,
			// strXmlLine.length());
			// if (!(bngIndex == -1 || bngIndex == 12))
			// list.add(element);
			// else
			// content = content==null ? element : content + "\n"
			// + element;
			// }
			// list.add(content);
			// br.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// String[] ss = (String[]) list.toArray(new String[0]);
			// if (list.get(1).equals(send_bean.getSrcMac())
			// && list.get(2).equals(send_bean.getDstMac())
			// && list.get(3).equals(send_bean.getSrcIP())
			// && list.get(4).equals(send_bean.getDstIP())
			// && list.get(5).equals(send_bean.getIpPro())
			// && list.get(6).equals(send_bean.getSrcPort())
			// && list.get(7).equals(send_bean.getDstPort())
			// && list.get(8).equals(send_bean.getContent())) {
			// int nums = Integer.parseInt(list.get(0))
			// + Integer.parseInt(send_bean.getSendTimes());
			// list.set(0, Integer.toString(nums));
			// } else {
			// list.set(0, send_bean.getSendTimes());
			// }
			// pkt_num = list.get(0);
			// }

			BufferedWriter writer = new BufferedWriter(new FileWriter(file,
					false));

			writer.write("PKT_NUM=" + "" + "\n");
			writer.write("SRC_MAC=" + send_bean.getSrcMac() + "\n");
			writer.write("DST_MAC=" + send_bean.getDstMac() + "\n");
			writer.write("SRC_IP=" + send_bean.getSrcIP() + "\n");
			writer.write("DST_IP=" + send_bean.getDstIP() + "\n");
			writer.write("IP_PRO=" + send_bean.getIpPro() + "\n");
			writer.write("SRC_PORT=" + send_bean.getSrcPort() + "\n");
			writer.write("DST_PORT=" + send_bean.getDstPort() + "\n");
			writer.write("DATA_CONTENT=" + send_bean.getContent() + "\n");

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void savetoConf(String file_name, SendPacketsModel send_bean)// 把数据包保存到文件
	{
		try {
			File file = new File(file_name);
			if (!file.exists()) {
				try {
					file.createNewFile();
					file.mkdir();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String pkt_num = (send_bean.getSendTimes() == null || send_bean
					.getSendTimes().length() == 0) ? "0" : send_bean
					.getSendTimes();
			int k = Integer.parseInt(pkt_num);
			pkt_num = Integer.toHexString(k);

			String Srcmacconf = send_bean.getSrcMac().replace(":", "");
			String Dstmacconf = send_bean.getDstMac().replace(":", "");

			int[] nums = new int[4];
			String[] Srcip = send_bean.getSrcIP().split("\\.");
			for (int i = 0; i < Srcip.length; i++) {
				nums[i] = Integer.parseInt(Srcip[i]);
				if (nums[i] < 16) {
					Srcip[i] = "0" + Integer.toHexString(nums[i]);
				} else {
					Srcip[i] = Integer.toHexString(nums[i]);
				}
			}
			String[] Dstip = send_bean.getDstIP().split("\\.");
			for (int i = 0; i < Dstip.length; i++) {
				nums[i] = Integer.parseInt(Dstip[i]);
				if (nums[i] < 16) {
					Dstip[i] = "0" + Integer.toHexString(nums[i]);
				} else {
					Dstip[i] = Integer.toHexString(nums[i]);
				}
			}

			int pro = Integer.parseInt(send_bean.getIpPro());
			String IpPro = "";
			if (pro < 16) {
				IpPro = "0" + Integer.toHexString(pro);
			} else {
				IpPro = Integer.toHexString(pro);
			}

			String Srcport = send_bean.getSrcPort();
			int port = Integer.parseInt(Srcport);
			Srcport = Integer.toHexString(port);

			String Dstport = send_bean.getDstPort();
			port = Integer.parseInt(Dstport);
			Dstport = Integer.toHexString(port);

			char[] content = send_bean.getContent().toCharArray();
			String cont = "";
			for (int i = 0; i < content.length; i++) {
				cont = cont + Integer.toHexString((int) content[i]);
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(file,
					false));

			writer.write("PKT_NUM=" + pkt_num + "\n");
			writer.write("SRC_MAC=" + Srcmacconf + "\n");
			writer.write("DST_MAC=" + Dstmacconf + "\n");
			writer.write("SRC_IP=" + Srcip[0] + Srcip[1] + Srcip[2] + Srcip[3]
					+ "\n");
			writer.write("DST_IP=" + Dstip[0] + Dstip[1] + Dstip[2] + Dstip[3]
					+ "\n");
			writer.write("IP_PRO=" + IpPro + "\n");
			writer.write("SRC_PORT=" + Srcport + "\n");
			writer.write("DST_PORT=" + Dstport + "\n");
			writer.write("DATA_CONTENT=" + cont + "\n");

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void savetoPkt(String file_name, SendPacketsModel send_bean)// 把数据包保存到文件
	{
		String pkt_num = "";
		try {
			File file = new File(file_name);
			if (!file.exists()) {
				try {
					file.createNewFile();
					file.mkdir();
				} catch (IOException e) {
					e.printStackTrace();
				}
				pkt_num = send_bean.getSendTimes();
				if (pkt_num == null || pkt_num.length() == 0)
					pkt_num = "0";
				sendTimesList.add(send_bean.getName());
			} else {
				BufferedReader br = null;
				String strXmlLine = "";
				try {
					br = new BufferedReader(new FileReader(file));
					while ((strXmlLine = br.readLine()) != null) {
						int bngIndex = strXmlLine.lastIndexOf("="); // 得到最后一个=号
						if (bngIndex < 0 || bngIndex >= strXmlLine.length())
							bngIndex = strXmlLine.length() - 1;
						pkt_num = strXmlLine.substring(bngIndex + 1,
								strXmlLine.length());
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (pkt_num == null || pkt_num.length() == 0)
					pkt_num = "0";
				if (send_bean.getSendTimes() == null
						|| send_bean.getSendTimes().length() == 0)
					send_bean.setSendTimes("0");
				pkt_num = Integer.toString(Integer.parseInt(pkt_num)
						+ Integer.parseInt(send_bean.getSendTimes()));
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(file,
					false));

			writer.write("SendTimes=" + pkt_num);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initList() {
		String file_name = GlobalConfig.getContextValue("conf.dir") + "/";
		File f = new File(file_name);
		File[] t = f.listFiles();

		curFileList.clear();// 先进行清空操作
		// delFileList.clear();

		for (int i = 0; i < t.length; i++) {
			int endIndex = t[i].getName().lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
			if (endIndex < 0 || endIndex > t[i].getName().length())
				endIndex = t[i].getName().length();
			String tempsuffix = t[i].getName().substring(0, endIndex);
			curFileList.add(tempsuffix);
			// delFileList.add(tempsuffix);
		}
		String file_name_pkt = GlobalConfig.getContextValue("sendData.dir")
				+ "/";
		File f_pkt = new File(file_name_pkt);
		File[] t_pkt = f_pkt.listFiles();

		sendTimesList.clear();
		for (int i = 0; i < t_pkt.length; i++) {
			int endIndex = t_pkt[i].getName().lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
			if (endIndex < 0 || endIndex > t_pkt[i].getName().length())
				endIndex = t_pkt[i].getName().length();
			String tempsuffix = t_pkt[i].getName().substring(0, endIndex);
			sendTimesList.add(tempsuffix);
			// delFileList.add(tempsuffix);
		}
	}

	public void SetElementVale(String value, int indx) {
		// System.out.println(value + " " + indx);
		if (indx == 0)
			curSendPacketsModel.setSendTimes(value);
		if (indx == 1)
			curSendPacketsModel.setSrcMac(value);
		if (indx == 2)
			curSendPacketsModel.setDstMac(value);
		if (indx == 3)
			curSendPacketsModel.setSrcIP(value);
		if (indx == 4)
			curSendPacketsModel.setDstIP(value);
		if (indx == 5)
			curSendPacketsModel.setIpPro(value);
		if (indx == 6)
			curSendPacketsModel.setSrcPort(value);
		if (indx == 7)
			curSendPacketsModel.setDstPort(value);
		if (indx > 7)
			curSendPacketsModel.setContent(value);
		// .....
	}

	// //////////////////////////////////////////////////////////////////////////////////////

	@At
	@Ok("raw:xml")
	public String getSendData(Ioc ioc, HttpServletRequest request,
			@Param("nocache") String randomString) throws IOException {

		String line = ""; // 用来保存第一行读取的内容
		createDataSendTimes();
		// File f = new File(GlobalConfig.getContextValue("upload.dir")
		// + "/sendnums.txt");

		// System.out.println(GlobalConfig.getContextValue("upload.dir")
		// + "/sendnums.txt");
		// if (!f.exists())
		// line = "100;130";//line 的内容就是显示的内容
		// else {
		// InputStream is = new FileInputStream(f);
		// BufferedReader reader = new BufferedReader(
		// new InputStreamReader(is));
		// line = reader.readLine();

		// reader.close();
		// }
		for (int i = 0; i < sendTimes.size(); i++) {
			line = line + sendTimes.get(i) + ";";
		}
		String speeds[] = line.split(";");

		for (int i = 0; i < speeds.length; i++) {
			// System.out.println("element"+speeds[i]);//line显示的内容12,11,10,20,15,14
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
					.addText(sendTimesList.get(i));

			consumptionGraphEle.addElement("value").addAttribute("xid", "" + i)
					.addAttribute("color", "#318DBD").addText(speeds[i]);
		}

		return document.asXML();

	}

	private void createDataSendTimes() {
		sendTimes.clear();
		for (int i = 0; i < sendTimesList.size(); i++) {
			String file_name = GlobalConfig.getContextValue("sendData.dir")
					+ "/" + sendTimesList.get(i) + ".txt";
			File file = null;
			BufferedReader br = null;
			String strXmlLine = "";
			try {
				file = new File(file_name);// 文件路径
				br = new BufferedReader(new FileReader(file));
				while ((strXmlLine = br.readLine()) != null) {
					String element = null;
					int bngIndex = strXmlLine.lastIndexOf("="); // 得到最后一个=号
					if (bngIndex < 0 || bngIndex >= strXmlLine.length())
						bngIndex = strXmlLine.length() - 1;
					element = strXmlLine.substring(bngIndex + 1,
							strXmlLine.length());
					sendTimes.add(element);
					break;
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	// private void createDataSendTimes() {
	// sendTimes.clear();
	// for (int i = 0; i < curFileList.size(); i++) {
	// // 结下来是读文件操作
	// String file_name = GlobalConfig.getContextValue("conf.dir") + "/"
	// + curFileList.get(i) + ".conf";
	// File file = null;
	// BufferedReader br = null;
	// String strXmlLine = "";
	// try {
	// file = new File(file_name);// 文件路径
	// br = new BufferedReader(new FileReader(file));
	// while ((strXmlLine = br.readLine()) != null) {
	// // System.out.println(strXmlLine);
	// String element = null;
	// int bngIndex = strXmlLine.lastIndexOf("="); // 得到最后一个=号
	// if (bngIndex < 0 || bngIndex >= strXmlLine.length())
	// bngIndex = strXmlLine.length() - 1;
	// element = strXmlLine.substring(bngIndex + 1,
	// strXmlLine.length());
	// sendTimes.add(element);
	// break;
	// }
	// br.close();
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// for (int i = 0; i < sendTimes.size(); i++) {
	// System.out.print(sendTimes.get(i) + " *******\n");
	// }
	// return;
	// }

	// //////////////////////////////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////receivepackets//////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@At
	@Ok("jsp:page.recpackets")
	public void recpackets() {

	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDatarec(String[] args) throws DocumentException,
			IOException {

		File file = new File(revDatapath);
		// File file = new File(GlobalConfig.getContextValue("amchartData.dir")
		// + "/" + "total_packet.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_rec.xml";
		File configFile = new File(path);
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDatadrop(String[] args) throws DocumentException,
			IOException {

		File file = new File(dropDatapath);
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_drop.xml";
		File configFile = new File(path);
		// File configFile = new
		// File("WebContent/amchartData/amcolumn_data1.xml");
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDatatcp(String[] args) throws DocumentException,
			IOException {

		File file = new File(tcpDatapath);
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_tcp.xml";
		File configFile = new File(path);
		// File configFile = new
		// File("WebContent/amchartData/amcolumn_data1.xml");
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDataudp(String[] args) throws DocumentException,
			IOException {

		File file = new File(udpDatapath);
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_udp.xml";
		File configFile = new File(path);
		// File configFile = new
		// File("WebContent/amchartData/amcolumn_data1.xml");
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////preventpackets//////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@At
	@Ok("jsp:page.preventpackets")
	public void preventpackets() {

	}

	@At
	@Ok("jsp:page.preventpackets")
	public void preventPacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") PreventPacketsModel preventPacketsModel) {

		curPreventPacketsModel.setIP(preventPacketsModel.getIP());
		String file_name = preventPath;
		savetoprevent(file_name, curPreventPacketsModel, 0);

		request.setAttribute("curPreventPacketsModel", curPreventPacketsModel);
	}

	public void savetoprevent(String file_name, PreventPacketsModel send_bean,
			int s)// 把数据包保存到文件
	{
		try {
			File file = new File(file_name);
			if (!file.exists()) {
				try {
					file.createNewFile();
					file.mkdir();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			int[] nums = new int[4];
			String[] Srcip = send_bean.getIP().split("\\.");
			for (int i = 0; i < Srcip.length; i++) {
				nums[i] = Integer.parseInt(Srcip[i]);
				if (nums[i] < 16) {
					Srcip[i] = "0" + Integer.toHexString(nums[i]);
				} else {
					Srcip[i] = Integer.toHexString(nums[i]);
				}
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file,
					false));

			if (s == 0)
				writer.write("7" + "\n");
			else
				writer.write("4" + "\n");
			writer.write(Srcip[3] + Srcip[2] + Srcip[1] + Srcip[0] + "\n");

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@At
	@Ok("jsp:page.preventpackets")
	public void recoverPacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") PreventPacketsModel preventPacketsModel) {

		String file_name = preventPath;
		savetoprevent(file_name, curPreventPacketsModel, 1);

		request.setAttribute("curPreventPacketsModel", curPreventPacketsModel);
	}

	@At
	@Ok("jsp:page.preventpackets")
	public void resetprePacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") PreventPacketsModel preventPacketsModel) {

		curPreventPacketsModel.setIP("");
		request.setAttribute("curPreventPacketsModel", curPreventPacketsModel);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////divpackets//////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@At
	@Ok("jsp:page.divpackets")
	public void divpackets() {

	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDatadiv1(String[] args) throws DocumentException,
			IOException {

		File file = new File(udpDatapath);
		// File file = new File(GlobalConfig.getContextValue("amchartData.dir")
		// + "/" + "total_packet.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_rec.xml";
		File configFile = new File(path);
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDatadiv2(String[] args) throws DocumentException,
			IOException {

		File file = new File(
				"/home/fedora/baixu/workplace/DemoPlatform/WebContent/amchartData/total_packet.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_drop.xml";
		File configFile = new File(path);
		// File configFile = new
		// File("WebContent/amchartData/amcolumn_data1.xml");
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDatadiv3(String[] args) throws DocumentException,
			IOException {

		File file = new File(
				"/home/fedora/baixu/workplace/DemoPlatform/WebContent/amchartData/total_packet.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_tcp.xml";
		File configFile = new File(path);
		// File configFile = new
		// File("WebContent/amchartData/amcolumn_data1.xml");
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	@At
	@Ok("raw:xml")
	public String getAmchartsDatadiv4(String[] args) throws DocumentException,
			IOException {

		File file = new File(
				"/home/fedora/baixu/workplace/DemoPlatform/WebContent/amchartData/total_packet.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		String path = GlobalConfig.getContextValue("amchartData.dir") + "/"
				+ "amcolumn_data_udp.xml";
		File configFile = new File(path);
		// File configFile = new
		// File("WebContent/amchartData/amcolumn_data1.xml");
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

		// XMLWriter out = new XMLWriter(new FileWriter(path));
		// out.write(doc);
		// out.close();

		return doc.asXML();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws DocumentException,
			IOException {
		File file = new File("./WebContent/amchartData/total_packet.txt");
		List list = new ArrayList();
		int[] nums = { 0 };

		File configFile = new File(
				"./WebContent/amchartData/amcolumn_data1.xml");
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

			// System.out.println((list.get(list.size() - 2) == '\n'));

			// System.out.println((list.get(list.size() - 2) == '\n'));
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
		XMLWriter out = new XMLWriter(new FileWriter(
				"./WebContent/amchartData/amcolumn_data1.xml"));
		out.write(doc);
		out.close();
		return;
	}
}
