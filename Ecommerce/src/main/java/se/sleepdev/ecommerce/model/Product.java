package se.sleepdev.ecommerce.model;


public final class Product
{
	public static final int EMPTY_PRODUCT_ID = -1;
	private final String productName;
	private final int productPrice;
	private final int productId;
	
	public Product(String productName, int productPrice)
	{
		this(EMPTY_PRODUCT_ID, productName, productPrice);
		
	}
	
	public Product(int productId,String productName, int productPrice)
	{
		
		this.productName = productName;
		this.productPrice = productPrice;
		this.productId = productId;
	}
	public String getProductName()
	{
		return productName;
	}
	public int getProductPrice()
	{
		return productPrice;
	}
	public int getProductId()
	{
		return productId;
	}

	@Override
	public int hashCode()
	{
		
		int result = 1;
		result = 37 * result + productId;
		result = 37 * result + ((productName == null) ? 0 : productName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Product)
		{
		Product other = (Product) obj;
		return productId == other.getProductId() && productName.equals(other.getProductName());
		}
		return false;
	}
	
	
	
	
	

}
