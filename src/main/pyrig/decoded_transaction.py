from org.lz1aq.pyrig_interfaces import I_DecodedTransaction
from radio import Radio
import json
import misc_utils
import logging
import logging.config


logging.config.fileConfig(misc_utils.get_logging_config(), disable_existing_loggers=False)
logger = logging.getLogger(__name__)



class DecodedTransaction(I_DecodedTransaction):
    """
    Contains the decoded transaction coming from the rig together
    with some additional control information
    """

    # Supported commands coming from the radio
    supported_commands = ["not_supported",  # When the transaction contained unknown data
                          "confirmation",   # Radio has sent positive for negative confirmation
                          "frequency",      # Radio has sent new frequency
                          "mode"]           # Radio has sent new mode


    def __init__(self, transaction, bytes_read):
        """
        :param transaction: JSON formatted string with the decoded transaction
        :type transaction: str
        :param bytes_read: The amount of bytes that were read from the supplied buffer in order to decode the transacti
        :type bytes_read: int
        :return:
        """
        self.transaction_ = transaction
        self.bytes_read_ = bytes_read


    def getTransaction(self):
        """
        :return: JSON formatted string with the decoded transaction
        :rtype: str
        """
        return self.transaction_


    def getBytesRead(self):
        """
        :return: The amount of bytes that were read from the supplied buffer in order to decode the transaction
        :rtype: int
        """
        return self.bytes_read_


    #+--------------------------------------------------------------------------+
    #|  Helper functions for building decoded transactions                      |
    #+--------------------------------------------------------------------------+


    @classmethod
    def insertNotSupported(cls, dest, data=""):
        """
        Inserts a decoded command coming from the radio into the supplied dictionary
        :param data: The transaction that couldn't be decoded in hex format
        :type data: str
        :param dest: The dict to which the following item will be added:
                     "not-supported":"the data that couldn't be decoded in hex format"
        :type dest: dict
        """
        dest["not_supported"] = misc_utils.get_as_hex_string(data)


    @classmethod
    def insertPositiveCfm(cls, dest):
        """
        Inserts a decoded command coming from the radio into the supplied dictionary
        :param dest: The dict to which the following item will be added: "confirmation": "1"
        :type dest: dict
        """
        dest["confirmation"] = "1"


    @classmethod
    def insertNegativeCfm(cls, dest):
        """
        Inserts a decoded command coming from the radio into the supplied dictionary
        :param dest: The dict to which the following item will be added: "confirmation": "0"
        :type dest: dict
        """
        dest["confirmation"] = "0"


    @classmethod
    def insertFreq(cls, dest, freq, vfo=Radio.VFO_NONE):
        """
        Inserts a decoded command coming from the radio into the supplied dictionary

        :param freq: frequency
        :type freq: str
        :param vfo: VFO that changed frequency (optional). Possible values are "0", "1" and so on...
        :type vfo: int
        :param dest: The dict to which the following item will be added: "frequency": {"frequency": "14195000", "vfo": "0"}
        :type dest: dict
        """
        sub = dict()
        sub["frequency"] = freq
        sub["vfo"] = str(vfo)
        dest["frequency"] = sub


    @classmethod
    def insertMode(cls, dest, mode, vfo=Radio.VFO_NONE):
        """
        Inserts a decoded command coming from the radio into the supplied dictionary

        :param mode: A string describing the working mode of the radio
        :type mode: str
        :param vfo: VFO that changed mode (optional). Possible values are "0", "1" and so on...
        :type vfo: int
        :param dest: The dict to which the following item will be added: "mode": {"mode": "cw", "vfo": "0"}
        :type dest: dict
        """
        sub = dict()
        sub["mode"] = mode
        sub["vfo"] = str(vfo)
        dest["mode"] = sub


    @classmethod
    def toJson(cls, dictionary):
        """
        Converts the supplied dict to JSON formatted string
        :param dictionary:  dict to be converted to JSON
        :type dictionary: dict
        :return: JSON formatted string
        :rtype: str
        """
        json.dumps(dictionary, indent=4)