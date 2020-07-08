package com.abrain.homework.controller;

import com.abrain.homework.domain.TransText;
import com.abrain.homework.repository.TransTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class HomeController {
    @Autowired
    private TransTextRepository transTextRepository;


    @GetMapping("/")
    public String index(Model model) {
        List<TransText> result = new ArrayList<>();
        transTextRepository.findAll().forEach(transText -> {
            result.add(transText);
        });
        model.addAttribute("transText",result);
        return "/index";
    }

    @PostMapping("/trans")
    @ResponseBody
    public List<List<String>> trans(String str) {
        String[] arrStr = str.split("\\n");
        List<List<String>> result=new ArrayList<>();
        List<String> resultId=new ArrayList<>();
        List<String> resultPhone=new ArrayList<>();
        List<String> resultName=new ArrayList<>();
        List<String> resultAddr=new ArrayList<>();
        List<String> resultPrice=new ArrayList<>();
        List<String> resultComId=new ArrayList<>();
        List<String> resultComNum=new ArrayList<>();

        String idExp = "^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$";
        String phoneExp = "^01(?:0|1|[6-9])-?(\\d{3,4})-?(\\d{4})$";
        String nameExp = "^[가-힣]{2,4}$";
        String addrExp= "^(([가-힣]+(시|도))( |)[가-힣]+(시|군|구)( |)(.|\\n)*)$";
        String priceExp = "^(-)?([1-9]([0-9]{0,2})?(,\\d{3})*|0)(\\.\\d+)?(원)?$";
        String companyId = "^[0-6][0-2][0-9]-?[0-9]{2}-?[0-9]{5}$";
        String companyNum ="^[0-9]{8}";

        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].matches(idExp)) {
                resultId.add(arrStr[i]);
            }
        }
        result.add(resultId);
        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].matches(phoneExp)) {
                resultPhone.add(arrStr[i]);
            }
        }
        result.add(resultPhone);

        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].matches(nameExp)) {
                resultName.add(arrStr[i]);
            }
        }
        result.add(resultName);
        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].matches(addrExp)) {
                resultAddr.add(arrStr[i]);
            }
        }
        result.add(resultAddr);
        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].matches(priceExp)) {
                resultPrice.add(arrStr[i]);
            }
        }
        result.add(resultPrice);
        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].matches(companyId)) {
                //사업자등록번호 조건이 맞아야 됨
                if (ckBisNo(arrStr[i])) {
                    resultComId.add(arrStr[i]);
                }
            }
        }
        result.add(resultComId);
        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].matches(companyNum)) {
                resultComNum.add(arrStr[i]);
            }
        }

    result.add(resultComNum);
        return result;
    }

    @PostMapping("/save")
    @ResponseBody
    public void save(TransText transText){
        transTextRepository.save(transText);
    }

    @GetMapping("/getList")
    @ResponseBody
    public List<TransText> getList(){
        List<TransText> result = new ArrayList<>();
        transTextRepository.findAll().forEach(transText -> {
            result.add(transText);
        });
        System.out.println(result);
        return result;
    }


    public boolean ckBisNo(String companyId) {
        companyId = companyId.replace("-", "");

        String[] arrComId = companyId.split("");
        int[] intArrComId = new int[arrComId.length];

        for (int i = 0; i < arrComId.length; i++) {
            intArrComId[i] = Integer.parseInt(arrComId[i]);
        }

        int sum = 0;
        int key[] = {1, 3, 7, 1, 3, 7, 1, 3, 5};


        // 0 ~ 8 까지 9개의 숫자를 체크키와 곱하여 합에더합니다.
        for (int i = 0; i < 9; i++) {
            sum += (key[i] * intArrComId[i]);
        }


        int chkSum = 0;

        chkSum = (int) Math.floor(key[8] * intArrComId[8] / 10);

        // 체크섬 합계에 더해줌

        sum += chkSum;

        int reminder = (10 - (sum % 10)) % 10;

        //값 비교

        if (reminder == intArrComId[9])
            return true;
        return false;
    }
}
