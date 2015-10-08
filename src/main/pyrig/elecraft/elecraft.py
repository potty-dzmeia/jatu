from radio import *
from serial_settings import SerialSettings
from encoded_transaction import EncodedTransaction
from decoded_transaction import DecodedTransaction
import utils

class Elecraft(radio.Radio):
    """
    Configuration script for Elecraft transceivers
    """

    #+--------------------------------------------------------------------------+
    #|  User configuration fields - change if needed                               |
    #+--------------------------------------------------------------------------+
    MANUFACTURER = "Elecraft"
    MODEL_NAME = "None"

    # Get default serial port settings
    serial_settings = SerialSettings() # If different values than the default ones are need - uncomment and set to desired value
    serial_settings.baudrate_min_   = 4800
    serial_settings.baudrate_max_   = 38400
    serial_settings.stop_bits_      = SerialSettings.STOPBITS_TWO
    serial_settings.rts_            = SerialSettings.RTS_STATE_ON      # This is used to power the electronics
     # serial_settings.data_bits_   = SerialSettings.DATABITS_EIGTH
    # serial_settings.handshake_    = SerialSettings.HANDSHAKE_CTSRTS
    # serial_settings.parity_       = SerialSettings.PARITY_NONE
    #serial_settings.dtr_           = SerialSettings.DTR_STATE_NONE














    #+--------------------------------------------------------------------------+
    #|   End of user configuration fields
    #+--------------------------------------------------------------------------+


    @classmethod
    def getManufacturer(cls):
        """
        :return: The manufacturer of the rig - E.g. "Kenwood"
        :rtype: str
        """
        return cls.MANUFACTURER


    @classmethod
    def getModel(cls):
        """
        :return: The model of the Rig - E.g. "IC-756PRO"
        :rtype: str
        """
        return cls.MODEL_NAME

    @classmethod
    def getSerialPortSettings(cls):
        """
        Returns the serial settings to be used when connecting to this rig

        :return: [SerialSettings] object holding the serial port settings
        :rtype: SerialSettings
        """
        return cls.serial_settings



    # @classmethod
    # def __transaction(cls, command, sub_command=None, data=None):
    #     """
    #     Assembles an Elecraft specific transaction ready to be send to the transceiver
    #
    #     Protocol is the following:
    #     [preamble(0xFE), preamble(0xFE), ctrl_id(0xE0), civ-address, command, sub_command, data...., 0xFD]
    #
    #     :param command: Command code. E.g. 0x05 is set frequency)
    #     :type command: list
    #     :param sub_command: Sub-command code (optional)
    #     :type sub_command: int
    #     :param data: Additional data bytes(optional)
    #     :type data: list
    #     :return: The ready transaction bytes
    #     :rtype: list
    #     """
    #     transaction= [0xFE, 0xFE, 0xE0, cls.CIV_ADDRESS, command]
    #     if sub_command is not None:
    #         transaction.append(sub_command)
    #     if data is not None:
    #         transaction += data
    #     transaction.append(0xFD)
    #     return transaction



    @classmethod
    def encodeSetVfoFreq(cls, freq, vfo):
        """
        Gets the command with which we can tell an Elecraft radio to change frequency

        :param freq: Specifying the frequency. E.g. 7100000 for 7.1MHz
        :type freq: int
        :param vfo: The vfo for which we want to set the frequency
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        result = "F%c%011ld;"%(cls.__vfo_number_to_letter(vfo), freq)
        return EncodedTransaction(result, confirmation_expected=0)


    @classmethod
    def encodeGetVfoFreq(cls, vfo):
        """
        Gets the command with which we can tell the radio send us the current frequency

        :param vfo: For which VFO we want the mode
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        result = "F%c;"%(cls.__vfo_number_to_letter(vfo))
        return EncodedTransaction(result, confirmation_expected=0)



    @classmethod
    def encodeSetVfoMode(cls, mode, vfo):
        """
        Get the command that must be send to the radio in order to set mode (e.g. CW)

        :param mode: Specifies the mode - see Radio.MODES for expected values
        :type mode: str
        :param vfo: The vfo which mode must be changed - see Radio.VFO_....
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        new_mode = mode.lower()
        if not cls.mode_codes.__contains__(new_mode):
            raise ValueError("Unsupported mode: "+mode+"!")

        if vfo == Radio.VFO_A:
            result = "MD%d;"%(new_mode)
        elif vfo == Radio.VFO_B:
            result = "MD$%d;"%(new_mode)
        else:
            raise Exception("encodeSetVfoMode(): Set VFO_NONE is not supported")

        return EncodedTransaction(result, confirmation_expected=0)


    # @classmethod
    # def encodeGetVfoMode(cls, vfo):
    #     """
    #     Gets the command with which we can tell the radio to send us the current mode
    #
    #     :param vfo: The VFO for which we want the current mode
    #     :type vfo: int
    #     :return: Object containing transaction with some additional control settings
    #     :rtype: EncodedTransaction
    #     """
    #     result = cls.__transaction(0x04)
    #     return EncodedTransaction(bytearray(result).__str__(), confirmation_expected=0)
    #
    #
    # @classmethod
    # def decode(cls, string_of_bytes):
    #     """
    #     Decodes information coming from an Elecraft radio.
    #     Converts string of bytes coming from the radio into a JSON formatted string with the decoded transaction.
    #
    #     :param string_of_bytes: Series of bytes from which we must extract the transaction. There is no guarantee
    #     that the first byte is the beginning of the transaction (i.e. there might be some trash in the beginning).
    #     :type string_of_bytes: str
    #     :return: Object containing the transaction and some additional control information
    #     :rtype: DecodedTransaction
    #     """
    #     trans = bytearray(string_of_bytes)
    #
    #     # Find the beginning of the transaction
    #     trans_start_index = trans.find(cls.TRANS_START)
    #     if trans_start_index == -1:
    #         return DecodedTransaction(None, 0)
    #
    #     # Find the end of the transaction (must be after trans_start_index)
    #     trans_end_index = trans.find(cls.TRANS_END, trans_start_index)
    #     if trans_end_index == -1:
    #         return DecodedTransaction(None, 0)
    #
    #     cmd_idx = trans_start_index + 4  # get the index of the command
    #
    #     if trans[cmd_idx] == cls.CFM_POSITIVE:      # <------------------------- positive confirm
    #         result = DecodedTransaction.createPositiveCfm()
    #
    #     elif trans[cmd_idx] == cls.CFM_NEGATIVE:    # <------------------------- negative confirm
    #         result = DecodedTransaction.createNegativeCfm()
    #
    #     elif trans[cmd_idx] == cls.SEND_FREQ:       # <------------------------- frequency
    #         freq = cls.__decodeFrequency(trans[(cmd_idx + 1):trans_end_index])
    #         result = DecodedTransaction.createFreq(freq)
    #
    #     elif trans[cmd_idx] == cls.SEND_MODE:       # <------------------------- mode
    #         mode = cls.__decodeMode(trans[cmd_idx+1])
    #         result = DecodedTransaction.createMode(mode)
    #
    #     else:                                       # <------------------------- not-supported
    #         result = DecodedTransaction.createNotSupported(utils.getListInHex(trans[trans_start_index:trans_end_index+1]))
    #
    #     # return the object with the decoded transaction and the amount of bytes that we have read from the supplied buffer(string)
    #     return DecodedTransaction(result, trans_end_index+1)



    @classmethod
    def __mode_from_byte_to_string(cls, mode):
        """
        Returns a string describing the current working mode
        :param mode: Number describing the mode see cls.mode_codes
        :type mode: int
        :return: String describing the working mode (e.g. "CW"). "none" if we couldn't recognize the mode.
        :rtype: str
        """

        # Convert the "mode" to valid string
        for key, value in cls.mode_codes.items():
            if mode == value:
                return key

        # In case of unknown mode integer
        return "none"



    # @classmethod
    # def __decodeFrequency(cls, frequency):
    #     """
    #     Converts the bytearray with the frequency to a string representation
    #     Example: 0x00, 0x02, 0x10, 0x14, 0x00 is converted to "14,100,200"
    #     :param frequency: Bytearray containing the frequency in a little endian format bcd format
    #     :type frequency: bytearray
    #     :return: Frequency in string format
    #     :rtype: str
    #     """
    #     return utils.fromBcd(frequency).__str__()

    @classmethod
    def getModes(cls):
        """
        The function returns a string with all the modes that the radio supports.
        Example: "cw ssb lsb"

        :return: A string with the supported modes. Each mode is separated from the next with space.
        :rtype: str
        """
        return " ".join("%s" % key for key in cls.mode_codes)


    # @classmethod
    # def getAvailableBands(cls):
    #     """
    #     The function returns a string with all the bands that it supports.
    #     Example: "3.5 7 14"
    #
    #     :return: A string with the supported bands. Each band is separated from the next with space.
    #     :rtype: str
    #     """
    #     return " ".join("%s" % key for key in cls.mode_codes)



    @classmethod
    def __vfo_number_to_letter(cls, vfo_number):
        """
        Converts VFO number to a letter that can be used in the communication with the radio
        Example: 0-->"A"; 1-->"B"
        :param vfo_number: VFO number (starting from 0)
        :type vfo_number: int
        :return: The VFO letter
        :rtype: str
        """
        if vfo_number == 0:
            return "A"
        if vfo_number == 1:
            return "B"
        else:
            raise Exception("Not allowed VFO number")

    #+--------------------------------------------------------------------------+
    #|   Elecraft command codes
    #+--------------------------------------------------------------------------+

    # TRANS_START = bytearray([0xFE, 0xFE, 0xE0, CIV_ADDRESS])  # Trans\s send by the Icom starts with: 0xFE 0xFE 0xE0 CIV
    # TRANS_END = bytearray([0xFD])  # Transactions send by the Icom ends with: 0xFD
    #
    # SEND_FREQ = 0x00        # send by the Icom when frequency has changed
    # SEND_MODE = 0x01        # send by the Icom whe mode has changed
    # CFM_POSITIVE = 0xFB     # Positive confirmation send by the Icom radio
    # CFM_NEGATIVE = 0xFA     # Negative confirmation send by the Icom radio
    #
    #
    # Codes used for changing the mode
    mode_codes ={'lsb':     0x01,
                 'usb':     0x02,
                 'cw':      0x03,
                 'fm':      0x04,
                 'am':      0x05,
                 'rtty':    0x06,
                 'cwr':     0x07,
                 'rttyr':   0x09}


