package top.kylewang.bos.web.controller.take_delivery;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.take_delivery.WayBill;
import top.kylewang.bos.service.take_delivery.WayBillService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/8 0008 15:12
 */
@Controller
public class WayBillController{

    @Autowired
    private WayBillService wayBillService;

    /**
     * 保存
     * @param wayBill
     * @return
     */
    @RequestMapping("waybill_save.action")
    @ResponseBody
    public Map<String, Object> save(WayBill wayBill){
        Map<String, Object> result = new HashMap<>(4);
        try{
            wayBillService.save(wayBill);
            //保存成功
            result.put("success",true);
            result.put("msg","保存运单成功!");
        }catch (Exception e){
            // 保存失败
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","保存运单失败!");
        }
        return result;
    }

    /**
     * 分页查询
     * @param wayBill
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("waybill_pageQuery.action")
    @ResponseBody
    public Map<String,Object> pageQuery(WayBill wayBill,int page,int rows){
        System.out.println(wayBill);
        Pageable pageable = new PageRequest(page - 1, rows,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
        Page<WayBill> pageData = wayBillService.findPageData(wayBill,pageable);

        System.out.println(pageData.getContent());
        System.out.println(pageData.getTotalElements());
        Map<String,Object> result = new HashMap<>(4);
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        return result;
    }

    /**
     * 根据运单号查询
     * @param wayBill
     * @return
     */
    @RequestMapping("waybill_findByWayBillNum.action")
    @ResponseBody
    public Map<String, Object> findByWayBillNum(WayBill wayBill){
        Map<String, Object> result = new HashMap<>(4);
        WayBill existWayBill = wayBillService.findByWayBillNum(wayBill.getWayBillNum());
        if(existWayBill!=null){
            result.put("success",true);
            result.put("wayBillData",existWayBill);
        }else{
            result.put("success",false);
        }
        return result;
    }

    /**
     * 导出表格
     * @return
     */
    @RequestMapping("waybill_exportXls.action")
    public void exportXls(WayBill wayBill, HttpServletResponse response) throws IOException {
        List<WayBill> wayBillList = wayBillService.findWayBills(wayBill);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("运单数据");
        // 表头
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("运单号");
        headRow.createCell(1).setCellValue("寄件人");
        headRow.createCell(2).setCellValue("寄件人电话");
        headRow.createCell(3).setCellValue("寄件人地址");
        headRow.createCell(4).setCellValue("收件人");
        headRow.createCell(5).setCellValue("收件人电话");
        headRow.createCell(6).setCellValue("收件人地址");
        // 表格数据
        for (WayBill w: wayBillList) {
            HSSFRow hssfRow = sheet.createRow(sheet.getLastRowNum()+1);
            hssfRow.createCell(0).setCellValue(w.getWayBillNum());
            hssfRow.createCell(1).setCellValue(w.getSendName());
            hssfRow.createCell(2).setCellValue(w.getSendMobile());
            hssfRow.createCell(3).setCellValue(w.getSendAddress());
            hssfRow.createCell(4).setCellValue(w.getRecName());
            hssfRow.createCell(5).setCellValue(w.getRecMobile());
            hssfRow.createCell(6).setCellValue(w.getRecAddress());
        }
        // 下载导出
        // 设置头信息
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=wayBillexcel.xls");
        ServletOutputStream outputStream = response.getOutputStream();
        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();
    }

    /**
     * 导出PDF
     * @param wayBill
     * @param response
     * @throws Exception
     */
    @RequestMapping("waybill_exportPdf.action")
    public void exportPdf(WayBill wayBill,HttpServletResponse response) throws Exception {
        // 查询出 满足当前条件 结果数据
        List<WayBill> wayBillList = wayBillService.findWayBills(wayBill);

        // 下载导出
        // 设置头信息
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","attachment;filename=" + "wayBillexcel.pdf");

        // 生成PDF文件
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        // 写PDF数据
        // 向document 生成pdf表格
        PdfPTable table = new PdfPTable(7);
        // 宽度
        table.setTotalWidth(600);
        // 水平对齐方式
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        // 垂直对齐方式
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        // 设置表格字体
        BaseFont cn = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
                false);
        Font font = new Font(cn, 10,Font.NORMAL, BaseColor.BLUE);

        // 写表头
        table.addCell(buildCell("运单号", font));
        table.addCell(buildCell("寄件人", font));
        table.addCell(buildCell("寄件人电话", font));
        table.addCell(buildCell("寄件人地址", font));
        table.addCell(buildCell("收件人", font));
        table.addCell(buildCell("收件人电话", font));
        table.addCell(buildCell("收件人地址", font));
        // 写数据
        for (WayBill w : wayBillList) {
            table.addCell(buildCell(w.getWayBillNum(), font));
            table.addCell(buildCell(w.getSendName(), font));
            table.addCell(buildCell(w.getSendMobile(), font));
            table.addCell(buildCell(w.getSendAddress(), font));
            table.addCell(buildCell(w.getRecName(), font));
            table.addCell(buildCell(w.getRecMobile(), font));
            table.addCell(buildCell(w.getRecAddress(), font));
        }
        // 将表格加入文档
        document.add(table);
        document.close();
    }

    private PdfPCell buildCell(String content, Font font) throws BadElementException {
        Phrase phrase = new Phrase(content,font);
        return new PdfPCell(phrase);
    }
}
