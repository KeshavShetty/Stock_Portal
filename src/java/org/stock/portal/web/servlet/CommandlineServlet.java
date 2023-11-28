package org.stock.portal.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  @web.servlet name="CommandlineServlet"
 *      description="Commandline Servlet"
 * 
 *  @web.servlet-mapping url-pattern="/misc/commandlineServlet"
 * 
 * @author kshe
 *
 */

public class CommandlineServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		System.out.println("In CommandlineServlet !!!!!!!");
		
		String executeCommand = request.getParameter("command");
		String commandStr = "cmd /c start D:\\projects\\Personnel\\newdatatools\\commandline4j.bat " +executeCommand;
		String responseString = runCommand(commandStr);		
		
		ServletOutputStream stream = response.getOutputStream();
		stream.write("suceess - ".getBytes());
		stream.write(responseString.getBytes());
		stream.flush();
		stream.close();
	}
	
	private String runCommand(String commandStr) {
		final StringBuffer sbf = new StringBuffer();
		try {
			final Process p = Runtime.getRuntime().exec(commandStr);
	
			new Thread(new Runnable() {
			    public void run() {
			     BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			     String line = null; 
	
			     try {
			        while ((line = input.readLine()) != null)
			            sbf.append(line);
			     } catch (IOException e) {
			            e.printStackTrace();
			     }
			    }
			}).start();
	
			p.waitFor();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return sbf.toString();
	}
	
}