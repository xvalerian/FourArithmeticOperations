import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Generate {
    static Random random = new Random();
    private static String[] symbols = new String[]{"+", "-", "x", "÷"};

    public static ArrayList<Subject> getSubjectList(int type, int count) {
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<Subject> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Subject s = getSubject(type);
            while (map.put(s.answer, 1) != null) {
                s = getSubject(type);
            }
            list.add(s);
        }
        return list;
    }

    /**
     * 获得题目和答案
     *
     * @param type 年级
     * @return 返回题目和答案
     */
    private static Subject getSubject(int type) {
        String subject = generateSubject(type);
        Subject s = new Subject(subject);
        while (s.getAnswer() == null) {
            s = new Subject(generateSubject(type));
        }

        if (type == 1) {
            //答案不能大于1000且答案不能为负数或小数
            while (s.getAnswer() == null || s.getAnswer().contains("-") || s.getAnswer().contains(".") || Integer.parseInt(s.getAnswer()) > 1000) {
                    s = new Subject(generateSubject(type));
            }
        } else if (type == 2) {
            //答案不能大于10000或小于-10000且答案不能为小数
            while (s.getAnswer() == null || s.getAnswer().contains(".") || Integer.parseInt(s.getAnswer()) > 10000 || Integer.parseInt(s.getAnswer()) < -10000) {
                s = new Subject(generateSubject(type));
            }
        }
        return s;
    }

    /**
     * 根据类型来获得数字
     *
     * @param type 年级
     * @return 返回数字
     */
    private static String getNum(int type) {
        if (type == 1) {
            return generateNumbers(99);
        } else if (type == 2) {
            return generateNumbers(999);
        } else if (type == 3) {
            return generateDecimal();
        } else if (type == 4) {
            return generateDecimal();
        } else if (type == 5) {
            return generateFraction();
        } else {
            return "";
        }
    }

    /**
     * 生成题目
     *
     * @param type 年级
     * @return 返回
     */
    private static String generateSubject(int type) {
        String subject = getNum(type);

        //随机取得数字的个数
        int count = random.nextInt(2) + 2;
        if (type == 4) {
            count = random.nextInt(8) + 2;
        }
        //带括号的数量
        int bCount = random.nextInt(3) + 1;
        String symbolFist = "";

        for (int i = 0; i < count; i++) {
            String symbol = generateSymbol();
            //记录第一个运算符
            if (i == 0) {
                symbolFist = symbol;
            } else if (i == 1) {
                //保证第一个运算符与第二个不一样
                while (symbolFist.equals(symbol)) {
                    symbol = generateSymbol();
                }
            }
            if (bCount > 0 && ("x".equals(symbol) || "÷".equals(symbol))) {
                subject = subject + symbol + generateBracketsSubject(type);
                bCount--;
            } else {
                subject = subject + symbol + getNum(type);
            }
        }
        return subject;
    }

    private static String generateBracketsSubject(int type) {
        String symbol = symbols[random.nextInt(2)];
        return "(" + getNum(type) + symbol + getNum(type) + ")";
    }


    /**
     * 生成数字
     *
     * @param num 生成数字的范围
     * @return 返回1~100内的随机数字
     */
    private static String generateNumbers(int num) {
        return String.valueOf(random.nextInt(num) + 1);
    }

    /**
     * 生成小数
     *
     * @return 返回1~100内的随机数字
     */
    private static String generateDecimal() {
        return String.format("%.2f", random.nextDouble() * 100);
    }

    /**
     * 生成分数
     *
     * @return 返回分子分母为1-100的分数
     */
    private static String generateFraction() {
        return String.format("%d/%d", random.nextInt(9) + 1, random.nextInt(9) + 1);
    }

    /**
     * 生成运算符号
     *
     * @return 返回运算符号
     */
    private static String generateSymbol() {

        return symbols[random.nextInt(4)];
    }

}
