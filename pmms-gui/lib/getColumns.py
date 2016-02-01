import sqlite3
__author__ = 'gexin'

conn = sqlite3.connect("C:\\cets_pmms\\db\\pmms.db")
print "Opened database successfully";
cursor = conn.execute("SELECT * FROM PMMS_RESULT;")
for columnName in cursor.description:
    print(columnName[0])
# for row in cursor:
#     print "ID = ", row[0]
#     print "NAME = ", row[1]
#     print "ADDRESS = ", row[2]
#     print "SALARY = ", row[3], "\n"

print "Operation done successfully";
conn.close()
