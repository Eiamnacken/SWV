class Author{
	/*@ predicate author(String f,String l,int a) =
	firstName |-> f &*& lastName |-> l &*& age |-> a &*&
	f != null &*&
	l != null &*&
	16 <= a &*& a <= 150;
	@*/
	private String firstName;
	private String lastName;
	private int age;
	
	public Author(String firstName,String lastName,int age)
	//@ requires firstName!=null&&lastName!=null&&16<=age&&age<=150;
	//@ ensures author(firstName,lastName,age);
	{

		this.firstName=firstName;
		this.lastName=lastName;
		this.age=age;
		
	}
	
	public String getFirstName()
    //@ requires author(?f,?l,?a);
    //@ ensures author(f,l,a) &*& result == f;
    {
        return firstName;
    }
    
    public void setFirstName(String firstName)
	//@ requires  author(?f,?l,?a) &*& firstName != null;
	//@  ensures  author(?f2,l,a) &*&  (f2!=null && firstName==f2);
	{
	        this.firstName = firstName;
        }
        
        public String getLastName()
    //@ requires author(?f,?l,?a);
    //@ ensures author(f,l,a) &*& result == l;
     {
        return lastName;
    }
    
            public void setLastName(String lastName)
	//@ requires  author(?f,?l,?a) &*& lastName != null;
	//@  ensures  author(f,?l2,a) &*&  (l2!=null && lastName==l2);
	{
	        this.lastName = lastName;
        }
    
            public int getAge()
    //@ requires author(?f,?l,?a);
    //@ ensures author(f,l,a) &*& result == a;
     {
        return age;
    }
    
            public void incrAge()
	//@ requires  author(?f,?l,?a) ;
	//@  ensures  author(f,l,?a2) &*&  (a2 == 150  || a2 == a+1) ;
	{
	 	if(age<150){
	 		age=age+1;
	 	 }       
        }
}

class Price{
    /*@ predicate price(int v,String c) =
    value |-> v &*& currency |-> c &*&
    c != null &*&
    0 <= v &*& v <= Integer.MAX_VALUE;
    @*/
    private int value;
    private String currency;

    public Price(int value, String currency)
    //@ requires 0 <= value && value <= Integer.MAX_VALUE && currency != null;
    //@ ensures price(value,currency);
     {
     	if(value<0){
     		this.value=0;
     	}else
	        this.value = value;
        this.currency = currency;
    }
    
           public int getValue()
    //@ requires price(?v,?c);
    //@ ensures price(v,c) &*& result == v;
     {
        return value;
    }
    
    public String getCurrency()
    //@ requires price(?v,?c);
    //@ ensures price(v,c) &*& result == c;
     {
        return currency;
    }
    
    public void doubleValue()
    //@ requires price(?v,?c) &*& v *2 <= Integer.MAX_VALUE;
    //@ ensures price(?v2,c) &*& (v*2 == Integer.MAX_VALUE  || v2==v*2);
    {
    	if((this.value*2)<Integer.MAX_VALUE)
	    	this.value *=2;
    }
    
}


class Book{
    /*@ predicate book(Author a,Price p) =
    author |-> a &*& price |-> p &*&
    p != null &*&
    a != null;
    @*/
    private Author author;
    private Price price;

    public Book(Author author, Price price)
    //@ requires author!=null && price != null;
    //@ ensures book(author,price);
     {
     	this.author=author;
     	this.price=price;
    }
    
        public void doublePrice()
   //@requires book(?a,?p) &*& p.price(?v,?c)  &*& (v*2<Integer.MAX_VALUE);
   //@ensures  book(a,?p2) &*& p2.price(?v2,c) &*& v2==v*2;
    {
    	this.price.doubleValue();
    }
    
}

class Main{

	public static void addOne(int []a)
	//@ requires  a[0..a.length] |-> ?vs1;
	/*@ ensures a[0..a.length]  |-> ?vs2 &*&
	forall_(int i; i<0 || i >= a.length || nth(i,vs1) == Integer.MAX_VALUE  || nth(i,vs2) == nth(i,vs1)+1);
	@*/
	{
		for(int i=0;i<a.length;i++)
		/*@ invariant 0<= i &*& i<= a.length &*&
		a[0..a.length] |-> ?vs2 &*&
		forall_(int k; k<0 || k >= i || nth(k,vs1) == Integer.MAX_VALUE  || nth(k,vs2) == nth(k,vs1)+1)  &*&
		forall_(int j; j<i || j>= a.length || nth(j,vs2)==nth(j,vs1));
		@*/
		{
			if(a[i]<Integer.MAX_VALUE){
				a[i]=a[i]+1;
			}
		}
	}
}

	