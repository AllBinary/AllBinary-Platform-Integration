package de.pleumann.antenna.device;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * omry Jul 25, 2006
 */
public class Device extends Task
{

	private String m_name;

	private String m_result;
	
	/**
	 * one of:
	 * has_capability
	 * get_capability
	 * in_group
	 * has_capability
	 * support_sound
	 * support_video
	 * support_package
	 * has_bug
	 * translate
	 */
	private String m_op;
	
	private String m_key;
	
	
	public Device()
	{
	}
	
	
	@Override
	public void execute() throws BuildException
	{
		if (this.m_result == null)
		{
			throw new BuildException("result property is not set");
		}

		if (this.m_name == null)
		{
			throw new BuildException("name property is not set");
		}

		if (this.m_op == null)
		{
			throw new BuildException("op property is not set");
		}
		
		if (this.m_key == null)
		{
			throw new BuildException("key property not set");
		}
		
		DeviceProps deviceProps = Devices.getDevice(this.m_name);
		if (deviceProps == null) 
		{
			throw new BuildException("Unsupported device \"" + this.m_name + "\"");
		}
		
		if (this.m_op.equalsIgnoreCase("has_capability"))
		{
			this.getProject().setProperty(this.m_result, "" + deviceProps.hasCapability(this.m_key));
		}
		else
		if (this.m_op.equalsIgnoreCase("get_capability"))
		{
			String capability = deviceProps.getCapability(this.m_key);
			this.getProject().setProperty(this.m_result, capability == null ? "" : capability);
		}
		else
		if (this.m_op.equalsIgnoreCase("in_group"))
		{
			this.getProject().setProperty(this.m_result, "" + deviceProps.inGroup(this.m_key));
		}
		else
		if (this.m_op.equalsIgnoreCase("has_capability"))
		{
			this.getProject().setProperty(this.m_result, "" + deviceProps.hasCapability(this.m_key));
		}
		else
		if (this.m_op.equalsIgnoreCase("support_sound"))
		{
			this.getProject().setProperty(this.m_result, "" + deviceProps.supportSound(this.m_key));
		}
		else
		if (this.m_op.equalsIgnoreCase("support_video"))
		{
			this.getProject().setProperty(this.m_result, "" + deviceProps.supportVideo(this.m_key));
		}
		else
		if (this.m_op.equalsIgnoreCase("support_package"))
		{
			this.getProject().setProperty(this.m_result, "" + deviceProps.supportsPackage(this.m_key));
		}
		else
		if (this.m_op.equalsIgnoreCase("has_bug"))
		{
			this.getProject().setProperty(this.m_result, "" + deviceProps.hasBug(this.m_key));
		}
		else
		if (this.m_op.equalsIgnoreCase("translate"))
		{
			this.getProject().setProperty(this.m_result, Util.expandMacros(this.m_key, deviceProps.getCapabilities()));
		}
		else
			throw new BuildException("Unsupported operation : " + this.m_op);
	}

	public void setName(String name)
	{
		this.m_name = name;
	}

	public void setResult(String result)
	{
		this.m_result = result;
	}

	public void setOp(String op)
	{
		String cap[] = new String[]
		{
			"has_capability", "get_capability", "in_group",
			"has_capability", "support_sound", "support_video",
			"support_package", "has_bug","translate"
		};
		for (int i = 0; i < cap.length; i++)
		{
			if (op.equals(cap[i]))
			{
				this.m_op = op;
				return;
			}
		}
		
		throw new BuildException("Unsupported operation \""+ op+"\" , should be one of \n" + toString(cap));
	}

	private String toString(String[] arr)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < arr.length; i++)
		{
			buf.append(arr[i]);
			if (i < arr.length - 1)
			{
				buf.append("|");
			}
		}
		return buf.toString();
	}

	public void setKey(String key)
	{
		this.m_key = key;
	}
}
