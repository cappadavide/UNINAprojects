/* Algoritmo DFS */

//gcc dfs.c librerie_esame/Graph/graph.c librerie_esame/Graph/list.c librerie_esame/Input/inputReader.c
#include <stdlib.h>
#include <stdio.h>
#include "librerie_esame/Graph/graph.h"
void dfs(Graph G);
void dfs_visit(Graph G,int i,int *aux);
void main(){
    Graph G;
    G=graphCreationMenu(0);
    dfs(G);
}

void dfs(Graph G){
    int i,*aux=calloc(G->nodes_count,sizeof(int)); //Tutto l'array inizializzato a 0
    for(i=0;i<G->nodes_count;i++){
        if(!aux[i]){ //se non è stato già visitato, visitiamolo
            printf("Parto da %d: ",i);
            dfs_visit(G,i,aux);
        }
    }
    free(aux);
}

void dfs_visit(Graph G,int i,int *aux){
    List tmp;
    aux[i]=1; //Questo vertice si può ritenere visitato
    for(tmp=G->adj[i];tmp!=NULL;tmp=tmp->next){
        printf("visito gli adiacenti.");
        if(!aux[tmp->target]){
            printf(" Vado a visitare l'adiacente %d.\n",tmp->target);
            dfs_visit(G,tmp->target,aux);
        }
    }
}