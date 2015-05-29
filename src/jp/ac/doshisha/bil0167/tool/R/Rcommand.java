/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.R;

import java.util.ArrayList;
import jp.ac.doshisha.bil0167.tool.main.GuiRConsole;
import org.rosuda.REngine.JRI.JRIEngine;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPList;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;

/**
 *
 * @author katsuya
 */
public class Rcommand {

    private static boolean firstexec = true;
    private static boolean firstfisherexec =true;

    public static void excuteChiSquaretest(String dataname,Boolean showresult) throws REngineException, REXPMismatchException {

        ArrayList commandlist = new ArrayList();
        if (firstexec) {
            commandlist.add("chisq.test2<-function(x)\n" +
"{\n" +
"	tal<-apply(x,2,sum)\n" +
"	nr<-nrow(x)\n" +
"	nc<-ncol(x)\n" +
"	te<-matrix(0,nr,1)\n" +
"	y<-cbind(x,chisq.p=te)\n" +
"	for(i in 1:nr){\n" +
"		temp<-rbind(x[i,],tal-x[i,])\n" +
"		y[i,nc+1]<-chisq.test(temp)$p.value\n" +
"		yy<-y[sort.list(y[,nc+1]),]\n" +
"	}\n" +
"	list(kekka=yy)\n" +
"}");
        }
        commandlist.add(dataname+".chisqre<-chisq.test2(" + dataname + ")");
        commandlist.add(dataname+".chisqres<-" + dataname + ".chisqre");
        commandlist.add("ncol<-ncol("+dataname+".chisqre$kekka)");
         commandlist.add(dataname+".chisqre<-"+dataname+".chisqre$kekka[,-ncol]");
         if(showresult){
             commandlist.add(dataname+".chisqres");
         }
//        commandlist.add("L<-ncol(temp)\n"
//                + "X<-temp\n"
//                + "N<-ncol(X)\n"
//                + "X.p<-round(t(100*t(X)/apply(t(X),1,sum)),2)\n"
//                + "Re<-zscor(X)\n"
//                + "res<-data.frame(X.p,Re)\n"
//                + "temp.f<-res[order(res[,L+1],decreasing=TRUE),]");
        GuiRConsole.setCommand(commandlist);
        firstexec = false;
//                     JRIEngine engine = GuiRConsole.getRengine();
//             engine.getRni();
             String re =dataname+".chisqre";
        RFrame.setDatanames(re);
//            REXP rdataframe = engine.parseAndEval(re);
//            REXPList attrList = rdataframe._attr(); // ないときは，null
//            ArrayList<String> array = new ArrayList<String>();
//            if (attrList != null) {
//                RList rList = attrList.asList();
//                REXP get = (REXP) rList.at(2);
//                String[] asStrings = get.asStrings();
//                for (int i = 0; i < asStrings.length; i++) {
//                    array.add((i + 1) + " " + asStrings[i]);
//                }
//            }
//           
//          //  System.out.println(array);
//           RFrame.putDataRowNames(dataname+".chisqre", array);
            
    }

      public static void excuteChiSquaretest2(String dataname) {

        ArrayList commandlist = new ArrayList();
        
            commandlist.add("zscor<-function(data){\n"
                    + "x<-data[,1];y<-data[,2];			\n"
                    + "xsum<-sum(x); ysum<-sum(y)		\n"
                    + "nr<-length(x)				\n"
                    + "res<-matrix(0,nr,2)				\n"
                    + "colnames(res)<-c(\"z-scor\",\"p-valu\")		\n"
                    + "xp<-x/xsum; yp<-y/ysum				\n"
                    + " for(i in 1:nr){				\n"
                    + "	P<-(x[i]+y[i])/(xsum+ysum)		\n"
                    + "	siguma<-sqrt(P*(1-P)*(1/xsum+1/ysum))　\n"
                    + "	res[i,1]<-abs(xp[i]-yp[i])/siguma	\n"
                    + "	res[i,2]<-2*(1-pnorm(res[i,1]))		\n"
                    + "}\n"
                    + "scor<-cbind(data,round(res,4))		\n"
                    + "scor[sort.list(scor[,3],decreasing =TRUE ),]　　	\n"
                    + "}");
        

        commandlist.add("zscor(" + dataname + ")");
        commandlist.add("L<-ncol(temp)\n"
                + "X<-temp\n"
                + "N<-ncol(X)\n"
                + "X.p<-round(t(100*t(X)/apply(t(X),1,sum)),2)\n"
                + "Re<-zscor(X)\n"
                + "res<-data.frame(X.p,Re)\n"
                + "temp.f<-res[order(res[,L+1],decreasing=TRUE),]");
        GuiRConsole.setCommand2(commandlist);
        firstexec = false;

    }
      
