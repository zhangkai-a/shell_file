import java.io.*;
import java.util.Scanner; 
public class shellpck{
	public static boolean  pd(String path,String sname)//�ж��ļ������Ƿ��и��ļ�
	{
		boolean a=false;
		File file = new File(path);   //���·��
        File[] array = file.listFiles();   //����ļ���Ŀ¼
          
        for(int i=0;i<array.length;i++){   
        	if((array[i].getName()).equals(sname))
        		{
        		a=true;
        		return a;
        		}
        }
		return a;
		
	}
	public static void echo(String string1,String Nowpath) //echo ��ӡ���� 
	{
		boolean judge1=true;
		char ss[] = string1.toCharArray();
		for(int i=6;i<string1.length()-8;i++)//ʵ�ֹܵ�
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
			string2=string2.replaceAll("\\\\n","\r\n");//�滻/n
			char ss1[] = string2.toCharArray();
			if(ss1[0]=='"' && ss1[string2.length()-1]=='"')
			{
				System.out.println(string2.substring(1,string2.length()-1));
			}
			else
				System.out.println(string2);
		}
		
		}
	public static void grep(String NowPath,String str1,String str2)//�ж��ļ����ַ���
	{

		 try {
		    Scanner scanner = new Scanner(new File(NowPath+"\\"+str1));
		    while (scanner.hasNextLine()) {		    	
		    	String line=scanner.nextLine();
		    	if(line.contains(str2))
		    	{
		    		line=line.replaceAll("\\\\n","\r\n");//�滻/n
		    		System.out.println(line);
		    	}
		    }
		    scanner.close();
		        
		} catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		}
	}
	public static void ls1(String path)//ls����
	{     
	        File file = new File(path);   //���·��
	        File[] array = file.listFiles();   //����ļ���Ŀ¼
	          
	        for(int i=0;i<array.length;i++){   
	        	System.out.println(array[i].getName());  
	        }  
	        
	}
	public static void cat(String NowPath,String str)//��ӡ�ļ�����
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
	public static void mkdir1(String Path,String str) throws IOException//�����ļ���
	{
		File file = new File(Path+"\\"+str);// 
		
		if(!file.mkdirs()) {
			System.out.println("�ļ��д���ʧ�ܣ�");
		} 
	}
	public static void cp(String Path,String file1,String file2) throws IOException//�����ļ�
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
		   File file = new File(NowPath+"//"+file1);//�ļ��ľ���·��
		   if(file.exists()){
			   file.delete(); 
		   }
		   else 
		   {
			   System.out.println("�ļ������ڣ�");
		   }
	}
	public static void main(String args[]) throws IOException //���������
	{
		 String NowPath;
		 NowPath=System.getProperty("user.dir");//·����
		 while(true)
		 {
		 int strlength=1;
		 System.out.print(NowPath+" :");
		 Scanner s = new Scanner(System.in);
		 String str = s.nextLine();
		 strlength=str.length();
		 if(strlength==2)//�����Ϊ2
		 {
			 if(str.equals("ls"))
				 ls1(NowPath);
			 else
				 System.out.println("�Ҳ���"+str+"���");
		 }
		 
		 else if(strlength==3)//�����Ϊ3
		 {
			 if(str=="pwd")
			 {
				 System.out.print(NowPath+" :");
			 }
			 else
				 System.out.println("�Ҳ���"+str+"���");
		 }
		 
		 else if(strlength>3)//������䳤�ȴ���3
		 {
			 
			 if("cd".equals(str.substring(0,2)))
			 {
				 if (pd(NowPath,str.substring(3)))
				 {
					 NowPath=NowPath+"\\"+str.substring(3);
					 System.out.println(str.substring(3));
				 }
				 else
					 System.out.println("�ļ�����û��"+str.substring(3)+"�ļ���");
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
				 System.out.println("�Ҳ���"+str+"���");
			 }
		 }
		}
		 
	}
}
