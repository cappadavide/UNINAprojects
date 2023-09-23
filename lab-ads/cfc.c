/*ALGORITMO CFC*/
//gcc cfc.c librerie_esame/Graph/graph.c librerie_esame/Graph/list.c librerie_esame/Input/inputReader.c librerie_esame/Queue/queue.c
#include <stdlib.h>
#include <stdio.h>
#include "librerie_esame/Graph/graph.h"
#include "librerie_esame/Queue/queue.h"
Queue dfs(Graph G);
void dfs2(Graph G,Queue L);
void dfs_visit2(Graph G,int i,int *aux,Queue L);
void dfs_visit(Graph G,int i,int *aux);
Graph grafotrasposto(Graph G);


void main(){
    Graph G;
    Queue L;
    G=graphCreationMenu(0);
    L=dfs(G);
    G=grafotrasposto(G);
    dfs2(G,L);
}

Graph grafotrasposto(Graph G){
    int i;
    Graph H;
    List tmp;
    H=initGraph(G->nodes_count);
    for(i=0;i<G->nodes_count;i++){
        tmp=G->adj[i];
        while(tmp!=NULL){
            addEdge(H,tmp->target,i,tmp->peso);
            tmp=tmp->next;
        }
    }
    printf("Il tuo grafo trasposto:\n");
    printGraph(H);
    return H;
}

Queue dfs(Graph G){
    Queue L;
    L=initQueue();
    int i,*aux=calloc(G->nodes_count,sizeof(int)); //Tutto l'array inizializzato a 0
    for(i=0;i<G->nodes_count;i++){
        if(!aux[i]){ //se non è stato già visitato, visitiamolo
            dfs_visit2(G,i,aux,L);
        }
    }
    printf("Coda pre reverse:\n");
    printQueue(L);
    reverseQueue(L);
    printf("Dopo reverse:\n");
    printQueue(L);
    free(aux);
    return L;
}
void dfs2(Graph G,Queue L){
    int i,count=0,*aux=calloc(G->nodes_count,sizeof(int)); //Tutto l'array inizializzato a 0
    while(!emptyQueue(L)){
        i=dequeue(L);
        if(!aux[i]){ //se non è stato già visitato, visitiamolo
            count++;
            dfs_visit(G,i,aux);
        }
    }
    printf("Numero cfc: %d\n",count);
    free(aux);
}

void dfs_visit2(Graph G,int i,int *aux,Queue L){
    List tmp;
    aux[i]=1; //Questo vertice si può ritenere visitato
    for(tmp=G->adj[i];tmp!=NULL;tmp=tmp->next){
        if(!aux[tmp->target]){
            dfs_visit2(G,tmp->target,aux,L);
        }
    }
    enqueue(L,i);
}
void dfs_visit(Graph G,int i,int *aux){
    List tmp;
    aux[i]=1; //Questo vertice si può ritenere visitato
    for(tmp=G->adj[i];tmp!=NULL;tmp=tmp->next){
        //printf("visito gli adiacenti.");
        if(!aux[tmp->target]){
            //printf(" Vado a visitare l'adiacente %d.\n",tmp->target);
            dfs_visit(G,tmp->target,aux);
        }
    }
}