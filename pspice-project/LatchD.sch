*version 9.1 264995852
u 50
U? 4
HB? 2
@libraries
@analysis
@targets
@attributes
@translators
@setup
unconnectedPins 0
connectViaLabel 0
connectViaLocalLabels 0
NoStim4ExtIFPortsWarnings 1
AutoGenStim4ExtIFPorts 1
@index
pageloc 1 0 3054 
@status
c 118:04:15:18:08:13;1526400493
*page 1 0 970 720 iA
@ports
port 3 IF_IN 120 200 h
a 1 sr 3 0 19 8 hcn 100 LABEL=D
port 2 IF_IN 120 270 U
a 1 sr 3 0 19 8 hcn 100 LABEL=CLK
port 4 IF_OUT 470 200 h
a 1 sr 3 0 31 8 hcn 100 LABEL=Q
port 5 IF_OUT 470 270 h
a 1 xr 3 0 31 8 hcn 100 LABEL=Q-
@parts
part 8 7404 210 280 h
a 0 sp 11 0 40 40 hln 100 PART=7404
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=DIP14
a 0 s 0:13 0 0 0 hln 100 GATE=A
a 0 a 0:13 0 0 0 hln 100 PKGREF=U3
a 0 ap 9 0 28 8 hln 100 REFDES=U3A
part 6 7408 280 190 h
a 0 sp 11 0 40 50 hln 100 PART=7408
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=DIP14
a 0 s 0:13 0 0 0 hln 100 GATE=A
a 0 a 0:13 0 0 0 hln 100 PKGREF=U1
a 0 ap 9 0 40 0 hln 100 REFDES=U1A
part 7 7408 280 260 h
a 0 sp 11 0 40 50 hln 100 PART=7408
a 0 s 0:13 0 0 0 hln 100 PKGTYPE=DIP14
a 0 s 0:13 0 0 0 hln 100 GATE=A
a 0 a 0:13 0 0 0 hln 100 PKGREF=U2
a 0 ap 9 0 40 0 hln 100 REFDES=U2A
block 33 blocksym33 410 180 h
a 0 x 0:13 0 0 0 hln 100 PKGREF=LatchSR
a 0 xp 9 0 0 0 hln 100 REFDES=LatchSR
*symbol blocksym33
d 
@type p
@attributes
a 0 sp 9 0 0 0 hln 100 REFDES=HB?
a 0 s 0 0 0 0 hln 100 PART=
@views
a 0 u 13 0 0 0 hln 100 DEFAULT=LatchSR.sch
@pins
p 2 58 95 hrb 100 Q- l 60 90 u
a 0 s 0:13 0 10 0 hln 100 ERC=o
a 0 s 0:13 0 10 0 hln 100 FLOAT=n
a 0 s 1 0 61 88 hln 100 PIN=
p 2 58 25 hrb 100 Q l 60 20 u
a 0 s 0:13 0 10 0 hln 100 ERC=o
a 0 s 0:13 0 10 0 hln 100 FLOAT=n
a 0 s 1 0 61 18 hln 100 PIN=
p 2 2 25 hlb 100 S l 0 20 h
a 0 s 0:13 0 0 0 hln 100 ERC=i
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 1 18 hln 100 PIN=
p 2 2 95 hlb 100 R l 0 90 h
a 0 s 0:13 0 0 0 hln 100 ERC=i
a 0 s 0:13 0 0 0 hln 100 FLOAT=n
a 0 s 1 0 1 88 hln 100 PIN=
@graphics 60 110 0 0 10
r 0 0 0 60 110
*end blocksym
part 1 titleblk 970 720 h
a 1 s 13 0 350 10 hcn 100 PAGESIZE=A
a 1 s 13 0 180 60 hcn 100 PAGETITLE=
a 1 s 13 0 340 95 hrn 100 PAGECOUNT=1
a 1 s 13 0 300 95 hrn 100 PAGENO=1
@conn
w 10
s 120 200 130 200 9
s 270 200 270 190 11
s 270 190 280 190 13
s 130 200 270 200 17
s 130 200 130 280 15
s 130 280 210 280 18
w 21
s 260 280 280 280 20
w 23
s 120 270 150 270 22
s 180 270 180 260 24
s 180 260 280 260 26
s 150 270 180 270 30
s 150 270 150 210 28
s 150 210 280 210 31
w 36
s 470 260 470 270 34
w 37
s 470 190 470 200 35
w 47
a 0 sr 0 0 0 0 hln 100 LABEL=Set
s 350 200 410 200 46
a 0 sr 3 0 380 198 hcn 100 LABEL=Set
w 49
a 0 sr 0 0 0 0 hln 100 LABEL=Reset
s 350 270 410 270 48
a 0 sr 3 0 380 268 hcn 100 LABEL=Reset
@junction
j 120 200
+ s 3
+ w 10
j 280 190
+ p 6 A
+ w 10
j 130 200
+ w 10
+ w 10
j 210 280
+ p 8 A
+ w 10
j 280 280
+ p 7 B
+ w 21
j 260 280
+ p 8 Y
+ w 21
j 120 270
+ s 2
+ w 23
j 280 260
+ p 7 A
+ w 23
j 150 270
+ w 23
+ w 23
j 280 210
+ p 6 B
+ w 23
j 470 270
+ s 5
+ w 36
j 470 200
+ s 4
+ w 37
j 470 200
+ p 33 Q
+ s 4
j 470 270
+ p 33 Q-
+ s 5
j 470 270
+ p 33 Q-
+ w 36
j 470 200
+ p 33 Q
+ w 37
j 350 200
+ p 6 Y
+ w 47
j 410 200
+ p 33 S
+ w 47
j 350 270
+ p 7 Y
+ w 49
j 410 270
+ p 33 R
+ w 49
@attributes
a 0 s 0:13 0 0 0 hln 100 PAGETITLE=
a 0 s 0:13 0 0 0 hln 100 PAGENO=1
a 0 s 0:13 0 0 0 hln 100 PAGESIZE=A
a 0 s 0:13 0 0 0 hln 100 PAGECOUNT=1
@graphics
