/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static jp.ac.doshisha.bil0167.tool.main.MecabBasicProcessing.MecabFormatter;

/**
 *
 * @author katsuya
 */
public class MecabBasicProcessing {

    private static int belongcount;
    private static String path;
    public final static String mecabpospath = "./setting/MecabPos.ini";
    
  

    public static void main(String[] args) {
       //test();

    }

    public static void ExcuteMecab(int belongcount, String path) {
        // TODO add your handling code here:
        MecabBasicProcessing.belongcount = belongcount;
        MecabBasicProcessing.path = path;

        try {

            int prevtaglinev = 0;
            int tagline = 1;

            int data = 0;
            int f = 0;
            int g = 0;
//            String[] command = {"cmd.exe", "/c", "mecab", path};
            String command = "mecab " + path + " -o " + "temp.txt";
       //     System.out.println(command);
            Runtime.getRuntime().exec(command);
            //BufferedReader bReader_i = new BufferedReader(new InputStreamReader(ps.getInputStream(), "MS932"));
            FileInputStream fis = new FileInputStream("temp.txt");
            InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
            BufferedReader bReader_i = new BufferedReader(in);
            StringBuilder stb = new StringBuilder();

            String targetLine;
            String tempbe1 = null;
            Vector<String> tempbelongs = new Vector<String>();
            HashMap<String, Integer> belongsCuN = new HashMap<String, Integer>();

            ArrayList<String> tword = new ArrayList<String>();

            HashMap<String, String> word = new HashMap<String, String>();
            HashMap<String, String> wordd = new HashMap<String, String>();
            HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();

            while (true) {

                targetLine = bReader_i.readLine();

                if (targetLine == null) {
                    break;
                } else if (targetLine.equals("EOS")) {
                    tagline = 1;
                    data++;
                    f = 0;
                    g = 0;
                    tempbelongs.clear();

                    prevtaglinev = 0;
                    continue;
                } else {
                    String targetWord = targetLine.split("[\t|,]")[7];
                    if (targetLine.split("[\t|,]")[7].equals("*")) {
                        targetWord = targetLine.split("[\t|,]")[0];
                    }

                    String targetType = targetLine.split("[\t|,]")[1];
                    String targetType2 = targetLine.split("[\t|,]")[2];

                    if (tagline <= belongcount && belongsCuN.size() == 0) {

                        String u = "第" + tagline + "属性-" + targetWord + ":";

                        belongsCuN.put(targetWord, 1);
                        tempbelongs.add(targetWord);

                        prevtaglinev = 1;

                    } else if (tagline <= (belongcount * 2) && belongsCuN.size() != 0 && targetWord != null) {
                        int tagnum = 1;
                        if (tagline != 1) {
                            tagnum = tagline - prevtaglinev;
                        }
                        prevtaglinev = tagnum;
                        for (int j = 0; j < belongsCuN.size(); j++) {

                            try {
                                if (tagnum == belongsCuN.get(targetWord)) {
                                    f = 1;

                                    tempbelongs.add(targetWord);
                                    break;
                                }
                            } catch (Exception e) {
                                break;

                            }
                            String t = "第" + tagnum + "属性-" + targetWord + ":";
                        }

                        if (f == 0 && !(targetWord.equals(",")) && targetWord != null) {

                            belongsCuN.put(targetWord, tagnum);

                            if (!targetWord.isEmpty() && targetWord != null) {
                                tempbelongs.add(targetWord);

                            }

                        }

                    }
                    f = 0;
                    tagline++;

                    if (tagline == belongcount * 2) {
                        StringBuilder sb = new StringBuilder();
                        for (String s : tempbelongs) {
                            sb.append(s + "\t");
                        }
                        tempbe1 = new String(sb);
                    }

                    if (tagline > belongcount * 2 && tword.size() == 0) {

                        String tild = tempbe1 + targetWord;

                        tword.add(tild);
                        word.put(tild, targetType);
                        wordd.put(tild, targetType2);
                        wordFreq.put(tild, 1);
                    } else if (tagline > belongcount * 2 && tword.size() != 0) {

                        for (int j = 0; j < tword.size(); j++) {
                            String s = tword.get(j);

                            String tild = tempbe1 + targetWord;

                            if (tild.equals(s)) {
                                int i = wordFreq.get(tild);
                                i++;
                                wordFreq.put(tild, i);
                                g = 1;

                                break;

                            }

                        }
                        if (g == 0) {

                            String tild = tempbe1 + targetWord;

                            tword.add(tild);
                            word.put(tild, targetType);
                            wordd.put(tild, targetType2);
                            wordFreq.put(tild, 1);

                        }
                        g = 0;

                    }

                }

            }
            ArrayList CreateMaps = BasicTextProcessing.CreateMaps(belongcount + 1);
            for (String s3 : tword) {

                if (word.get(s3).equals("名詞") || word.get(s3).equals("動詞") || word.get(s3).equals("形容詞")) {
                    String temp = s3 + "\t" + word.get(s3) + "\t" + wordd.get(s3) + "\t" + wordFreq.get(s3);
                    ArrayList<String> keys = new ArrayList<String>();
                    for (int i = 0; i < belongcount + 1; i++) {
                        keys.add(s3.split("\t|,")[i]);
                    }
                    System.out.println(BasicTextProcessing.InsertRegisterKeys(keys, wordFreq.get(s3), (Map) CreateMaps.get(0)));
                    try {
                        CreateMaps.add(0, BasicTextProcessing.InsertRegisterKeys(keys, wordFreq.get(s3), (Map) CreateMaps.get(0)));
                    } catch (Exception ex) {

                    }

                    System.out.println(CreateMaps);
                    //  BasicTextProcessing.NewRegisterKeys(tword, data, tword);

                }

            }

            //SQLiteTest();
           // ps.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void ExcuteMecab2(int belongcount, String path) {
        // TODO add your handling code here:
        MecabBasicProcessing.path = path;

        try {

            int prevtaglinev = 0;
            int tagline = 1;

            int data = 0;
            int f = 0;
            int g = 0;
//            String[] command = {"cmd.exe", "/c", "mecab", path};
            String command = "mecab " + path + " -o " + "tampY.txt";
            System.out.println(command);
            Runtime.getRuntime().exec(command);
            // 標準出力
            //BufferedReader bReader_i = new BufferedReader(new InputStreamReader(ps.getInputStream(), "MS932"));
            FileInputStream fis = new FileInputStream("tampY.txt");
            InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
            BufferedReader bReader_i = new BufferedReader(in);
            StringBuilder stb = new StringBuilder();
           // System.out.println(bReader_i.readLine());
            while (true) {
                String line = bReader_i.readLine();
                stb.append(line + "\n");
                System.out.println(line);
                if (line == null) {
                    break;
                }
            }
            //System.out.println(stb.toString());
            String MecabFormatter = MecabFormatter(stb.toString());
            // System.out.println(MecabFormatter);
            String outputFileName = "temp_result.txt";

            // ファイルオブジェクトの生成
            File outputFile = new File(outputFileName);
            outputFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(outputFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "Shift_JIS");
            PrintWriter pw = new PrintWriter(osw);
            pw.write(MecabFormatter);
            pw.close();

        } catch (Exception ex) {

        }
    }
    
       public static String ExMecab(String path,String filename,boolean isColumnname) {
        // TODO add your handling code here:
        MecabBasicProcessing.path = path;
        String outputfilename = null;
        //   System.out.println(path);
          // System.out.println(filename);
        try {

          
//            String[] command = {"cmd.exe", "/c", "mecab", path};
             filename = filename.replaceAll(".csv", "");
           filename = filename.replaceAll(".txt", "");
          String resultfilename ="./temp/result_"+filename+".txt";
//        String command = "mecab " + path + " -o " + resultfilename;
//          //  System.out.println(command);
//            Runtime.getRuntime().exec(command);
          taggedText tg = new taggedText(resultfilename, filename,isColumnname);
          //Thread.sleep(2000);
          tg.run();
          outputfilename= tg.getOutputFileName();
          //  System.out.println(outputfilename);
return outputfilename; 
        } catch (Exception ex) {

        }
        return outputfilename;
    }

    public static String MecabFormatter(String text) {
        StringBuffer sb = new StringBuffer();
        Map<String, String> posMap = readInPosProcessingFile(mecabpospath);
        String[] sentences = text.split("EOS");
        for (String sentence : sentences) {
            String[] words = sentence.split("\n");
            for (String wordPOS : words) {
                StringTokenizer st = new StringTokenizer(wordPOS);
                if (st.countTokens() < 2) {
                    continue;
                }

                String word = null;
                String pos_list = null;
                if (st.hasMoreTokens()) {
                    word = (String) st.nextToken();
                    pos_list = (String) st.nextToken();
                }

                String[] elements = pos_list.split(",");
                if (elements.length > 1) {
                    String originalPos = elements[0];
                    if (elements[1].compareTo("*") != 0) {
                        originalPos += "-" + elements[1];
                    }
                    if (elements[2].compareTo("*") != 0) {
                        originalPos += "-" + elements[2];
                    }

                    String pos;
                    if (posMap.containsKey(originalPos)) {
                        pos = posMap.get(originalPos);
                    } else {
                        pos = elements[0];
                        posMap.put(originalPos, pos);
                    }

                    if (!originalPos.startsWith("記号-空白") && !originalPos.equals("\\")) {
                        word = processKeyKuhao(word);
                        //                              System.out.println("word\t"+word);
                        sb.append(word);
                        sb.append("＜");
                        sb.append(pos);
                        sb.append("＞");
                    }
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static Map<String, String> readInPosProcessingFile(String file) {
        Map<String, String> posMap = new HashMap<String, String>();
        try {
            FileInputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "SJIS"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] pos = line.split("=");
                posMap.put(pos[0], pos[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posMap;
    }

    private static String processKeyKuhao(String word) {
        String retVal = word;
        if (word.equals("＜")) {
            retVal = "KEYP_LEFT";
        }
        if (word.equals("＞")) {
            retVal = "KEYP_RIGHT";
        }

        return retVal;
    }
}

class taggedText implements  Runnable{
    private static String resultfilename;
    private static String filename;
    private static boolean isColumnname;
    private static String outputFileName ;
    private static StringBuilder noisestb = new StringBuilder();
    private static StringBuilder synonymstb = new StringBuilder();
    public static String getOutputFileName() {
        return outputFileName;
    }
    

    public taggedText(String resultfilename,String filename,boolean isColumnname) {
        taggedText.resultfilename = resultfilename;
        taggedText.filename = filename;
        taggedText.isColumnname = isColumnname;
       outputFileName = "./temp/tagged_"+filename+".txt";
      //  System.out.println("ou"+outputFileName);
    }



    @Override
    public void run() {
        noisestb = method.loadNoiseData();
        synonymstb = method.loadSynonymData();
     //   System.out.println("mecab"+noisestb);
        try{
         FileInputStream fis = new FileInputStream(resultfilename);
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
            //System.out.println(stb.toString());
            String MecabFormatter = MecabFormatter(stb.toString());
             //System.out.println(MecabFormatter);
                 HashSet<String> hs = new HashSet<String>();
                  String newtext = MecabFormatter;
//                  System.out.println(newtext);
                  if(isColumnname){
                 String columnname = MecabFormatter.split("\n")[0];
        newtext = newtext.replace(columnname, "");
                  }
                   newtext = newtext.replace("\n", "@\n");
        String regex = "([^＞＜])*(＜[^＞＜]*＞,)";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(MecabFormatter);

        while (m.find()) {

            hs.add(m.group(2));
        }
        for (String temp : hs) {
            newtext = newtext.replaceAll(temp, "#");

        }
        newtext = newtext.replaceAll("#＜名詞＞", "#");
//                         System.out.println(newtext);
 //バグにつきコメントアウト中
        //削除語適応
//        String [] noise = noisestb.toString().split("\n");
//        for(String temp:noise){
//            String tregex = "＞"+temp;
//            //System.out.println(tregex);
//            newtext = newtext.replaceAll(tregex, "＞");
//        }
//            //System.out.println("newtext"+newtext);
//        //類義語適応
//        String [] synonym =synonymstb.toString().split("\n");
//        for(String temp:synonym){
//                String word = temp.split("<=")[0];
//                String target = temp.split("<=")[1];
//                newtext = newtext.replaceAll(target,word);
//            
//        }
            //コメントアウトここまで
         //  System.out.println(newtext);
            // ファイルオブジェクトの生成
            File outputFile = new File(outputFileName);
          //  System.out.println(outputFileName);
          //  outputFile.createNewFile();
//System.out.println("outputfilename"+outputFileName);
            FileOutputStream fos = new FileOutputStream(outputFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "Shift_JIS");
            PrintWriter pw = new PrintWriter(osw);
            pw.write(newtext);
            pw.close();

        } catch (Exception ex) {
            //System.out.println(ex);
        }
    }
    
}