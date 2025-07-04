package antenna.preprocessor.v3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.ant.Project;

import antenna.preprocessor.IPreprocessor;
import antenna.preprocessor.PreprocessorException;
import antenna.preprocessor.v3.PPException;
import de.pleumann.antenna.misc.Strings;
import de.pleumann.antenna.misc.Utility;

/**
 * Implementation for the {@link IPreprocessor} Interface.
 * 
 * @author Omry Yadan
 */
public class PreprocessorBridge implements IPreprocessor {

    /**
     * 
     */
    private Preprocessor m_preprocessor;

    /**
     * 
     */
    private final Utility m_utility;

    /**
     * @param utility
     */
    public PreprocessorBridge(Utility utility) {
        m_utility = utility;
        ILineFilter filter = null;
        ILogger logger = null;

        if (utility != null) {
            final Project project = utility.getProject();
            filter = new ILineFilter() {
                public String filter(String line) {
                    return project.replaceProperties(line);
                }
            };

            logger = new ILogger() {
                public void log(String message) {
                    project.log(message);
                }
            };
        }

        m_preprocessor = new Preprocessor(logger, filter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#setMode(int)
     */
    public void setMode(int mode) {
        m_preprocessor.setVerbose((mode & IPreprocessor.MODE_VERBOSE) != 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#setFile(java.io.File)
     */
    public void setFile(File fileName) {
        m_preprocessor.setFile(fileName);
    }

    /**
     * @param in
     * @param out
     * @param encoding
     * @return
     * @throws IOException
     * @throws PreprocessorException
     */
    public boolean preprocess(InputStream in, OutputStream out, String encoding)
            throws IOException, PreprocessorException {
        try {
            return m_preprocessor.preprocess(in, out, encoding);
        } catch (PPException e) {
            throw new PreprocessorException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#preprocess(de.pleumann.antenna.misc.Strings,
     *      java.lang.String)
     */
    public boolean preprocess(Strings lines, String encoding)
            throws PreprocessorException, IOException {
        try {
            return m_preprocessor.preprocess(lines.getVector(), encoding);
        } catch (PPException e) {
            throw new PreprocessorException(e.getMessage(), e);
        }
    }

    /**
     * @return
     */
    public boolean isVerbose() {
        return m_preprocessor.isVerbose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#addSymbols(java.lang.String)
     */
    public void addSymbols(String defines) throws PreprocessorException {
        try {
            m_preprocessor.addDefines(defines);
        } catch (PPException e) {
            throw new PreprocessorException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#addSymbols(java.io.InputStream)
     */
    public void addSymbols(InputStream in) throws PreprocessorException,
            IOException {
        try {
            m_preprocessor.addDefines(in);
        } catch (PPException e) {
            throw new PreprocessorException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#addSymbols(java.io.File)
     */
    public void addSymbols(File file) throws PreprocessorException, IOException {
        try {
            m_preprocessor.addDefines(file);
        } catch (PPException e) {
            throw new PreprocessorException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#clearSymbols()
     */
    public void clearSymbols() throws PreprocessorException {
        m_preprocessor.clearDefines();
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#outputDefinesToFile(java.io.File,
     *      java.lang.String)
     */
    public void outputDefinesToFile(File file, String encoding)
            throws PreprocessorException, IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(m_preprocessor.getDefines().toString().getBytes(
                    encoding != null ? encoding : "UTF-8"));
            out.flush();
        } finally {
            if (out != null)
                out.close();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#printSymbols()
     */
    public void printSymbols() throws PreprocessorException {
        m_utility.getProject().log(
                "Symbols: " + m_preprocessor.getDefines().toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see antenna.preprocessor.IPreprocessor#setDebugLevel(java.lang.String)
     */
    public void setDebugLevel(String level) throws PreprocessorException {
        if ("none".equals(level) || level == null) {
            m_preprocessor.getDefines().undefine("DEBUG");
        } else {
            if (level.equalsIgnoreCase("debug")
                    || level.equalsIgnoreCase("info")
                    || level.equalsIgnoreCase("warn")
                    || level.equalsIgnoreCase("error")
                    || level.equalsIgnoreCase("fatal")) {
            	try
				{
            		m_preprocessor.addDefines("DEBUG=" + level);
				} catch (Exception e)
				{
					throw new PreprocessorException("Error adding defines",e);
				}
            } else {
                throw new PreprocessorException(
                        "Unsupported debug level "
                                + level
                                + ", Supported values are [debug|info|warn|error|fatal|none]");
            }
        }
    }
}
