package antenna.preprocessor.v2;

import java.io.File;

/**
 * Provides a specialized exception type for use inside the preprocessor.
 */
public class PPException extends Exception {

	public static final int UNKNOWN_LINE = -1;
	
	private final int m_lineNumber;
	private File m_file;
	
	public PPException(String message)
	{
		this(message, null, null);
	}

	public PPException(String message, Throwable cause)
	{
		this(message, null, cause);
	}
	
	public PPException(String message, File file)
	{
		this(message, file, null, UNKNOWN_LINE);
	}

	public PPException(String message, File file, int lineNumber)
	{
		this(message,file,  null, lineNumber);
	}
	
	public PPException(String message, File file, Throwable cause)
	{
		this(message, file, cause, UNKNOWN_LINE);
	}
	
	public PPException(String message, File file, Throwable cause, int lineNumber)
	{
		super(message, cause);
		this.m_lineNumber = lineNumber;
		this.m_file = file;
	}

	public int getLineNumber()
	{
		return m_lineNumber;
	}
	
	public File getFile()
	{
		return m_file;
	}
	
	public String getMessage()
	{
		if (this.m_file != null)
		{
			String ln = this.m_lineNumber != UNKNOWN_LINE ? ":" + this.m_lineNumber : "";
			return m_file + ln + " : " + super.getMessage();	
		}
		else
		{
			if (this.m_lineNumber != UNKNOWN_LINE)
			{
				return "Line #" + this.m_lineNumber + " : " + super.getMessage();
			}
			else
			{
				return super.getMessage();
			}
		}
		
	}
}
