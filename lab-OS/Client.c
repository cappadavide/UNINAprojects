#include <netdb.h> 
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <unistd.h>
#include <sys/socket.h>
#include <sys/time.h> 
#include <sys/epoll.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <errno.h>
#include <fcntl.h>
#include <signal.h>
#define MAX 1000 

#define RED     "\x1b[31m"
#define GREEN   "\x1b[32m"
#define YELLOW  "\x1b[33m"
#define BLUE    "\x1b[34m"
#define MAGENTA "\x1b[35m"
#define CYAN    "\x1b[36m"
#define WHITE   "\x1b[37m"
#define RST   "\x1b[0m"
#define BRIGHT_CYAN "\x1b[96m"

extern int errno;

struct config{
    unsigned int port;
    char ip[28];
    char hostname[100];
};

char playerLetter;
int gameFinished = 0;
pthread_t cleaner;
int stopClean=0;
/*Signal handler per SIGINT e SIGPIPE. Se il segnale è SIGPIPE allora viene stampato "Disconnesso dal server". Viene stampato poi un messaggio di uscita (Anche se il segnale è SIGINT) e poi chiuso il programma.*/
void clientAbort(int signalvalue);
/*Imposta serverConfig con l'ip del server o l'hostname in base alla scelta dell'utente.*/
void chooseServer(struct sockaddr_in *serverConfig);
/*Menù principale. Aspetta un input, la scelta. Se è valida si prosegue con la funzionalità corrispondente.*/
void homeClient(int server_sd);
/*Comunica al server che sta per eseguire il login. Aspetta in input le credenziali che scrive su un'unica stringa, inviata poi al server. Restituisce '1' se il login avviene con successo, '0' altrimenti.*/
char loginCred(int server_sd);
/*Comunica al server che sta per eseguire la registrazione. Aspetta in input le credenziali che scrive su un'unica stringa, inviata poi al server.*/
void regCred(int server_sd);
/*Stampa in output la guida di gioco scritta su GameGuide.txt.*/
void printGuide();
/*Stampa in output le informazioni scritte su GameInfo.txt*/
void printInfo();
/*Aspetta un segnale dal server dopo un tentativo di login. Restituisce 1 se il login avviene con successo, 0 altrimenti. Stampa in output l'esito.*/
int checkLoginStatus(char *msg);
/*Scrive prima username, poi '\n' e poi password su creds. Ritorna la lunghezza di creds.*/
int combineStr(char *creds,char *username, char *password);
/*Stampa in output una riga della mappa di gioco inviata dal server.*/
void printRow(char *buff);
/*Stampa la mappa di gioco inviata dal server, una riga per volta, chiamando printRow. Se riceve 0 come numero di righe e di colonne, imposta gameFinished = 1.*/
void printMap(int server_sd);
/*Riceve la lunghezza del messaggio e il messaggio stesso dal server, chiamando read sul socket. Stampa poi in output la stringa.*/
void receiveMessage(int server_sd);
/*Riceve la lunghezza del messaggio e il messaggio stesso dal server, scrivendolo su buffer.*/
void receiveSignal(int server_sd, char *buffer);



/*Thread utilizzato per svuotare il buffer di stdin.*/
void *cleanerThread(void *args){
    while(!stopClean){
        getchar();
    }
    pthread_exit(0);
} 

void clientAbort(int signalvalue){
    if(signalvalue == SIGPIPE)
        printf("Disconnesso dal server.");
    printf("\nRitorna presto!\n");
    exit(0);
}


void printRow(char *buff){
    printf(RED"|"RST" ");
    for(int i = 0; i < strlen(buff); i++){
        if(buff[i] == playerLetter)
            printf(YELLOW"%c"RST" ", buff[i]);
        else if(buff[i] == 'x')
            printf(GREEN"%c"RST" ", buff[i]);
        else if(buff[i]>='1'&&buff[i]<='9')
            printf(BRIGHT_CYAN"%c"RST" ", buff[i]);
        else if(buff[i]>='A'&&buff[i]<='H')
            printf(BLUE"%c"RST" ",buff[i]);
        else
            printf("%c ", buff[i]);
    }
    printf(RED"|"RST);
    printf("\n");
}

void receiveMessage(int server_sd){
    char buffer[250];
    int n_bytes;
    read(server_sd, &n_bytes, sizeof(int));
    if(n_bytes > 0){
        read(server_sd, buffer, n_bytes);
        buffer[n_bytes] = '\0';
        printf(GREEN"%s"RST,buffer);
        if(strstr(buffer, "uscito") != NULL)
            printf("Premi un tasto per chiudere il gioco: ");
        else
            printf("Comando: ");
        
    }
    else
        printf("Comando: ");
    
    

}