       public static void excuteFishertest(String dataname,Boolean showresult) {

        ArrayList commandlist = new ArrayList();
        if (firstfisherexec) {
            commandlist.add("fisher2<-function(x)\n" +
"{\n" +
"	tal<-apply(x,2,sum)\n" +
"	nr<-nrow(x)\n" +
"	nc<-ncol(x)\n" +
"	te<-matrix(0,nr,1)\n" +
"	y<-cbind(x,fisher.p=te)\n" +
"	for(i in 1:nr){\n" +
"		temp<-rbind(x[i,],tal-x[i,])\n" +
"		y[i,nc+1]<-fisher.test(temp,simulate.p.value=TRUE)$p.value\n" +
"		yy<-y[sort.list(y[,nc+1]),]\n" +
"	}\n" +
"	list(kekka=yy)\n" +
"}");
        }

//        commandlist.add(dataname+".fisher2.result<-fisher2(" + dataname + ")");
//              commandlist.add(dataname+".fisher2.result");
//              GuiRConsole.setChisqdataname(dataname+".fisher2.result");
              
              
                      commandlist.add(dataname+".fisher2re<-fisher2(" + dataname + ")");
                      
                      commandlist.add(dataname+".fisher2res<-(" + dataname + ".fisher2re)");
        commandlist.add("ncol<-ncol("+dataname+".fisher2re$kekka)");
         commandlist.add(dataname+".fisher2re<-"+dataname+".fisher2re$kekka[,-ncol]");
         if(showresult){
             commandlist.add(dataname+".fisher2res");
         }
//        commandlist.add("L<-ncol(temp)\n"
//                + "X<-temp\n"
//                + "N<-ncol(X)\n"
//                + "X.p<-round(t(100*t(X)/apply(t(X),1,sum)),2)\n"
//                + "Re<-zscor(X)\n"
//                + "res<-data.frame(X.p,Re)\n"
//                + "temp.f<-res[order(res[,L+1],decreasing=TRUE),]");
        GuiRConsole.setCommand(commandlist);
        firstfisherexec = false;
           String re =dataname+".fisher2re";
        RFrame.setDatanames(re);
     //   RFrame.setDatanames(dataname+".fisher2.result");

    }

    public static void excutePCM(String dataname, boolean center, boolean scale, boolean summary, boolean bar) {
        String centerst;
        String scalest;
        if (center) {
            centerst = "TRUE";
        } else {
            centerst = "FALSE";
        }
        if (scale) {
            scalest = "TRUE";
        } else {
            scalest = "FALSE";
        }

        String command1 = dataname + ".pca<-prcomp(" + dataname + ".subset,center=" + centerst + ",scale.=" + scalest + ")";

        GuiRConsole.setCommand(command1);
        if (summary) {
            String command2 = "summary(" + dataname + ".pca)";
            GuiRConsole.setCommand(command2);
        }
        if (bar) {
            GuiRConsole.setCommand("JavaGD()");
            String command3 = "pro<-100*summary(" + dataname + ".pca)$importance[-1,]\n"
                    + "pro[2,]<-pro[2,]-pro[1,]\n"
                    + " barplot(pro,col = c(\"lightblue\",\"cornsilk\"), legend.text =c(\"Proportion of Variance\",\"Cumulative Proportion\"), args.legend =list(x = \"top\"))\n"
                    + " grid()";
            GuiRConsole.setCommand(command3);
        }

    }

    public static void excutePCM2(boolean ax, String x, String y, String dataname) {
        String command;
        if (ax) {

            GuiRConsole.setCommand("JavaGD()");
            command = " a<-" + x + "\n"
                    + "b<-" + y + "\n"
                    + "pro.x<-round(100*" + dataname + ".pca$sdev[a]^2/sum(" + dataname + ".pca$sdev^2),2)\n"
                    + " pro.y<-round(100*" + dataname + ".pca$sdev[b]^2/sum(" + dataname + ".pca$sdev^2),2)\n"
                    + " lab.x<- paste(\"PC\", a,\"(\", pro.x, \"%)\", sep = \"\")\n"
                    + " lab.y<- paste(\"PC\", b,\"(\", pro.y, \"%)\", sep = \"\")";
            GuiRConsole.setCommand(command);
            String command2 = "x1<-" + dataname + ".pca$rotation[,a]\n"
                    + "y1<-" + dataname + ".pca$rotation[,b]\n"
                    + " plot(x1,y1,type=\"n\",xlab=lab.x,ylab=lab.y)\n"
                    + " text(x1,y1,lab=rownames(" + dataname + ".pca$rotation))";
            GuiRConsole.setCommand(command2);
        } else {
            command = " a<-1\n"
                    + "b<-2\n"
                    + "pro.x<-round(100*" + dataname + ".pca$sdev[a]^2/sum(" + dataname + ".pca$sdev^2),2)\n"
                    + " pro.y<-round(100*" + dataname + ".pca$sdev[b]^2/sum(" + dataname + ".pca$sdev^2),2)\n"
                    + " lab.x<- paste(\"PC\", a,\"(\", pro.x, \"%)\", sep = \"\")\n"
                    + " lab.y<- paste(\"PC\", b,\"(\", pro.y, \"%)\", sep = \"\")";
            GuiRConsole.setCommand(command);
        }

    }

