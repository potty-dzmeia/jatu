from radio import *
from serial_settings import SerialSettings
from encoded_transaction import EncodedTransaction
from decoded_transaction import DecodedTransaction
import utils
import logging
import logging.config


logging.config.fileConfig(utils.get_logging_config(), disable_existing_loggers=False)
logger = logging.getLogger(__name__)


class Elecraft(Radio):
    """
    Configuration script for Elecraft transceivers
    """

    #+--------------------------------------------------------------------------+
    #|  User configuration fields - change if needed                            |
    #+--------------------------------------------------------------------------+
    MANUFACTURER = "Elecraft"
    MODEL_NAME   = "None"

    # Get default serial port settings
    serial_settings = SerialSettings() # If different values than the default ones are need - uncomment and set to desired value
    serial_settings.baudrate_min_   = 4800
    serial_settings.baudrate_max_   = 38400
    serial_settings.stop_bits_      = SerialSettings.STOPBITS_TWO
    serial_settings.rts_            = SerialSettings.RTS_STATE_ON      # This is used to power the electronics
    # serial_settings.data_bits_    = SerialSettings.DATABITS_EIGTH
    # serial_settings.handshake_    = SerialSettings.HANDSHAKE_CTSRTS
    # serial_settings.parity_       = SerialSettings.PARITY_NONE
    # serial_settings.dtr_          = SerialSettings.DTR_STATE_NONE














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


    @classmethod
    def getAvailableModes(cls):
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


    @classmethod
    def encodeGetVfoMode(cls, vfo):
        """
        Gets the command with which we can tell the radio to send us the current mode

        :param vfo: The VFO for which we want the current mode
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        if vfo == Radio.VFO_A:
            result = "MD;"
        elif vfo == Radio.VFO_B:
            result = "MD$;"
        else:
            raise Exception("encodeSetVfoMode(): Set VFO_NONE is not supported")

        return EncodedTransaction(result, confirmation_expected=0)


    @classmethod
    def decode(cls, data):
        """
        Decodes information coming from an Elecraft radio.
        Converts strings coming from the radio into a JSON formatted string with the decoded transaction.

        Example of incoming command: "MD$1;" - which means that vfo 2 is in LSB mode

        :param data: Series of bytes from which we must extract the incoming command.
        :type data: array
        :return: Object containing the transaction and some additional control information
        :rtype: DecodedTransaction
        """

        # Find the character ";" which signals the end of the command
        data = data.tostring()
        end = data.find(';')

        # The incoming data does not contain one complete transaction...
        if end == -1:
            return DecodedTransaction(None, 0)

        json_result = cls.__parse(data[:end+1])


        # return the object with the decoded transaction and the amount of bytes that we have read from the supplied buffer(string)
        return DecodedTransaction(json_result, end+1)


    @classmethod
    def __parse(cls, trans):
        """
        Parses the string data into a meaningful JSON block containing the command coming from the radio

        This function actually calls the responsible parser depending on the incoming command code

        :param data: A single transaction string coming from the radio that we have to parse to a meaningful JSON block
        :type data: str
        :return: JSON formatted block containing the parsed data
        :rtype: str
        """
        result = ""

        for s in cls.parsers:
            if trans.startswith(s):  # if we have parser for the current command...
                fn = cls.parsers[s]
                result = getattr(fn, '__func__')(cls, trans) # call the responsible parser
                break

        if result == "":
            result = DecodedTransaction.createNotSupported(trans)

        logger.debug("input data: {0}".format(trans))
        logger.debug("parsed result: {0}".format(result))
        return result


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
                logger.info("returns = " + key)
                return key

        # In case of unknown mode integer
        return "none"


    @classmethod
    def __parse_frequency_vfo_a(cls, command):
        """
        Extracts the Frequency value from the command

        :param command: String starting of the type "FA00007000000;"
        :type command: str
        :return: JSON formatted block containing the parsed data
        :rtype: str
        """
        return DecodedTransaction.createFreq(command[2:-1].lstrip('0'), vfo=Radio.VFO_A)


    @classmethod
    def __parse_frequency_vfo_b(cls, command):
        """
        Extracts the Frequency value from the command

        :param command: String starting of the type "FB00007000000;"
        :type command: str
        :return: JSON formatted block containing the parsed data
        :rtype: str
        """
        return DecodedTransaction.createFreq(command[2:-1].lstrip('0'), vfo=Radio.VFO_B)


    @classmethod
    def __parse_mode(cls, command):
        """
        Extracts the Mode value from the command

        :param command: String starting of the type "MD1;" or "MD$1;"
        :type command: str
        :return: JSON formatted block containing the parsed data
        :rtype: str
        """
        if command[2] != '$':
            m = cls.__mode_from_byte_to_string(int(command[2]))
            logger.info("m = "+m)
            return DecodedTransaction.createMode(m, vfo=Radio.VFO_A)
        else:
            m = cls.__mode_from_byte_to_string(int(command[3]))
            logger.info("m = "+m)
            return DecodedTransaction.createMode(m, vfo=Radio.VFO_B)


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


    # Commands coming from the Elecraft that we can understand(parse)
    parsers = {"FA": __parse_frequency_vfo_a,       # VFO A frequency
               "FB": __parse_frequency_vfo_b,       # VFO B frequency
               "MD": __parse_mode,}                 # Operating mode

    #+--------------------------------------------------------------------------+
    #|   Elecraft command codes
    #+--------------------------------------------------------------------------+

    # Codes used for changing the mode
    mode_codes ={'lsb':     0x01,
                 'usb':     0x02,
                 'cw':      0x03,
                 'fm':      0x04,
                 'am':      0x05,
                 'rtty':    0x06,
                 'cwr':     0x07,
                 'rttyr':   0x09}


