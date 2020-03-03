package com.bn.Util;

import  java.io.InputStream;
import  java.io.OutputStream;
import  java.net.Socket;
import  java.util.Arrays;
import  java.util.List;
public class NetInfoUtil {
    List<String[]> ls;
    public static Socket sc;
    public  static  InputStream in;
    public static OutputStream out;
    public static byte[] datapic;
    public static String message="";

    //建立连接
    public static void connect()throws Exception{
        sc = new Socket(Constant.host,15570);
        in = sc.getInputStream();
        out =sc.getOutputStream();
    }
    //关闭连接
    public static void disConnect(){
        if(out!= null)
            try {
                out.flush();
                out.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        if(in !=null)
            try {
                in.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        if(sc !=null)
            try {
                sc.close();
            } catch (Exception e){
                e.printStackTrace();
            }
    }
    public static String[]  getUserPass(String inputtel) {
        try{
            connect();
            IOUtilCommonSocket.writeString(Constant.getUserPassword+inputtel,out);
            int length=IOUtilCommonSocket.readIntNI(in);
            byte[] data=IOUtilCommonSocket.readBytes(in,length);
            byte[] tb={data[0],data[1],data[2],data[3]};
            int type=ConvertUtilCommonSocket.fromBytesToInt(tb);
            byte[] realData=Arrays.copyOfRange(data, 4,data.length);
            switch(type) {
                case 0://字符串
                    message=ConvertUtilCommonSocket.fromBytesToString(realData);
                    break;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToArray(message);
    }
    public static void insertUser(String s) {//插入用户
        try{
            connect();
            System.out.println(message+"111111111111111111111111111");
            IOUtilCommonSocket.writeString(Constant.insertUser+s,out);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static byte[] getPic(String picName){
        try{
            connect();
            IOUtilCommonSocket.writeString(Constant.getPic+picName, out);
            int length=IOUtilCommonSocket.readIntNI(in);
            byte[] data=IOUtilCommonSocket.readBytes(in,length);
            byte[] tb=Arrays.copyOfRange(data, 0,4);
            int type=ConvertUtilCommonSocket.fromBytesToInt(tb);
            byte[] realData=Arrays.copyOfRange(data, 4,data.length);
            switch(type)
            {
                case 1://字节数组
                    datapic=realData;
                    break;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeCtx();
            disConnect();
        }
        return datapic;
    }
    public static void closeCtx() {
        try{
            IOUtilCommonSocket.writeString("<#closeCtx#>", out);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String GetPostListXx() {
        try{
            connect();
            IOUtilCommonSocket.writeString(Constant.GetPostListXx,out);
            int length=IOUtilCommonSocket.readIntNI(in);
            byte[] data=IOUtilCommonSocket.readBytes(in,length);
            byte[] tb={data[0],data[1],data[2],data[3]};
            int type=ConvertUtilCommonSocket.fromBytesToInt(tb);
            byte[] realData=Arrays.copyOfRange(data, 4,data.length);
            switch(type) {
                case 0://字符串
                    message=ConvertUtilCommonSocket.fromBytesToString(realData);
                    break;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return message;
    }
    public static String getUserXx(String mes) {
        try{
            connect();
            IOUtilCommonSocket.writeString(Constant.getUserXx+mes,out);
            int length=IOUtilCommonSocket.readIntNI(in);
            byte[] data=IOUtilCommonSocket.readBytes(in,length);
            byte[] tb={data[0],data[1],data[2],data[3]};
            int type=ConvertUtilCommonSocket.fromBytesToInt(tb);
            byte[] realData=Arrays.copyOfRange(data, 4,data.length);
            switch(type) {
                case 0://字符串
                    message=ConvertUtilCommonSocket.fromBytesToString(realData);
                    break;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return message;
    }
}
