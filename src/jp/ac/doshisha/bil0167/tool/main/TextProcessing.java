/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author katsuya
 */
public class TextProcessing {

    static String path = "temp_result.txt";
    static ArrayList<String> tagarray = new ArrayList<String>();

    public static void setPath(String path) {
        TextProcessing.path = path;
    }

    public static void setTagarray(ArrayList<String> tagarray) {
        TextProcessing.tagarray = tagarray;
    }

    public static void main(String[] args) {
//      try {
//          String string = readfile();
//         // serch(string);
//          replaceBelongs(string);
//          //writefile();
//      } catch (UnsupportedEncodingException ex) {
//          Logger.getLogger(TextProcessing.class.getName()).log(Level.SEVERE, null, ex);
//      } catch (IOException ex) {
//          Logger.getLogger(TextProcessing.class.getName()).log(Level.SEVERE, null, ex);
//      }
        analyze();
    }

    public static String readfile() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
        BufferedReader bReader_i = new BufferedReader(in);
        StringBuilder stb = new StringBuilder();
        // System.out.println(bReader_i.readLine());
        while (true) {
            String line = bReader_i.readLine();
            stb.append(line + "@\n");
            // System.out.println(line);
            if (line == null) {
                break;
            }
        }
        System.out.println(stb.toString());
        return stb.toString();
    }

    public static String readfile(String path2) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        FileInputStream fis = new FileInputStream(path2);
        InputStreamReader in = new InputStreamReader(fis, "UTF-8");
        BufferedReader bReader_i = new BufferedReader(in);
        StringBuilder stb = new StringBuilder();
        // System.out.println(bReader_i.readLine());
        while (true) {
            String line = bReader_i.readLine();
            stb.append(line + "@\n");
            // System.out.println(line);
            if (line == null) {
                break;
            }
        }
        // System.out.println(stb.toString());
        return stb.toString();
    }

    public static void writefile(String writetext) throws IOException {
        String outputFileName = "replacedtxt.txt";

        File outputFile = new File(outputFileName);
        // outputFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(outputFile);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        PrintWriter pw = new PrintWriter(osw);
        pw.write(writetext);
        pw.close();
    }

    public static ArrayList serch(String text) {

        String regex = "";
        StringBuilder stb = new StringBuilder(regex);
        ArrayList array = new ArrayList();

        HashSet<String> set = new HashSet<String>();
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        for (String temp : tagarray) {
            stb.append("＞([^＞＜]*)<" + temp + ">|");
        }
        //String regex = "＞([^＞＜]*)＜名詞＞";
        Pattern p = Pattern.compile(stb.toString());

        Matcher m = p.matcher(text);
        int j = 0;
        while (m.find()) {
            int start = m.start();
            int end = m.end();

            //   System.out.println("[全体] " + m.group());
            for (int i = 1; i <= m.groupCount(); i++) {
                //      System.out.println("[Group" + i + "] " + m.group(i));
                set.add(m.group(i));
                j++;
            }
        }
        //  System.out.println(j);
        for (String temp : set) {

            String regex2 = temp;
            Pattern p2 = Pattern.compile(regex2);
            Matcher m2 = p2.matcher(text);
            int count = 0;
            while (m2.find()) {
                count++;
            }
            result.put(temp, count);

        }
        for (String temp : set) {
            //  System.out.println(temp+"\t"+result.get(temp));

        }
        array.add(set);
        array.add(result);
        return array;
    }

    public static void replaceBelongs(String text) {
        HashSet<String> hs = new HashSet<String>();
        String newtext = text;
        String regex = "([^＞＜])*(＜[^＞＜]*＞,)";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(text);

        while (m.find()) {

            System.out.println("[全体] " + m.group());
            hs.add(m.group(2));
        }
        for (String temp : hs) {
            System.out.println(temp);
            newtext = newtext.replaceAll(temp, "#");

        }
        newtext = newtext.replaceAll("#＜名詞＞", "#");
        System.out.println(newtext);
        try {
            writefile(newtext);
        } catch (IOException ex) {
            Logger.getLogger(TextProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void analyze() {
        String inputtext = null;
        try {
            inputtext = readfile("C:\\replacedtxt.txt");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TextProcessing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder stb = new StringBuilder();
        String regex = "3#..(.+)@";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(inputtext);

        while (m.find()) {

            //  System.out.println(m.group(1));
            stb.append(m.group(1));
        }
        String newstring = stb.toString().replaceAll("@", "");
        ArrayList serch1 = serch(newstring);

        StringBuilder stb2 = new StringBuilder();
        String regex2 = "4#..(.+)@";
        Pattern p2 = Pattern.compile(regex2);

        Matcher m2 = p2.matcher(inputtext);

        while (m2.find()) {

            // System.out.println(m.group(1));
            stb2.append(m2.group(1));
        }
        String newstring2 = stb2.toString().replaceAll("@", "");
        ArrayList serch2 = serch(newstring2);
        match(serch1, serch2);

    }

    public static ArrayList match(ArrayList data1, ArrayList data2) {
        ArrayList array = new ArrayList();
        HashSet<String> has = new HashSet();
        HashSet<String> data1has = (HashSet) data1.get(0);
        HashSet<String> data2has = (HashSet) data2.get(0);
        HashMap data1map = (HashMap) data1.get(1);
        HashMap data2map = (HashMap) data2.get(1);
        for (String temp : data1has) {
            has.add(temp);

        }
        for (String temp : data2has) {
            has.add(temp);
        }
        //  System.out.println("\t男\t女");

        for (String temp : has) {
            try {
                String data01 = data1map.get(temp).toString();
                String data02 = data2map.get(temp).toString();

                if (data01 == null) {
                    data01 = "0";
                }

                if (data02 == null) {
                    data02 = "0";
                }

                System.out.println("\t" + data01 + "\t" + data02);
            } catch (Exception e) {

            }

        }

        return array;

    }

}
