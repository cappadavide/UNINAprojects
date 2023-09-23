*version 9.1 615035590
u 49
HB? 3
DSTM? 3
U? 2
? 6
@libraries
@analysis
.TRAN 1 0 0 0
+0 2ns
+1 1000ns
.OP 0 
@targets
@attributes
@translators
a 0 u 13 0 0 0 hln 100 PCBOARDS=PCB
a 0 u 13 0 0 0 hln 100 PSPICE=PSPICE
a 0 u 13 0 0 0 hln 100 XILINX=XILINX
@setup
unconnectedPins 0
connectViaLabel 0
connectViaLocalLabels 0
NoStim4ExtIFPortsWarnings 1
AutoGenStim4ExtIFPorts 1
@index
pageloc 1 0 4788 
@status
n 0 118:04:15:18:08:38;1526400518 e 
s 2832 118:04:15:18:20:53;1526401253 e 
*page 1 0 970 720 iA
@ports
@parts
part 27 7404 320 210 u
a 0 sp 11 0 40 40 hln 100 PART=7404
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=DIP14
a 0 s 0:13 0 0 0 hln 100 GATE=A
a 0 a 0:13 0 0 0 hln 100 PKGREF=U1
a 0 ap 9 0 28 8 hln 100 REFDES=U1A
block 2 blocksym2 220 230 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=L1
a 0 xp 9 0 0 0 hln 100 REFDES=L1
*symbol blocksym2
d 
@type p
@attributes
a 0 sp 9 0 0 0 hln 100 REFDES=HB?
a 0 s 0 0 0 0 hln 100 PART=
@views
a 0 u 13 0 0 0 hln 100 DEFAULT=Latch D.sch
@pins
p 2 20 0 hct 100 CLK l 20 0 d
a 0 s 0:13 0 0 0 hln 100 ERC=i
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 21 -2 hln 100 PIN=
p 2 2 55 hlb 100 D l 0 50 h
a 0 s 0:13 0 0 0 hln 100 ERC=i
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 1 48 hln 100 PIN=
p 2 38 35 hrb 100 Q l 40 30 u
a 0 s 0:13 0 0 0 hln 100 ERC=o
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 41 28 hln 100 PIN=
p 2 38 65 hrb 100 Q- l 40 60 u
a 0 s 0:13 0 0 0 hln 100 ERC=o
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 41 58 hln 100 PIN=
@graphics 40 100 0 0 10
r 0 0 0 40 100
*end blocksym
block 13 blocksym2 330 230 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=L2
a 0 xp 9 0 0 0 hln 100 REFDES=L2
*symbol blocksym13
d 
@type p
@attributes
a 0 sp 9 0 0 0 hln 100 REFDES=HB?
a 0 s 0 0 0 0 hln 100 PART=
@views
a 0 u 13 0 0 0 hln 100 DEFAULT=Latch D.sch
@pins
p 2 20 0 hct 100 CLK l 20 0 d
a 0 s 0:13 0 0 0 hln 100 ERC=i
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 21 -2 hln 100 PIN=
p 2 2 55 hlb 100 D l 0 50 h
a 0 s 0:13 0 0 0 hln 100 ERC=i
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 1 48 hln 100 PIN=
p 2 38 35 hrb 100 Q l 40 30 u
a 0 s 0:13 0 0 0 hln 100 ERC=o
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 41 28 hln 100 PIN=
p 2 38 65 hrb 100 Q- l 40 60 u
a 0 s 0:13 0 0 0 hln 100 ERC=o
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 41 58 hln 100 PIN=
@graphics 40 100 0 0 10
r 0 0 0 40 100
*end blocksym
part 26 DigClock 350 190 d
a 0 x 0:13 0 0 0 hln 100 PKGREF=CLK
a 1 xp 9 0 0 -2 hln 100 REFDES=CLK
a 0 u 0 0 0 20 hln 100 ONTIME=100ns
a 0 u 0 0 0 30 hln 100 OFFTIME=100ns
part 37 STIM1 200 280 h
a 0 u 0 0 0 90 hln 100 COMMAND2=+75ns 1
a 0 x 0:13 0 0 0 hln 100 PKGREF=DATA
a 0 xp 9 0 1 -2 hln 100 REFDES=DATA
a 0 u 0 0 0 80 hln 100 COMMAND1=0ns 0
a 0 u 0 0 0 100 hln 100 COMMAND3=+100ns 0
a 0 u 0 0 0 110 hln 100 COMMAND4=+50ns 1
a 0 u 0 0 0 70 hln 100 TIMESTEP=2ns
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 300 95 hrn 100 PAGENO=1
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
part 39 nodeMarker 390 260 h
a 0 s 0 0 0 0 hln 100 PROBEVAR=
a 0 a 0 0 4 22 hlb 100 LABEL=2
part 41 nodeMarker 390 290 h
a 0 s 0 0 0 0 hln 100 PROBEVAR=
a 0 a 0 0 4 22 hlb 100 LABEL=3
part 44 nodeMarker 300 260 h
a 0 s 0 0 0 0 hln 100 PROBEVAR=
a 0 a 0 0 4 22 hlb 100 LABEL=5
part 38 nodeMarker 350 200 h
a 0 s 0 0 0 0 hln 100 PROBEVAR=CLK:1
a 0 a 0 0 4 22 hlb 100 LABEL=1
part 43 nodeMarker 210 280 v
a 0 s 0 0 0 0 hln 100 PROBEVAR=DATA:pin1
a 0 a 0 0 4 22 hlb 100 LABEL=4
@conn
w 12
a 0 up 0:33 0 0 0 hln 100 LVL=
s 260 290 280 290 11
a 0 up 33 0 270 289 hct 100 LVL=
w 4
a 0 up 0:33 0 0 0 hln 100 LVL=
s 270 210 240 210 33
a 0 up 33 0 255 209 hct 100 LVL=
s 240 210 240 230 35
w 23
a 0 sr 0 0 0 0 hln 100 LABEL=Q
a 0 up 0:33 0 0 0 hln 100 LVL=
s 370 260 390 260 22
a 0 sr 3 0 380 258 hcn 100 LABEL=Q
a 0 up 33 0 380 259 hct 100 LVL=
s 390 260 400 260 40
w 25
a 0 sr 0 0 0 0 hln 100 LABEL=Q-
a 0 up 0:33 0 0 0 hln 100 LVL=
s 370 290 390 290 24
a 0 sr 3 0 380 288 hcn 100 LABEL=Q-
a 0 up 33 0 380 289 hct 100 LVL=
s 390 290 400 290 42
w 10
a 0 up 0:33 0 0 0 hln 100 LVL=
a 0 sr 0 0 0 0 hln 100 LABEL=N1
s 260 260 300 260 16
a 0 up 33 0 285 259 hct 100 LVL=
a 0 sr 3 0 285 258 hcn 100 LABEL=N1
s 310 260 310 280 18
s 310 280 330 280 20
s 300 260 310 260 45
w 15
a 0 sr 0 0 0 0 hln 100 LABEL=CLK
a 0 up 0:33 0 0 0 hln 100 LVL=
s 350 200 350 210 46
a 0 sr 3 0 352 200 hln 100 LABEL=CLK
s 350 190 350 200 28
a 0 up 33 0 352 201 hlt 100 LVL=
s 350 210 350 230 32
s 350 210 320 210 30
w 6
a 0 up 0:33 0 0 0 hln 100 LVL=
a 0 sr 0 0 0 0 hln 100 LABEL=Data
s 210 280 220 280 47
a 0 sr 3 0 210 278 hcn 100 LABEL=Data
s 200 280 210 280 7
a 0 up 33 0 210 279 hct 100 LVL=
@junction
j 350 230
+ p 13 CLK
+ w 15
j 330 280
+ p 13 D
+ w 10
j 320 210
+ p 27 A
+ w 15
j 350 210
+ w 15
+ w 15
j 270 210
+ p 27 Y
+ w 4
j 300 260
+ p 44 pin1
+ w 10
j 370 260
+ p 13 Q
+ w 23
j 390 260
+ p 39 pin1
+ w 23
j 370 290
+ p 13 Q-
+ w 25
j 390 290
+ p 41 pin1
+ w 25
j 240 230
+ p 2 CLK
+ w 4
j 220 280
+ p 2 D
+ w 6
j 260 260
+ p 2 Q
+ w 10
j 260 290
+ p 2 Q-
+ w 12
j 350 200
+ p 38 pin1
+ w 15
j 200 280
+ p 37 pin1
+ w 6
j 350 190
+ p 26 1
+ w 15
j 210 280
+ p 43 pin1
+ w 6
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
