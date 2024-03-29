package com.szo.core.extend.swftools;

import com.szo.core.util.FileUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * OFFICE文档转换服务类
 *
 * @author Administrator
 */
public class OpenOfficePDFConverter implements PDFConverter {

    private static OfficeManager officeManager;
    /**
     * OpenOffice安装根目录
     */
    private static String OFFICE_HOME = ConStant.OFFICE_HOME;
    private static int[] port = {8100};

    public void convert2PDF(String inputFile, String pdfFile, String extend) {

        if (extend.equals("txt")) {

            String odtFile = FileUtils.getFilePrefix(inputFile) + ".odt";
            if (new File(odtFile).exists()) {
                System.out.println("odt文件已存在！");
                inputFile = odtFile;
            } else {
                try {
                    FileUtils.copyFile(inputFile, odtFile);
                    inputFile = odtFile;
                } catch (FileNotFoundException e) {
                    System.out.println("Odt文档不存在！");
                    e.printStackTrace();
                }
            }

        }
        startService();
        //pdfFile = pdfFile.replaceAll(" ", "").replaceAll("　", "");
        System.out.println("进行文档转换转换:" + inputFile + " --> " + pdfFile);
        OfficeDocumentConverter converter = new OfficeDocumentConverter(
                officeManager);
        try {
            converter.convert(new File(inputFile), new File(pdfFile));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        stopService();
        System.out.println("进行文档转换转换---- 结束----");
    }

    public void convert2PDF(String inputFile, String extend) {
        //inputFile = inputFile.replaceAll(" ", "").replaceAll("　", "");
        String pdfFile = FileUtils.getFilePrefix2(inputFile) + ".pdf";
        convert2PDF(inputFile, pdfFile, extend);

    }

    public static void startService() {
        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        try {
            // 准备启动服务
            configuration.setOfficeHome(OFFICE_HOME);// 设置OpenOffice.org安装目录
            // 设置转换端口，默认为8100
            configuration.setPortNumbers(port);
            // 设置任务执行超时为5分钟
            configuration.setTaskExecutionTimeout(1000 * 60 * 5L);
            // 设置任务队列超时为24小时
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);

            officeManager = configuration.buildOfficeManager();
            officeManager.start(); // 启动服务
            System.out.println("office转换服务启动成功!");
        } catch (Exception ce) {
            System.out.println("office转换服务启动失败!详细信息:" + ce);
        }
    }

    public static void stopService() {
        System.out.println("关闭office转换服务....");
        if (officeManager != null) {
            officeManager.stop();
        }
        System.out.println("关闭office转换成功!");
    }
}
