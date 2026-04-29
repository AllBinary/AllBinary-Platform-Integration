package antenna.preprocessor.v2.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import antenna.preprocessor.v2.Preprocessor.ILineFilter;
import antlr.ANTLRException;
import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;

/**
 * omry 06/02/2007
 */
public class Defines
{
	
	private Hashtable m_defines;
	private final ILineFilter m_lineFilter;

	public Defines()
	{
		this.m_lineFilter = null;
		this.clear();
	}
	
	public Defines(ILineFilter lineFilter) 
	{
		this.m_lineFilter = lineFilter;
		this.clear();
	}
	
	public Defines(String defines) throws ANTLRException
	{
		this.m_lineFilter = null;
		this.clear();
		this.addDefines(defines);
	}

	public Defines(String defines, ILineFilter lineFilter) throws ANTLRException
	{
		this.m_lineFilter = lineFilter;
		this.clear();
		this.addDefines(defines);
	}

	public void addDefines(String defines) throws RecognitionException, TokenStreamException
	{
		if (this.m_lineFilter != null)
		{
			defines = this.m_lineFilter.filter(defines);
		}
		
		APPLexer lexer = new APPLexer(new StringReader(defines));
		APPParser parser = new APPParser(lexer);
		parser.defines();
		CommonAST ast = (CommonAST) parser.getAST();
//		new DumpASTVisitor().visit(ast);
		for (AST node = ast; node != null && node.getType() != APPLexerTokenTypes.EOF; node = node.getNextSibling())
		{
			this.define(node);
		}
	}
	
	public void loadDefines(File file) throws IOException, RecognitionException, TokenStreamException
	{
		FileInputStream in = new FileInputStream(file);
		try
		{
			this.loadDefines(in);
		}
		finally
		{
			in.close();
		}
	}
	
	public void loadDefines(InputStream in) throws RecognitionException, TokenStreamException, IOException
	{
		String line = null;
		Hashtable backup = (Hashtable) this.m_defines.clone();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		try
		{
			while ((line = reader.readLine()) != null)
			{
				if (this.m_lineFilter != null)
				{
					line = this.m_lineFilter.filter(line);
				}
				if (line.length() > 0)
				{
					APPLexer lexer = new APPLexer(new StringReader(line));
					APPParser parser = new APPParser(lexer);
					parser.define();
					AST ast = parser.getAST();
//					new DumpASTVisitor().visit(ast);
					this.define(ast);
				}
			}
		}
		catch (RecognitionException e)
		{
			this.m_defines = backup;
			throw e;
		}
		catch (TokenStreamException e)
		{
			this.m_defines = backup;
			throw e;
		}
		catch (IOException e)
		{
			this.m_defines = backup;
			throw e;
		}
	}

	public void define(String key) throws RecognitionException, TokenStreamException
	{
		this.define(key, null);
	}
	
	public void define(String key, String value) throws RecognitionException, TokenStreamException
	{
		String def;
		if (value == null)
		{
			def = key;
		}
		else
		{
			def = key + "=" + value;
		}
		
		APPLexer lexer = new APPLexer(new StringReader(def));
		APPParser parser = new APPParser(lexer);
		parser.define();
		this.define((CommonAST) parser.getAST());
	}

	public void define(AST node) throws RecognitionException
	{
		String key = node.getText();
		
		int action = -1;
		
		if (node.getFirstChild() != null && 
			(
				node.getFirstChild().getType() == APPLexerTokenTypes.LITERAL_add_if_new || 
				node.getFirstChild().getType() == APPLexerTokenTypes.LITERAL_unset)
			)
		{
			action = node.getFirstChild().getType();
			node = node.getFirstChild();
			node = node.getNextSibling();
			node = node.getNextSibling();
		}
		else
		{
			node = node.getFirstChild();
		}
		
		
		Literal literal;
		if (node != null)
		{
			literal = new Literal(node.getType(), node.getText());
		}
		else
		{
			literal = new Literal(Literal.BOOLEAN, "true");
		}
		Define define = new Define(key, literal);
		
		switch(action)
		{
			case -1:
				this.m_defines.put(key, define);
				break;
			case APPLexerTokenTypes.LITERAL_add_if_new:
				if (!this.m_defines.containsKey(key)) this.m_defines.put(key, define);
				break;				
			case APPLexerTokenTypes.LITERAL_unset:
				this.m_defines.remove(key);
				break;	
			default:
				throw new RecognitionException("Unsupported action " + action);
		}
	}

	public boolean isDefined(String text)
	{
		return this.m_defines.containsKey(text);
	}

	public Define getDefine(String text)
	{
		return (Define) this.m_defines.get(text);
	}
	
	public boolean undefine(String key)
	{
		return this.m_defines.remove(key) != null;
	}
	
	public String toString()
	{
		Enumeration keys = this.m_defines.keys();
		Vector v = new Vector(this.m_defines.size());
		while(keys.hasMoreElements())
		{
			String key = (String) keys.nextElement();
			v.add(key);
		}
		
		Collections.sort(v, new Comparator()
		{
		
			public int compare(Object arg0, Object arg1)
			{
				String s1 = (String) arg0;
				String s2 = (String) arg1;
				if (s1 != null) s1 = s1.toLowerCase();
				if (s2 != null) s2 = s2.toLowerCase();
				if (s1 == null && s2 == null) return 0;
				if (s1 == null) return -1;
				if (s2 == null) return +1;
				return s1.compareTo(s2);
			}
		});
		
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < v.size(); i++)
		{
			String key = (String) v.get(i);
			Define def = (Define) this.m_defines.get(key);
			b.append(def);
			b.append("\n");
		}
		return b.toString();
	}
	
	public String[] keys()
	{
		String keys[] = new String[this.m_defines.size()];
		this.m_defines.keySet().toArray(keys);
		return keys;
	}
	
	public Define[] values()
	{
		Define defines[] = new Define[this.m_defines.size()];
		this.m_defines.values().toArray(defines);
		return defines;
	}
	
	public Defines copy()
	{
		Defines copy = new Defines();
		copy.m_defines = (Hashtable) this.m_defines.clone();
		return copy;
	}
	

	public void clear()
	{
		this.m_defines = new Hashtable();
	}
}
