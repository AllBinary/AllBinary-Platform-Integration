/* -----------------------------------------------------------------------------
 * Antenna - An Ant-to-end solution for wireless Java 
 *
 * Copyright (c) 2002-2004 Joerg Pleumann <joerg@pleumann.de>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * -----------------------------------------------------------------------------
 */
package de.pleumann.antenna;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.xml.sax.SAXException;

import antenna.preprocessor.IPreprocessor;
import antenna.preprocessor.PreprocessorException;
import de.pleumann.antenna.device.DeviceProps;
import de.pleumann.antenna.device.Devices;
import de.pleumann.antenna.misc.Conditional;
import de.pleumann.antenna.misc.Strings;
import de.pleumann.antenna.misc.Utility;

// implementation of task depends on backend version.
public class WtkPreprocess extends MatchingTask {
	
    private Conditional condition;
	   	

    private File sourceDir;

    private File targetDir;

    private String newext;

    private String encoding;

    private int mode = IPreprocessor.MODE_NORMAL | IPreprocessor.MODE_INDENT;

    private Utility utility;
    
    /**
     * A comma separated list of symbols 
     */
    private String m_symbols;
    
    /**
     * Current device name, may be null.
     */
	private String m_device;
	
	private String m_saveSymbols;

	/**
	 * Preprocessing backend version number
	 */
	private String m_backendVersion = "3";
	
	private boolean m_printSymbols = false;


	private Vector m_symbolsFile = new Vector();

	/**
	 * Preprocessor debub level
	 */ 
	private String m_debugLevel;
	
    public void init() {
        super.init();
        this.utility = Utility.getInstance(getProject(), this);

        this.condition = new Conditional(getProject());
        this.sourceDir = getProject().resolveFile(".");
        createInclude().setName("**/*.java");
    }

    public void setSrcdir(File value) {
        sourceDir = value;
    }

    public void setDestdir(File value) {
        this.targetDir = value;
    }

    public void setSymbols(String symbols) 
    {
    	this.m_symbols = symbols;
    }
    
	public void setVerbose(boolean verbose) {
        if (verbose) {
            this.mode = this.mode | IPreprocessor.MODE_VERBOSE;
        }
        else {
            this.mode = this.mode & ~IPreprocessor.MODE_VERBOSE;
        }
    }

    public void setBackup(boolean backup) {
        if (backup) {
            this.mode = this.mode | IPreprocessor.MODE_BACKUP;
        }
        else {
            this.mode = this.mode & ~IPreprocessor.MODE_BACKUP;
        }
    }

    public void setIndent(boolean indent) {
        if (indent) {
            this.mode = this.mode | IPreprocessor.MODE_INDENT;
        }
        else {
            this.mode = this.mode & ~IPreprocessor.MODE_INDENT;
        }
    }

    public void setTest(boolean test) {
        if (test) {
            this.mode = this.mode | IPreprocessor.MODE_TEST;
        }
        else {
            this.mode = this.mode & ~IPreprocessor.MODE_TEST;
        }
    }

    public void setFilter(boolean filter) {
        if (filter) {
            this.mode = this.mode | IPreprocessor.MODE_FILTER;
        }
        else {
            this.mode = this.mode & ~IPreprocessor.MODE_FILTER;
        }
    }

    public void setNewext(String newext) {
        this.newext = newext;
    }

    public void setEncoding(String encoding) {
          this.encoding = encoding;
    }

    public void setIf(String s) {
        this.condition.setIf(s);
    }

    public void setUnless(String s) {
        this.condition.setUnless(s);
    }

    public boolean isActive() {
        return this.condition.isActive();
    }

