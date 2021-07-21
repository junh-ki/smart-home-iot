import psycopg2

# connect to the db
db = psycopg2.connect(
    host = "localhost",
    database = "sensors",
    user = "postgres",
    password = "password",
    port = 5432
)

# cursor
cur = db.cursor()

# execute queries
cur.execute("insert into employees (id, name) values (%s, %s)", (1, "Jun"))
cur.execute("select id, name from employees")

rows = cur.fetchall()

for r in rows:
    print(f"id {r[0]} name {r[1]}")

# commit the transaction
db.commit()

# close the cursor
cur.close()

# close the connection
db.close()