void receiveSignal(int server_sd, char *buffer){
    int n_bytes;
    read(server_sd, &n_bytes, sizeof(int));
    if(n_bytes > 0){
        read(server_sd, buffer, n_bytes);
        buffer[n_bytes] = '\0';
    }

}

void printMap(int server_sd){
    char row[30];
    int rows;
    int cols;
    read(server_sd, &rows, sizeof(int));
    read(server_sd, &cols, sizeof(int));
    if(rows>0&&cols>0){
        printf("Lettera giocatore: %c\n\n", playerLetter);
        printf("  ");
        for(int i = 0; i < cols; i++)
            printf(RED"_"RST" ");
        printf("\n");
        for(int i = 0; i < rows; i++){
            read(server_sd, row, cols);
            row[cols]='\0';
            printRow(row);
        }

        printf("  ");
        for(int i = 0; i < cols; i++)
            printf(RED"─"RST" ");

        printf("\n\n");
    }
    else{
        gameFinished = 1;
    }
    
}

char firstChar(char *buffer){
    return buffer[0];
}


void game(int server_sd){
    char msg;
    char buffer[200];
    char buf[500];
    char row[16];
    int rows;
    int cols;
    int stdin_copy;
    pthread_create(&cleaner, NULL, cleanerThread, NULL);
    read(server_sd, &playerLetter, 1);
    stopClean=1;

    while(1){
        system("clear");
        printMap(server_sd);
        receiveMessage(server_sd); //Info
        fgets(buffer, sizeof(buffer), stdin);
        msg = firstChar(buffer);
        write(server_sd, &msg, 1);
        if(gameFinished){
            printf("Ricevo la lettera...\n");
            stopClean=0;
            pthread_create(&cleaner, NULL, cleanerThread, NULL);
            if(read(server_sd, &playerLetter, 1)<=0){
                break;
            }
            stopClean=1;
            printf("Lettera ricevuta\n");
            gameFinished = 0;
        }       
    }
    stopClean=1;
}


int main() 
{ 
    signal(SIGINT,clientAbort);
    signal(SIGPIPE, clientAbort);
    int sockfd, connfd; 
    pthread_t tid;
    struct sockaddr_in serverConfig;
    sockfd = socket(AF_INET, SOCK_STREAM, 0); 
   
    if (sockfd == -1){ 
        printf("C'è stato un problema: chiusura imminente.\n"); 
        exit(0); 
    } 
    else{
        chooseServer(&serverConfig);
        if (connect(sockfd, (struct sockaddr*)&serverConfig, sizeof(serverConfig)) != 0) { 
            printf("Errore: %s\nHai inserito bene i parametri?\n",strerror(errno));
            exit(0); 
        } 
        else
            printf("Connesso al server!\n");
            system("clear");
            homeClient(sockfd);
        }
} 

void chooseServer(struct sockaddr_in *serverConfig){
    int chooseOption;
    unsigned int port;
    char ip[28];
    char hostname[100];
    serverConfig->sin_family = AF_INET; 
    struct hostent *p;
    printf("Benvenuto!\n");
    printf("Specificare come vuoi selezionare il server:\n(1)Tramite IP\n(2)Tramite Hostname\n");
    scanf("%d",&chooseOption);
    switch (chooseOption)
    {
    case 1:
        printf("Inserisci IP:\n");
        scanf("%s",ip);
        if(inet_aton(ip, &serverConfig->sin_addr)==0){
            printf("Ip non valido\n");
            exit(1);
        }   
        break;
    case 2:
        printf("Inserisci hostname\n");
        scanf("%s",hostname);
        p=gethostbyname(hostname);
        if(!p){
            herror("gethostbyname");
            exit(1);
        }
        serverConfig->sin_addr.s_addr=*(uint32_t*)(p->h_addr_list[0]);
        break;
    
    default:
        printf("Operazione annullata. Chiusura imminente.\n");
        exit(0);
        break;
    }
    printf("Inserisci porta\n");
    scanf("%u",&port);
    serverConfig->sin_port=htons(port);
}

int checkLoginStatus(char *msg){
    printf("Segnale: %s\n", msg);
    if(strcmp(msg,"~OKLOGIN")==0){
        printf("Login effettuato!\n");
        return 1;
    }
    else if(strcmp(msg,"~USRNOTEXISTS")==0){
        printf("L'utente non esiste\n");
    }
    else if(strcmp(msg, "~USRLOGGED") == 0){
                printf("L'utente è già loggato\n");
    }
    else if(strcmp(msg,"~NOVALIDPW")==0){
        printf("La password inserita non è corretta\n");
    }
    else if(strcmp(msg,"~SERVERISFULL")==0){
        printf("Il server è pieno\n");
    }
    else{
        printf("Qualcosa è andato storto\n");
    }
    return 0;
}

