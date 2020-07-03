//package jp.co.sample.emp_management.controller;
//
//import java.util.List;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import net.arnx.jsonic.JSON;
//
///** 商品検索フォームのオートコンプリート用コントローラ. */
//@Controller
//@RequestMapping("/getAutoComplete")
//public class GetAutoCompleteController {
//
//    /** 全商品名をオートコンプリートに渡す. */
//    @ResponseBody
//    @RequestMapping
//    public String getAutoComplete(){
//        List<String> nameList = getAutoCompleteService.getAllNameList();
//        return JSON.encode(nameList);
//    }
//}