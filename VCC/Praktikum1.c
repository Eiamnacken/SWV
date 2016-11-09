#include <vcc.h>
#include <limits.h>


unsigned int addition(unsigned  int a,unsigned int b);
int min(int a,int b);

unsigned int addition(unsigned  int a,unsigned int b)
_(requires   a+b <= UINT_MAX)
_(ensures \result == a+b)
{
    return a+b;
}

int min(int a,int b)
_(ensures a>b ==> \result == b)
_(ensures a<b ==> \result == a)
{
  int min =a;
  if(b<a) min=b;
  return min;
}



void swap(int *a,int *b)
_(writes b)
_(writes a)
 _(ensures *a==\old(*b))
 _(ensures *b==\old(*a)) 
{
  int temp=*a;
  *a=*b;
  *b=temp;
  
}


void fortyTwo(int src[], int dest[], size_t size)
_(requires \thread_local_array(src,size))
_(requires \forall size_t k; k<size ==> (src[k]*42)<=INT_MAX)
_(requires \forall size_t k; k<size ==> (src[k]*42)>=(-INT_MAX-1))
_(writes \array_range(dest,size))
_(requires \arrays_disjoint(src,size,dest,size))
_(ensures \forall size_t k;k < size ==> dest[k]==src[k]*42)
{
  size_t i=0;
  for(i=0;i<size;i++)
  _(invariant i<=size)
  _(invariant \forall size_t k; k<size  ==> (src[k]*42)<=INT_MAX)
  _(invariant \forall size_t k; k<size  ==> (src[k]*42)>=-INT_MAX-1)
  _(invariant \forall size_t k; k<i  ==> dest[k]==src[k]*42)
  {
    dest[i]=src[i]*42;
  }
}

