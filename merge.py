with open('output/final.txt') as f:
    read_data = f.read().split()

max_1 = 0
max_2 = 0
max_3 = 0
max_4 = 0
max_5 = 0
max_web_1 = ""
max_web_2 = ""
max_web_3 = ""
max_web_4 = ""
max_web_5 = ""

for data in read_data:
    if "website" in data:
        hold = data
    else:
        if data > max_1:
            max_5 = max_4
            max_web_5 = max_web_4
            max_4 = max_3
            max_web_4 = max_web_3
            max_3 = max_2
            max_web_3 = max_web_2
            max_2 = max_1
            max_web_2 = max_web_1
            max_1 = data
            max_web_1 = hold
        elif data > max_2:
            max_5 = max_4
            max_web_5 = max_web_4
            max_4 = max_3
            max_web_4 = max_web_3
            max_3 = max_2
            max_web_3 = max_web_2
            max_2 = data
            max_web_2 = hold
        elif data > max_3:
            max_5 = max_4
            max_web_5 = max_web_4
            max_4 = max_3
            max_web_4 = max_web_3
            max_3 = data
            max_web_3 = hold
        elif data > max_4:
            max_5 = max_4
            max_web_5 = max_web_4
            max_4 = data
            max_web_4 = hold
        elif data > max_5:
            max_5 = data
            max_web_5 = hold


print "Most frequently viewed web sites (top 5):"           
print "1: " + max_web_1 + " was accessed " + max_1 + " times"
print "2: " + max_web_2 + " was accessed " + max_2 + " times"
print "3: " + max_web_3 + " was accessed " + max_3 + " times"
print "4: " + max_web_4 + " was accessed " + max_4 + " times"
print "5: " + max_web_5 + " was accessed " + max_5 + " times" 
