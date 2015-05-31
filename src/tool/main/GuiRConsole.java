package jp.ac.doshisha.bil0167.tool.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import jp.ac.doshisha.bil0167.tool.R.ChiSquaretestGraphicsFrame;
import jp.ac.doshisha.bil0167.tool.R.RFrame;
import jp.ac.doshisha.bil0167.tool.R.Rcommand;
import jp.ac.doshisha.bil0167.tool.R.SelectVariablesPanel;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineCallbacks;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.REngineInputInterface;
import org.rosuda.REngine.REngineOutputInterface;
import org.rosuda.REngine.REngineUIInterface;
import org.rosuda.REngine.JRI.JRIEngine;
import org.rosuda.REngine.REXPMismatchException;

public class GuiRConsole implements REngineCallbacks, REngineInputInterface,
        REngineOutputInterface, REngineUIInterface {

    private JTextPane outputTextPane;
    private static JRIEngine rengine;
    private final static LinkedList<String> commandQueue = new LinkedList<String>();
    private SimpleAttributeSet inputAttr = new SimpleAttributeSet();
    private SimpleAttributeSet outputAttr = new SimpleAttributeSet();
    private SimpleAttributeSet errorAttr = new SimpleAttributeSet();
    private static boolean busy;
    private static boolean interrupt = false;
    private static JFrame frame;
    private static boolean defalutdataframe = false;

    private static boolean defalutdataframe2 = false;
    public static Vector<String> temptext = new Vector<String>();
    private static boolean showresultflag = false;
    public static boolean inputwaitflag = false;
    static int waitcounter = 0;
    static int waitcounters = 0;
    private static String chisqdataname ="";

    public static void setChisqdataname(String chisqdataname) {
        GuiRConsole.chisqdataname = chisqdataname;
    }
    
    
    private static List<String> commands =  new LinkedList<String>();
		
	private static int idx = 0;

    public static void setDefalutdataframe(boolean defalutdataframe) {
        GuiRConsole.defalutdataframe = defalutdataframe;
    }

    public static void setDefalutdataframe2(boolean defalutdataframe) {
        GuiRConsole.defalutdataframe = defalutdataframe;
    }

    public static void setCommand(String text) {
        //  temptext = new Vector<String>();
        commands.add(text);
        idx = commands.size()-1;
        addCommandToQueue2(text);
        showresultflag = true;

    }

    public static void setCommand(ArrayList<String> commandlist) {
        temptext = new Vector<String>();
        waitcounter = commandlist.size() * 2;

        for (String text : commandlist) {
            commands.add(text);
            addCommandToQueue2(text);
        }
    
//        waitcounters = 0;
//               WaitShowResult2 wsr = new WaitShowResult2(3,chisqdataname);
//        Thread thread = new Thread(wsr);
//        thread.start();
    idx = commands.size()-1;
    }
    
    public static void setCommand2(ArrayList<String> commandlist) {
        temptext = new Vector<String>();
        waitcounter = commandlist.size() * 2;
        WaitShowResult wsr = new WaitShowResult();
        Thread thread = new Thread(wsr);
        thread.start();
        try {
            rengine.parseAndEval(commandlist.get(0));
                        rengine.parseAndEval(commandlist.get(1));
                                    rengine.parseAndEval(commandlist.get(2));
            //  addCommandToQueue2();
        } catch (REngineException ex) {
            Logger.getLogger(GuiRConsole.class.getName()).log(Level.SEVERE, null, ex);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(GuiRConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        showresultflag = true;

    }
    public static void main(String[] args){
        StartGUIRConsole();
    }

    public static void StartGUIRConsole() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GuiRConsole guiRConsole = new GuiRConsole();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(GuiRConsole.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public GuiRConsole() throws UnsupportedEncodingException {
        initComponents();
        initAttributeSets();
        initR();
        if (defalutdataframe) {

            NameForm nm = new NameForm(Tool_Main.getFormatR(), "\t");

            nm.setVisible(true);
        } else if (defalutdataframe2) {
   //   RUtility.readInVector(rengine.getRni(), Tool_Main.getFormatR().toString(), "\t", "temp");
            // System.out.println(Tool_Main.getFormatR());
            StringBuilder stb = new StringBuilder(SelectBelongsFrame.getForR());
            System.out.println(stb);
            NameForm nm = new NameForm(stb, "\t");

            nm.setVisible(true);
        }
        setCommand("library(JavaGD)");
     //   setCommand("JavaGD()");

    }

    private void initComponents() {
// JFrame を作成
        frame = createFrame();
        outputTextPane = createOutputTextPane();
        JTextArea inputTextArea = createInputTextArea();

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(outputTextPane), BorderLayout.CENTER);
        frame.add(new JScrollPane(inputTextArea), BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    public static JRIEngine getRengine() {
        return rengine;
    }

    private void initAttributeSets() {
        inputAttr.addAttribute(StyleConstants.Foreground, Color.RED);
        outputAttr.addAttribute(StyleConstants.Foreground, Color.BLACK);
        errorAttr.addAttribute(StyleConstants.Foreground, Color.BLUE);
    }

    private JFrame createFrame() {
        final JFrame frame = new JFrame(" R GUIコンソール");
        frame.setBounds(0, 0, 600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                interrupt = true;
//addCommandToQueue("base::q(save=\"no\")");
            }
        });
        return frame;
    }

    private JTextPane createOutputTextPane() {
        JTextPane outputTextPane = new JTextPane();
        outputTextPane.setEditable(false);
     //   outputTextPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        outputTextPane.setBorder(new EmptyBorder(0, 2, 0, 2));

        return outputTextPane;
    }

    private JTextArea createInputTextArea() {
        JTextArea inputTextArea = new JTextArea();
        inputTextArea.setLineWrap(true);
        inputTextArea.setRows(15);
        inputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        inputTextArea.setBorder(new EmptyBorder(0, 2, 0, 2));
        inputTextArea.getActionMap().put("submitCommand", new SubmitCommandAction());
        inputTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                "submitCommand");
        inputTextArea.getActionMap().put("stop", new StopAction());
        inputTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                "stop");
        inputTextArea.getActionMap().put("show", new ShowHistory());
        inputTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                "show");
        return inputTextArea;
    }

    private void initR() {
        try {
            rengine = new JRIEngine(new String[]{"--no-save"},
                    this,
                    false);
            rengine.getRni().startMainLoop();
        } catch (REngineException e) {
//System.exit(1); 

        }
    }

    private synchronized void addCommandToQueue(String command) {
        commandQueue.addLast(command);
        notifyAll();
    }

    private static void addCommandToQueue2(String command) {
        commandQueue.addLast(command);

    }

    private synchronized String getCommandFromQueue() {

        while (commandQueue.isEmpty()) {
            rengine.getRni().rniIdle();
            try {
                wait(25);
            } catch (InterruptedException e) {
            }
        }
        return commandQueue.poll();
    }

    private class SubmitCommandAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea inputTextArea = (JTextArea) e.getSource();
