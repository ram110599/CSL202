import sys
from bs4 import BeautifulSoup

#print "The file is taken via command line ", sys.argv[1]
#list to store all the href links
l = []
with open(sys.argv[1]) as fp:
    soup = BeautifulSoup(fp,"lxml")
    for link in soup.find_all('a'):
    	l.append(link.get('href'))
    	print link.text
    #print(link.get('href'))

for item in l:
	print item