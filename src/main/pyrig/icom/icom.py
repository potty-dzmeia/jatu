#! /usr/bin/python

import radio
import rig

class Icom(radio.Radio):
    """
    Configuration script for Icom transcievers
    """

    serial_settings = rig.SerialPortSettings()
    civ_address = 0x5c  # The address of the Icom transceiver. Value of 0x5c is good for 756Pro

    @classmethod
    def getSerialPortSettings(cls):
        """
        Returns a JSON formatted string describing the COM port settings that
        needs to be used when communicating with this Rig.
        """
        return cls.serialSettings.toJson()

    @classmethod
    def encodeSetFreq(cls, freq, vfo):
        """
        Get the command for frequency change

        :return: String of bytes ready to be send to the Rig
        """
        return "set freq command: " + freq.__str__() + "  ;for VFO: " + vfo.__str__()

