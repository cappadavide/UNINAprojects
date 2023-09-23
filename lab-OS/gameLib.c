#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h> 
#include <unistd.h>
#include "gameLib.h"
#define MAX_USERS 8





int randNumb(){
  return (rand()%9+16);
}

int calculateMaxNumber(int rows, int cols, int element){
  int minNum = min(rows, cols);
  return ((element*minNum)/16);
}


void createMap(struct mapObjects* info,int rows,int cols,struct cell **map){
  int i;
  int r,c,index;
  int idWarehouse=1,idObstacle=0;
  struct warehouse * magazzino;
  struct items *oggetto;
  struct obstacles *ostacolo;
  char items[3]={'$','@','s'};
  printf("Choosing number of warehouses...\n");
  info->n_warehouses=rand()%(calculateMaxNumber(rows, cols, 4))+1; 
  printf("Number of Warehouses: %d\n",info->n_warehouses);
  printf("Choosing number of obstacles...\n");
  info->n_obstacles= (rand()%(calculateMaxNumber(rows, cols, min(rows,cols))))+8;
  printf("Number of Obstacles: %d\n", info->n_obstacles);
  printf("Choosing number of items...\n");
  info->n_items=rand()%(calculateMaxNumber(rows, cols, 5))+12; 
  printf("Number of items: %d\n",info->n_items);
  printf("Generating map...\n");
  i=0;
  while(i<info->n_warehouses){
    r=rand()%rows;
    c=rand()%cols;
    if(map[r][c].isWareHouse==0){
      magazzino=(struct warehouse *)malloc(sizeof(struct warehouse));
      map[r][c].isWareHouse=1;
      i++;
      map[r][c].object=idWarehouse+'0';//cappadavide
      magazzino->id=idWarehouse++;
      map[r][c].pointer=(void *)magazzino;
    }
  }
  printf("warehouses done\n");
  i=0;
  while(i<info->n_obstacles){
    r=rand()%rows;
    c=rand()%cols;
    if(map[r][c].isWareHouse==0&&map[r][c].isObstacle==0){
      ostacolo=(struct obstacles *)malloc(sizeof(struct obstacles));
      map[r][c].isObstacle=1;
      i++;
      map[r][c].object = 'x';
      ostacolo->id=idObstacle++;
      map[r][c].pointer=(void *)ostacolo;
    }
  }
  printf("obstacles done\n");
  i=0;
  while(i<info->n_items){
    r=rand()%rows;
    c=rand()%cols;
    idWarehouse=rand()%info->n_warehouses+1;
    index=rand()%3;
    if(map[r][c].isWareHouse==0&&map[r][c].isObstacle==0&&map[r][c].object==' '){
      oggetto=(struct items *)malloc(sizeof(struct items));
      map[r][c].object=items[index];
      i++;
      oggetto->warehouse=idWarehouse;
      oggetto->object=items[index];
      map[r][c].pointer=(void *)oggetto;
    }

  }
  printf("items done\n");

}


void printMatrix(int rows, int cols, struct cell **map){
  int i, j;
  for(i = 0; i < rows; i++){
    for(j = 0; j < cols; j++){
      printf("%c ", map[i][j].object);
    }
    printf("\n");
  }
}

int min(int rows,int cols){
  if(rows<=cols)
    return rows;
  else
    return cols;
  
}

