/*Prova cancellazione nodo da albero abr */

#include <stdio.h>
#include <stdlib.h>
#include "librerie_esame/BST/tree.h"

Tree stacca_min(Tree T,Tree P);
Tree cancellaradice(Tree T);
Tree cancella(Tree T,int k);

int main(){
    int n;
    Tree T;
    T=treeCreationMenu(0);
    inOrderPrint(T);
    printf("Che nodo vuoi cancellare?\n");
    scanf("%d",&n);
    T=cancella(T,n);
    inOrderPrint(T);
}

Tree stacca_min(Tree T,Tree P){
    if(T!=NULL){
        if(T->sx!=NULL)
            return stacca_min(T->sx,T);
        else
        {
            if(T=P->sx)
                P->sx=T->dx;
            else
                P->dx=T->dx;
            return T;
            
        }
        
    }
    return NULL;
}

Tree cancellaradice(Tree T){
    Tree tmp;
    if(T!=NULL){
        if(T->sx==NULL||T->dx==NULL){
            if(T->sx==NULL)
                tmp=T->dx;
            else
                tmp=T->sx;
            free(T);
            T=tmp;
            
        }
        else
        {
            tmp=stacca_min(T->dx,T);
            T->info=tmp->info;
            free(tmp);
            
        }
    
    }
    return T;
}

Tree cancella(Tree T,int k){
    if(T!=NULL){
        if(T->info>k)
            T->sx=cancella(T->sx,k);
        else if(T->info<k)
            T->dx=cancella(T->dx,k);
        else
            T=cancellaradice(T);
        
    }
    return T;
}

