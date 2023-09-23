/*Algoritmi sullo heap */

/* */

#include <stdio.h>
#include <stdlib.h>
#define MAX 20
int HeapSize,ArraySize;

int left(int i)
{
	return 2*i+1;
}

int right(int i)
{
	return 2*i+2;
}

int p(int i)
{
	return (i-1)/2;
}

void swap(int A[MAX], int i, int j)
{
	int tmp = A[i];
	A[i] = A[j];
	A[j] = tmp;
}

void Heapify(int A[MAX], int i)
{
	int l,r,largest;
	l = left(i);
	r = right(i);
	if (l < HeapSize && A[l] > A[i])
		largest = l;
	else largest = i;
	if (r < HeapSize && A[r] > A[largest])
		largest = r;

	if (largest != i) {
		swap(A, i, largest);
		Heapify(A, largest);
	}
}


void BuildHeap(int A[MAX])
{
int i;
    HeapSize = ArraySize;
    for (i=ArraySize/2; i>=0; i--)
		Heapify(A, i);
}

void HeapSort(int A[MAX])
{
int i;
	BuildHeap(A);
	for (i=ArraySize-1; i>=1; i--) {
		swap(A, 0, i);
		HeapSize--;
		Heapify(A, 0);
	}
}
void cancella(int A[MAX],int k,int i){
	if(i<=ArraySize-1){
		if(A[2*i+1]>=k)
			cancella(A,k,2*i+1);
		if(A[2*i+2]>=k)	
			cancella(A,k,2*i+2);
		if(A[i]==k){
			A[i]=A[ArraySize-1];
			ArraySize=ArraySize-1;
			HeapSize=ArraySize;
			Heapify(A,i);
		}
	}
}



int main(){
    int dim,A[MAX],i,n;
    printf("Quanti elementi deve contenere l'array?\n ");
    scanf("%d",&dim);
    while(dim>MAX){
        printf("No, max elementi pari a %d\n",MAX);
        scanf("%d",&dim);
    }
    printf("Inserisci gli elementi:\n");
    for(i=0;i<dim;i++){
        scanf("%d",&A[i]);
    }
    HeapSize=dim;
    ArraySize=dim;
	BuildHeap(A);
	for(i=0;i<ArraySize;i++){
        printf("%d ",A[i]);
    }
	printf("Che elemento vuoi cancellare?\n");
	scanf("%d",&n);
	cancella(A,n,0);
	for(i=0;i<ArraySize;i++){
        printf("%d ",A[i]);
    }
    HeapSort(A);
    printf("\nArray ordinato\n");
    for(i=0;i<ArraySize;i++){
        printf("%d ",A[i]);
    }
    printf("\n\n");
	
	
	system("pause");
 return 0;   
}