//            commands = new LinkedList<String>();
//		idx = 0;
                commands.add(inputTextArea.getText());
                    idx = commands.size()-1;
            addCommandToQueue(inputTextArea.getText());
            inputTextArea.setText("");
        }
    }

    private class StopAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (busy) {
                rengine.getRni().rniStop(0);
                commandQueue.clear();
            }
        }
    }
    
    private class ShowHistory extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
                        JTextArea inputTextArea = (JTextArea) e.getSource();
                        	final String line =commands.get(idx);
                        if(idx > 0){
									idx--;
                        }else {
									idx = commands.size() - 1;
                        }

          
				     		
								
								
								 inputTextArea.removeAll();
                                                               //  System.out.println(commands);
                                                                 inputTextArea.setText(line);
								    }
        
    }
//---------------------------------------------------------------------
// 以下はR の対話ループのコールバック関数
//---------------------------------------------------------------------

    @Override
    public String RReadConsole(REngine eng, String prompt, int addToHistory) {
        waitcounters ++;
        writeConsole(prompt, 2); // プロンプトを出力
        String command = getCommandFromQueue(); // コマンドをキューから取得
        command += "\n"; // コマンドの最後に改行を追加
        writeConsole(command, 2); // 入力したコマンドを出力
        return command;
    }

    @Override
    public void RWriteConsole(REngine eng, String text, int oType) {
        writeConsole(text, oType);
    }

    private void writeConsole(final String text, final int type) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Document document = outputTextPane.getDocument();

                SimpleAttributeSet attrSet;
                switch (type) {// type に応じた色のAttributeSet を選択
                    case 0:
                        attrSet = outputAttr;
                        break;
                    case 1:
                        attrSet = errorAttr;
                        break;
                    default:
                        attrSet = inputAttr;
                        break;
                }
                try {// text を挿入
           
                    
                    document.insertString(document.getLength(), text, attrSet);
                   //コメントアウト
//                    if (type == 0) {
//
//                        try {
//                            temptext.add(text);
//                        } catch (Exception ex) {
//
//                        }
//
//                    }
//
//                    if (type == 2) {
//                        waitcounter--;
//                        if (waitcounter == 0) {
//                            inputwaitflag = true;
//                        }
//
//                    }

                } catch (BadLocationException e) {
// 通常この例外は発生しない
                }