    public static void viewBiplot(String dataname) {
        GuiRConsole.setCommand("JavaGD()");
        GuiRConsole.setCommand("biplot(" + dataname + ".pca,choices=c(a,b),xlab=lab.x,ylab=lab.y)");
    }

    public static void showPCMplot1(String dataname) {
        GuiRConsole.setCommand("JavaGD()");
        String command = "x1<-" + dataname + ".pca$x[,a]\n"
                + "y1<-" + dataname + ".pca$x[,b]\n"
                + "plot(x1,y1,type=\"n\",xlab=lab.x,ylab=lab.y)\n"
                + "lab.t<-rownames(" + dataname + ".pca$x)\n"
                + "if(length(lab.t)==0)lab.t<-1:nrow(" + dataname + ".pca$x)\n"
                + " text(x1,y1,lab=lab.t)";
        GuiRConsole.setCommand(command);

    }

    public static void showPCMplot2(String dataname) {
        GuiRConsole.setCommand("JavaGD()");
        String command = "x1<-" + dataname + ".pca$rotation[,a]\n"
                + "y1<-" + dataname + ".pca$rotation[,b]\n"
                + "plot(x1,y1,type=\"n\",xlab=lab.x,ylab=lab.y)\n"
                + "lab.t<-rownames(" + dataname + ".pca$rotation)\n"
                + "if(length(lab.t)==0)lab.t<-1:nrow(" + dataname + ".pca$rotation)\n"
                + " text(x1,y1,lab=lab.t)";
        GuiRConsole.setCommand(command);

    }

    public static void setSubsetF(String dataname, ArrayList<String> subsetlist, String columnnumber) {
        StringBuilder subset = new StringBuilder();
        for (int i = 0; i < subsetlist.size(); i++) {
            if (i == subsetlist.size() - 1) {
                subset.append(subsetlist.get(i));
            } else {
                subset.append(subsetlist.get(i) + ",");
            }
        }
        StringBuilder column = new StringBuilder("1:");
        column.append(columnnumber);

        String command1 = dataname + ".subset<-data.frame(" + dataname + ".f[c(" + subset.toString() + ")," + column.toString() + "])";
        GuiRConsole.setCommand(command1);

    }

    public static void setSubset(String dataname, ArrayList<String> subsetlist) throws REngineException, REXPMismatchException {
        JRIEngine engine = GuiRConsole.getRengine();
       REXP columnnumber =  engine.parseAndEval("ncol("+dataname+")");
       int ncolumn = columnnumber.asInteger();
        StringBuilder subset = new StringBuilder();
        for (int i = 0; i < subsetlist.size(); i++) {
            if (i == subsetlist.size() - 1) {
                subset.append(subsetlist.get(i));
            } else {
                subset.append(subsetlist.get(i) + ",");
            }
        }
//        StringBuilder column = new StringBuilder("1:");
//        column.append(2);
GuiRConsole.setCommand("nc<-ncol("+dataname+")");
        String command1 = dataname + ".subset<-t(" + dataname + "[c(" + subset.toString() + "),1:nc])";
        GuiRConsole.setCommand(command1);

    }
    
    
    public static ArrayList<String> getDataRows(String dataname) throws REngineException, REXPMismatchException{
        JRIEngine engine = GuiRConsole.getRengine();
             engine.getRni();
            REXP rdataframe = engine.parseAndEval(dataname);
            REXPList attrList = rdataframe._attr(); // ないときは，null
            ArrayList<String> array = new ArrayList<String>();
            if (attrList != null) {
                RList rList = attrList.asList();
                REXP get = (REXP) rList.at(1);
                String[] asStrings = get.asStrings();
                System.out.println(asStrings);
                for (int i = 0; i < asStrings.length; i++) {
                    array.add((i + 1) + " " + asStrings[i]);
                }
            }
            System.out.println(array);
            return array;
    }

}
