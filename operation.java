import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class operation {
    static Stack<Character> readstack=new Stack<>();
    static Stack<Character> opestack=new Stack<>();
    static List<String> gramlist = new ArrayList<String>();
        static char[] tsymbol = {'+', '*', 'i', '(', ')', '#'};
        static char[] priority = {' ', '<', '>', '='};
        static int[][] table = {{2, 1, 1, 1, 2, 2},
                {2, 2, 1, 1, 2, 2},
                {2, 2, 0, 0, 2, 2},
                {1, 1, 1, 1, 3, 2},
                {2, 2, 0, 0, 2, 2},
                {1, 1, 1, 1, 0, 0}};




        public static void analyze(String in) {
            int len=in.length();
            char  ope,cur,pre;
            for(int i=0;i<len;i++)
            {
                cur=in.charAt(i);
                pre=opestack.peek();
                ope=getope(pre,cur);
                if(i==len-1&&readstack.size()==2&&readstack.peek()=='N')
                    return;
                switch(ope){
                    case ' ':
                        System.out.println("E");
                        return;
                    case '<':
                        readstack.push(cur);
                        opestack.push(cur);
                        System.out.println("I" + cur);
                        break;
                    case '=':
                    case '>':
                        StringBuilder stringBuilder = new StringBuilder();
                        while(true){
                            char popc = readstack.pop();
                            if(terminate(popc))
                                opestack.pop();
                            if(popc == '#'){
                                System.out.println("RE");
                                return;
                            }
                            stringBuilder.insert(0, popc);
                            if(gramlist.contains(stringBuilder.toString())){
                                readstack.push('N');
                                i--;
                                System.out.println("R");
                                break;
                            }
                        }
                        break;
                }
            }
        }

        public static boolean terminate(char c) {
            if(c!='+' && c!='*' && c!='i' && c!='#' && c!='(' && c!=')')
                return false;
            else
                return true;
        }

        public static char getope(char prev, char cur){
            int iprev=0, jcur=0;
            switch(prev){
                case '+':
                    iprev = 0;
                    break;
                case '*':
                    iprev = 1;
                    break;
                case 'i':
                    iprev = 2;
                    break;
                case '(':
                    iprev = 3;
                    break;
                case ')':
                    iprev = 4;
                    break;
                case '#':
                    iprev = 5;
                    break;
                default:
                    return ' ';
            }
            switch(cur){
                case '+':
                    jcur = 0;
                    break;
                case '*':
                    jcur = 1;
                    break;
                case 'i':
                    jcur = 2;
                    break;
                case '(':
                    jcur = 3;
                    break;
                case ')':
                    jcur = 4;
                    break;
                case '#':
                    jcur = 5;
                    break;
                default:
                    return  ' ';
            }
            switch(table[iprev][jcur]){
                case 0:
                    return ' ';
                case 1:
                    return '<';
                case 2:
                    return '>';
                case 3:
                    return '=';
                default:
                    return ' ';
            }
        }

        public static void main(String[] args) throws IOException {
            //String file="E:\\1821大三上学习资料\\编译原理_邵兵\\ope\\src\\input.txt";
            String file=args[0];
            opestack.push('#');
            readstack.push('#');
            gramlist.add("N+N");
            gramlist.add("N*N");
            gramlist.add("(N)");
            gramlist.add("i");
            BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String input = read.readLine();
            StringBuilder in = new StringBuilder(input);
           // System.out.println(in);
            in.append('#');
            analyze(in.toString());
        }
    }

