package com.bn.Util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ConvertUtilCommonSocket 
{
	public static byte[] fromStringToBytes(String s)
	{
		byte[] ba=s.getBytes(Charset.forName("UTF-8"));
		return ba;
	}

	public static byte[] fromFloatToBytes(float f)
	{
		int ti=Float.floatToIntBits(f);
		return fromIntToBytes(ti);
	}

	public static byte[] fromIntToBytes(int k)
	{
		byte[] buff = new byte[4];
		buff[0]=(byte)(k&0x000000FF);
		buff[1]=(byte)((k&0x0000FF00)>>>8);
		buff[2]=(byte)((k&0x00FF0000)>>>16);
		buff[3]=(byte)((k&0xFF000000)>>>24);
		
		return buff;
	}

	public static byte[] fromIntToBytesNI(int k)
	{
		byte[] buff = new byte[4];
		buff[3]=(byte)(k&0x000000FF);
		buff[2]=(byte)((k&0x0000FF00)>>>8);
		buff[1]=(byte)((k&0x00FF0000)>>>16);
		buff[0]=(byte)((k&0xFF000000)>>>24);
		
		return buff;
	}

	public static String fromBytesToString(byte[] bufId)
	{
		String s=null;		
		try 
		{
			s=new String(bufId,"UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return s;
	}

	public static int fromBytesToInt(byte[] buff)
	{
		return (buff[3] << 24) 
			+ ((buff[2] << 24) >>> 8) 
			+ ((buff[1] << 24) >>> 16) 
			+ ((buff[0] << 24) >>> 24);
	}

	public static int fromBytesToIntNI(byte[] buff)
	{
		return (buff[0] << 24) 
			+ ((buff[1] << 24) >>> 8) 
			+ ((buff[2] << 24) >>> 16) 
			+ ((buff[3] << 24) >>> 24);
	}
	public static float fromBytesToFloat(byte[] buf)
	{
		int k= fromBytesToInt(buf);		
		return Float.intBitsToFloat(k);
	}
}
