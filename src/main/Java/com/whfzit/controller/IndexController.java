package com.whfzit.controller;

import com.whfzit.service.ExcelService;
import com.whfzit.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    ExcelService excelService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/selectAll")
    public @ResponseBody
    Map root(@RequestParam HashMap<String, Object> params) {
        Map result = indexService.selectAll(params);
        return result;
    }

    @RequestMapping("/test")
    public void test() {
        excelService.processDir(ExcelService.sWebAppPath+ ExcelService.ExcelDir);
    }

    @RequestMapping("/upload")
    public @ResponseBody Map upload(@RequestParam(value="file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        HashMap<String, String> result = new HashMap<>();
        if (fileName != null) {
            String ext = fileName.substring(fileName.lastIndexOf("."));
            if (ext.trim().toLowerCase().equals(".xls") || ext.trim().toLowerCase().equals(".xlsx")) {
                File xlsFile = new File(ExcelService.sWebAppPath+ ExcelService.ExcelDir + "\\" +fileName);
                file.transferTo(xlsFile);
                result =excelService.processTotalExcel(xlsFile, ext);
            }
            else {
                result.put("code", "2");
                result.put("msg", "文件格式错误");
            }
        }
        else {
            result.put("code", "3");
            result.put("msg", "文件上传失败，请检查文件是否存在");
        }
//        OutputStreamWriter writer = new OutputStreamWriter(
//                new FileOutputStream(ExcelService.sWebAppPath+ ExcelService.ExcelDir + "\\" +fileName),
//                "utf-8");
//        InputStreamReader reader = new InputStreamReader(file.getInputStream(), "utf-8");
//        char[] in = new char[8];
//        while (reader.read(in) != -1) {
//            writer.write(in);
//        }
//        writer.close();
//        reader.close();

        return result;

    }

}
