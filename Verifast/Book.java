class Author {
    private String firstName;
    private String lastName;
    private int age;

    public Author(String firstName, String lastName, int age)
    //@ requires true;
    //@ ensures this.firstName |-> firstName &*& this.lastName |-> lastName &*& this.age |-> ?a &*& a<160;
     { 
        this.firstName = firstName;
        this.lastName = lastName;
        if(age>=160){
        	this.age=159;
        }else
	        this.age = age;
    }

    public String getFirstName()
    //@ requires firstName |-> ?name &*& name != null;
    //@ ensures true;
    {
        return firstName;
    }

  public void setFirstName(String firstName)
	//@ requires  this.firstName |-> _;
	//@  ensures  this.firstName |-> ?name &*&  name==firstName;
    {
        this.firstName = firstName;
    }

    public String getLastName()
    //@ requires lastName |-> ?name &*& name !=null;
    //@ ensures true;
     {
        return lastName;
    }

    public void setLastName(String lastName)
    //@ requires this.lastName |-> _;
    //@ ensures this.lastName |-> ?name &*& name==lastName;
     {
        this.lastName = lastName;
    }

    public int getAge()
    //@ requires age  |-> ?a &*& (a<160 && a>0);
    //@ ensures true;
    {
        return age;
    }

    public void incAge()
    //@ requires this.age |-> ?a;
    //@ ensures this.age |-> ?b &*& (b>=160 || b==a+1);
     {
     	if(this.age<160){
	        this.age +=1;
        }
    }
}

class Price{

    private int value;
    private String currency;

    public Price(int value, String currency)
    //@ requires true;
    //@ ensures this.value |-> ?b &*& b>=0 &*& this.currency |-> currency;
     {
     	if(value<0){
     		this.value=0;
     	}else
	        this.value = value;
        this.currency = currency;
    }

    public int getValue()
    //@ requires value|-> ?b &*& b>=0;
    //@ ensures true;
    {
        return value;
    }

    public String getCurrency()
    //@ requires currency |-> ?c &*& c!=null;
    //@ ensures true;
    {
        return currency;
    }
    
    public void doubleValue()
    //@ requires value |-> ?p &*&  p*2>=Integer.MIN_VALUE &*& p*2<= Integer.MAX_VALUE;
    //@ ensures value |-> ?b &*& p*2==b;
    {
    	if((this.value*2)<=Integer.MAX_VALUE && (this.value*2)>=Integer.MIN_VALUE)
	    	this.value *=2;
    }
}

class Book{

    private Author author;

    private Price price;

    public Book(Author author, Price price)
    //@ requires true;
    //@ ensures this.author |-> author &*& this.price |-> price;
    {
        this.author = author;
        this.price = price;
    }

    public void doublePrice()
   //@requires price |-> ?p &*& p!=null &*& Price_value(p,?v) &*& (v*2<Integer.MAX_VALUE ) &*& (v*2>Integer.MIN_VALUE);
   //@ensures  price |-> ?n &*& Price_value(n,?c) &*& c==v*2;
    {
    	this.price.doubleValue();
    }
}