    public void execute() throws BuildException {
        if (!isActive()) return;

        if (this.targetDir == null) {
            throw new BuildException("Need a target directory");
        }
        StringTokenizer tok = new StringTokenizer(this.sourceDir.toString(), "" + File.pathSeparatorChar);
        while(tok.hasMoreElements()){
        	String dir = tok.nextToken();
    		try {
    			IPreprocessor pp;
    			if ("1".equals(this.m_backendVersion))
    			{
					pp = new antenna.preprocessor.v1.Preprocessor(this.utility, encoding);
					pp.addSymbols(this.m_symbols);
					if (this.m_printSymbols)
						pp.printSymbols();
    			}
    			else
   				if ("2".equals(this.m_backendVersion))
    			{
    				pp = new antenna.preprocessor.v2.PreprocessorBridge(this.utility);
    			}
   				else
   				if ("3".equals(this.m_backendVersion))
    			{
    				pp = new antenna.preprocessor.v3.PreprocessorBridge(this.utility);
    			}
    			else
    			{
    				throw new BuildException("Unsupported backend version " + this.m_backendVersion);
    			}
    			
    			
    			// for any backend except version 1:
    			if (!this.m_backendVersion.equals("1"))
    			{
    				try
    				{
    					pp.setDebugLevel(this.m_debugLevel);
    				}
    				catch (Exception e)
    				{
    					log(e.getMessage(),Project.MSG_WARN);
    				}
    				String deviceDefines = this.getDeviceDefines();
    				String symbols = this.addSymbols(this.m_symbols, deviceDefines);
    				pp.addSymbols(symbols);
    				
    				for(int i=0;i<this.m_symbolsFile.size();i++)
    				{
    					Symbols_File f = (Symbols_File) this.m_symbolsFile.get(i);
    					if (f.name != null)
    					{
    						File file = new File(f.name);
    						if (!file.exists())
    						{
    							log("Symbols file not found : " + file.getAbsolutePath(),Project.MSG_INFO);
    						}
    						else
    						{
    							try
    							{
    								pp.addSymbols(file);
    							}
    							catch (PreprocessorException e)
    							{
    								log("Error preprocessing symbols" + file.getAbsolutePath(),Project.MSG_ERR);
    								e.printStackTrace();
    							}
    							catch (IOException e)
    							{
    								log("IOException adding " + file.getAbsolutePath(),Project.MSG_ERR);
    								e.printStackTrace();
    							}
    						}
    					}
    					else
    					if (f.list != null)
    					{
    						StringTokenizer t = new StringTokenizer(f.list, " ,");
    						while(t.hasMoreElements())
    						{
    							File ff = new File(t.nextToken());
    							if (!ff.exists())
    							{
    								log("Symbols file not found : " +ff.getAbsolutePath(),Project.MSG_WARN);
    								continue;
    							}
    							
    							try
    							{
    								pp.addSymbols(ff);
    							}
    							catch (Exception e)
    							{
    								log("Error adding symbols from " + ff.getAbsolutePath(),Project.MSG_WARN);
    							}
    						}
    					}
    				}
    				
    				if (this.m_printSymbols)
    					pp.printSymbols();
    				
    				if (this.m_saveSymbols != null)
    				{
    					try
    					{
    						pp.outputDefinesToFile(new File(this.m_saveSymbols), encoding);
    					}
    					catch (IOException e)
    					{
    						log("Error saving defines to file " + this.m_saveSymbols,Project.MSG_WARN);
    					}
    				}
    				
    			}

				File file = new File(dir);
				this.preprocess(pp, file, mode, newext, encoding, utility);
    		}
    		catch (Exception ex) {
    			ex.printStackTrace();
    			throw new BuildException("Preprocessing failed: " + ex.getMessage(), ex);
        	}
        }
    }
	
	


