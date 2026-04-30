package antenna.preprocessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.pleumann.antenna.misc.Strings;


/**
 * omry 
 * Aug 3, 2006
 */
public interface IPreprocessor
{
	public static final int MODE_NORMAL = 0; // Normal operation
	public static final int MODE_BACKUP = 1; // Backup source files
	public static final int MODE_CLEANUP = 2; // Cleanup uncommented lines
	public static final int MODE_FILTER = 4; // Remove directives
	public static final int MODE_TEST = 8; // Test only, don't write
	public static final int MODE_VERBOSE = 16; // Verbose operation
	public static final int MODE_QUERY = 32; // Query available defines
	public static final int MODE_TARGET = 64; // Write to different directory
	public static final int MODE_INDENT = 128; // Comment indentation
	
	void addSymbols(String defines) throws PreprocessorException;
	void addSymbols(InputStream in) throws PreprocessorException, IOException;
	void addSymbols(File file) throws PreprocessorException, IOException;
	void clearSymbols() throws PreprocessorException;
	void printSymbols() throws PreprocessorException;
	void outputDefinesToFile(File file, String encoding) throws PreprocessorException, IOException;
	boolean preprocess(Strings lines, String encoding) throws PreprocessorException, IOException;
	void setMode(int mode);
	void setFile(File fileName);
	void setDebugLevel(String level) throws PreprocessorException;
}
