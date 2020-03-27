import java.io.File;
import java.util.HashMap;

public class Util {
    public static String getLanguageString(String lId, String sId){
        HashMap<String, HashMap<String,String>> languageMap = getLanguageMap();
        return languageMap.get(lId).get(sId);
    }
    public static HashMap<String, HashMap<String,String>> getLanguageMap(){
        HashMap<String, HashMap<String,String>> languageMap = new HashMap<>();
        HashMap<String,String> chinese = new HashMap<>();
        HashMap<String,String> english = new HashMap<>();
        chinese.put("menu", "1.三年级\n2.四年级\n3.五年级\n4.复杂运算\n5.真分数运算\n请输入数字选择题目类型：");
        chinese.put("count", "请输入题目数量：");
        chinese.put("trueCount", "正确数量：");
        chinese.put("failCount", "错误数量：");
        chinese.put("subject", "题目");
        chinese.put("fail", "错误，正确答案：");
        chinese.put("true", "正确");
        english.put("menu", "1.Grade three\n2.fourth grade\n3.fifth grade\n4.Complex operation\n5.Fractional operation\nPlease enter a number to select the type of question");
        english.put("count", "Please enter the number of questions");
        english.put("trueCount", "True Count：");
        english.put("failCount", "Fail Count：");
        english.put("subject", "Subject");
        english.put("fail", "Fail，True Answer：");
        english.put("true", "True");
        languageMap.put("chinese", chinese);
        languageMap.put("english", english);
        return languageMap;
    }

}
