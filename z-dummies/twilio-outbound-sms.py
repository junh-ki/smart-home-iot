"""
Prior to using this script, twilio library should be installed first:

    $ pip3 install twilio
	
"""

# Imports
from twilio.rest import Client

# Configurations
account_sid = 'ACc52aed1a81b0f26298ad038f470e2ce5'
auth_token = '092718eb556dc03892b89052413082ca'

# Setting the client with the configurations
client = Client(account_sid, auth_token)

# Creating the message
client.messages.create(
    to = '+4915205633279',
    from_ = '+16106164032',
    body = 'Hello World!'
)