int combineStr(char *creds,char *username,char *password){
    strcpy(creds,username);
    strcat(creds,"\n");
    strcat(creds,password);
    return strlen(creds);
}

//Gestione del login nel client
char loginCred(int server_sd){
    char username[100];
    char password[100];
    char creds[200];
    char msg[20];
    int n;
    system("clear");
    write(server_sd,"~USRLOGIN",9);
    memset(creds,'\0',sizeof(creds));
    memset(msg,'\0',sizeof(msg));
    printf("Inserisci nome utente: ");
    getchar(); //scarico il buffer
    scanf("%[^\n]", username); 
    if(strstr(username," ")==NULL){
        printf("Per favore inserire password: ");
        getchar(); //scarico il buffer
        scanf("%[^\n]", password);
        if(strstr(password," ")==NULL){
            n=combineStr(creds,username,password);
            write(server_sd,&n,sizeof(int));
            write(server_sd,creds,strlen(creds));
            receiveSignal(server_sd, msg);
            if(checkLoginStatus(msg))
                return '1';
            else
                return '0';
            
        }
        else{
            printf("La password non può contenere spazi.\n");
            n=-1;
            write(server_sd,&n,sizeof(int));
        }
    }
    else{
        printf("Il nome utente non può contenere spazi.\n");
        n=-1;
        write(server_sd,&n,sizeof(int));
    }
    return '0';
    
}

//Gestione della registrazione nel client
void regCred(int server_sd){
    char username[100];
    char password[100];
    char creds[200];
    char msg[20];
    int n;   
    system("clear");         
    write(server_sd,"~USRSIGNUP",10);
    memset(creds,'\0',sizeof(creds));
    memset(msg,'\0',sizeof(msg));
    printf("Inserisci nome utente: ");
    getchar(); //scarico il buffer
    scanf("%[^\n]", username); 
    if(strstr(username," ")==NULL){
        printf("Per favore inserire password ");
        getchar(); //scarico il buffer
        scanf("%[^\n]", password);
        if(strstr(password," ")==NULL){
            n=combineStr(creds,username,password);
            write(server_sd,&n,sizeof(int));
            write(server_sd,creds,strlen(creds));
            receiveSignal(server_sd, msg);
            if(strcmp(msg,"~SIGNUPOK")==0){
                printf("Registrazione effettuata con successo!\n");
            }
            else{
                printf("Utente già registrato.\n");
            }
        }
        else{
            printf("La password non può contenere spazi.\n");
            n=-1;
            write(server_sd,&n,sizeof(int));
        }
    }
    else{
        printf("Il nome utente non può contenere spazi.\n");
        n=-1;
        write(server_sd,&n,sizeof(int));
    }
}

void printGuide(){
    char buffer[10];
    int nread;
    int fd=open("GameGuide.txt",O_RDONLY);
    if(fd<0){
        perror("Qualcosa è andato storto");
    }
    else{
        system("clear");
        while((nread=read(fd,buffer,5))>0){
            write(STDOUT_FILENO,buffer,nread);
        }
        close(fd);
    }
}

void printInfo(){
    char buffer[10];
    int nread;
    int fd=open("GameInfo.txt",O_RDONLY);
    if(fd<0){
        perror("Qualcosa è andato storto");
    }
    else{
        system("clear");
        while((nread=read(fd,buffer,3))>0){
            write(STDOUT_FILENO,buffer,nread);
        }
        close(fd);
    }
}

void homeClient(int server_sd){
    char scelta[30];
    char home[]="\n----PROGETTO LSO-GIOCO----\nBenvenuto,cosa vuoi fare?\n(1)Login\n(2)Registrati\n(3)Aiuto\n(4)Informazioni sul gioco\n(5)Esci\n\nScelta:";
    char log = '0';
    while(log != '1'){
        write(STDOUT_FILENO,home,sizeof(home));
        scanf("%s", scelta);
        if(strlen(scelta)==1){
            switch(scelta[0]){
                case '1':
                    log=loginCred(server_sd);
                    break;
                case '2':
                    regCred(server_sd);
                    break;
                case '3':
                    printGuide();
                    break;
                case '4':
                    printInfo();
                    break;
                case '5':
                    printf("Uscita...\n");
                    write(server_sd,"~USREXIT",8);
                    exit(0);
                    break;
                default:
                    printf("Scelta non valida, riprovare.\n");
                    break;
                
            }            
        }
        else{
            printf("Scelta non valida, riprovare.\n");
        }
    
        
    }
        game(server_sd);
}