package de.xyr.reget;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 * XyrRegetDownloadManager.java
 *
 *
 * Created: Tue Nov 13 00:19:34 2001
 *
 * @author arnaud.denagbe@free.fr
 * @version 1.0
 */
public class XyrRegetDownloadManager
{
    private Vector urls;
    private XyrReget parent;
    private int maxThreads;
    private int nbThreads;
    private Object sync;
    private String dstDir = "C:\\Temp\\ChevaliersDuZodiaque\\";

    public XyrRegetDownloadManager(XyrReget parent, int maxThreads)
    {
	System.getProperties().put("proxySet", "true");
	System.getProperties().put("proxyHost", "proxy.lion-ag.de");
	System.getProperties().put("proxyPort", "8080");

	urls = new Vector();
	this.parent = parent;
	this.maxThreads = maxThreads;
	nbThreads = 0;
	sync = new Object();
	new MainThread().start();
    }

    public void add(Vector urls)
    {
	for(Enumeration e = urls.elements(); e.hasMoreElements(); )
	{
	    String url = (String)e.nextElement();
	    if (!this.urls.contains(url))
	    {
		this.urls.add(url);
	    }
	}
	synchronized(sync)
	{
	    sync.notify();
	}
    }

    public void setMaxThreads(int maxThreads)
    {
	synchronized(sync)
	{
	    this.maxThreads = maxThreads;
	    sync.notifyAll();
	}
    }
    
    public int getMaxThreads()
    {
	return maxThreads;
    }
    
    public void setDstDir(String dstDir)
    {
	this.dstDir = dstDir;
    }

    private class MainThread extends Thread
    {
	public void run()
	{
	    while(true)
	    {
		synchronized(sync)
		{
		    try
		    {
			for(; !urls.isEmpty() && nbThreads < maxThreads; nbThreads++)
			{
			    String url = (String)urls.firstElement();
			    urls.remove(url);
			    new DownloadThread(url, dstDir).start();
			}
			sync.wait();
		    }
		    catch(Exception e)
		    {
		    }
		}
	    }
	}
    }

    private class DownloadThread extends Thread
    {
	private final static String EXIT_SUCCESS = "OK";
	private final static int BUFFER_SIZE = 10000;
	private URL url;
	private String dest;
	private String status;
	private byte[] buffer;

	public DownloadThread(String url, String dest) throws Exception
	{
	    this.url = new URL(url);	    
	    int index = url.lastIndexOf('/');	    
	    if (index > 0 &&  index < url.length() - 1)
	    {
		String filename = url.substring(index + 1);
		this.dest = dest.endsWith(File.separator) ?
		    dest + filename :
		    dest + File.separatorChar + filename;
	    }
	    else
	    {
		throw new Exception("Incorrect url");
	    }
	    
	    buffer = new byte[BUFFER_SIZE];
	}

	public void run()
	{
	    //System.out.println("Downloading file: " + url + " --> " + dest);

	    try
	    {		
		URLConnection urlConn = url.openConnection();
		long remoteSize = new Integer(urlConn.getContentLength()).longValue();
		RandomAccessFile out = new RandomAccessFile(dest, "rw");
		long localSize = out.length();
		if (localSize < remoteSize)
		{
		    out.seek(localSize);
		}
		
		while(localSize < remoteSize)
		{
		    System.out.println("Downloading file: " + url + " --> " + dest);
		    System.out.println("******************** Rentre **********************");
		    try
		    {
			InputStream in = urlConn.getInputStream();
			in.skip(localSize);
			BufferedInputStream bin = new BufferedInputStream(in);		
			for(int nbRead; (nbRead = bin.read(buffer, 0, buffer.length)) != -1 ; )
			{
			    out.write(buffer, 0, nbRead);
			}
			in.close();
			localSize = out.length();
		    }
		    catch(Exception e)
		    {		    
		    }
		}

		out.close();
	    }
	    catch(Exception e)
	    {
		status = e.toString() + ": " + e.getMessage();
		System.err.println(status);
	    }

	    synchronized(sync)
	    {
		nbThreads--;
		sync.notifyAll();
	    }
	    
	    status = EXIT_SUCCESS;
	}

	public String getExitStatus()
	{
	    return status;
	}
    }
}// XyrRegetDownloadManager
