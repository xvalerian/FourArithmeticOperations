public class Fraction {

    private int numerator;     //定义属性:分子1
    private int denominator;  //定义属性:分母1


    //构造方法
    //constructor生成

    public Fraction(int numerator1, int denominator1) {
        this.numerator = numerator1;
        this.denominator = denominator1;
    }

    public Fraction(int num) {
        this.numerator = num;
        this.denominator = 1;
    }

    public Fraction(String f) {
        String[] s = f.split("/");
        if(s.length < 2) {
            this.numerator = Integer.parseInt(f);
            this.denominator = 1;
        } else{
            this.numerator = Integer.parseInt(s[0]);
            this.denominator = Integer.parseInt(s[1]);
        }

    }

    //加法
    public String addition(Fraction r) {

        int x = numerator * r.denominator + r.numerator * denominator;
        int y = denominator * r.denominator;
        return gcd(x,y);

    }


    //减法
    public String subtraction(Fraction r) {

        int x = numerator * r.denominator - r.numerator * denominator;
        int y = denominator * r.denominator;
        return gcd(x,y);
    }


    //乘法
    public String  multiplication(Fraction r){

        int x = numerator * r.numerator;
        int y = denominator * r.denominator;
        return gcd(x,y);
    }

    //除法
    public String division(Fraction r){

        int x = numerator * r.denominator;
        int y = denominator * r.numerator;
        return gcd(x,y);

    }

    //欧几里得+判断输出
    public String gcd(int m,int n) {

        //定义四个空变量
        int r;
        int t;
        int u;
        int w;

        //t和w用来存储分子和分母的初始数据
        t = m;
        w = n;

        //求最大公因数
        while (n != 0) {
            r = m % n;
            m = n;
            n = r;
        }
        //循环结束后此时m即为最大公因数
        //放个u = m看着方便
        u = m;

        //分子分母分别除以最大公因数
        t = t / u;
        w = w / u;

        //判断+输出
        //分子分母都为1 直接输出1
        if(t == 1 && w == 1){

            return "1";
        }
        //分子为0 分母不为0 直接输出0
        else if(t == 0 && w != 0){

            return "0";
        }
        //分子分母都不为0且不为1 直接输出
        else {
            if(t > 0 && w < 0){
                return "-" + t + "/" + w * -1;
            }
            return t + "/" + w;
        }
    }

}