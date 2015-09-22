#! /usr/bin/python

import radio
import rig
import utils

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
    def _transaction(cls, command, data):
        """
        Assembles a transaction(command) ready to be send to an Icom transceiver

        Protocol is the following:
        [preamble, preamble, civ, ctrl_id, command]

        :param command: The command that we would like to execute
        :param data: Additional data in case the command supports it
        :return: The transaction (list of bytes)
        """
        transaction= [0xfe,0xfe,cls.civ_address,0xe0,command]
        if len(data):
            transaction += data
        transaction.append(0xfd)
        return transaction



    @classmethod
    def encodeSetFreq(cls, freq, vfo):
        """
        Gets the command with which we can tell an Icom radio to change frequency

        :param freq: integer specifying the frequency. E.g. 7100000 for 7.1MHz
        :param vfo: the vfo for which we want to set the frequency
        :return: String of bytes containing the command
        """
        bytes = cls._transaction(0x05, utils.toBcd(freq,10))
        return bytearray(bytes).__str__()

