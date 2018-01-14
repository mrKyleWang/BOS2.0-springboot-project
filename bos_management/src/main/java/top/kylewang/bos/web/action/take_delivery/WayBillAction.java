package top.kylewang.bos.web.action.take_delivery;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.take_delivery.WayBill;
import top.kylewang.bos.service.take_delivery.WayBillService;
import top.kylewang.bos.web.action.common.BaseAction;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/8 0008 15:12
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class WayBillAction extends BaseAction<WayBill>{

    @Autowired
    private WayBillService wayBillService;

    /**
     * 保存
     * @return
     */
    @Action(value = "waybill_save",results = {@Result(name = "success",type = "json")})
    public String save(){
        Map<String, Object> result = new HashMap<>(4);
        try{
            wayBillService.save(model);
            //保存成功
            result.put("success",true);
            result.put("msg","保存运单成功!");
        }catch (Exception e){
            // 保存失败
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","保存运单失败!");
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    /**
     * 分页查询
     * @return
     */
    @Action(value = "waybill_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        System.out.println(model);
        Pageable pageable = new PageRequest(page - 1, rows,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
        Page<WayBill> pageData = wayBillService.findPageData(model,pageable);

        System.out.println(pageData.getContent());
        System.out.println(pageData.getTotalElements());

        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }

    /**
     * 根据运单号查询
     * @return
     */
    @Action(value = "waybill_findByWayBillNum",results = {@Result(name = "success",type = "json")})
    public String findByWayBillNum(){
        Map<String, Object> result = new HashMap<>(4);
        WayBill wayBill = wayBillService.findByWayBillNum(model.getWayBillNum());
        if(wayBill!=null){
            result.put("success",true);
            result.put("wayBillData",wayBill);
        }else{
            result.put("success",false);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    /**
     * 导出表格
     * @return
     */
    @Action(value = "waybill_exportXls")
    public String exportXls() throws IOException {
        List<WayBill> wayBillList = wayBillService.findWayBills(model);
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
        for (WayBill wayBill: wayBillList) {
            HSSFRow hssfRow = sheet.createRow(sheet.getLastRowNum()+1);
            hssfRow.createCell(0).setCellValue(wayBill.getWayBillNum());
            hssfRow.createCell(1).setCellValue(wayBill.getSendName());
            hssfRow.createCell(2).setCellValue(wayBill.getSendMobile());
            hssfRow.createCell(3).setCellValue(wayBill.getSendAddress());
            hssfRow.createCell(4).setCellValue(wayBill.getRecName());
            hssfRow.createCell(5).setCellValue(wayBill.getRecMobile());
            hssfRow.createCell(6).setCellValue(wayBill.getRecAddress());
        }
        // 下载导出
        // 设置头信息
        ServletActionContext.getResponse().setContentType("application/vnd.ms-excel");
        ServletActionContext.getResponse().setHeader("Content-Disposition","attachment;filename=wayBillexcel.xls");
        ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();
        return NONE;
    }

    /**
     * 导出PDF
     * @return
     */
    @Action(value = "waybill_exportPdf")
    public String exportPdf() throws Exception {
        // 查询出 满足当前条件 结果数据
        List<WayBill> wayBillList = wayBillService.findWayBills(model);

        // 下载导出
        // 设置头信息
        ServletActionContext.getResponse().setContentType("application/pdf");
        ServletActionContext.getResponse().setHeader("Content-Disposition","attachment;filename=" + "wayBillexcel.pdf");

        // 生成PDF文件
        Document document = new Document();
        PdfWriter.getInstance(document, ServletActionContext.getResponse()
                .getOutputStream());
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
        for (WayBill wayBill : wayBillList) {
            table.addCell(buildCell(wayBill.getWayBillNum(), font));
            table.addCell(buildCell(wayBill.getSendName(), font));
            table.addCell(buildCell(wayBill.getSendMobile(), font));
            table.addCell(buildCell(wayBill.getSendAddress(), font));
            table.addCell(buildCell(wayBill.getRecName(), font));
            table.addCell(buildCell(wayBill.getRecMobile(), font));
            table.addCell(buildCell(wayBill.getRecAddress(), font));
        }
        // 将表格加入文档
        document.add(table);

        document.close();

        return NONE;
    }

    private PdfPCell buildCell(String content, Font font) throws BadElementException {
        Phrase phrase = new Phrase(content,font);
        return new PdfPCell(phrase);
    }
}
