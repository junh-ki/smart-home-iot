import time
import psycopg2

# connect to the db
# TODO: Let's make this dynamically (argparse.ArgumentParser())
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
# TODO: formatting, auto id - test, auto timestamp (time.time() didn't work)
# ERROR: psycopg2.errors.DatatypeMismatch: column "timestamp" is of type timestamp without time zone but expression is of type numeric
# TODO: only when singleton is empty, it should include id, otherwise: change the values of all the fields other than id
# TODO: let's try pgadmin4 docker
cur.execute("insert into singleton (id, off_temperature, on_temperature, humidity, barometric_pressure, is_motion_detected) values (42, 20, 35, 30, 98473, True)")
cur.execute("select id, off_temperature from singleton")

rows = cur.fetchall()

for r in rows:
    print(f"id {r[0]} off_temperature {r[1]}")

# commit the transaction
db.commit()

# close the cursor
cur.close()

# close the connection
db.close()
