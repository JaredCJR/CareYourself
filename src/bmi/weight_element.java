package bmi;

public class weight_element {
	
	private int index = 0;
	private double original_weight = 0;
	private double cut_weight = 0;
	private float mapping_y_px = 0;
	private float mapping_x_px = 0;
	
	public void weight_element(){
		//constructor
		this.index = 0;
		this.original_weight = 0;
		this.cut_weight = 0;
		this.mapping_y_px = 0;
		this.mapping_x_px = 0;
	}
	
	public void set_index(int a)
	{
		this.index=a;
	}
	
	public void set_original_weight(double a)
	{
		this.original_weight=a;
	}
	
	public void set_cut_weight(double a)
	{
		this.cut_weight=a;
	}
	
	public void set_mapping_y_px(float a)
	{
		this.mapping_y_px=a;
	}
	
	public void set_mapping_x_px(float a)
	{
		this.mapping_x_px=a;
	}
	
	public int get_index()
	{
		return this.index;
	}
	
	public double get_original_weight()
	{
		return this.original_weight;
	}
	
	public double get_cut_weight()
	{
		return this.cut_weight;
	}
	
	public float get_mapping_x_px()
	{
		return this.mapping_x_px;
	}
	
	public float get_mapping_y_px()
	{
		return this.mapping_y_px;
	}

}
