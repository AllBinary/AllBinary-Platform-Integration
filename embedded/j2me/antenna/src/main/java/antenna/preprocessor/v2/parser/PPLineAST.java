package antenna.preprocessor.v2.parser;

import antlr.CommonAST;
import antlr.Token;

/**
 * omry 19/02/2007
 */
public class PPLineAST extends CommonAST
{

	private int column, line;

	@Override
	public void initialize(Token tok)
	{
		super.initialize(tok);
		this.column = tok.getColumn();
		this.line = tok.getLine();
	}

	@Override
	public int getColumn()
	{
		return this.column;
	}

	@Override
	public int getLine()
	{
		return this.line;
	}

	public String toString()
	{
		StringBuffer result = new StringBuffer("");

		result.append(super.toString()).append("[").append(this.getLine()).append(":").append(this.getColumn()).append(" - ")
				.append(this.getClass().getName()).append("(").append(this.getType()).append(")] : ").append(this.getText());

		return result.toString();
	}

}
