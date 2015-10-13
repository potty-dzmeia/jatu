from radio import *
from serial_settings import SerialSettings
from encoded_transaction import EncodedTransaction
from decoded_transaction import DecodedTransaction
import misc_utils
import logging
import logging.config


logging.config.fileConfig(misc_utils.get_logging_config(), disable_existing_loggers=False)
logger = logging.getLogger(__name__)


class Elecraft(Radio):
    """
    Configuration file for Elecraft transceivers
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
    serial_settings.rts_            = SerialSettings.RTS_STATE_OFF
    serial_settings.dtr_          = SerialSettings.DTR_STATE_OFF
    # serial_settings.data_bits_    = SerialSettings.DATABITS_EIGTH
    # serial_settings.handshake_    = SerialSettings.HANDSHAKE_CTSRTS
    # serial_settings.parity_       = SerialSettings.PARITY_NONE


    # The AI meta-command can be used to enable automatic responses from the K3 to a computer in response to K3 front panel control changes by the operator.
    AUTO_INFO_MODE = "AI1;" # Possible values are: "AI0;", "AI1;", "AI2;", "AI3;"












    #+--------------------------------------------------------------------------+
    #|   End of user configuration fields                                       |
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


    #+--------------------------------------------------------------------------+
    #|  Encode methods below                                                    |
    #+--------------------------------------------------------------------------+


    @classmethod
    def encodeInit(cls):
        """
        If the radio needs some initialization before being able to be used.

        :return: Initialization command that is to be send to the Rig
        :rtype: EncodedTransaction
        """
        return list([EncodedTransaction(cls.AUTO_INFO_MODE)])


    @classmethod
    def encodeCleanup(cls):
        """
        If the radio needs some cleanup after being used.

        :return: Cleanup command that is to be send to the Rig
        :rtype: EncodedTransaction
        """
        logger.info("encodeCleanup() not implemented")
        return list()


    @classmethod
    def encodeSetFreq(cls, freq, vfo):
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
        logger.debug("returns: {0}".format(result))
        return list([EncodedTransaction(result)])


    @classmethod
    def encodeGetFreq(cls, vfo):
        """
        Gets the command with which we can tell the radio send us the current frequency

        :param vfo: For which VFO we want the mode
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        result = "F%c;"%(cls.__vfo_number_to_letter(vfo))
        logger.debug("returns: {0}".format(result))
        return list([EncodedTransaction(result)])


    @classmethod
    def encodeSetMode(cls, mode, vfo):
        """
        Get the command that must be send to the radio in order to set mode (e.g. CW)

        :param mode: Specifies the mode - see Radio.MODES for expected values
        :type mode: str
        :param vfo: The vfo which mode must be changed - see Radio.VFO_....
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        mode = mode.lower()
        if not cls.mode_codes.__contains__(mode):
            raise ValueError("Unsupported mode: " + mode + " !")

        if vfo == Radio.VFO_A:
            result = "MD%d;"%(cls.mode_codes[mode])
        elif vfo == Radio.VFO_B:
            result = "MD$%d;"%(cls.mode_codes[mode])
        else:
            logger.warning("encodeSetMode(): Set VFO_NONE is not supported")

        logger.debug("returns: {0}".format(result))
        return list([EncodedTransaction(result)])


    @classmethod
    def encodeGetMode(cls, vfo):
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
            result = ""
            logger.warning("encodeSetMode(): Get VFO_NONE is not supported")

        logger.debug("returns: {0}".format(result))
        return list([EncodedTransaction(result)])


    #+--------------------------------------------------------------------------+
    #|  Decode methods below                                                    |
    #+--------------------------------------------------------------------------+


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
    def __parse_info(cls, command):
        """
        Extract the frequency and mode from the IF command.

        The format of the incoming command is the following:
        IF[f]*****+yyyyrx*00tmvspbd1*; where the fields are defined as follows:

        [f]     Operating frequency, excluding any RIT/XIT offset (11 digits; see FA command format)
        *       represents a space (BLANK, or ASCII 0x20)
        +       either "+" or "-" (sign of RIT/XIT offset)
        yyyy    RIT/XIT offset in Hz (range is -9999 to +9999 Hz when computer-controlled)
        r       1 if RIT is on, 0 if off
        x       1 if XIT is on, 0 if off
        t       1 if the K3 is in transmit mode, 0 if receive
        m       operating mode (see MD command)
        v       receive-mode VFO selection, 0 for VFO A, 1 for VFO B
        s       1 if scan is in progress, 0 otherwise
        p       1 if the transceiver is in split mode, 0 otherwise
        b       Basic RSP format: always 0; K2 Extended RSP format (K22): 1 if present IF response is due to a band change; 0 otherwise
        d       Basic RSP format: always 0; K3 Extended RSP format (K31): DATA sub-mode, if applicable (0=DATA A, 1=AFSK A, 2= FSK D, 3=PSK D)

        :param command: The "IF" command
        :type command: str
        :return: JSON formatted block containing the parsed data
        :rtype: str
        """

        return DecodedTransaction.createFreq(command[2:-1].lstrip('0'), vfo=Radio.VFO_B)


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
    #|   Private methods                                                        |
    #+--------------------------------------------------------------------------+


    @classmethod
    def __mode_from_byte_to_string(cls, mode):
        """
        Returns a string describing the current working mode
        :param mode: Integer describing the mode see cls.mode_codes
        :type mode: int
        :return: String describing the working mode cls.mode_codes
        :rtype: str
        """

        # Convert the "mode" to valid string
        for key, value in cls.mode_codes.items():
            if mode == value:
                logger.info("returns = " + key)
                return key

        # In case of unknown mode integer
        return "none"




    # Commands coming from the Elecraft that we can understand(parse)
    parsers = { "FA": __parse_frequency_vfo_a,       # VFO A frequency
                "FB": __parse_frequency_vfo_b,       # VFO B frequency
                "MD": __parse_mode,                  # Operating mode
                "IF": __parse_info,}                 # IF (Transceiver Information; GET only)


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


