from pymongo import MongoClient


client = MongoClient(
    "mongodb://admin:admin@ds021010.mlab.com:21010/code_troopers")

db = client.code_troopers
result = db.iot_pi.delete_many({})
