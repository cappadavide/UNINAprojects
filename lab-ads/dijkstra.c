/*Algoritmo per il calcolo del percorso minimo
                    Dijkstra
*/
//gcc dijkstra.c librerie_esame/Graph/graph.c librerie_esame/Queue/queue.c librerie_esame/Input/inputReader.c librerie_esame/Graph/list.c
#include <stdio.h>
#include <stdlib.h>
#include "librerie_esame/Graph/graph.h"
#include "librerie_esame/Queue/queue.h"
void dijkstra(Graph G);
Queue riempicoda(Queue Q,Graph G,int source);

void main(){
    int min;
    Graph G;
    G=graphCreationMenu(0);
    printf("Il tuo grafo e' il seguente:\n");
    printGraph(G);
    dijkstra(G);
}

void dijkstra(Graph G){
    int source=-1,n;
    Queue Q,S; //Versione: implementato come un array non ordinato (e non come heap)
    int *pi,*d;
    List tmp;
    S=initQueue();
    pi=(int *)malloc(G->nodes_count*sizeof(int)); //Array predecessori
    d=(int *)malloc(G->nodes_count*sizeof(int)); //Array distanza sorgente-vertice
    printf("Scegli sorgente:\n");
    while(source>G->nodes_count-1||source<0)
        scanf("%d",&source);
    Q=riempicoda(Q,G,source);//Nella coda ci saranno i vertici
    printQueue(Q);
   
    /*Inizializzazione array pred/dist */
    for(int i=0;i<G->nodes_count;i++){
        pi[i]=-1;
        d[i]=-1;
    }
    d[source]=0;
    ////////////////////

    while(emptyQueue(Q)!=1){ //(V + V × TEXTRACT-MIN + E × TRELAX)
                             //Dunque, la complessità dipende molto da come è implementata la coda di priorità
        n=dequeue(Q); 
        printf("Ho estratto il vertice %d",n);
        enqueue(S,n);
        printf(". L'ho aggiunto a S\n");
        if(d[n]!=-1){
            for(tmp=G->adj[n];tmp!=NULL;tmp=tmp->next){
                /*FUNZIONE RELAX (u,v)*/
                if(d[tmp->target]!=-1&&d[tmp->target]>d[n]+tmp->peso){
                    d[tmp->target]=d[n]+tmp->peso;
                    pi[tmp->target]=n;
                }
                else if(d[tmp->target]==-1){
                    d[tmp->target]=tmp->peso+d[n];
                    pi[tmp->target]=n;
                }
            ///////////////////////
            }
        }
        else{
            enqueue(Q,n);
            n=dequeue(Q);
        }
    }
    printf("Percorso minimo da source a..?\n");
        n=-1;
        while(n>G->nodes_count-1||n<0)
            scanf("%d",&n);
        if(d[n]!=-1)
            printf("Distanza: %d\n",d[n]);
        else
            printf("Vertice non raggiungibile\n");
        
}

Queue riempicoda(Queue Q,Graph G,int source){
    int i;
    Q=initQueue();
    enqueue(Q,source);
    for(i=0;i<G->nodes_count;i++){
        if(i!=source)
            enqueue(Q,i);

    }
    return Q;
}