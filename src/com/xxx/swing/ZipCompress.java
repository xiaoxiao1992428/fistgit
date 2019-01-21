package com.xxx.swing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JTextArea;

public class ZipCompress implements Runnable {

	
	  private String zipFileName;      // Ŀ�ĵ�Zip�ļ�
	    private String sourceFileName;   //Դ�ļ�����ѹ�����ļ����ļ��У�
	    private JTextArea jta ;
	    
	    public ZipCompress(String zipFileName,String sourceFileName,JTextArea jta)
	    {
	        this.zipFileName=zipFileName;
	        this.sourceFileName=sourceFileName;
	        this.jta=jta;
	    }
	    
//	    public void zip() throws Exception
//	    {
//	        File zipFile = new File("d:/u04/"+zipFileName);
//	        System.out.println("ѹ����...");
//	        
//	        //����zip�����
//	        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFile));
//	        
//	        //�������������
//	        BufferedOutputStream bos = new BufferedOutputStream(out);
//	        
//	        File sourceFile = new File(sourceFileName);
//	        
//	        //���ú���
//	        compress(out,bos,sourceFile,sourceFile.getName());
//	        bos.flush();
//	        bos.close();
//	        out.close();
//	    }
	    
//	    public void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base) throws Exception
//	    {
//	        //���·��ΪĿ¼���ļ��У�
//	        if(sourceFile.isDirectory())
//	        {
//	        
//	            //ȡ���ļ����е��ļ��������ļ��У�
//	            File[] flist = sourceFile.listFiles();
//	            
//	            if(flist.length==0)//����ļ���Ϊ�գ���ֻ����Ŀ�ĵ�zip�ļ���д��һ��Ŀ¼�����
//	            {
//	                System.out.println(base+"/");
//	                out.putNextEntry(  new ZipEntry(base+"/") );
//	            }
//	            else//����ļ��в�Ϊ�գ���ݹ����compress���ļ����е�ÿһ���ļ������ļ��У�����ѹ��
//	            {
//	                for(int i=0;i<flist.length;i++)
//	                {
//	                    compress(out,bos,flist[i],base+"/"+flist[i].getName());
//	                }
//	            }
//	        }
//	        else//�������Ŀ¼���ļ��У�����Ϊ�ļ�������д��Ŀ¼����㣬֮���ļ�д��zip�ļ���
//	        {
//	            out.putNextEntry( new ZipEntry(base) );
//	            FileInputStream fos = new FileInputStream(sourceFile);
//	            BufferedInputStream bis = new BufferedInputStream(fos);
//	            
//	            int tag;
//
//	            //��Դ�ļ�д�뵽zip�ļ���
//	            while((tag=bis.read())!=-1)
//	            {
//	                out.write(tag);
//	            }
//	            bis.close();
//	            fos.close();
//		        jta.append(sourceFile.getName()+"ѹ����ɣ�");
//		        jta.paintImmediately(jta.getBounds()); 
//	            
//	        }
//	    }
	    
	    
	    /**

	     * ѹ����ZIP ����2

	     * @param srcFiles ��Ҫѹ�����ļ��б�

	     * @param out           ѹ���ļ������

	     * @throws RuntimeException ѹ��ʧ�ܻ��׳�����ʱ�쳣

	     */

	    public  void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {

	        long start = System.currentTimeMillis();

	        ZipOutputStream zos = null ;

	        try {

	            zos = new ZipOutputStream(out);

	            for (File srcFile : srcFiles) {

	                byte[] buf = new byte[2*1024];

	                zos.putNextEntry(new ZipEntry(srcFile.getName()));

	                int len;

	                FileInputStream in = new FileInputStream(srcFile);

	                while ((len = in.read(buf)) != -1){

	                    zos.write(buf, 0, len);

	                }

	                zos.closeEntry();

	                in.close();

	            }

	            long end = System.currentTimeMillis();

	            System.out.println("ѹ����ɣ���ʱ��" + (end - start) +" ms");

	        } catch (Exception e) {

	            throw new RuntimeException("zip error from ZipUtils",e);

	        }finally{

	            if(zos != null){

	                try {

	                    zos.close();

	                } catch (IOException e) {

	                    e.printStackTrace();

	                }

	            }

	        }

	      }
	    
