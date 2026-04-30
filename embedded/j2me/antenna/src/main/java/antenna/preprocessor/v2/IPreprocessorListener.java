package antenna.preprocessor.v2;


/**
 * omry 
 * 18/02/2007
 */
public interface IPreprocessorListener
{
	/**
	 * Used to indicate unknown int value (line line, offset etc).
	 */
	public static final int UNKNOWN = -1;
	
	void warning(String message, int lineNumber, int offset, int length);
	void error(Exception e, int lineNumber, int offset, int length);
}
