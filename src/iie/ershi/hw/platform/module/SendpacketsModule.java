package iie.ershi.hw.platform.module;

import iie.ershi.hw.platform.common.GlobalConfig;
import iie.ershi.hw.platform.model.SendPacketsModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

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
		//curSendPacketsModel.setSendTimes("1");
		//curSendPacketsModel.setSrcMac("FF-FF-FF-FF-FF");
		//curSendPacketsModel.setDstMac("FF-FF-FF-FF-FF");
		//curSendPacketsModel.setSrcIP("172.0.0.1");
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
	}

	@At
	@Ok("jsp:page.sendpackets")
	//@Ok("redirect:/platform/init")
	public void savePacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {
		curSendPacketsModel.setName(sendPacketsModel.getName());
		if(sendPacketsModel.getName()==null)curSendPacketsModel.setName(" ");
		curSendPacketsModel.setSendTimes(sendPacketsModel.getSendTimes());
		curSendPacketsModel.setSrcMac(sendPacketsModel.getSrcMac());
		curSendPacketsModel.setDstMac(sendPacketsModel.getDstMac());
		curSendPacketsModel.setSrcIP(sendPacketsModel.getSrcIP());
		curSendPacketsModel.setDstIP(sendPacketsModel.getDstIP());
		curSendPacketsModel.setIpPro(sendPacketsModel.getIpPro());
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		
		
		String file_name=curSendPacketsModel.getName()+".lxx";
		savetoFile(file_name, curSendPacketsModel);
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
	//@Ok("redirect:/platform/init")
	public void sendPacket(Ioc ioc, HttpServletRequest request,
			@Param("::packet.") SendPacketsModel sendPacketsModel) {
		curSendPacketsModel.setSendTimes("ok");
		curSendPacketsModel.setSrcMac("ok");
		curSendPacketsModel.setDstMac("ok");
		curSendPacketsModel.setSrcIP("ok");
		curSendPacketsModel.setDstIP("ok");
		curSendPacketsModel.setIpPro("ok");
		curSendPacketsModel.setName("ok");
		request.setAttribute("curSendPacketsModel", curSendPacketsModel);
		//add your code about send packets here...
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

	public void savetoFile(String file_name, SendPacketsModel send_bean)
	{
		 try {
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			
	           File file=new File(file_name); 
	           if(!file.exists()){            
	        	   try {                 
	        		   file.createNewFile();//创建文件 
	        		   file.mkdir();//创建目录
	        		   System.out.print("OKOKOKOKKOKOKKOKOKOKOKOKKO!");
	        		   file.delete();//调用完马上就删除了
	        		   file.deleteOnExit();//程序退出时，可以作为临时文件。
	        		   } catch (IOException e) {                
	        			   e.printStackTrace();             
	        			   }         
	        	   } 
	           BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
	           
	           
	           /*这里需要添加文件的保存。。。另外文件保存路径在了eclipse工作环境下*/
	            writer.write("SEND_TIMES="+send_bean.getSendTimes()+"\n");
	            writer.write("SRC_MAC="+send_bean.getSrcMac()+"\n");
	            writer.write("SRC_IP="+send_bean.getSrcIP()+"\n");
	            writer.write("DST_IP="+send_bean.getDstIP()+"\n");
	          
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}

	public static void main(String[] args) throws IOException {

		System.out.println("test");

		String line; // 用来保存第一行读取的内容

		File f = new File("D:/regex_Speed.txt");

        if(!f.exists()){            
     	   try {                 
     		   f.createNewFile();//创建文件 
     		   f.mkdir();//创建目录
     		  
     		   } catch (IOException e) {                
     			   e.printStackTrace();             
     			   }         
     	   } 

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
