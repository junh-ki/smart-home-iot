import argparse
import time
import psycopg2

# python3 test.py --host localhost -d sensors -u postgres --password password --port 5432

def getConfig():
    parser = argparse.ArgumentParser()
    parser.add_argument("--host", metavar='\b', help="host", type=str) # "localhost"
    parser.add_argument("-d", "--database", metavar='\b', help="name of database", type=str) # "sensors"
    parser.add_argument("-u", "--user", metavar='\b', help="name of database user", type=str) # "postgres"
    parser.add_argument("--password", metavar='\b', help="password of database", type=str) # "password"
    parser.add_argument("--port", metavar='\b', help="port of database", type=int) # "5432"
    args = parser.parse_args()
    return args

# get configurations
args = getConfig()

# connect to the db
# TODO: Let's make this dynamically (argparse.ArgumentParser())
db = psycopg2.connect(
    host = args.host,
    database = args.database,
    user = args.user,
    password = args.password,
    port = args.port
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

print("done!")
