import java.io.*;
import java.util.Scanner; 
public class shellpck{
	public static boolean  pd(String path,String sname)//判断文件夹内是否有该文件
	{
		boolean a=false;
		File file = new File(path);   //获得路径
        File[] array = file.listFiles();   //获得文件夹目录
          
        for(int i=0;i<array.length;i++){   
        	if((array[i].getName()).equals(sname))
        		{
        		a=true;
        		return a;
        		}
        }
		return a;
		
	}
	public static void echo(String string1,String Nowpath) //echo 打印操作 
	{
		boolean judge1=true;
		char ss[] = string1.toCharArray();
		for(int i=6;i<string1.length()-8;i++)//实现管道
		{
			if(ss[i]=='"' && "| grep".equals(string1.substring(i+2,i+8)))
			{
				judge1=false;
				grep(Nowpath,string1.substring(i+9),string1.substring(6,i));
				break;
			}
		}
		if(judge1)
		{
			String string2=string1.substring(5);
			string2=string2.replaceAll("\\\\n","\r\n");//替换/n
			char ss1[] = string2.toCharArray();
			if(ss1[0]=='"' && ss1[string2.length()-1]=='"')
			{
				System.out.println(string2.substring(1,string2.length()-1));
			}
			else
				System.out.println(string2);
		}
		
		}
	public static void grep(String NowPath,String str1,String str2)//判断文件内字符串
	{

		 try {
		    Scanner scanner = new Scanner(new File(NowPath+"\\"+str1));
		    while (scanner.hasNextLine()) {		    	
		    	String line=scanner.nextLine();
		    	if(line.contains(str2))
		    	{
		    		line=line.replaceAll("\\\\n","\r\n");//替换/n
		    		System.out.println(line);
		    	}
		    }
		    scanner.close();
		        
		} catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		}
	}
	public static void ls1(String path)//ls操作
	{     
	        File file = new File(path);   //获得路径
	        File[] array = file.listFiles();   //获得文件夹目录
	          
	        for(int i=0;i<array.length;i++){   
	        	System.out.println(array[i].getName());  
	        }  
	        
	}
	public static void cat(String NowPath,String str)//打印文件内容
	{
		try {
		    Scanner scanner = new Scanner(new File(NowPath+"\\"+str.substring(4)));
		    while (scanner.hasNextLine()) {
		        System.out.println(scanner.nextLine());
		    }
		    scanner.close();
		        
		} catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		}
	}
	public static void mkdir1(String Path,String str) throws IOException//创建文件夹
	{
		File file = new File(Path+"\\"+str);// 
		
		if(!file.mkdirs()) {
			System.out.println("文件夹创建失败！");
		} 
	}
	public static void cp(String Path,String file1,String file2) throws IOException//复制文件
	{
		FileInputStream in = new FileInputStream(new File(Path+"\\"+file2));
	    FileOutputStream out = new FileOutputStream(new File(Path+"\\"+file1));
	    byte[] buff = new byte[512];
	    int n = 0;
	    while ((n = in.read(buff)) != -1) {
	        out.write(buff, 0, n);
	    }
	    out.flush();
	    in.close();
	    out.close();
		
	}
	public static void rm(String NowPath,String file1)
	{
		   File file = new File(NowPath+"//"+file1);//文件的绝对路径
		   if(file.exists()){
			   file.delete(); 
		   }
		   else 
		   {
			   System.out.println("文件不存在！");
		   }
	}
	public static void main(String args[]) throws IOException //主程序入口
	{
		 String NowPath;
		 NowPath=System.getProperty("user.dir");//路径名
		 while(true)
		 {
		 int strlength=1;
		 System.out.print(NowPath+" :");
		 Scanner s = new Scanner(System.in);
		 String str = s.nextLine();
		 strlength=str.length();
		 if(strlength==2)//命令长度为2
		 {
			 if(str.equals("ls"))
				 ls1(NowPath);
			 else
				 System.out.println("找不到"+str+"命令！");
		 }
		 
		 else if(strlength==3)//命令长度为3
		 {
			 if(str=="pwd")
			 {
				 System.out.print(NowPath+" :");
			 }
			 else
				 System.out.println("找不到"+str+"命令！");
		 }
		 
		 else if(strlength>3)//命令语句长度大于3
		 {
			 
			 if("cd".equals(str.substring(0,2)))
			 {
				 if (pd(NowPath,str.substring(3)))
				 {
					 NowPath=NowPath+"\\"+str.substring(3);
					 System.out.println(str.substring(3));
				 }
				 else
					 System.out.println("文件夹内没有"+str.substring(3)+"文件。");
			 }
			 else if("cp".equals(str.substring(0,2)))
			 {
				 int t=0;
				 char[] c = str.toCharArray();
				 for(int i=5;i<=str.length();i++)
				 {
					 if(c[i]==' ')
					 {
						 t=i;
						 break;
					 }
				 }
				 cp(NowPath,str.substring(t+1),str.substring(3,t));
			 }
			 else if("rm".equals(str.substring(0,2)))
			 {
				 rm(NowPath,str.substring(3));
			 }
			 else if("cat".equals(str.substring(0,3)))
			 {
				 cat(NowPath,str);
			 }
			 else if("grep".equals(str.substring(0,4)))
			 {
					int t=0;
					 char[] c = str.toCharArray();
					 for(int i=5;i<=str.length();i++)
					 {
						 if(c[i]==' ')
						 {
							 t=i;
							 break;
						 }
					 }
				 grep(NowPath,str.substring(t+1),str.substring(6,t-1));
			 }
			 else if("echo".equals(str.substring(0,4)))
			 {
				 echo(str,NowPath);
			 }
			 else if("mkdir".equals(str.substring(0,5)))
			 {
				 mkdir1(NowPath,str.substring(6));
			 }
			 else 
			 {
				 System.out.println("找不到"+str+"命令！");
			 }
		 }
		}
		 
	}
}
