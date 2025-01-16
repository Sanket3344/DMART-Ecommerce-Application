import java.util.*;
class Dmart
{
	String address;
	long contact;
	String email;
	String website;
	ArrayList<Product> productList = new ArrayList<>();
	Cashier cashier;
	Customer customer;
	Cart cart;

	Dmart(String address,long contact,String email,String website)
	{
		this.address = address;
		this.contact = contact;
		this.email = email;
		this.website = website;
	}
	public void displayDmart()
	{
		System.out.println("Dmart [Address: "+address+", Contact: "+contact+", Email: "+email+", Website: "+website+"]");
	}
	public void addProduct(String category,String name,double orgPrice,Barcode barcode)
	{
		Product product = new Product(category, name, orgPrice, barcode);
		productList.add(product);
	}
	public void addCart(String type)
	{
		cart = new Cart(type);
	}
	public void addCashier(String name,String id,long contact,int counterNum)
	{
		cashier = new Cashier(name,id,contact,counterNum);
	}
	public void addCustomer(String name,long contact)
	{
		if(cashier != null && productList.size() != 0)
		{
			customer  = new Customer(name,contact);
		}
		else
		{
			System.out.println("add Cashier and Product First");
		} 
	}
}

class Product
{
	String category;
	String name;
	double orgPrice;
	Barcode barcode;

	Product(String category, String name,double orgPrice,Barcode barcode)
	{
		this.category = category;
		this.name = name;
		this.orgPrice = orgPrice;
		this.barcode = barcode;
	}
	public void displayProduct()
	{
		System.out.println("Product [Category: "+category+", Name: "+ name+", Original Price: "+ orgPrice+ "]");
	}
	public void addBarcode(String productId,double dmartPrice)
	{
		barcode = new Barcode(productId, dmartPrice);
	}
}

class Barcode
{
	String productId;
	double dmartPrice;

	Barcode(String productId, double dmartPrice)
	{
		this.productId = productId;
		this.dmartPrice = dmartPrice;
	}
	public void displayBarcode()
	{
		System.out.println("Barcode [Product Id: "+productId+ ", Dmart Price: "+dmartPrice+"]");
	}
}

class Customer
{
	String name;
	long contact;
	String paymentMode;
	String billNo;
	double totalBill;

	ArrayList<Product> customerCart = new ArrayList<>();
	Customer(String name,long contact)
	{
		this.name = name;
		this.contact = contact;
	}
	public void displayCustomer()
	{
		System.out.println("Customer [Name: "+name+", Contact: "+contact+", Payment Mode: "+paymentMode+", Bill No: "+ billNo+ ", Total Bill: "+totalBill+"]");
	}
}

class Cashier
{
	String name;
	String id;
	long contact;
	int counterNum;

	Cashier(String name,String id,long contact,int counterNum)
	{
		this.name = name;
		this.id = id;
		this.contact = contact;
		this.counterNum = counterNum;
	}
	public void displayCashier()
	{
		System.out.println("Cashier [Name: "+name+", Id: "+id+", Contact: "+contact+", Counter Number: "+counterNum+"]");
	}
}

class Cart
{
	String type;

	ArrayList<Product> cart = new ArrayList<>();
	Cart(String type)
	{
		this.type = type;
	}	
	public void displayCart()
	{
		System.out.println("Cart [Type: "+type+", Capacity: "+ cart.size());

		for(Product i : cart)
		{
			i.displayProduct();
			i.barcode.displayBarcode();
		}
	}
}

class DmartCompositionAggregationDriver
{
	static Scanner sc;
	public static void main(String[] args) 
	{
		sc = new Scanner(System.in);
		Dmart dmart = new Dmart("Deccan",9876543215l,"dmart@gmail.com","www.dmart.com");
		dmart.displayDmart();

		for(int i=1;i<=2;i++)
		{
			System.out.println("Add Product  ");
		//	System.out.println();
			sc.nextLine();
			System.out.print("Category: ");
			Scanner sc1= new Scanner(System.in);
			String category = sc1.nextLine();
			System.out.print("Product Name: ");
			String name = sc1.nextLine();
			System.out.print("MRP: ");
			double price = sc.nextDouble();
			System.out.print("Product Id: ");
			sc.nextLine();
			String id = sc.nextLine();
			System.out.print("Dmart Price: ");
			double dprice = sc.nextDouble();
		//	System.out.println();
			dmart.addProduct(category,name,price,(new Barcode(id,dprice)));
		}			

		for(Product i : dmart.productList)
		{
			i.displayProduct();
			i.barcode.displayBarcode();
		}

		dmart.addCashier("Ramesh Kumar","DCASH123",9876543210l,2);
		dmart.cashier.displayCashier();
		dmart.addCart("Basket");
		dmart.cart.displayCart();
		dmart.addCustomer("Suresh Kumar",9898563218l);
		dmart.customer.displayCustomer();

		for(;;)
		{
			System.out.print("Enter the Product Id: ");
			sc.nextLine();
			String pid = sc.nextLine();
			boolean flag = false;

			for(Product i : dmart.productList)
			{
				if(pid.equals(i.barcode.productId))
				{
					dmart.customer.customerCart.add(i);
					flag = true;
				}
			}
			if(!flag)
			{
				System.out.println("Product Not Found ");
			}
			System.out.print("Do u want to continue shopping (Y/N): ");
			char ch = sc.next().charAt(0);
			if(ch == 'Y')
			{
				continue;
			}
			else 
			{
				break;
			}
		}
		double bill = 0;
		for(Product i : dmart.customer.customerCart)
		{
			i.displayProduct();
			i.barcode.displayBarcode();
			dmart.customer.totalBill += i.barcode.dmartPrice;
			bill += i.orgPrice;
		}

		System.out.println("Total Bill is: "+ bill);
		System.out.println("Discounted Price: "+ dmart.customer.totalBill);

	}	
}