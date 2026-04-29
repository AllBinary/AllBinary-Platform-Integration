package antenna.preprocessor.v2.parser;


/**
 * omry 
 * 06/02/2007
 */
public class Literal
{
	public static final int NUMBER = APPLexerTokenTypes.NUMBER;
	public static final int STRING = APPLexerTokenTypes.STRING;
	public static final int SYMBOL = APPLexerTokenTypes.SYMBOL;
	public static final int BOOLEAN = -1;
	public static final int DEBUG_LEVEL = -2;
	
	private int m_type;
	private String m_value;
	
	public Literal(int type, String value)
	{
		this.m_type = type;
		switch (type)
		{
			case Literal.SYMBOL:
				break;
			case Literal.NUMBER:
				Double.parseDouble(value);
				break;
			case Literal.STRING:
				if (value.toLowerCase().equals("false") || value.toLowerCase().equals("true"))
				{
					this.m_type = Literal.BOOLEAN;
				}
				else
				{
					try
					{
						Double.parseDouble(value);
						this.m_type = Literal.NUMBER;
					}
					catch (NumberFormatException e){}
				}
				break;
			case APPLexerTokenTypes.LITERAL_false:
			case APPLexerTokenTypes.LITERAL_true:
			case Literal.BOOLEAN:
				this.m_type = Literal.BOOLEAN;
				if (!value.toLowerCase().equals("false") && !value.toLowerCase().equals("true")) throw new IllegalArgumentException("Invalid boolean value");
				value = value.toLowerCase();
				break;
			case APPLexerTokenTypes.LITERAL_debug:
			case APPLexerTokenTypes.LITERAL_info:
			case APPLexerTokenTypes.LITERAL_warn:
			case APPLexerTokenTypes.LITERAL_error:
			case APPLexerTokenTypes.LITERAL_fatal:
			case Literal.DEBUG_LEVEL:
				this.m_type = Literal.DEBUG_LEVEL;
				break;
			default:
				throw new IllegalArgumentException("unsupported type " + type + " for value " + value);
		}
		this.m_value = value;
	}
	
	public String toString()
	{
		switch (this.m_type)
		{
			case Literal.STRING:
				return "\""+this.m_value+"\"";
			case Literal.NUMBER:
			case Literal.BOOLEAN:
			case Literal.SYMBOL:
			case Literal.DEBUG_LEVEL:
			default:
				return this.m_value;
			
		}
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Literal)
		{
			Literal other = (Literal) obj;
			return other.m_type == this.m_type && other.m_value.equals(this.m_value);
		}
		return false;
	}

	public boolean isFalse()
	{
		return this.m_type == Literal.BOOLEAN && this.m_value.equals("false");
	}

	public boolean isTrue()
	{
		return this.m_type == Literal.BOOLEAN && this.m_value.equals("true");
	}
	
	public String getValue()
	{
		return this.m_value;
	}
	
	public boolean isDebugLevel()
	{
		return this.m_type == Literal.DEBUG_LEVEL;
	}
	
	public boolean isNumber()
	{
		return this.m_type == Literal.NUMBER;
	}
	
	public boolean isString()
	{
		return this.m_type == Literal.STRING;
	}
	
	public boolean isSymbol()
	{
		return this.m_type == Literal.SYMBOL;
	}
	
	public boolean isBoolean()
	{
		return this.m_type == Literal.BOOLEAN;
	}
	
}
