#! /usr/bin/python

import radio
import rig
import utils

class Icom(radio.Radio):
    """
    Configuration script for Icom transcievers
    """

    #-------------------------------------------------------------
    # Configuration fields below - change if needed
    #-------------------------------------------------------------
    # Get default serial port settings
    serial_settings = rig.SerialPortSettings()
    # The address of the Icom transceiver. Value of 0x5c is good for 756Pro
    civ_address = 0x5c
    #-------------------------------------------------------------



    @classmethod
    def getSerialPortSettings(cls):
        """
        Returns a JSON formatted string describing the COM port settings that
        needs to be used when communicating with this Rig.
        """
        return cls.serialSettings.toJson()



    @classmethod
    def __transaction(cls, command, sub_command=None, data=None):
        """
        Assembles a transaction ready to be send to an Icom transceiver

        Protocol is the following:
        [preamble(0xFE), preamble(0xFE), ctrl_id(0xE0), civ-address, command, sub_command, data...., 0xFD]

        :param command:     [Integer] Command code. E.g. 0x05 is set frequency)
        :param sub_command: [Integer] Sub-command code (optional)
        :param data:        [list of bytes] Additional data (optional)
        :return:            [list of bytes] The ready transaction
        """
        transaction= [0xFE, 0xFE, 0xE0, cls.civ_address, command]
        if sub_command is not None:
            transaction.append(sub_command)
        if data is not None:
            transaction += data
        transaction.append(0xFD)
        return transaction



    @classmethod
    def encodeSetFreq(cls, freq, vfo):
        """
        Gets the command with which we can tell an Icom radio to change frequency

        :param freq: integer specifying the frequency. E.g. 7100000 for 7.1MHz
        :param vfo: the vfo for which we want to set the frequency
        :return: String of bytes containing the command
        """
        bytes = cls.__transaction(0x05, data=utils.toBcd(freq,10))
        return bytearray(bytes).__str__()



    @classmethod
    def encodeSetMode(cls, mode, vfo):
        """
        Get the command that must be send to the radio in order to set mode (e.g. CW)

        :param mode: (String) specifying the mode - see Radio.modes
        :param vfo: The vfo which mode must be changed
        :return: The command (String of bytes)
        """
        if not cls.mode_codes.__contains__(mode):
            raise ValueError("Unsupported mode!")

        bytes = cls.__transaction(0x06, sub_command=cls.mode_codes[mode])
        return bytearray(bytes).__str__()




    # Icom control codes used for changing the mode
    mode_codes ={'LSB':     0x00,
                 'USB':     0x01,
                 'AM':      0x02,
                 'CW':      0x03,
                 'RTTY':    0x04,
                 'FM':      0x05,
                 'CWR':     0x07,
                 'RTTYR':   0x08}


