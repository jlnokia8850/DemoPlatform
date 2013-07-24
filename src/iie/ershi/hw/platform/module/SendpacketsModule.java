package iie.ershi.hw.platform.module;

import iie.ershi.hw.platform.common.GlobalConfig;
import iie.ershi.hw.platform.model.SendPacketsModel;

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

	
	private List<String> curFileList=new ArrayList<String>();
	//private List<String> delFileList=new ArrayList<String>();
	private int defaulted;
	 
	@At
	@Ok("jsp:page.sendpackets")
	// @Ok("redirect:/regex/config?init=true&scanover=false")
	public void init(Ioc ioc, HttpServletRequest request) {
		//curSendPacketsModel.setSendTimes("1");
		//curSendPacketsModel.setSrcMac("FF-FF-FF-FF-FF");
		//curSendPacketsModel.setDstMac("FF-FF-FF-FF-FF");
		//curSendPacketsModel.setSrcIP("172.0.0.1");
		
		defaulted=0;
		
		initList();
		
		request.setAttribute("curFileList", curFileList);
		//request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
	}

	@At
	@Ok("jsp:page.sendpackets")
	public void savePacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {
		
		curSendPacketsModel.setName(sendPacketsModel.getName());
	
		if(sendPacketsModel.getName()=="")curSendPacketsModel.setName("packet"+ (++defaulted));
		curSendPacketsModel.setSendTimes(sendPacketsModel.getSendTimes());
		curSendPacketsModel.setSrcMac(sendPacketsModel.getSrcMac());
		curSendPacketsModel.setDstMac(sendPacketsModel.getDstMac());
		curSendPacketsModel.setSrcIP(sendPacketsModel.getSrcIP());
		curSendPacketsModel.setDstIP(sendPacketsModel.getDstIP());
		curSendPacketsModel.setIpPro(sendPacketsModel.getIpPro());
		curSendPacketsModel.setSrcPort(sendPacketsModel.getSrcPort());
		curSendPacketsModel.setDstPort(sendPacketsModel.getDstPort());
		curSendPacketsModel.setContent(sendPacketsModel.getContent());
		
		String file_name= GlobalConfig.getContextValue("conf.dir")+"/"+curSendPacketsModel.getName()+".conf";

		savetoFile(file_name, curSendPacketsModel);
		
		curFileList.add(curSendPacketsModel.getName());
		//delFileList.add(curSendPacketsModel.getName());
		
		request.setAttribute("curFileList", curFileList);
		//request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		System.out.println("the last test:"+file_name);
		//initList();
		// curSendPacketsModel.setSendTimes(packet.getSendTimes());
		// request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		// curSendPacketsModel.setCharsetSize("256");
		// curSendPacketsModel.setRegexAlgor("DFA压缩匹配算法");

		// DecimalFormat df = new DecimalFormat("######0.00");
		 //File f = new File(GlobalConfig.getContextValue("upload.dir") + "/regex_Data.txt");
		 //double f_size = (double) (f.length() / 1024 / 1024); // MB
		// df.format(f_size);
		// curRegexType.setDataSize(Double.toString(f_size));
		//
		// RuleFileInfo ruleFileInfo = readFileByLines(GlobalConfig.getContextValue("upload.dir") + "/regex_Rule.txt");
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
			@Param("filename") String petName){//函数用于处理选择包名，显示对应的协议内容。主要是读文件操作
	
		System.out.print(petName);
		
		curSendPacketsModel.setName(petName);
		
		//结下来是读文件操作
		String file_name= GlobalConfig.getContextValue("conf.dir")+"/"+petName+".conf";
		File file=null;
		BufferedReader br=null;
		String strXmlLine = "";
		try{
			file = new File(file_name);//文件路径
			br = new BufferedReader(new FileReader(file));
			int indx=0;
			 while ((strXmlLine = br.readLine()) != null) {
				 System.out.println(strXmlLine);
				 String element=null;
				 int bngIndex = strXmlLine.lastIndexOf("="); // 得到最后一个=号
				 if(bngIndex<0||bngIndex>=strXmlLine.length())bngIndex=strXmlLine.length()-1;
				 element=strXmlLine.substring(bngIndex+1, strXmlLine.length());
				 SetElementVale(element,indx);
				 indx++;
			 }
			 br.close();
			
        }catch (IOException e) {
            e.printStackTrace();
        }
		
		request.setAttribute("curFileList", curFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		return ;
	}
	@At
	@Ok("jsp:page.sendpackets")
	public void deltpacket(Ioc ioc, HttpServletRequest request,
			@Param("filename") String petName){//函数用于处理选择包名，显示对应的协议内容。主要是读文件操作
	
		System.out.print(petName);
		
		//结下来是删除文件操作
		String file_name= GlobalConfig.getContextValue("conf.dir")+"/"+petName+".conf";
		File file=null;

		
		file = new File(file_name);//文件路径
		if (file.isFile() && file.exists()) {   
			System.gc();//启动jvm垃圾回收
			file.delete();
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
		//delFileList.remove(petName);
		request.setAttribute("curFileList", curFileList);
		//request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
	}
	@At
	@Ok("jsp:page.sendpackets")
	//@Ok("redirect:/platform/init")
	public void sendPacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {//发送数据包，此函数应该什么都不要做
		//curSendPacketsModel.setSendTimes("ok");
		//curSendPacketsModel.setSrcMac("ok");
		//curSendPacketsModel.setDstMac("ok");
		//curSendPacketsModel.setSrcIP("ok");
		//curSendPacketsModel.setDstIP("ok");
		//curSendPacketsModel.setIpPro("ok");
		//curSendPacketsModel.setName("ok");
		request.setAttribute("curFileList", curFileList);
		//request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		//add your code about send packets here...
	}//
	
	@At
	@Ok("jsp:page.sendpackets")
	//@Ok("redirect:/platform/init")
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
		//request.setAttribute("delFileList", delFileList);
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		//add your code about send packets here...
	}
	

	public void savetoFile(String file_name, SendPacketsModel send_bean)//把数据包保存到文件
	{
		 try {
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			
	           File file=new File(file_name); 
	           if(!file.exists()){            
	        	   try {                 
	        		   file.createNewFile();//创建文件 
	        		   file.mkdir();//创建目录
	        		   System.out.print("OKOKOKOKKOKOKKOKOKOKOKOKKO!");
	        		  // file.delete();//调用完马上就删除了
	        		  // file.deleteOnExit();//程序退出时，可以作为临时文件。
	        		   } catch (IOException e) {                
	        			   e.printStackTrace();             
	        			   }         
	        	   } 
	           BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
	           
	           
	            writer.write("SEND_TIMES="+send_bean.getSendTimes()+"\n");
	            writer.write("SRC_MAC="+send_bean.getSrcMac()+"\n");
	            writer.write("DST_MAC="+send_bean.getDstMac()+"\n");
	            writer.write("SRC_IP="+send_bean.getSrcIP()+"\n");
	            writer.write("DST_IP="+send_bean.getDstIP()+"\n");
	            writer.write("IP_PRO="+send_bean.getIpPro()+"\n");
	            writer.write("SRC_PORT="+send_bean.getSrcPort()+"\n");
	            writer.write("DST_PORT="+send_bean.getDstPort()+"\n");
	            writer.write("DATA_CONTENT="+send_bean.getContent()+"\n");
	            
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}
	public void initList()
	{
		String file_name= GlobalConfig.getContextValue("conf.dir")+"/";
		File f = new File(file_name);
		File[] t = f.listFiles();
		
		curFileList.clear();//先进行清空操作
		//delFileList.clear();

		for (int i = 0; i < t.length; i++) 
		   { 
			   int endIndex = t[i].getName().lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
			   if(endIndex<0 || endIndex>t[i].getName().length()) endIndex=t[i].getName().length();
			   String tempsuffix = t[i].getName().substring(0, endIndex);
			   //System.out.println(tempsuffix);
			   curFileList.add(tempsuffix);
			   //delFileList.add(tempsuffix);
		   }
	}
	public void SetElementVale(String value, int indx)
	{
		System.out.println(value + " "+indx);
		if(indx==0)curSendPacketsModel.setSendTimes(value);
		if(indx==1)curSendPacketsModel.setSrcMac(value);
		if(indx==2)curSendPacketsModel.setDstMac(value);
		if(indx==3)curSendPacketsModel.setSrcIP(value);
		if(indx==4)curSendPacketsModel.setDstIP(value);
		if(indx==5)curSendPacketsModel.setIpPro(value);
		if(indx==6)curSendPacketsModel.setSrcPort(value);
		if(indx==7)curSendPacketsModel.setDstPort(value);
		if(indx==8)curSendPacketsModel.setContent(value);
		//.....
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	@At
	@Ok("jsp:page.recpackets")
	public void recpackets() {

	}

	@At
	@Ok("raw:xml")
	public String getSendData() throws IOException {

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
				list.add(line);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// for (Iterator i = list.iterator(); i.hasNext();) {
		// String str = (String) i.next();
		// System.out.println(str);
		// }

		nums = new int[12];
		for (int i = 0; i < list.size(); i++) {
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