// 一番下が表示されるようにスクロール
//                outputTextPane.scrollRectToVisible(new Rectangle(0, outputTextPane.getHeight(),
//                        0, 0));

            }

        });

    }

    @Override
    public void RShowMessage(REngine eng, String text) {
        writeConsole(text, 1); // エラーとして出力
    }

    @Override
    public void RFlushConsole(REngine eng) {
    }

    @Override
    public void RBusyState(REngine eng, int state) {

        busy = (state == 1);

    }

    @Override
    public String RChooseFile(REngine eng, boolean newFile) {
        return null;
    }

    public static boolean isInterrupt() {
        return interrupt;
    }

    public static JFrame getFrame() {
        return frame;
    }

}

class WaitShowResult implements Runnable {

    @Override
    public void run() {
        while (!GuiRConsole.inputwaitflag) {
          //  System.out.println("");
        }
        StringBuilder stb = new StringBuilder();
        for (String temp : GuiRConsole.temptext) {
            stb.append(temp);
        }
        StringBuilder stb2 = new StringBuilder();
        for (int i = 5; i < GuiRConsole.temptext.size(); i++) {
            stb2.append(GuiRConsole.temptext.get(i) + ",");
        }
        String[] split = stb2.toString().split("\n");
        Vector<String> vec = new Vector();
        for (int i = 0; i < split.length; i++) {
            String[] splittab = split[i].split(",");
            try {
                vec.add(splittab[0]);
            } catch (Exception ex) {

            }
        }
        //  System.out.println(vec);
        final Vector newvec = new Vector();
        for (int i = 1; i < vec.size(); i++) {
            String str = vec.get(i);
            str.replaceAll(" ", "");
            newvec.add(i + " " + str);

        }

        RFrame.setElement(newvec);
        GuiRConsole.inputwaitflag = false;
        final JFrame jf = new JFrame();
        JPanel jp = new JPanel();
        JTextArea ta = new JTextArea(stb.toString());
        JScrollPane js = new JScrollPane(ta);
        JButton jb = new JButton("OK");
        jp.add(js, BorderLayout.NORTH);
        jp.add(jb, BorderLayout.SOUTH);
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jf2 = new JFrame();
                SelectVariablesPanel sv = new SelectVariablesPanel();
                sv.setElements(RFrame.getDatanames());
                jf2.add(sv);
                jf2.setSize(500, 320);
                jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jf2.setVisible(true);
                jf.dispose();
            }
        });
        jf.add(jp);
        jf.setSize(400, 450);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  jf.setVisible(true);
//                 JFrame jf2 = new JFrame();
//                    SelectVariablesPanel sv = new SelectVariablesPanel();
//                    sv.setElements(newvec);
//                    jf2.add(sv);
//                    jf2.setSize(500,320);
//                    jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    jf2.setVisible(true);
        ChiSquaretestGraphicsFrame cgf = new ChiSquaretestGraphicsFrame();
        cgf.getSelectVariablesPanel1().setElements(newvec);
        cgf.setVisible(true);
    }

}


class WaitShowResult2 implements Runnable {
private static int waitcounter ;
private static String dataname;
    public WaitShowResult2(int counter,String resultdataname) {
        waitcounter = counter;
        dataname = resultdataname;
    }

    @Override
    public void run() {
        
        while (waitcounter>GuiRConsole.waitcounters) {
            System.out.print("");

        }

        


        //  jf.setVisible(true);
//                 JFrame jf2 = new JFrame();
//                    SelectVariablesPanel sv = new SelectVariablesPanel();
//                    sv.setElements(newvec);
//                    jf2.add(sv);
//                    jf2.setSize(500,320);
//                    jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    jf2.setVisible(true);
        ChiSquaretestGraphicsFrame cgf = new ChiSquaretestGraphicsFrame();
    try {
            ArrayList<String> dataRows = Rcommand.getDataRows(dataname);
            cgf.getSelectVariablesPanel1().setElements(dataRows);
    } catch (REngineException ex) {
        Logger.getLogger(WaitShowResult2.class.getName()).log(Level.SEVERE, null, ex);
    } catch (REXPMismatchException ex) {
        Logger.getLogger(WaitShowResult2.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        cgf.setVisible(true);
    }

}