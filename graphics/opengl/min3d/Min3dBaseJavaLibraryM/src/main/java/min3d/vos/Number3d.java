//MIT license
package min3d.vos;

import org.allbinary.string.CommonSeps;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.math.NoDecimalTrigTable;

public class Number3d
{
    public float x;
    public float y;
    public float z;

    public Number3d()
    {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Number3d(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setAll(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void normalize()
    {
        double sqrt = Math.sqrt((double) this.x * this.x + this.y * this.y + this.z * this.z);
        float mod = (float) sqrt;

        if (mod != 0.0f && mod != 1.0f)
        {
            mod = 1 / mod;
            this.x *= mod;
            this.y *= mod;
            this.z *= mod;
        }
    }

    public void add(Number3d n)
    {
        this.x += n.x;
        this.y += n.y;
        this.z += n.z;
    }

    public void subtract(Number3d n)
    {
        this.x -= n.x;
        this.y -= n.y;
        this.z -= n.z;
    }

    public void multiply(float f)
    {
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public float length()
    {
        double sqrt = Math.sqrt((double) this.x * this.x + this.y * this.y + this.z * this.z);
        return (float) sqrt;
    }

    public Number3d clone()
    {
        return new Number3d(this.x, y, z);
    }

    private final NoDecimalTrigTable noDecimalTrigTable = NoDecimalTrigTable.getInstance();

    public void rotateX(int angle, Number3d initialNumber3d)
    {
        float cosRY = (float) this.noDecimalTrigTable.cos(angle) / 10000;
        float sinRY = (float) this.noDecimalTrigTable.sin(angle) / 10000;

        this.y = (initialNumber3d.y * cosRY) - (initialNumber3d.z * sinRY);
        this.z = (initialNumber3d.y * sinRY) + (initialNumber3d.z * cosRY);
    }

    public void rotateY(int angle, Number3d initialNumber3d)
    {
        float cosRY = (float) this.noDecimalTrigTable.cos(angle) / 10000;
        float sinRY = (float) this.noDecimalTrigTable.sin(angle) / 10000;

        this.x = (initialNumber3d.x * cosRY) + (initialNumber3d.z * sinRY);
        this.z = (initialNumber3d.x * -sinRY) + (initialNumber3d.z * cosRY);
    }

    public void rotateZ(int angle, Number3d initialNumber3d)
    {
        float cosRY = (float) this.noDecimalTrigTable.cos(angle) / 10000;
        float sinRY = (float) this.noDecimalTrigTable.sin(angle) / 10000;

        this.x = (initialNumber3d.x * cosRY) - (initialNumber3d.y * sinRY);
        this.z = (initialNumber3d.x * sinRY) + (initialNumber3d.y * cosRY);
    }

    private static Number3d _temp = new Number3d();
    
	public void rotateXSlow(float angle)
	{
		float cosRY = (float) Math.cos((double) angle);
		float sinRY = (float) Math.sin((double) angle);

		_temp.setAll(this.x, this.y, this.z); 

		this.y = (_temp.y*cosRY)-(_temp.z*sinRY);
		this.z = (_temp.y*sinRY)+(_temp.z*cosRY);
	}
	
	public void rotateYSlow(float angle)
	{
		float cosRY = (float) Math.cos((double) angle);
		float sinRY = (float) Math.sin((double) angle);

		_temp.setAll(this.x, this.y, this.z); 
		
		this.x = (_temp.x*cosRY)+(_temp.z*sinRY);
		this.z = (_temp.x*-sinRY)+(_temp.z*cosRY);
	}
	
	public void rotateZSlow(float angle)
	{
		float cosRY = (float) Math.cos((double) angle);
		float sinRY = (float) Math.sin((double) angle);

		_temp.setAll(this.x, this.y, this.z); 		

		this.x = (_temp.x*cosRY)-(_temp.y*sinRY);
		this.y = (_temp.x*sinRY)+(_temp.y*cosRY);
	}
	
    public void append(final StringMaker stringBuffer) {

        final String COMMA_SEP = CommonSeps.getInstance().COMMA_SEP;

        stringBuffer.append(Float.toString(this.x));
        stringBuffer.append(COMMA_SEP);
        stringBuffer.append(Float.toString(this.y));
        stringBuffer.append(COMMA_SEP);
        stringBuffer.append(Float.toString(this.z));
        
    }
        
    //@Override
    public String toString()
    {
        final StringMaker stringBuffer = new StringMaker();

        this.append(stringBuffer);

        return stringBuffer.toString();
    }
}
