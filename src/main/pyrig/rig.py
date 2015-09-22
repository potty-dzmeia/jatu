#! /usr/bin/python


from org.lz1aq.rig_interfaces import I_Rig
import json


class SerialPortSettings:

    def __init__(self):
        self.baudrate_min = 0
        self.baudrate_max = 19200
        self.data_bits = 8
        self.stop_bits = 1
        self.parity = "none"
        self.handshake = "none"
        self.write_delay = 0  # Delay between each byte sent out, in milliseconds
        self.post_write_delay = 0  # Delay between each commands send out, in milliseconds
        self.timeout = 200  # Timeout, in milliseconds
        self.retry = 3  # Maximum number of retries if command fails, 0 to diMsable

    def toJson(self):
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



class Rig(I_Rig):
    """
    A rig is an equipment which can be controlled with the help of commands
    send from the program to the rig. The Rig can also send data backwards.
    """

    @property
    def getManufacturer(self):
        """
        Returns a string specifying the manufacturer of the rig - E.g. "Kenwood"
        """
        raise NotImplementedError("getManufacturer")


    @property
    def getModel(self):
        """
        Returns a string specifying the model of the Rig - E.g. "IC-756PRO"
        """
        raise NotImplementedError("getModel")


    @property
    def getSerialPortSettings(self):
        """
        Returns a JSON formatted string describing the COM port settings that
        needs to be used when communicating with this Rig.
        """
        raise NotImplementedError("getSerialPortSettings")


    @property
    def decode(self, stringOfBytes):
        """
        Decodes information coming from the Rig.
        Converts string of bytes comming from the rig into a JSON formatted
        command.

        :return: json formatted command.
        """
        raise NotImplementedError("decode")


    @property
    def encode_Init(self):
        """
        If a rig needs some initialization before being able to be used.
        
        :return: Initialization command that is to be send to the Rig
        """
        raise NotImplementedError("encode_Init")


    @property
    def encode_Cleanup(self):
        """
        If a rig needs some cleanup after being used.
        
        :return: Cleanup command that is to be send to the Rig 
        """
        raise NotImplementedError("encode_Cleanup")
    