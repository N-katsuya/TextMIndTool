package jp.ac.doshisha.bil0167.tool.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author katuya
 */
public class method {
    
    /**
	 * コピー元のパス[srcPath]から、コピー先のパス[destPath]へ
	 * ファイルのコピーを行います。
	 * コピー処理にはFileChannel#transferToメソッドを利用します。
	 * 尚、コピー処理終了後、入力・出力のチャネルをクローズします。
	 * @param srcPath    コピー元のパス
	 * @param destPath    コピー先のパス
	 * @throws IOException    何らかの入出力処理例外が発生した場合
	 */
	public static void copyTransfer(String srcPath, String destPath) 
	    throws IOException {
	    
	    FileChannel srcChannel = new
	        FileInputStream(srcPath).getChannel();
	    FileChannel destChannel = new
	        FileOutputStream(destPath).getChannel();
	    try {
	        srcChannel.transferTo(0, srcChannel.size(), destChannel);
	    } finally {
	        srcChannel.close();
	        destChannel.close();
	    }

	}

    public static Vector<Vector<String>> readCsv(File f) {
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        try {
            FileInputStream s = new FileInputStream(f);
            InputStreamReader r = new InputStreamReader(s, "Shift_JIS");
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.substring(0, line.length() - 1);
                String[] ary = line.split("[\t|,]");
                Vector<String> v = new Vector<String>();
                for (String cell : ary) {
                    v.add(cell);
                }
                data.add(v);
            }
            br.close();
            r.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static int countColumnNumber(File f) {
        int counter = 0;
        try {
            FileInputStream s = new FileInputStream(f);
            InputStreamReader r = new InputStreamReader(s, "Shift_JIS");
            BufferedReader br = new BufferedReader(r);
            String line;
            line = br.readLine();
            line = line.substring(0, line.length() - 1);
            String[] ary = line.split("[\t|,]");

            counter = ary.length;

            br.close();
            r.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }
    
     public static ArrayList<TreeSet<String>> getBelongsData( Vector<Vector<String>>  data,int belongsnumber) {
        ArrayList<TreeSet<String>> result = new ArrayList<>();
  for(int i=0;i<belongsnumber;i++){
      result.add(selectColumn(data, i));
      
  }
        
        
        return result;
    }
     
     public static TreeSet<String> selectColumn( Vector<Vector<String>>  data,int columnpointer) {
        TreeSet<String> result = new TreeSet<String>();
             for(Vector<String>data1:data){
                 if(data1.get(columnpointer)!=""){
             result.add(data1.get(columnpointer));
                 }
        
        }
        
        
        return result;
    }

    public static void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);

        for (int i = 0; i < model.getColumnCount(); i++) {
            out.write(model.getColumnName(i) + ",");
        }
        out.write("\n");
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                out.write(model.getValueAt(i, j).toString() + ",");
            }
            out.write("\n");
        }
        out.close();
    }

    public static StringBuilder FormatPulsarToR(StringBuilder stb, String columnsnames, int belo) {
        StringBuilder resultstb = new StringBuilder();
        String[] split = stb.toString().split("\n");
        HashMap<String, String> newline = new HashMap<String, String>();
        ArrayList<String> word = new ArrayList<String>();
        HashMap<String, String> freq = new HashMap<String, String>();
        for (int i = 0; i < split.length; i++) {
            String[] splittab = split[i].split("\t");
            //     System.out.println(splittab[1]+splittab[2]);
            if (word.indexOf(splittab[1]) == -1) {
                word.add(splittab[1]);
            }
            if (freq.get(splittab[1]) != null) {
                String str = freq.get(splittab[1]);
                StringBuilder stb2 = new StringBuilder(str);
                stb2.append("\t" + splittab[2]);
                // System.out.println(stb2);
                freq.put(splittab[1], stb2.toString());
            } else {
                freq.put(splittab[1], splittab[2]);
            }

            newline.put(splittab[1], freq.get(splittab[1]));
            //    System.out.println(newline.get(splittab[1]));
//            if (freq.size() == belo) {
//               freq.removeAllElements();
//            }
            // System.out.println(newline.get(splittab[1]));
        }
        //  System.out.println(word.get(0)+newline.get(word.get(0)));
        for (String temp : word) {
            // System.out.println(temp + "\t" + newline.get(temp));
            resultstb.append(temp + "\t" + newline.get(temp) + "\n");
        }
        return resultstb;

    }

    public static StringBuilder loadNoiseData(){
        StringBuilder stb = new StringBuilder();
        String noisepath = Tool_Main.getNoisepath();
        try{
                 FileInputStream fis = new FileInputStream(noisepath);
            InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
            BufferedReader bReader_i = new BufferedReader(in);
            while (true) {
                String line = bReader_i.readLine();
                //System.out.println("1:"+line);
                line = line.replace("/", "＜");
                //System.out.println("2:"+line);
                line = line.replace(";", "＞");
                
               // System.out.println("3:"+line);
              stb.append(line + "\n");
              //  System.out.println(line);
                if (line == null) {
                    break;
                }
            }
            String text = stb.toString();
            text = text.replace("null", "");
            bReader_i.close();
            //System.out.println(text);
          }catch(Exception ex){
              
          }
        return stb;
    }
    
    public static StringBuilder loadSynonymData(){
        StringBuilder stb = new StringBuilder();
        String synonympath = Tool_Main.getSynonympath();
        try{
                 FileInputStream fis = new FileInputStream(synonympath);
            InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
            BufferedReader bReader_i = new BufferedReader(in);
           // System.out.println(bReader_i.readLine());
            while (true) {
                String line = bReader_i.readLine();
                String word = line.split("<=")[0];
                String target = line.split("<=")[1];
                //String [] targetarray = target.split("||");
                String [] array = target.split("\\|");
                for(String temp:array){

                    stb.append(word+"<="+temp+"\n");
                }
              //  System.out.println(line);
                if (line == null) {
                    break;
                }
            }
            String text = stb.toString();
            text = text.replace("null", "");
            bReader_i.close();
          }catch(Exception ex){
              
          }
       // System.out.println(stb);
        return stb;
    }
    
}
