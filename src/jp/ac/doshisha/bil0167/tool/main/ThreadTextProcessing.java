/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author katsuya
 */
public class ThreadTextProcessing implements Runnable{
    private static String filepath;
    private static ArrayList<String> selectedbelongs;
    private static ArrayList<String> selectedtags;
    private static boolean showresulttable = true;

 ThreadTextProcessing(String filepath,ArrayList<String>selectedbelongs,ArrayList<String> selectedtags) {
        ThreadTextProcessing.filepath = filepath;
        ThreadTextProcessing.selectedbelongs = selectedbelongs;
        ThreadTextProcessing.selectedtags = selectedtags;
    }
    

    @Override
    public void run() {
        try{
                 FileInputStream fis = new FileInputStream(filepath);
            InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
           BufferedReader bReader_i = new BufferedReader(in);
            StringBuilder stb = new StringBuilder();
           // System.out.println(bReader_i.readLine());
            while (true) {
                String line = bReader_i.readLine();
                stb.append(line + "\n");
              //  System.out.println(line);
                if (line == null) {
                    break;
                }
            }
            String text = stb.toString();
            
            ArrayList<String> segRegex = createSegRegex(selectedbelongs);
          //  ArrayList<String> segText = new ArrayList<String>();
             Vector result = new Vector();
             
            for(String regex:segRegex){
                String segText=getSegText(text, regex);
                result.add(serch(segText));
                
            }
            Vector<Vector<String>> resulttable = mather(result);
            if(showresulttable){
                Vector<String> columnnames = new Vector<String>();
                columnnames.add("");
                for(String belongs:selectedbelongs){
                    columnnames.add(belongs);
                }
                        DefaultTableModel model;

        model = new DefaultTableModel(resulttable, columnnames);
        ResultBasicTable rbt = new ResultBasicTable(model);
        rbt.setTabledata(resulttable);
        rbt.setColumnnames(columnnames);
        rbt.setVisible(true);
            }
            
        }catch(Exception e){
            
        }
        
    }
    public static ArrayList<String> createSegRegex(ArrayList<String> belongs){
        ArrayList<String> array = new ArrayList<String>();
        //String regex = "3#..(.+)@";
        String regex = "#(.+)@";
        for(String temp :belongs){
        array.add(temp+regex);
        }
        
        return array;
        
    } 
    
    
    public static void main(String args[]){
          try{
              String filepath2="D:\\NetBeans 8.0.1\\workspace\\TextMindTools2\\temp\\tagged_tyousa2.txt";
                 FileInputStream fis = new FileInputStream(filepath2);
            InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
            BufferedReader bReader_i = new BufferedReader(in);
            StringBuilder stb = new StringBuilder();
           // System.out.println(bReader_i.readLine());
            while (true) {
                String line = bReader_i.readLine();
                stb.append(line+"\n");
              //  System.out.println(line);
                if (line == null) {
                    break;
                }
            }
            String text = stb.toString();
              //System.out.println(text);
            String regex = "男#(...+)@";
              String segText = getSegText(text, regex);
              selectedtags = new ArrayList<>();
              selectedtags.add("名詞");
              selectedtags.add("形容詞");
              //selectedtags.add("動詞");
            //  System.out.println(serch(segText));
           //   System.out.println(getSegText(text, regex));
              
                String regex2 = "女#(...+)@";
              String segText2 = getSegText(text, regex2);
//   
//                String regex1 = "B#(...+)@";
//              String segText1 = getSegText(text, regex1);
//                String regex3 = "C#(...+)@";
//              String segText3 = getSegText(text, regex3);
//                 String regex31 = "D#(...+)@";
//              String segText31 = getSegText(text, regex31);
//                 String regex32 = "E#(...+)@";
//              String segText32 = getSegText(text, regex32);
//                 String regex33= "G#(...+)@";
//              String segText33 = getSegText(text, regex33);
            //  System.out.println(serch(segText2));
              Vector result = new Vector();
              result.add(serch(segText));
              result.add(serch(segText2));
//              result.add(serch(segText1));
//              result.add(serch(segText3));
//              result.add(serch(segText31));result.add(serch(segText32));result.add(serch(segText33));
           //   System.out.println(result);
              mather(result);
            
            ArrayList<String> segtext = new ArrayList<String>();
            
        }catch(Exception e){
              System.out.println(e);
        }
    }
    
