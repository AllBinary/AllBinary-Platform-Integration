package antenna.preprocessor.v2.parser;



/**
 * omry 
 * 06/02/2007
 */
public class Define
{
	public final String m_key;
	public final Literal m_value;
	
	public Define(String key)
	{
		this.m_key = key;
		this.m_value = null;
	}
	
	public Define(String key, Literal value)
	{
		this.m_key = key;
		this.m_value = value;
	}
	
	public String toString()
	{
		if (this.m_value !=  null)
		{
			return this.m_key + "=" + this.m_value;
		}
		else
		{
			return this.m_key;
		}
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Define)
		{
			Define d = (Define) obj;
			if (!m_key.equals(d.m_key)) return false;
			Literal l1 = m_value;
			Literal l2 = d.m_value;
			return (l1.equals(l2)); 
		}
		return false;
	}
	
}