	// this method allow using a different filter to handle the lines. 
	public void preprocess(IPreprocessor pp, File sourceDir, int mode, String newext,
			String encoding, Utility utility) throws PreprocessorException, IOException
	{

		pp.setMode(mode);
		String[] files = getDirectoryScanner(sourceDir).getIncludedFiles();
		log("Preprocessing " + files.length + " file(s) at " + sourceDir);
		
		String filename = ""; // For exception handling

		try
		{
			for (int i = 0; i < files.length; i++)
			{
				filename = files[i];

				String sourceFile = "" + sourceDir + File.separatorChar + filename;

				Strings lines = this.loadFile(encoding, new File(sourceFile));

				pp.setFile(new File(sourceFile));


				
				boolean modified = pp.preprocess(lines, encoding);
				// if preprocessing modifies the file, or
				// we are putting the output in a different directory
				// or we are changing the file extension
				// then we have to write a new file
				if (modified || !sourceDir.equals(this.targetDir) || (newext != null))
				{
					try
					{
						if ((mode & IPreprocessor.MODE_VERBOSE) != 0)
						{
							System.out.println(filename + " ... modified");
						}

						if ((mode & IPreprocessor.MODE_TEST) == 0)
						{
							String targetFile;

							if (newext != null)
							{
								int dot = filename.indexOf('.');

								if (dot != -1)
								{
									filename = filename.substring(0, dot) + newext;
								}
								else
								{
									filename = filename + newext;
								}
							}

							targetFile = "" + this.targetDir + File.separatorChar + filename;

							File file = new File(targetFile + "~");
							file.delete();
							if (!new File(targetFile).renameTo(file) && (this.targetDir == null))
							{
								throw new java.io.IOException();
							}

							new File(targetFile).getParentFile().mkdirs();

							if (encoding != null && encoding.length() > 0)
							{
								lines.saveToFile(targetFile, encoding);
							}
							else
							{
								lines.saveToFile(targetFile);
							}

							if ((mode & IPreprocessor.MODE_BACKUP) == 0)
							{
								file.delete(); // ??????
							}
						}
					}
					catch (java.io.UnsupportedEncodingException uee)
					{
						throw new PreprocessorException("Unknown encoding \"" + encoding, new File(files[i]));
					}
					catch (java.io.IOException e)
					{
						throw new PreprocessorException("File write error", new File(files[i]));
					}
				}
				else
				{
					if ((mode & IPreprocessor.MODE_VERBOSE) != 0)
					{
						System.out.println(filename + " ... not modified");
					}
				}
			}
		}
		catch (IOException e)
		{
			if ((mode & IPreprocessor.MODE_VERBOSE) == 0)
			{
				System.out.println(filename + " ... not modified, " + e.getMessage());
			}

			throw e;
		}
		catch (PreprocessorException error)
		{
			if ((mode & IPreprocessor.MODE_VERBOSE) == 0)
			{
				System.out.println(filename + " ... not modified, " + error.getMessage());
			}

			throw error;
		}
	}

	private Strings loadFile(String encoding, File sourceFile) throws PreprocessorException
	{
		Strings lines = new Strings();
		try
		{
			if (encoding != null && encoding.length() > 0)
				lines.loadFromFile(sourceFile, encoding);
			else
				lines.loadFromFile(sourceFile);
		}
		catch (java.io.UnsupportedEncodingException e)
		{
			throw new PreprocessorException("Unknown encoding \"" + encoding + "\"", sourceFile, e);
		}
		catch (java.io.IOException e)
		{
			throw new PreprocessorException("File read error", sourceFile, e);
		}
		return lines;
	}
	
	public void setDevice(String device)
	{
		this.m_device = device;
	}
	
	private String getDeviceDefines()
	{
		DeviceProps deviceProps = Devices.getDevice(this.m_device);
		String deviceDefines = "";
		if (deviceProps == null)
		{
			getProject().log("Warning: unknown device \"" + this.m_device + "\"");
		}
		else
		{
			deviceDefines = deviceProps.getDefinesString();
		}
		return deviceDefines;
	}

	private String addSymbols(String current, String deviceDefines)
	{
		String s = "";
		s = this.append(this.m_symbols, s);
		s = this.append(s, deviceDefines);
		return s;
	}

	private String append(String cur, String s)
	{
		if (cur == null || cur.length() == 0)
			return s;
		String ss = s != null && s.length() > 0 ? cur + ","+ s : cur;
		return ss;
	}

	public void setVersion(String backendVersion)
	{
		this.m_backendVersion = backendVersion;
	}

	/**
	 * If true, active symbols will be printed before preprocessing.
	 */
	public void setPrintSymbols(boolean printSymbols)
	{
		this.m_printSymbols = printSymbols;
	}
	
    public static class Symbols_File
    {
    	private String name;
    	private String list;

		public void setName(String name)
    	{
			this.name = name;
    	}
		
		public void setList(String list)
    	{
			this.list = list;
    	}
		
    }
    
	public Symbols_File createSymbols_File() 
	{
		Symbols_File a = new Symbols_File();
		this.m_symbolsFile.addElement(a);
		return a;
	}
	
	public void setSaveSymbols(String file)
	{
		this.m_saveSymbols = file;
	}
	
	public void setDebugLevel(String debug)
	{
		this.m_debugLevel = debug;
	}
	
	public void setDeviceDBPath(String path) throws BuildException
	{
		try
		{
			Devices.setDatabaseDir(path);
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}
}
