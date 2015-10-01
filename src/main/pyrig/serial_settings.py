import json
from org.lz1aq.pyrig_interfaces import I_Rig

class SerialSettings(I_Rig.I_SerialSettings):

    PARITY_NONE, PARITY_EVEN, PARITY_ODD, PARITY_MARK, PARITY_SPACE = 'None', 'Even', 'Odd', 'Mark', 'Space'
    STOPBITS_ONE, STOPBITS_ONE_POINT_FIVE, STOPBITS_TWO = (1, 1.5, 2)
    DATABITS_FIVE, DATABITS_SIX, DATABITS_SEVEN, DATABITS_EIGTH = (5, 6, 7, 8)
    HANDSHAKE_NONE, HANDSHAKE_XONXOFF, HANDSHAKE_CTSRTS = 'None', 'XonXoff', 'CtsRts'

    def __init__(self):
        # underscore added because of a jython issue
        self.baudrate_min_ = 2400
        self.baudrate_max_ = 19200
        self.data_bits_ = self.DATABITS_EIGTH
        self.stop_bits_ = self.STOPBITS_ONE
        self.parity_ = self.PARITY_NONE
        self.handshake_ = self.HANDSHAKE_NONE

    def __str__(self):
        """Packs the class variables into a JSON formatted string
        :rtype : str
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
        """
        :rtype: int
        """
        return self.baudrate_min_


    def getBauderateMax(self):
        """
        :rtype: int
        """
        return self.baudrate_max_


    def getDataBits(self):
        """
        :rtype: int
        """
        return self.data_bits_

    def getStopBits(self):
        """
        :rtype: int
        """
        return self.stop_bits_


    def getParity(self):
        """
        :rtype: str
        """
        return self.parity_


    def getHandshake(self):
        """
        :rtype: str
        """
        return self.handshake_
