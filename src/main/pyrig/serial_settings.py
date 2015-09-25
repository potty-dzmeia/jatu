#! /usr/bin/python
import json
from org.lz1aq.pyrig_interfaces import I_Rig

class SerialSettings(I_Rig.I_SerialSettings):

    def __init__(self):
        self.baudrate_min = 0
        self.baudrate_max = 19200
        self.data_bits = 8
        self.stop_bits = 1
        self.parity_ = "none"                                                               #underscore added because of jython issue
        self.handshake_ = "none"                                                            #underscore added because of jython issue
        self.write_delay = 0  # Delay between each byte sent out, in milliseconds
        self.post_write_delay = 0  # Delay between each commands send out, in milliseconds
        self.timeout_ = 200  # Timeout, in milliseconds                                     #underscore added because of jython issue
        self.retry_ = 1  # Maximum number of retries if command fails (0 for no retry)      #underscore added because of jython issue

    def __str__(self):
        """Packs the class variables into a JSON formatted string"""
        jsonBlock = dict()
        jsonBlock["baudrate_min"] = self.baudrate_min
        jsonBlock["baudrate_max"] = self.baudrate_max
        jsonBlock["data_bits"] = self.baudrate_min
        jsonBlock["data_bits"] = self.data_bits
        jsonBlock["stop_bits"] = self.stop_bits
        jsonBlock["parity"] = self.parity
        jsonBlock["handshake"] = self.handshake
        jsonBlock["write_delay"] = self.write_delay
        jsonBlock["post_write_delay"] = self.post_write_delay
        jsonBlock["timeout"] = self.timeout
        jsonBlock["retry"] = self.retry

        return json.dumps(jsonBlock, indent=4)

    def getBauderate_Min(self):
        return self.baudrate_min

    def getBauderateMax(self):
        return self.baudrate_max

    def getDataBits(self):
        return self.data_bits

    def getStopBits(self):
        return self.stop_bits

    def getParity(self):
        return self.parity_

    def getHandshake(self):
        return self.handshake_

    def getWriteDelay(self):
        return self.write_delay

    def getPostWriteDelay(self):
        return self.post_write_delay

    def getTimeout(self):
        return  self.timeout_

    def getRetry(self):
        return self.retry_
