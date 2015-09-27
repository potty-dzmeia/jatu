#! /usr/bin/python

import radio
from serial_settings import SerialSettings
from encoded_transaction import EncodedTransaction
from decoded_transaction import DecodedTransaction
import utils

class Icom(radio.Radio):
    """
    Configuration script for Icom transceivers
    """

    #+--------------------------------------------------------------------------+
    #|  User configuration fields - change if needed                           |
    #+--------------------------------------------------------------------------+

    # Get default serial port settings
    serial_settings = SerialSettings()
    # If different value than the default one is need - uncomment and set to desired value
    # serial_settings.baudrate_min_ = 2400
    # serial_settings.baudrate_max_ = 19200
    # serial_settings.data_bits_ = 8
    # serial_settings.stop_bits_ = 1
    # serial_settings.handshake_ = "None"       # possible values 'None', 'XON_XOFF' and 'CTS_RTS'
    # serial_settings.parity_ = "None"          # possible values 'None', 'Even', 'Odd', 'Mark', 'Space'

    # The address of the Icom transceiver. Value of 0x5c is good for 756Pro
    CIV_ADDRESS = 0x5c















    #+--------------------------------------------------------------------------+
    #|   End of user configuration fields
    #+--------------------------------------------------------------------------+


    @classmethod
    def getSerialPortSettings(cls):
        """
        Returns the serial settings to be used when connecting to this rig

        :return: [SerialSettings] object holding the serial port settings
        """
        return cls.serial_settings



    @classmethod
    def __transaction(cls, command, sub_command=None, data=None):
        """
        Assembles an Icom specific transaction ready to be send to the transceiver

        Protocol is the following:
        [preamble(0xFE), preamble(0xFE), ctrl_id(0xE0), civ-address, command, sub_command, data...., 0xFD]

        :param command:     [int] Command code. E.g. 0x05 is set frequency)
        :param sub_command: [int] Sub-command code (optional)
        :param data:        [list] Additional data bytes(optional)
        :return:            [list] The ready transaction bytes
        """
        transaction= [0xFE, 0xFE, 0xE0, cls.CIV_ADDRESS, command]
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

        :param freq:  [int] specifying the frequency. E.g. 7100000 for 7.1MHz
        :param vfo:   [int] the vfo for which we want to set the frequency
        :return:      [SerialTransaction] Object with the command with some additional settings
        """
        result = cls.__transaction(0x05, data=utils.toBcd(freq,10))
        return EncodedTransaction(bytearray(result).__str__())



    @classmethod
    def encodeSetMode(cls, mode, vfo):
        """
        Get the command that must be send to the radio in order to set mode (e.g. CW)

        :param mode:  [String] specifies the mode - see Radio.modes
        :param vfo:   [int] The vfo which mode must be changed
        :return:      [String] of bytes containing the command
        """
        if not cls.mode_codes.__contains__(mode):
            raise ValueError("Unsupported mode: "+mode+"!")

        result = cls.__transaction(0x06, sub_command=cls.mode_codes[mode])
        return EncodedTransaction(bytearray(result).__str__())



    TRANS_START = bytearray([0xFE, 0xFE, 0xE0, CIV_ADDRESS]) # Trans\s send by the Icom starts with: 0xFE 0xFE 0xE0 CIV
    TRANS_END = bytearray([0xFD]) # Transactions send by the Icom ends with: 0xFD

    @classmethod
    def decode(cls, stringOfBytes):
        """
        Decodes information coming from an Icom radio.
        Converts string of bytes coming from the radio into a JSON formatted string with the decoded transaction.

        :param stringOfBytes: [String] Series of bytes from which we must extract the transaction. There is no guarantee
        that the first byte is the beginning of the transaction (i.e. there might be some trash in the beginning).
        :return: [DecodedTransaction] object containing the transaction and some additional control information
        """
        trans = bytearray(stringOfBytes)

        trans_start_index = trans.find(cls.TRANS_START)   # get the beginning of the transaction
        trans_end_index = trans.find(cls.TRANS_END)       # get the end of the transaction

        # No complete transaction was found
        # ---------------------------------
        if trans_start_index == -1 or trans_end_index == -1:
            return DecodedTransaction(None, 0)
        

        raise NotImplementedError("Functionality missing")








    # Icom control codes used for changing the mode
    mode_codes ={'LSB':     0x00,
                 'USB':     0x01,
                 'AM':      0x02,
                 'CW':      0x03,
                 'RTTY':    0x04,
                 'FM':      0x05,
                 'CWR':     0x07,
                 'RTTYR':   0x08}


