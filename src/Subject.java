import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subject {
    String subjectString;
    String answer;
    String userAnswer;

    public Subject(String subString) {
        this.subjectString =
                subString
                        .replace("+", " + ")
                        .replace("-", " - ")
                        .replace("x", " x ")
                        .replace("÷", " ÷ ")+" = ";
        try {
            answer = getAnswer(subString);
        } catch (Exception e){
            answer = null;
        }

    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectString='" + subjectString + '\'' +
                ", answer='" + answer + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                '}';
    }

    public String getAnswer() {
        return answer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isTrue(){
        if(userAnswer == null){
            return false;
        }
        return answer.equals(userAnswer);
    }

    /**
     * 根据题目计算答案
     *
     * @param subject 题目
     * @return 返回
     */
    private  String getAnswer(String subject) {
        Matcher m;
        //先将括号内的算完
        while (subject.contains("(") || subject.contains(")")) {
            Pattern r = Pattern.compile("\\((.*?)\\)");
            m = r.matcher(subject);
            if(m.find()){
                subject = subject.replace(m.group(0), getAnswer(m.group(1)));
            }
        }

        //先将所有乘法和除法计算完
        while (subject.contains("x") || subject.contains("÷")) {
            m = Pattern.compile("[0-9\\.\\/]*([x÷])[0-9\\.\\/]*").matcher(subject);
            if(m.find()){
                subject = getResult(subject, m.group(1));
            } else {
                return subject;
            }
        }
        //先将所有乘法计算完
        while (subject.contains("+") || subject.contains("-")) {
            Matcher m1 = Pattern.compile("[0-9\\.\\/]{1}([-\\+])[0-9\\.\\/]*").matcher(subject);
            Matcher m2 = Pattern.compile("-[0-9\\.\\/]*([-\\+])[0-9\\.\\/]*").matcher(subject);
            if(m1.find()){
                subject = getResult(subject, m1.group(1));
            } else if (m2.find()){
                subject = getResult(subject, m2.group(1));
            } else {
                return subject;
            }
        }

        return subject;
    }

    /**
     * 根据字符计算结果
     *
     * @param subject 题目
     * @param symbol  需要计算的运算符号
     * @return 返回计算好后的字符串
     */
    private  String getResult(String subject, String symbol) {

        Pattern r = Pattern.compile("[0-9\\.\\/]*" + symbol + "[0-9\\.\\/]*");
        if (subject.contains(symbol+"-")){
            r = Pattern.compile("[0-9\\.\\/]*" + symbol + "-[0-9\\.\\/]*");
        }
        if ("+".equals(symbol)) {
            r = Pattern.compile("[0-9\\.\\/]*\\+[0-9\\.\\/]*");
            if (subject.contains(symbol+"-")){
                r = Pattern.compile("[0-9\\.\\/]*\\+-[0-9\\.\\/]*");
            }
        }
        Matcher m = r.matcher(subject);
        boolean isFind = m.find();
        if (!isFind) {
            return "";
        }
        String[] nums;
        if ("+".equals(symbol)) {
            nums = m.group(0).split("\\+");
            if (m.group(0).charAt(0) == '-') {
                r = Pattern.compile("-[0-9\\.\\/]*\\+[0-9\\.\\/]*");
                if (subject.contains(symbol+"-")){
                    r = Pattern.compile("-[0-9\\.\\/]*\\+-[0-9\\.\\/]*");
                }
                m = r.matcher(subject);
                if(m.find()){
                    String[] nums_tmp = m.group(0).split(symbol);
                    nums[0] = nums_tmp[1];
                    nums[1] = nums_tmp[2];
                }
            } else {
                nums = m.group(0).split("\\+");
            }
        } else {
            nums = m.group(0).split(symbol);
            //特判诸如-1-1的算式
            if (m.group(0).charAt(0) == '-') {
                r = Pattern.compile("-[0-9\\./]*" + symbol + "[0-9\\./]*");
                if (subject.contains(symbol+"-")){
                    r = Pattern.compile("-[0-9\\./]*" + symbol + "-[0-9\\./]*");
                }
                m = r.matcher(subject);
                if(m.find()){
                    String[] nums_tmp = m.group(0).split(symbol);
                    nums[0] = nums_tmp[1];
                    nums[1] = nums_tmp[2];
                }
            } else {
                nums = m.group(0).split(symbol);

            }
        }
        String result = "";
        switch (symbol) {
            case "+":
                //判断是否有小数
                if (subject.contains(".")) {
                    BigDecimal a = new BigDecimal(nums[0]);
                    BigDecimal b = new BigDecimal(nums[1]);
                    result = String.valueOf(a.add(b));
                } else  if (subject.contains("/")) {
                    Fraction a = new Fraction(nums[0]);
                    Fraction b = new Fraction(nums[1]);
                    result = String.valueOf(a.addition(b));
                } else {
                    result = String.valueOf(Integer.parseInt(nums[0]) + Integer.parseInt(nums[1]));
                }
                break;
            case "-":
                if (subject.contains(".")) {
                    BigDecimal a = new BigDecimal(nums[0]);
                    BigDecimal b = new BigDecimal(nums[1]);
                    result = String.valueOf(a.subtract(b));
                } else  if (subject.contains("/")) {
                    Fraction a = new Fraction(nums[0]);
                    Fraction b = new Fraction(nums[1]);
                    result = String.valueOf(a.subtraction(b));
                } else {
                    result = String.valueOf(Integer.parseInt(nums[0]) - Integer.parseInt(nums[1]));
                }
                break;
            case "x":
                if (subject.contains(".")) {
                    BigDecimal a = new BigDecimal(nums[0]);
                    BigDecimal b = new BigDecimal(nums[1]);
                    result = String.valueOf(a.multiply(b));
                } else  if (subject.contains("/")) {
                    Fraction a = new Fraction(nums[0]);
                    Fraction b = new Fraction(nums[1]);
                    result = String.valueOf(a.multiplication(b));
                } else {
                    result = String.valueOf(Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]));
                }
                break;
            case "÷":
                if (subject.contains("/")) {
                    Fraction a = new Fraction(nums[0]);
                    Fraction b = new Fraction(nums[1]);
                    result = String.valueOf(a.division(b));
                } else {
                    BigDecimal a = new BigDecimal(nums[0]);
                    BigDecimal b = new BigDecimal(nums[1]);
                    result = String.valueOf(a.divide(b, 4, RoundingMode.HALF_UP));
                }
                break;
        }
        subject = subject.replace(m.group(0), result);
        subject = subject.replace("--", "+");
        subject = subject.replace("++", "+");
        subject = subject.replace("+-", "-");
        subject = subject.replace("÷+", "÷");
        subject = subject.replace("x+", "x");
        subject = subject.replace("-+", "-");
        if (subject.charAt(0) == '+') {
            subject = subject.substring(1);
        }
        return subject;
    }

}