		  /**

	     * ѹ����ZIP ����1

	     * @param srcDir ѹ���ļ���·�� 

	     * @param out    ѹ���ļ������

	     * @param KeepDirStructure  �Ƿ���ԭ����Ŀ¼�ṹ,true:����Ŀ¼�ṹ; 

	     *                          false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)

	     * @throws RuntimeException ѹ��ʧ�ܻ��׳�����ʱ�쳣

	     */

	    public  void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)

	            throws RuntimeException{

	        

	        long start = System.currentTimeMillis();

	        ZipOutputStream zos = null ;

	        try {

	            zos = new ZipOutputStream(out);

	            File sourceFile = new File(srcDir);

	            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);

	            long end = System.currentTimeMillis();

	            System.out.println("ѹ����ɣ���ʱ��" + (end - start) +" ms");

	        } catch (Exception e) {

	            throw new RuntimeException("zip error from ZipUtils",e);

	        }finally{

	            if(zos != null){

	                try {

	                    zos.close();

	                } catch (IOException e) {

	                    e.printStackTrace();

	                }

	            }

	        }

	        

	      }
	    
	    private  void compress(File sourceFile, ZipOutputStream zos, String name,
	    		
	    		            boolean KeepDirStructure) throws Exception{
	    		
	    		        byte[] buf = new byte[2*1024];
	    		
	    		        if(sourceFile.isFile()){
	    		
	    		            // ��zip����������һ��zipʵ�壬��������nameΪzipʵ����ļ�������
	    		
	    		            zos.putNextEntry(new ZipEntry(name));
	    		
	    		            // copy�ļ���zip�������
	    		
	    		            int len;
	    		
	    		            FileInputStream in = new FileInputStream(sourceFile);
	    		
	    		            while ((len = in.read(buf)) != -1){
	    		
	    		                zos.write(buf, 0, len);
	    		
	    		            }
	    		
	    		            // Complete the entry
	    		            zos.flush();
	    		            zos.closeEntry();
	    		
	    		            in.close();
	    			        jta.append(sourceFile.getName()+"ѹ����ɣ�\r\n");
	    			        jta.paintImmediately(jta.getBounds());
	    		
	    		        } else {
	    		
	    		            File[] listFiles = sourceFile.listFiles();
	    		
	    		            if(listFiles == null || listFiles.length == 0){
	    		
	    		                // ��Ҫ����ԭ�����ļ��ṹʱ,��Ҫ�Կ��ļ��н��д���
	    		
	    		                if(KeepDirStructure){
	    		
	    		                    // ���ļ��еĴ���
	    		
	    		                    zos.putNextEntry(new ZipEntry(name + "/"));
	    		
	    		                    // û���ļ�������Ҫ�ļ���copy
	    		                    zos.flush();
	    		                    zos.closeEntry();
	    		
	    		                }
	    		
	    		                
	    		
	    		            }else {
	    		
	    		                for (File file : listFiles) {
	    		
	    		                    // �ж��Ƿ���Ҫ����ԭ�����ļ��ṹ
	    		                	if(file.getName().lastIndexOf(".zip")>0){
	    		                		continue ;
	    		                	}else{
		    		                    if (KeepDirStructure) {
		    		        	    		
		    		                        // ע�⣺file.getName()ǰ����Ҫ���ϸ��ļ��е����ּ�һб��,
		    		
		    		                        // ��Ȼ���ѹ�����оͲ��ܱ���ԭ�����ļ��ṹ,���������ļ����ܵ�ѹ������Ŀ¼����
		    		
		    		                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
		    		
		    		                    } else {
		    		
		    		                        compress(file, zos, file.getName(),KeepDirStructure);
		    		
		    		                    }
	    		                	}

	    		
	    		                    
	    		
	    		                }
	    		
	    		            }
	    		
	    		        }
	    		
	    		    }

		@Override
		public void run() {
			  try{
//				  File file = new File(sourceFileName);
			        System.out.println("ѹ����...");
//			        File zipFile = new File("d:/u04/"+zipFileName);
//			        //����zip�����
//			        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFile));
//			        compress(file,out,zipFileName,false);
//			       
			        
			        FileOutputStream fos1 = new FileOutputStream(new File("d:/u04/"+zipFileName));
			        
			        toZip("D:/u04/", fos1,true);
			        jta.append("all files ѹ����ɣ�\r\n");
			        jta.paintImmediately(jta.getBounds()); 
			        
			        
			        
			        
		        }catch(Exception e){
		            e.printStackTrace();
		        }
			
		}
}
