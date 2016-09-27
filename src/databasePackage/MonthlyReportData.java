package databasePackage;

public class MonthlyReportData 
{
	private String _productName;
	private int _quantity;
	private int _sellPrice;
	private int _buyPrice;
	private int _itemProfit;
	
	public MonthlyReportData()
	{
		
	}
	
	public MonthlyReportData(String productName, int buyPrice, int sellPrice, int quantity)
	{
		_productName = productName;
		_buyPrice = buyPrice;
		_sellPrice = sellPrice;
		_quantity = quantity;
	}
	
	public String getProductName()
	{
		return _productName;
	}
	
	public int getBuyPrice()
	{
		return _buyPrice;
	}
	
	public int getSellPrice()
	{
		return _sellPrice;
	}
	
	public int getQuantity()
	{
		return _quantity;
	}
	
	public int getItemProfit()
	{
		return _itemProfit;
	}
	
	public void setAdditionalQuantity(int additionalQuantity)
	{
		_quantity = _quantity + additionalQuantity;
	}
	
	public void setQuantity(int quantity)
	{
		_quantity = quantity;
	}
	
	public void setProductName(String productName)
	{
		_productName = productName;
	}
	public void setBuyPrice(int buyPrice)
	{
		_buyPrice = buyPrice;
	}
	public void setSellPrice(int sellPrice)
	{
		_sellPrice = sellPrice;
	}
	
	public void calculateItemProfit()
	{
		_itemProfit = ((this._sellPrice - this._buyPrice) * this._quantity);
	}
	
    public String toString()
	{
		return "Product Name " + this._productName + " Buy Price: " + this._buyPrice + " Sell Price: " + this._sellPrice + " Quantity: " + this._quantity;
		
	}
	
	
}
