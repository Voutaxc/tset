import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class operation {
    static Stack<Character> readstack=new Stack<>();
    static Stack<Character> opestack=new Stack<>();
    static List<String> gramlist = new ArrayList<String>();
        static char[] symbol={'+','*','i','(',')','#'};
        static char[] pri = {' ','<','>','='};
        static int[][] ptable=
                {{1,-1,-1,-1,1,1},
                {1,1,-1,-1,1,1},
                {1,1,2,2,1,1},
                {-1,-1,-1,-1,0,2},
                {1,1,2,2,1,1},
                {-1,-1,-1,-1,2,0}};

//-1  < 0=  1> 2 " "


        public static void analyze(String in) {
            int len=in.length();
            char ope,cur,pre;
            for(int i=0;i<len;i++)
            {
                cur=in.charAt(i);
                pre=opestack.peek();
                ope=getpri(pre,cur);
                if(i==len-1&&readstack.size()==2&&readstack.peek()=='N')
                    return;
                switch(ope){
                    case ' ':
                        System.out.println("E");
                        return;
                    case '<':
                        readstack.push(cur);
                        opestack.push(cur);
                        System.out.println("I"+cur);
                        break;
                    case '=':
                    case '>':
                        StringBuilder stringBuilder=new StringBuilder();
                        while(true){
                            char popc=readstack.pop();
                            if(terminate(popc))
                                opestack.pop();
                            if(popc=='#'){
                                System.out.println("RE");
                                return;
                            }
                            stringBuilder.insert(0,popc);
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

        public static boolean terminate(char c){
            if(c=='+'||c=='*'||c=='i'||c=='#'||c=='('||c==')')
                return true;
            else
                return false;
        }

        public static char getpri(char pre,char cur){
            int lpre=0, lcur=0;
            for(int i=0;i<6;i++)
            {
                if(pre==symbol[i])
                {
                    lpre=i;
                    break;
                }
                if(i==5)
                  return ' ';
            }

            for(int i=0;i<6;i++)
            {
                if(cur==symbol[i])
                {
                    lcur=i;
                    break;
                }
                if(i==5)
                    return ' ';

            }
            switch(ptable[lpre][lcur]){
                case -1:
                    return '<';
                case 0:
                    return '=';
                case 1:
                    return '>';
                case 2:
                    return ' ';
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
            System.out.println(in);
            analyze(in.toString());
        }
    }