    public static String getSegText(String originaltext,String regex){
         StringBuilder stb = new StringBuilder();
        //String regex = "3#..(.+)@";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(originaltext);

        while (m.find()) {

            //  System.out.println(m.group(1));
            stb.append(m.group(1)+"\n");
        }
        String newstring = stb.toString().replaceAll("@", "");
    //    System.out.println("置換前\t"+newstring);
       // System.out.println(newstring);
       newstring = newstring.replaceAll("[^#].*#", "");
     //   System.out.println(newstring);
       newstring = newstring.replaceAll("\n", "");
      // newstring = newstring.replaceAll(" ", "");
      // System.out.println(newstring);
        return newstring;
    }
    
       public static ArrayList serch(String segtext) {

      
        StringBuilder stb = new StringBuilder();
        ArrayList array = new ArrayList();

        HashSet<String> set = new HashSet<String>();
        HashMap<String, String> result = new HashMap<String, String>();
        int counter =1;
        for (String temp : selectedtags) {
            
            if(selectedtags.size()==counter){
                 stb.append("([^＞＜]*)＜" + temp + "＞");
            }else{
                stb.append("([^＞＜]*)＜" + temp + "＞|");
            }
            counter ++;
        }
 //   System.out.println(stb);
        //String regex = "＞([^＞＜]*)＜名詞＞";
        Pattern p = Pattern.compile(stb.toString());

        Matcher m = p.matcher(segtext);
        int j = 0;
        while (m.find()) {
            int start = m.start();
            int end = m.end();

          //  System.out.println("[全体] " + m.group());
          for (int i = 1; i <= m.groupCount(); i++) {
             //        System.out.println("[Group" + i + "] " + m.group(i));
              if(m.group(i)!=null){
                  String tempst = m.group(i);
                set.add(tempst);
           //    System.out.println(m.group(i));
              }
//                j++;
          }
        }
        //  System.out.println(j);
        //タグ内部なしテキスト生成
        String plaintext = segtext.replaceAll("＜[^＜]+＞","＜＞");
        //頻度計算
        for (String temp : set) {
          //  System.out.println(temp);
            String regex2 = "＞("+temp+")＜";
       //     System.out.println(regex2);
            Pattern p2 = Pattern.compile(regex2);
            Matcher m2 = p2.matcher(plaintext);
            int count = 0;
            while (m2.find()) {
                count++;
            }
            result.put(temp, String.valueOf(count));

        }
        for (String temp : set) {
         //     System.out.println(temp+"\t"+result.get(temp));

        }
        array.add(set);
        array.add(result);
        return array;
    }
       
       public static Vector<Vector<String>> mather(Vector<ArrayList>seg){
           Vector<Vector<String>> resulttable = new Vector<Vector<String>>();
            HashSet<String> sumword = new HashSet<String>();
           for(ArrayList array:seg){
               HashSet<String> hs = (HashSet)array.get(0);
               for(String st:hs){
                
                   if(hs!=null){
                   sumword.add(st);
                   }
               }
               
           }
         //  System.out.println(sumword);
           ArrayList<String>temparray = new ArrayList<String>();
           temparray.addAll(sumword);
           temparray.remove(0);
        //   System.out.println(temparray);
             for(String temp:temparray){
                   Vector<String> cell = new Vector<String>();
                 cell.add(temp);
                 for(ArrayList array:seg){
                      HashMap<String, String> freq = (HashMap)array.get(1);
                      if(freq.get(temp)==null){
                          cell.add("0");
                      }else{
               cell.add(freq.get(temp));
                      }
           }
                 resulttable.add(cell);
                 
           }
         // System.out.println(resulttable);
           for(Vector<String>temp:resulttable){
               for(String st:temp){
             // System.out.print(st+"\t");
               }
         //   System.out.println("");
           }
             
           return resulttable;
       } 
      public static String zenkakuNumToHankaku(String s) {
    StringBuffer sb = new StringBuffer(s);
    for (int i = 0; i < sb.length(); i++) {
      char c = sb.charAt(i);
      if (c >= '０' && c <= '９') {
        sb.setCharAt(i, (char)(c - '０' + '0'));
      }
    }
    return sb.toString();
  }
}
