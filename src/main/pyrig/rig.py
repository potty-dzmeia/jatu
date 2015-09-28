from org.lz1aq.pyrig_interfaces import I_Rig



class Rig(I_Rig):
    """
    A rig is an equipment which can be controlled with the help of commands
    send from the program to the rig. The Rig can also send data backwards.
    """

    
    
    @property
    def getManufacturer(self):
        """
        :return: The manufacturer of the rig - E.g. "Kenwood"
        :rtype: str
        """
        raise NotImplementedError("getManufacturer")


    @property
    def getModel(self):
        """
        :return: The model of the Rig - E.g. "IC-756PRO"
        :rtype: str
        """
        raise NotImplementedError("getModel")


    @property
    def getSerialPortSettings(self):
        """
        :return: The COM port settings that needs to be used when communicating with this Rig.
        :rtype: SerialSettings
        """
        raise NotImplementedError("getSerialPortSettings")


    @property
    def decode(self, string_of_bytes):
        """
        Decodes information coming from the Rig.
        Converts string of bytes coming from the rig into a JSON formatted command.

        :param string_of_bytes: Series of bytes from which we must extract the transaction. There is no guarantee
        that the first byte is the beginning of the transaction (i.e. there might be some trash in the beginning).
        :type string_of_bytes: str
        :return: Object containing the transaction and some additional control information
        :rtype: DecodedTransaction
        """
        raise NotImplementedError("decode")


    @property
    def encodeInit(self):
        """
        If a rig needs some initialization before being able to be used.
        
        :return: Initialization command that is to be send to the Rig
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_Init")


    @property
    def encodeCleanup(self):
        """
        If a rig needs some cleanup after being used.
        
        :return: Cleanup command that is to be send to the Rig
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_Cleanup")
    