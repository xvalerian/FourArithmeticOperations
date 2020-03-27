import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Select Language:\n1.中文(简体)\n2.English\nPlease enter a number:");
        int lId = sc.nextInt();
        String lIdStr = "chinese";
        if(lId == 2){
            lIdStr = "english";
        }
        System.out.println(Util.getLanguageString(lIdStr,"menu"));
        int type = sc.nextInt();
        System.out.println(Util.getLanguageString(lIdStr,"count"));
        int count = sc.nextInt();
        ArrayList<Subject> list = Generate.getSubjectList(type, count);
        int i = 1;
        int trueCount = 0;
        int failCount = 0;
        for (Subject subject : list) {
            System.out.print(Util.getLanguageString(lIdStr,"subject") + i +":  " + subject.subjectString);
            String userAnswer = sc.next();
            subject.setUserAnswer(userAnswer);
            if(subject.isTrue()){
                trueCount++;
                System.out.println(Util.getLanguageString(lIdStr,"true"));
            } else {
                failCount++;
                System.out.println(Util.getLanguageString(lIdStr,"fail") + subject.getAnswer());
            }
            i++;;
        }
        System.out.println(Util.getLanguageString(lIdStr,"trueCount") + trueCount);
        System.out.println(Util.getLanguageString(lIdStr,"failCount") + failCount);


    }
}