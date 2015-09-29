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
        :param data: Additional data that the radio has send (e.g. "14,000,000")
        :type data: str
        :return: JSON formatted string holding the command and the data
        :rtype: str
        """
        if data is not None:
            if type(data) is not str:
                raise ValueError("Parameter 'data' should be a string")
        if command not in cls.supported_commands:
            raise ValueError("Unknown command")

        jsonBlock = dict()
        jsonBlock["command"] = command
        if data is not None:
            jsonBlock["data"] = data
        return json.dumps(jsonBlock, indent=4)


    @classmethod
    def createNotSupported(cls):
        """
        Creates a JSON formatted transaction indicating that the decoded command is not supported
        :return: The following string {"command": "not-supported"}
        :rtype: str
        """
        return cls.__create("not_supported")


    @classmethod
    def createPositiveCfm(cls):
        """
        Creates a JSON formatted transaction indicating that the decoded command is not supported
        :return: The following string {"command": "positive_cfm"}
        :rtype: str
        """
        return cls.__create("confirmation", str(1))


    @classmethod
    def createNegativeCfm(cls):
        """
        Creates a JSON formatted transaction indicating that the decoded command is not supported
        :return: The following string  {"command": "negative_cfm"}
        :rtype: str
        """
        return cls.__create("confirmation", str(0))


    @classmethod
    def createFreq(cls, freq):
        """
        Creates a JSON formatted transaction holding  frequency
        :param freq: frequncy
        :type freq: str
        :return: String of the type {"command": "frequency", "data": "14000100"}
        :rtype: str
        """
        return cls.__create("frequency", freq)


    @classmethod
    def createMode(cls, mode):
        """
        Creates a JSON formatted transaction holding  mode
        :param mode: A string describing the working mode of the radio
        :type mode: str
        :return: String of the type {"command": "mode", "data": "cw"}
        :rtype: str
        """
        if mode not in Radio.modes:
            raise ValueError("Unknown command")

        return cls.__create("mode", mode)

