/*  Algoritmo BFS */

//gcc bfs.c librerie_esame/Graph/graph.c librerie_esame/Graph/list.c librerie_esame/Input/inputReader.c librerie_esame/Queue/queue.c
#include <stdlib.h>
#include <stdio.h>
#include "librerie_esame/Graph/graph.h"
#include "librerie_esame/Queue/queue.h"

void bfs(Graph G);
void bfs_visit(Graph G,int i,int *aux);

void main(){
    Graph G;
    G=graphCreationMenu(0);
    bfs(G);
}

void bfs(Graph G){
    int i,*aux=calloc(G->nodes_count,sizeof(int)); //Tutto l'array inizializzato a 0
    for(i=0;i<G->nodes_count;i++){
        if(!aux[i]){ //se non è stato già visitato, visitiamolo
            printf("Parto da %d: ",i);
            bfs_visit(G,i,aux);
        }
    }
    free(aux);
}

void bfs_visit(Graph G,int i,int *aux){
    List tmp;
    Queue Q;
    Q=initQueue();
    enqueue(Q,i);
    while(emptyQueue(Q)!=1){
        i=dequeue(Q);
        printf("Preso %d dalla coda\n",i);
        aux[i]=1;
        for(tmp=G->adj[i];tmp!=NULL;tmp=tmp->next){
            if(!aux[tmp->target]){
                printf("Visito %d.\n",tmp->target);
                enqueue(Q,tmp->target);
                aux[tmp->target]=1;

            }
        }
    }
    
}
