first screen->

two entries -
row, col

second screen->
Draw a grid with row and col

represent the grid in memory with arr[][]

for every change pos event->
generate a new location for charX, charY

new location for charX = arr[m][n]
0<=m<col
0<=n<row
m = random(0,col)
n = random(0, row)

new location for charY = arr[m2][n2]
0<=m2<col where m2 != m
0<=n2<row where n2 != n

m2 = random(0, col)
while(m2==m){
    m2 = random(0, col)
}

n2 = random(0, row)
while(n2==n){
n2 = random(0, row)
}

update the location and enable the UI