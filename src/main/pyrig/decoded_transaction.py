from org.lz1aq.pyrig_interfaces import I_Rig
from radio import Radio
import json

class DecodedTransaction(I_Rig.I_DecodedTransaction):
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
    #|   Helper Class methods for creating the JSON string                      |
    #+--------------------------------------------------------------------------+
    @classmethod
    def __create(cls, command, data=None):
        """
        Creates a JSON formatted string holding the decoded data coming from the radio
        :param command: The command that was send by the radio (e.g. "frequency")
        :type command: str
        :param data: JSON object holding additional data that the radio has send (e.g. "14,000,000")
        :type data: dict
        :return: JSON formatted string holding the command and the data
        :rtype: str
        """
        if data is not None:
            if type(data) is not dict:
                raise ValueError("Parameter 'data' should be dictionary")
        if command not in cls.supported_commands:
            raise ValueError("Unknown command")

        jsonBlock = dict()
        jsonBlock[command] = data
        return json.dumps(jsonBlock, indent=4)


    @classmethod
    def createNotSupported(cls, data=None):
        """
        Creates a JSON formatted transaction indicating that the decoded command is not supported
        :param data: The transaction that couldn't be decoded in hex format
        :type data: str
        :return: The following string {"not-supported": {"not-supported":"the data that couldn't be decoded in hex format"}}
        :rtype: str
        """
        if data is not None:
            json_command_content = dict()
            json_command_content["not_supported"] = data;
        return cls.__create("not_supported", json_command_content)


    @classmethod
    def createPositiveCfm(cls):
        """
        Creates a JSON formatted transaction indicating that the decoded command is not supported
        :return: The following string {"confirmation": {"confirmation": "1"}}
        :rtype: str
        """
        json_command_content = dict()
        json_command_content["confirmation"] = "1";
        return cls.__create("confirmation", json_command_content)


    @classmethod
    def createNegativeCfm(cls):
        """
        Creates a JSON formatted transaction indicating that the decoded command is not supported
        :return: The following string  {"confirmation": {"confirmation": "0"}}
        :rtype: str
        """
        json_command_content = dict()
        json_command_content["confirmation"] = "0";
        return cls.__create("confirmation", json_command_content)


    @classmethod
    def createFreq(cls, freq, vfo=None):
        """
        Creates a JSON formatted transaction holding  frequency
        :param freq: frequency
        :type freq: str
        :param vfo: VFO that changed frequency (optional). Possible values are "0", "1" and so on...
        :type vfo: int
        :return: String of the type {"frequency": {"frequency": "14195000", "vfo": "0"}}
        :rtype: str
        """
        json_command_content = dict()
        json_command_content["frequency"] = freq;
        if vfo is not None:
            json_command_content = dict()
            json_command_content["vfo"] = str(vfo)
        return cls.__create("frequency", json_command_content)


    @classmethod
    def createMode(cls, mode, vfo=None):
        """
        Creates a JSON formatted transaction holding  mode
        :param mode: A string describing the working mode of the radio
        :type mode: str
        :param vfo: VFO that changed mode (optional). Possible values are "0", "1" and so on...
        :type vfo: int
        :return: String of the type {"mode": {"mode": "cw", "vfo": "0"}}
        :rtype: str
        """
        json_command_content = dict()
        json_command_content["mode"] = mode;
        if vfo is not None:
            json_command_content = dict()
            json_command_content["vfo"] = str(vfo);
        return cls.__create("mode", json_command_content)

