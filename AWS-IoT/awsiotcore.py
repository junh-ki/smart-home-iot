"""
Prior to using this script, AWSIoTPythonSDK library should be installed first:

    $ pip3 install AWSIoTPythonSDK
	
"""

import time
from AWSIoTPythonSDK.MQTTLib import AWSIoTMQTTClient

def helloworld(self, params, packet):
    print('Received Message from AWS IoT Core')
    print('Topic: ' + packet.topic)
    print('Payload: ', packet.payload)

myMQTTClient = AWSIoTMQTTClient('JunClientID') # random key, if another connection using the same key is opened the previous one is auto closed by AWS
myMQTTClient.configureEndpoint('a2c6m8mqv60jjy-ats.iot.us-east-2.amazonaws.com', 8883)
myMQTTClient.configureCredentials('/home/pi/AWS-IoT/root-ca.pem', '/home/pi/AWS-IoT/private.pem.key', '/home/pi/AWS-IoT/certificate.pem.crt')

myMQTTClient.configureOfflinePublishQueueing(-1) # Infinite offline publish queueing
myMQTTClient.configureDrainingFrequency(2) # Draining: 2Hz
myMQTTClient.configureConnectDisconnectTimeout(10) # 10 sec
myMQTTClient.configureMQTTOperationTimeout(5) # 5 sec
print('Initiating IoT Core Topic ...')
myMQTTClient.connect()

# ##### Subscriber #####
#
# myMQTTClient.subscribe('home/helloworld', 1, helloworld)
# 
# while True:
#     time.sleep(5)

##### Publisher #####
print('Publishing Message from RPI')
myMQTTClient.publish(
    topic='home/helloworld',
    QoS=1,
    payload='{\'Message\': \'Message By RPI\'}'
)
