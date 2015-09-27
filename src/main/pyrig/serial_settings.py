#! /usr/bin/python
import json
from org.lz1aq.pyrig_interfaces import I_Rig

class SerialSettings(I_Rig.I_SerialSettings):

    def __init__(self):
        # underscore added because of jython issue
        self.baudrate_min_ = 2400
        self.baudrate_max_ = 19200
        self.data_bits_ = 8
        self.stop_bits_ = 1
        self.parity_ = "None"       # possible values 'None', 'Even', 'Odd', 'Mark', 'Space'
        self.handshake_ = "none"    # possible values 'None', 'XON_XOFF' and 'CTS_RTS'

    def __str__(self):
        """Packs the class variables into a JSON formatted string
        :rtype : json formatted string
        """
        jsonBlock = dict()
        jsonBlock["baudrate_min"] = self.baudrate_min_
        jsonBlock["baudrate_max"] = self.baudrate_max_
        jsonBlock["data_bits"] = self.baudrate_min_
        jsonBlock["data_bits"] = self.data_bits_
        jsonBlock["stop_bits"] = self.stop_bits_
        jsonBlock["parity"] = self.parity
        jsonBlock["handshake"] = self.handshake
        return json.dumps(jsonBlock, indent=4)

    def getBauderate_Min(self):
        return self.baudrate_min_

    def getBauderateMax(self):
        return self.baudrate_max_

    def getDataBits(self):
        return self.data_bits_

    def getStopBits(self):
        return self.stop_bits_

    def getParity(self):
        return self.parity_

    def getHandshake(self):
        return self.handshake_
