from org.lz1aq.pyrig_interfaces import I_Rig

class DecodedTransaction(I_Rig.I_DecodedTransaction):
    """
    Contains the decoded transaction coming from the rig together
    with some additional control information
    """

    # Commands coming from the radio
    supported_commands = ["not-supported",  # When the transaction contained unknown data
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
    #|   Class methods                                                          |
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
        if command not in cls.supported_commands:
            raise ValueError("Unknown command")


        jsonBlock = dict()
        jsonBlock["command"] =  command
        if data is not None:
            jsonBlock["data"] = data
        return jsonBlock.dumps(jsonBlock, indent=4)


    @classmethod
    def createNotSupported(cls):
        return cls.create("not-supported")

    @classmethod
    def createPositiveCfm(cls):
        return cls.create("positive_cfm")

    @classmethod
    def createNegativeCfm(cls):
        return cls.create("negative_cfm")

    @classmethod
    def createFreq(cls, freq):
        return cls.create("frequency", freq)

    @classmethod
    def createFreq(cls, mode):
        return cls.create("mode", mode)

